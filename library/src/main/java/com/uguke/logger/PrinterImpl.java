package com.uguke.logger;

import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.util.LongSparseArray;
import android.support.v4.util.SparseArrayCompat;
import android.text.TextUtils;
import android.util.SparseArray;
import android.util.SparseBooleanArray;
import android.util.SparseIntArray;
import android.util.SparseLongArray;

import com.uguke.logger.adapter.LogAdapter;
import com.uguke.logger.strategy.FormatStrategy;
import com.uguke.logger.constant.Language;
import com.uguke.logger.constant.Level;
import com.uguke.logger.constant.Table;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Collection;
import java.util.Hashtable;
import java.util.Map;

public class PrinterImpl implements SimplePrinter, MultiPrinter {

    private StringBuilder builder;
    // 当前日志等级
    private ThreadLocal<Level> currentLevel;
    // 当前方法数
    private ThreadLocal<Integer> currentCount;
    // 当前Tag
    private ThreadLocal<String> currentTag;
    // 日志适配器
    private Map<Class, LogAdapter> adapters;

    private PrinterImpl() {
        this.builder = new StringBuilder();
        this.currentTag = new ThreadLocal<>();
        this.currentLevel = new ThreadLocal<>();
        this.currentCount = new ThreadLocal<>();
        adapters = new Hashtable<>();
    }

    private static class Holder {
        static final PrinterImpl instance = new PrinterImpl();
    }

    public static PrinterImpl getInstance() {
        return Holder.instance;
    }

    @Override
    public SimplePrinter t(String tag) {
        currentTag.set(tag);
        return this;
    }

    @Override
    public SimplePrinter t(int methodCount) {
        currentCount.set(methodCount);
        return this;
    }

    @Override
    public SimplePrinter t(String tag, int methodCount) {
        currentTag.set(tag);
        currentCount.set(methodCount);
        return this;
    }

    @Override
    public synchronized void log(Level level, Object msg, Throwable t) {
        // 保存当前日志等级
        currentLevel.set(level);
        for (Class clazz : adapters.keySet()) {
            LogAdapter adapter = adapters.get(clazz);
            // 获取格式化策略
            FormatStrategy strategy = adapter.getStrategy();
            // 获取表格样式
            Table table = strategy.getTable();
            // 如果打印日志等级不够则不打印
            if (Level.isLow(level, strategy.getLevel())) {
                continue;
            }
            // 获取顶部边框
            String topBorder = table.getTopBorder(strategy.getMaxLength());
            // 获取底部边框
            String bottomBorder = table.getBottomBorder(strategy.getMaxLength());
            // 获取Object内容
            if (msg == Language.EN.getJson()) {
                msg = strategy.getLanguage().getJson();
            }
            String content = getContent(msg, strategy.getLanguage());

            // 获取Tag
            String tag = strategy.getTag();
            if (!TextUtils.isEmpty(currentTag.get())) {
                tag += "_" + currentTag.get();
            }
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

    @Override
    public void addLogAdapter(LogAdapter adapter) {
        adapters.put(adapter.getClass(), adapter);
    }

    @Override
    public void removeLogAdapter(Class clazz) {
        adapters.remove(clazz);
    }

    @Override
    public void removeAllLogAdapter() {
        adapters.clear();
    }

    @Override
    public void v(String msg, Object... args) {
        log(Level.VERBOSE, formatMessage(msg, args), null);
    }

    @Override
    public void v(String msg, Throwable t, Object... args) {
        log(Level.VERBOSE, formatMessage(msg, args), t);
    }

    @Override
    public void v(Object msg) {
        log(Level.VERBOSE, msg, null);
    }

    @Override
    public void v(Object msg, Throwable t) {
        log(Level.VERBOSE, msg, t);
    }

    @Override
    public void d(String msg, Object... args) {
        log(Level.DEBUG, formatMessage(msg, args), null);
    }

    @Override
    public void d(String msg, Throwable t, Object... args) {
        log(Level.DEBUG, formatMessage(msg, args), t);
    }

    @Override
    public void d(Object msg) {
        log(Level.DEBUG, msg, null);
    }

    @Override
    public void d(Object msg, Throwable t) {
        log(Level.DEBUG, msg, t);
    }

    @Override
    public void i(String msg, Object... args) {
        log(Level.INFO, formatMessage(msg, args), null);
    }

    @Override
    public void i(String msg, Throwable t, Object... args) {
        log(Level.INFO, formatMessage(msg, args), t);
    }

    @Override
    public void i(Object msg) {
        log(Level.INFO, msg, null);
    }

    @Override
    public void i(Object msg, Throwable t) {
        log(Level.INFO, msg, t);
    }

    @Override
    public void w(String msg, Object... args) {
        log(Level.WARN, formatMessage(msg, args), null);
    }

    @Override
    public void w(String msg, Throwable t, Object... args) {
        log(Level.WARN, formatMessage(msg, args), t);
    }

    @Override
    public void w(Object msg) {
        log(Level.WARN, msg, null);
    }

    @Override
    public void w(Object msg, Throwable t) {
        log(Level.WARN, msg, t);
    }

    @Override
    public void e(String msg, Object... args) {
        log(Level.ERROR, formatMessage(msg, args), null);
    }

    @Override
    public void e(String msg, Throwable t, Object... args) {
        log(Level.ERROR, formatMessage(msg, args), t);
    }

    @Override
    public void e(Object msg) {
        log(Level.ERROR, msg, null);
    }

    @Override
    public void e(Object msg, Throwable t) {
        log(Level.ERROR, msg, t);
    }

    @Override
    public void wtf(String msg, Object... args) {
        log(Level.ASSERT, formatMessage(msg, args), null);
    }

    @Override
    public void wtf(String msg, Throwable t, Object... args) {
        log(Level.ASSERT, formatMessage(msg, args), t);
    }

    @Override
    public void wtf(Object msg) {
        log(Level.ASSERT, msg, null);
    }

    @Override
    public void wtf(Object msg, Throwable t) {
        log(Level.ASSERT, msg, t);
    }

    @Override
    public void json(String json) {
        json(Level.INFO, json);
    }

    @Override
    public void json(Level level, String json) {
        if (TextUtils.isEmpty(json)) {
            log(level, Language.EN.getJson(), null);
            return;
        }
        try {
            json = json.trim();
            if (json.startsWith("{")) {
                JSONObject jsonObject = new JSONObject(json);
                String message = jsonObject.toString(2);
                log(level, message, null);
                return;
            }
            if (json.startsWith("[")) {
                JSONArray jsonArray = new JSONArray(json);
                String message = jsonArray.toString(2);
                log(level, message, null);
                return;
            }
            log(level, Language.EN.getJson(), null);
        } catch (JSONException e) {
            log(level, Language.EN.getJson(), null);
        }
    }

    private void logHeader(LogAdapter adapter, String tag) {
        // 格式化策略
        FormatStrategy strategy = adapter.getStrategy();
        // 表格样式
        Table table = strategy.getTable();
        // 日志等级
        Level level = currentLevel.get();
        // 方法数
        int methodCount =  currentCount.get() == null ? strategy.getMethodCount() : currentCount.get();
        // 获取内容分割线
        String divider = table.getContentDivider(strategy.getMaxLength());
        // 获取当前该线程的堆栈转储堆栈跟踪元素的数组
        StackTraceElement[] trace = Thread.currentThread().getStackTrace();
        //是否显示线程信息
        if (strategy.isShowThread()) {
            String message = table.getBorderVertical() + table.getIndent() +
                    strategy.getLanguage().getThread() + Thread.currentThread().getName();
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
            builder.append(table.getBorderVertical())
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

    private void logContent(LogAdapter adapter, String tag, String content) {
        // 将文本内容按\n分割开
        String[] lines = content.split(System.getProperty("line.separator"));

        // 格式化策略
        FormatStrategy strategy = adapter.getStrategy();
        // 表格样式
        Table table = strategy.getTable();
        // 日志等级
        Level level = currentLevel.get();

        for (String line : lines) {
            //游标
            int cursor = 0;
            //可打印的长度
            int measure = strategy.getMaxLength() - table.getBorderVertical().length();
            //一行的内容
            String newLine = table.getIndent() + line;
            //循环打印
            while (newLine.length() > cursor) {
                //结束位置
                int end = newLine.length();
                if (end >= cursor + measure) {
                    end = cursor + measure;
                }
                //截取内容
                String message = table.getBorderVertical() + newLine.substring(cursor, end);
                adapter.log(level, tag, message);
                cursor += measure;
            }
        }
    }

    private void logThrowable(LogAdapter adapter, String tag, Throwable t) {
        if (t == null) return;

        // 格式化策略
        FormatStrategy strategy = adapter.getStrategy();
        // 表格样式
        Table table = strategy.getTable();
        // 日志等级
        Level level = currentLevel.get();
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

        if (!TextUtils.isEmpty(exception)) {
            // 将文本内容按\n分割开
            String[] lines = exception.split(System.getProperty("line.separator"));
            adapter.log(level, tag, divider);
            for (String line : lines) {
                adapter.log(level, tag, table.getBorderVertical() + table.getIndent() + line);
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

    @NonNull
    private String getContent(Object o, Language language) {
        if (o == null) return "";
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
                count ++;
            }
        } else if (o instanceof SparseArray) {
            SparseArray array = (SparseArray) o;
            for (int i = 0; i < array.size(); i++) {
                getItemString(i, array.keyAt(i), array.valueAt(i), language);
            }
        } else if (o instanceof SparseBooleanArray) {
            SparseBooleanArray array = (SparseBooleanArray) o;
            for (int i = 0; i < array.size(); i++) {
                getItemString(i, array.keyAt(i), array.valueAt(i), language);
            }
        } else if (o instanceof SparseIntArray) {
            SparseIntArray array = (SparseIntArray) o;
            for (int i = 0; i < array.size(); i++) {
                getItemString(i, array.keyAt(i), array.valueAt(i), language);
            }
        } else if (o instanceof SparseLongArray) {
            SparseLongArray array = (SparseLongArray) o;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
                for (int i = 0; i < array.size(); i++) {
                    getItemString(i, array.keyAt(i), array.valueAt(i), language);
                }
            } else {
                builder.append(language.getValue())
                        .append(String.valueOf(o))
                        .append("\n");
            }
        } else if (o instanceof SparseArrayCompat) {
            SparseArrayCompat array = (SparseArrayCompat) o;
            for (int i = 0; i < array.size(); i++) {
                getItemString(i, array.keyAt(i), array.valueAt(i), language);
            }
        } else if (o instanceof LongSparseArray) {
            LongSparseArray array = (LongSparseArray) o;
            for (int i = 0; i < array.size(); i++) {
                getItemString(i, array.keyAt(i), array.valueAt(i), language);
            }
        } else if (o instanceof android.util.LongSparseArray) {
            android.util.LongSparseArray array = (android.util.LongSparseArray) o;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                for (int i = 0; i < array.size(); i++) {
                    getItemString(i, array.keyAt(i), array.valueAt(i), language);
                }
            } else {
                builder.append(language.getValue())
                        .append(String.valueOf(o))
                        .append("\n");
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

    private String formatMessage(String msg, Object... args) {
        if (args == null)
            return msg;
        return String.format(msg, args);
    }
}
