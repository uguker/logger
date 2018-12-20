package com.uguke.java.logger;

import com.uguke.java.logger.adapter.BaseAdapter;
import com.uguke.java.logger.strategy.FormatStrategy;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Collection;
import java.util.Hashtable;
import java.util.Map;

/**
 * 功能描述：
 * @author LeiJue
 * @date 2018/12/18
 */
class Printer {

    private StringBuilder mBuilder;
    /** 当前日志等级 **/
    private ThreadLocal<Integer> mCurrentLevel;
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

    private synchronized void log(int level, Object msg, Throwable t) {
        // 保存当前日志等级
        mCurrentLevel.remove();
        mCurrentLevel.set(level);
        for (Class clazz : mAdapters.keySet()) {
            BaseAdapter adapter = mAdapters.get(clazz);
            // 获取格式化策略
            FormatStrategy strategy = adapter.getStrategy();
            // 如果打印日志等级不够则不打印
            if (level < strategy.getLevel()) {
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

    void d(String msg, Object... args) {
        log(0, Utils.format(msg, args), null);
    }

    void d(String msg, Throwable t, Object... args) {
        log(0, Utils.format(msg, args), t);
    }

    void i(String msg, Object... args) {
        log(1, Utils.format(msg, args), null);
    }

    void i(String msg, Throwable t, Object... args) {
        log(1, Utils.format(msg, args), t);
    }

    void w(String msg, Object... args) {
        log(2, Utils.format(msg, args), null);
    }

    void w(String msg, Throwable t, Object... args) {
        log(2, Utils.format(msg, args), t);
    }

    void e(String msg, Object... args) {
        log(3, Utils.format(msg, args), null);
    }

    void e(String msg, Throwable t, Object... args) {
        log(3, Utils.format(msg, args), t);
    }

    private void logHeader(BaseAdapter adapter, String tag) {
        // 格式化策略
        FormatStrategy strategy = adapter.getStrategy();
        // 表格样式
        int table = strategy.getTable();
        int lang = strategy.getLang();
        // 日志等级
        int level = mCurrentLevel.get();
        // 方法数
        int methodCount =  mCurrentCount.get() == null ? strategy.getMethodCount() : mCurrentCount.get();
        // 获取内容分割线
        String divider = Utils.getDivider(strategy);
        // 获取当前该线程的堆栈转储堆栈跟踪元素的数组
        StackTraceElement[] trace = Thread.currentThread().getStackTrace();
        //是否显示线程信息
        if (strategy.isHasThread()) {
            String message = Constants.TABLE_SIDE[table][2] + Constants.TABLE_INDENT +
                    Constants.LANG_THREAD[lang] + Thread.currentThread().getName();
            adapter.log(level, tag, message);
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
            mBuilder.append(Constants.TABLE_SIDE[table][2])
                    .append(Constants.TABLE_INDENT)
                    .append(head)
                    .append(name)
                    .append(".")
                    .append(trace[stackIndex].getMethodName())
                    .append(" (")
                    .append(trace[stackIndex].getFileName())
                    .append(":")
                    .append(trace[stackIndex].getLineNumber())
                    .append(")");
            head += Constants.TABLE_INDENT;
            adapter.log(level, tag, mBuilder.toString());
        }

        if (methodCount > 0) {
            adapter.log(level, tag, divider);
        }
    }

    private void logContent(BaseAdapter adapter, String tag, String content) {
        // 将文本内容按\n分割开
        String[] lines = content.split(Constants.ENTER);
        // 格式化策略
        FormatStrategy strategy = adapter.getStrategy();
        // 表格样式
        int table = strategy.getTable();
        // 日志等级
        int level = mCurrentLevel.get();

        for (String line : lines) {
            // 可打印的字节长度
            int length = strategy.getLength() * 2;
            String newLine = line;
            // 如果不为空则循环打印
            while (!Utils.empty(newLine)) {
                // 获取可打印长度内的字符串
                String sub = Utils.sub(newLine, length);
                // 如果为空说明打印完成
                if (sub == null) {
                    break;
                }
                adapter.log(level, tag, Constants.TABLE_SIDE[table][2] + Constants.TABLE_INDENT + sub);
                newLine = newLine.replace(sub, "");
            }
        }
    }

    private void logThrowable(BaseAdapter adapter, String tag, Throwable t) {
        if (t == null) {
            return;
        }
        // 格式化策略
        FormatStrategy strategy = adapter.getStrategy();
        // 表格样式
        int table = strategy.getTable();
        // 日志等级
        int level = mCurrentLevel.get();
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
            String[] lines = exception.split(Constants.ENTER);
            adapter.log(level, tag, divider);
            for (String line : lines) {
                adapter.log(level, tag, Constants.TABLE_SIDE[table][2] + Constants.TABLE_INDENT + line);
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
            for (Object o : c) {
                toItem(index, null, o, lang);
                index ++;
            }
        } else if (obj instanceof Map) {
            int index = 0;
            Map map = (Map) obj;
            for (Object key : map.keySet()) {
                toItem(index, key, map.get(key), lang);
                index++;
            }
        } else if (clazz.isArray()) {
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
        mBuilder.append(Constants.LANG_INDEX[lang])
                .append(index)
                .append("  ");
        if (key != null) {
            mBuilder.append(Constants.LANG_KEY[lang])
                    .append(String.valueOf(key))
                    .append("  ");
        }
        mBuilder.append(Constants.LANG_VALUE[lang])
                .append(String.valueOf(value))
                .append(Constants.ENTER);
    }
}
