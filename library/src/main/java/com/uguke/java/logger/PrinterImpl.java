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
class PrinterImpl {

    private StringBuilder builder;
    /** 当前日志等级 **/
    private ThreadLocal<Level> mCurrentLevel;
    /** 当前方法数 **/
    private ThreadLocal<Integer> mCurrentCount;
    /** 当前Tag **/
    private ThreadLocal<String> mCurrentTag;
    /** 日志适配器 **/
    private Map<Class, BaseAdapter> adapters;

    private PrinterImpl() {
        this.builder = new StringBuilder();
        this.mCurrentTag = new ThreadLocal<>();
        this.mCurrentLevel = new ThreadLocal<>();
        this.mCurrentCount = new ThreadLocal<>();
        adapters = new Hashtable<>();
    }

    private static class Holder {
        static final PrinterImpl INSTANCE = new PrinterImpl();
    }

    static PrinterImpl getInstance() {
        return Holder.INSTANCE;
    }

    PrinterImpl t(String tag) {
        mCurrentTag.remove();
        mCurrentTag.set(tag);
        return this;
    }

    PrinterImpl t(int methodCount) {
        mCurrentCount.remove();
        mCurrentCount.set(methodCount);
        return this;
    }

    PrinterImpl t(String tag, int methodCount) {
        mCurrentTag.set(tag);
        mCurrentCount.set(methodCount);
        return this;
    }

    private synchronized void log(Level level, Object msg, Throwable t) {
        // 保存当前日志等级
        mCurrentLevel.remove();
        mCurrentLevel.set(level);
        for (Class clazz : adapters.keySet()) {
            BaseAdapter adapter = adapters.get(clazz);
            // 获取格式化策略
            FormatStrategy strategy = adapter.getStrategy();
            // 获取表格样式
            Table table = strategy.getTable();
            // 如果打印日志等级不够则不打印
            if (Level.isLow(level, strategy.getLevel())) {
                continue;
            }
            // 获取边框
            String topBorder = table.getTopBorder(strategy.getMaxLength());
            String bottomBorder = table.getBottomBorder(strategy.getMaxLength());
            String content = getContent(msg, strategy.getLanguage());
            String tag = createFinalTag(strategy.getTag(), mCurrentTag.get());

            // 绘制上边框
            adapter.log(level, tag, topBorder);
            // 绘制头部
            logHeader(adapter, tag);
            // 绘制内容
            logContent(adapter, tag, content);
            // 绘制异常内容
            logThrowable(adapter, tag, t);
            // 绘制下边框
            adapter.log(level, tag, bottomBorder);
        }
    }

    void addLogAdapter(BaseAdapter adapter) {
        adapters.put(adapter.getClass(), adapter);
    }

    void removeLogAdapter(Class clazz) {
        adapters.remove(clazz);
    }

    void removeAllLogAdapter() {
        adapters.clear();
    }

    void d(String msg, Object... args) {
        log(Level.DEBUG, Utils.format(msg, args), null);
    }

    void d(String msg, Throwable t, Object... args) {
        log(Level.DEBUG, Utils.format(msg, args), t);
    }

    void i(String msg, Object... args) {
        log(Level.INFO, Utils.format(msg, args), null);
    }

    void i(String msg, Throwable t, Object... args) {
        log(Level.INFO, Utils.format(msg, args), t);
    }

    void w(String msg, Object... args) {
        log(Level.WARN, Utils.format(msg, args), null);
    }

    void w(String msg, Throwable t, Object... args) {
        log(Level.WARN, Utils.format(msg, args), t);
    }

    void e(String msg, Object... args) {
        log(Level.ERROR, Utils.format(msg, args), null);
    }

    void e(String msg, Throwable t, Object... args) {
        log(Level.ERROR, Utils.format(msg, args), t);
    }

    private void logHeader(BaseAdapter adapter, String tag) {
        // 格式化策略
        FormatStrategy strategy = adapter.getStrategy();
        // 表格样式
        Table table = strategy.getTable();
        // 日志等级
        Level level = mCurrentLevel.get();
        // 方法数
        int methodCount =  mCurrentCount.get() == null ? strategy.getMethodCount() : mCurrentCount.get();
        // 获取内容分割线
        String divider = table.getContentDivider(strategy.getMaxLength());
        // 获取当前该线程的堆栈转储堆栈跟踪元素的数组
        StackTraceElement[] trace = Thread.currentThread().getStackTrace();
        //是否显示线程信息
        if (strategy.isShowThread()) {
            String message = table.mBV + table.mIndent +
                    strategy.getLanguage().mThread + Thread.currentThread().getName();
            adapter.log(level, tag, message);
            adapter.log(level, tag, divider);
        }
        // 获取方法偏移数
        int stackOffset = 0;
        for (int i = strategy.getMethodOffset(); i < trace.length; i++) {
            StackTraceElement e = trace[i];
            String name = e.getClassName();
            if (!name.equals(PrinterImpl.class.getName()) &&
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
            builder.setLength(0);
            builder.append(table.getBV())
                    .append(table.getIndent())
                    .append(head)
                    .append(name)
                    .append(".")
                    .append(trace[stackIndex].getMethodName())
                    .append(" (")
                    .append(trace[stackIndex].getFileName())
                    .append(":")
                    .append(trace[stackIndex].getLineNumber())
                    .append(")");
            head += table.getIndent();
            adapter.log(level, tag, builder.toString());
        }

        if (methodCount > 0) {
            adapter.log(level, tag, divider);
        }
    }

    private void logContent(BaseAdapter adapter, String tag, String content) {
        // 将文本内容按\n分割开
        String[] lines = content.split(System.getProperty("line.separator"));

        // 格式化策略
        FormatStrategy strategy = adapter.getStrategy();
        // 表格样式
        Table table = strategy.getTable();
        // 日志等级
        Level level = mCurrentLevel.get();

        for (String line : lines) {
            // 可打印的字节长度
            int length = (strategy.getMaxLength() - 1) * 2;
            String newLine = table.getIndent() + line;
            // 如果不为空则循环打印
            while (!Utils.isEmpty(newLine)) {
                // 获取可打印长度内的字符串
                String sub = Utils.sub(newLine, length);
                // 如果为空说明打印完成
                if (sub == null) {
                    break;
                }
                adapter.log(level, tag, table.getBV() + sub);
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
        Table table = strategy.getTable();
        // 日志等级
        Level level = mCurrentLevel.get();
        // 获取内容分割线
        String divider = table.getContentDivider(strategy.getMaxLength());

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

        if (exception != null && exception.length() != 0) {
            // 将文本内容按\n分割开
            String[] lines = exception.split(System.getProperty("line.separator"));
            adapter.log(level, tag, divider);
            for (String line : lines) {
                adapter.log(level, tag, table.getBV() + table.getIndent() + line);
            }
        }
    }

    private void getItemString(int position, Object key, Object value, Language language) {
        builder.append(language.getPosition())
                .append(position)
                .append("  ")
                .append(language.getKey())
                .append(String.valueOf(key))
                .append("  ")
                .append(language.getValue())
                .append(String.valueOf(value))
                .append("\n");
    }

    private void getItemString(int position, Object value, Language language) {
        builder.append(language.getPosition())
                .append(position)
                .append("  ")
                .append(language.getValue())
                .append(String.valueOf(value))
                .append("\n");
    }

    private String getContent(Object o, Language language) {
        if (o == null) {
            return "";
        }
        builder.setLength(0);
        Class clazz = o.getClass();
        if (o instanceof Collection) {
            int count = 0;
            Collection c = (Collection) o;
            for (Object o1 : c) {
                getItemString(count, o1, language);
                count ++;
            }
        } else if (o instanceof Map) {
            int count = 0;
            Map map = (Map) o;
            for (Object key : map.keySet()) {
                getItemString(count, key, map.get(key), language);
                count++;
            }
        } else if (clazz.isArray()) {
            if (clazz.equals(boolean [].class)) {
                boolean [] array = (boolean []) o;
                for (int i = 0; i < array.length; i++) {
                    getItemString(i, array[i], language);
                }
            } else if (clazz.equals(byte [].class)) {
                byte [] array = (byte []) o;
                for (int i = 0; i < array.length; i++) {
                    getItemString(i, array[i], language);
                }
            }  else if (clazz.equals(char [].class)) {
                char [] array = (char []) o;
                for (int i = 0; i < array.length; i++) {
                    getItemString(i, array[i], language);
                }
            } else if (clazz.equals(int [].class)) {
                int [] array = (int []) o;
                for (int i = 0; i < array.length; i++) {
                    getItemString(i, array[i], language);
                }
            } else if (clazz.equals(short [].class)) {
                short [] array = (short []) o;
                for (int i = 0; i < array.length; i++) {
                    getItemString(i, array[i], language);
                }
            } else if (clazz.equals(long [].class)) {
                long [] array = (long []) o;
                for (int i = 0; i < array.length; i++) {
                    getItemString(i, array[i], language);
                }
            } else if (clazz.equals(float [].class)) {
                float [] array = (float []) o;
                for (int i = 0; i < array.length; i++) {
                    getItemString(i, array[i], language);
                }
            } else if (clazz.equals(double [].class)) {
                double [] array = (double []) o;
                for (int i = 0; i < array.length; i++) {
                    getItemString(i, array[i], language);
                }
            } else {
                Object [] array = (Object []) o;
                for (int i = 0; i < array.length; i++) {
                    getItemString(i, array[i], language);
                }
            }
        } else if (o instanceof String) {
            builder.append(String.valueOf(o));
        } else {
            builder.append(language.getValue())
                    .append(String.valueOf(o))
                    .append("\n");
        }
        return builder.toString();
    }

    private String createFinalTag(String globalTag, String mCurrentTag) {
        String finalTag = "";
        if (globalTag != null && globalTag.length() > 0) {
            finalTag += "[" + globalTag + "]";
        }
        if (mCurrentTag != null && mCurrentTag.length() > 0) {
            finalTag += "[" + mCurrentTag + "]";
        }
        return finalTag;
    }
}
