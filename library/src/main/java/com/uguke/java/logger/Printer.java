package com.uguke.java.logger;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Collection;
import java.util.Date;
import java.util.Hashtable;
import java.util.Map;

/**
 * 功能描述：
 * @author LeiJue
 * @date 2018/12/18
 */
class Printer {

    /** 制表符四角 **/
    static final String [] TABLE_CORNER = {"╔", "╗", "╝", "╚"};
    /** 制表符边 **/
    static final String [] TABLE_SIDE = {"╠", "╣", "║"};
    /** 制表符线 **/
    static final String TABLE_LINE = "═";
    /** 间隔 **/
    static final String TABLE_INDENT = "\u3000";

    static final String [] LANG_TIME = {"日期：", "Time: "};
    static final String [] LANG_THREAD = {"线程：", "Thread: "};
    static final String [] LANG_INDEX = {"索引：", "Index: "};
    static final String [] LANG_KEY = {"键：", "Key: "};
    static final String [] LANG_VALUE = {"值：", "Value: "};
    static final String [] LANG_RESULT = {"遍历结果：\n", "Traversal result:\n"};
    static final String ENTER = System.getProperty("line.separator");

    private StringBuilder mBuilder;
    /** 当前日志等级 **/
    private ThreadLocal<Level> mCurrentLevel;
    /** 当前方法数 **/
    private ThreadLocal<Integer> mCurrentCount;
    /** 当前Tag **/
    private ThreadLocal<String> mCurrentTag;
    /** 日志适配器 **/
    private Map<Class, BaseAdapter> mAdapters;

    private Printer() {
        mBuilder = new StringBuilder();
        mCurrentTag = new ThreadLocal<>();
        mCurrentLevel = new ThreadLocal<>();
        mCurrentCount = new ThreadLocal<>();
        mAdapters = new Hashtable<>();
    }

    static class Holder {
        static final Printer INSTANCE = new Printer();
    }

    static Printer getInstance() {
        return Holder.INSTANCE;
    }

    Printer t(String tag) {
        mCurrentTag.remove();
        mCurrentTag.set(tag);
        return this;
    }

    Printer t(int methodCount) {
        mCurrentCount.remove();
        mCurrentCount.set(methodCount);
        return this;
    }

    Printer t(String tag, int methodCount) {
        return t(tag).t(methodCount);
    }

    private synchronized void log(Level level, Object msg, Throwable t) {
        // 保存当前日志等级
        mCurrentLevel.remove();
        mCurrentLevel.set(level);
        for (Class clazz : mAdapters.keySet()) {
            BaseAdapter adapter = mAdapters.get(clazz);
            // 获取格式化策略
            LoggerStrategy strategy = adapter.getStrategy();
            // 如果打印日志等级不够则不打印
            if (level.mCode < strategy.getLevel().mCode) {
                continue;
            }

            // 获取边框
            String top = Utils.getTop(strategy);
            String bottom = Utils.getBottom(strategy);
            String content = toContent(msg, strategy.getLang());
            String tag = Utils.tag(strategy.getTag(), mCurrentTag.get());

            // 绘制上边框
            adapter.log(level, tag, top);
            // 绘制头部
            logHeader(adapter, tag);
            // 绘制内容
            logContent(adapter, tag, content);
            // 绘制异常内容
            logThrowable(adapter, tag, t);
            // 绘制下边框
            adapter.log(level, tag, bottom);
        }
    }

    void addLogAdapter(BaseAdapter adapter) {
        mAdapters.put(adapter.getClass(), adapter);
    }

    void removeLogAdapter(Class clazz) {
        mAdapters.remove(clazz);
    }

    void removeAllLogAdapter() {
        mAdapters.clear();
    }

    void d(Object msg, Object... args) {
        log(Level.D, Utils.format(msg, args), null);
    }

    void d(Object msg, Throwable t, Object... args) {
        log(Level.D, Utils.format(msg, args), t);
    }

    void i(Object msg, Object... args) {
        log(Level.I, Utils.format(msg, args), null);
    }

    void i(Object msg, Throwable t, Object... args) {
        log(Level.I, Utils.format(msg, args), t);
    }

    void w(Object msg, Object... args) {
        log(Level.W, Utils.format(msg, args), null);
    }

    void w(Object msg, Throwable t, Object... args) {
        log(Level.W, Utils.format(msg, args), t);
    }

    void e(Object msg, Object... args) {
        log(Level.E, Utils.format(msg, args), null);
    }

    void e(Object msg, Throwable t, Object... args) {
        log(Level.E, Utils.format(msg, args), t);
    }

    private void logHeader(BaseAdapter adapter, String tag) {
        // 格式化策略
        LoggerStrategy strategy = adapter.getStrategy();
        int lang = strategy.getLang();
        // 日志等级
        Level level = mCurrentLevel.get();
        // 方法数
        int methodCount =  mCurrentCount.get() == null ? strategy.getMethodCount() : mCurrentCount.get();
        // 获取内容分割线
        String divider = Utils.getDivider(strategy);
        // 获取当前该线程的堆栈转储堆栈跟踪元素的数组
        StackTraceElement[] trace = Thread.currentThread().getStackTrace();
        //是否显示日期及线程信息
        // 生成线程信息
        String thread = TABLE_SIDE[2] + TABLE_INDENT + LANG_THREAD[lang] +
                Thread.currentThread().getName();
        if (adapter instanceof DiskAdapter) {
            // 生成日期信息
            String date = TABLE_SIDE[2] + TABLE_INDENT + LANG_TIME[lang] +
                    strategy.getDateFormat().format(new Date());
            adapter.log(level, tag, date);
            if (strategy.isHasThread()) {
                adapter.log(level, tag, thread);
            }
            adapter.log(level, tag, divider);
        } else if (strategy.isHasThread()) {
            adapter.log(level, tag, thread);
            adapter.log(level, tag, divider);
        }

        // 获取方法偏移数
        int stackOffset = 0;
        for (int i = strategy.getMethodOffset(); i < trace.length; i++) {
            StackTraceElement e = trace[i];
            String name = e.getClassName();
            if (!name.equals(Printer.class.getName()) &&
                    !name.equals(Logger.class.getName())) {
                --i;
                stackOffset = i;
                break;
            }
        }
        //与当前堆栈轨迹对应的方法计数可能超过堆栈轨迹，修剪计数。
        if (methodCount + stackOffset > trace.length) {
            methodCount = trace.length - stackOffset - 1;
        }

        String head = "";
        for (int i = methodCount; i > 0; i--) {
            int stackIndex = i + stackOffset;
            if (stackIndex >= trace.length) {
                continue;
            }
            String name = trace[stackIndex].getClassName();
            name = name.substring(name.lastIndexOf(".") + 1);
            mBuilder.setLength(0);
            mBuilder.append(TABLE_SIDE[2])
                    .append(TABLE_INDENT)
                    .append(head)
                    .append(name)
                    .append(".")
                    .append(trace[stackIndex].getMethodName())
                    .append(" (")
                    .append(trace[stackIndex].getFileName())
                    .append(":")
                    .append(trace[stackIndex].getLineNumber())
                    .append(")");
            head += TABLE_INDENT;
            adapter.log(level, tag, mBuilder.toString());
        }

        if (methodCount > 0) {
            adapter.log(level, tag, divider);
        }
    }

    private void logContent(BaseAdapter adapter, String tag, String content) {
        // 将文本内容按\n分割开
        String[] lines = content.split(ENTER);
        // 格式化策略
        LoggerStrategy strategy = adapter.getStrategy();
        // 日志等级
        Level level = mCurrentLevel.get();
        for (String line : lines) {
            // 可打印的字节长度
            int length = strategy.getLength() * 2;
            String newLine = line;
            // 如果不为空则循环打印
            while (!Utils.isEmpty(newLine)) {
                // 获取可打印长度内的字符串
                String sub = Utils.sub(newLine, length);
                // 如果为空说明打印完成
                if (sub == null) {
                    break;
                }
                adapter.log(level, tag, TABLE_SIDE[2] + TABLE_INDENT + sub);
                newLine = newLine.replace(sub, "");
            }
        }
    }

    private void logThrowable(BaseAdapter adapter, String tag, Throwable t) {
        if (t == null) {
            return;
        }
        // 格式化策略
        LoggerStrategy strategy = adapter.getStrategy();
        // 日志等级
        Level level = mCurrentLevel.get();
        // 获取内容分割线
        String divider = Utils.getDivider(strategy);

        String exception = null;
        try {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            t.printStackTrace(pw);
            pw.flush();
            exception = sw.toString();
            pw.close();
            sw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 异常信息直接原样打印
        if (exception != null && exception.length() != 0) {
            // 将文本内容按\n分割开
            String[] lines = exception.split(ENTER);
            adapter.log(level, tag, divider);
            for (String line : lines) {
                adapter.log(level, tag, TABLE_SIDE[2] + TABLE_INDENT + line);
            }
        }
    }

    private String toContent(Object obj, int lang) {
        if (obj == null) {
            return "";
        }
        mBuilder.setLength(0);
        Class clazz = obj.getClass();
        if (obj instanceof Collection) {
            int index = 0;
            Collection c = (Collection) obj;
            mBuilder.append(LANG_RESULT[lang]);
            for (Object o : c) {
                toItem(index, null, o, lang);
                index ++;
            }
        } else if (obj instanceof Map) {
            int index = 0;
            Map map = (Map) obj;
            mBuilder.append(LANG_RESULT[lang]);
            for (Object key : map.keySet()) {
                toItem(index, key, map.get(key), lang);
                index++;
            }
        } else if (clazz.isArray()) {
            mBuilder.append(LANG_RESULT[lang]);
            if (clazz.equals(boolean [].class)) {
                boolean [] array = (boolean []) obj;
                for (int i = 0; i < array.length; i++) {
                    toItem(i, null, array[i], lang);
                }
            } else if (clazz.equals(byte [].class)) {
                byte [] array = (byte []) obj;
                for (int i = 0; i < array.length; i++) {
                    toItem(i, null, array[i], lang);
                }
            }  else if (clazz.equals(char [].class)) {
                char [] array = (char []) obj;
                for (int i = 0; i < array.length; i++) {
                    toItem(i, null, array[i], lang);
                }
            } else if (clazz.equals(int [].class)) {
                int [] array = (int []) obj;
                for (int i = 0; i < array.length; i++) {
                    toItem(i, null, array[i], lang);
                }
            } else if (clazz.equals(short [].class)) {
                short [] array = (short []) obj;
                for (int i = 0; i < array.length; i++) {
                    toItem(i, null, array[i], lang);
                }
            } else if (clazz.equals(long [].class)) {
                long [] array = (long []) obj;
                for (int i = 0; i < array.length; i++) {
                    toItem(i, null, array[i], lang);
                }
            } else if (clazz.equals(float [].class)) {
                float [] array = (float []) obj;
                for (int i = 0; i < array.length; i++) {
                    toItem(i, null, array[i], lang);
                }
            } else if (clazz.equals(double [].class)) {
                double [] array = (double []) obj;
                for (int i = 0; i < array.length; i++) {
                    toItem(i, null, array[i], lang);
                }
            } else {
                Object [] array = (Object []) obj;
                for (int i = 0; i < array.length; i++) {
                    toItem(i, null, array[i], lang);
                }
            }
        } else {
            mBuilder.append(String.valueOf(obj));
        }
        return mBuilder.toString();
    }

    private void toItem(int index, Object key, Object value, int lang) {
        mBuilder.append(LANG_INDEX[lang])
                .append(index)
                .append("  ");
        if (key != null) {
            mBuilder.append(LANG_KEY[lang])
                    .append(String.valueOf(key))
                    .append("  ");
        }
        mBuilder.append(LANG_VALUE[lang])
                .append(String.valueOf(value))
                .append(ENTER);
    }

}
