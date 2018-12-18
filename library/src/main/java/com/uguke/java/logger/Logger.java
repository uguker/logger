package com.uguke.java.logger;

import com.uguke.java.logger.adapter.BaseAdapter;

/**
 * 功能描述：Logger
 * @author LeiJue
 */
public final class Logger {

    private Logger() {
        throw new UnsupportedOperationException("Can't instantiate me...");
    }

    private static class Holder {
        static final PrinterImpl instance = PrinterImpl.getInstance();

    }

    public static void addLogAdapter(BaseAdapter adapter) {
        PrinterImpl.getInstance().addLogAdapter(adapter);
    }

    public static void removeLogAdapter(Class clazz) {
        PrinterImpl.getInstance().removeLogAdapter(clazz);
    }

    public static void removeAllLogAdapter() {
        PrinterImpl.getInstance().removeAllLogAdapter();
    }

    public static PrinterImpl t(String tag) {
        return PrinterImpl.getInstance().t(tag);
    }

    public static PrinterImpl t(int methodCount) {
        return PrinterImpl.getInstance().t(methodCount);
    }

    public static PrinterImpl t(String tag, int methodCount) {
        return PrinterImpl.getInstance().t(tag, methodCount);
    }

    public static void d(String msg, Object... args) {
        PrinterImpl.getInstance().d(msg, args);
    }

    public static void d(String msg, Throwable t, Object... args) {
        PrinterImpl.getInstance().d(msg, t, args);
    }

    public static void i(String msg, Object... args) {
        PrinterImpl.getInstance().i(msg, args);
    }

    public static void i(String msg, Throwable t, Object... args) {
        PrinterImpl.getInstance().i(msg, t, args);
    }

    public static void w(String msg, Object... args) {
        PrinterImpl.getInstance().w(msg, args);
    }

    public static void w(String msg, Throwable t, Object... args) {
        PrinterImpl.getInstance().w(msg, t, args);
    }

    public static void e(String msg, Object... args) {
        PrinterImpl.getInstance().e(msg, args);
    }

    public static void e(String msg, Throwable t, Object... args) {
        PrinterImpl.getInstance().e(msg, t, args);
    }
}
