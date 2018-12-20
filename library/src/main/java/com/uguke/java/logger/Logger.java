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
        static final Printer instance = Printer.getInstance();

    }

    public static void addLogAdapter(BaseAdapter adapter) {
        Printer.getInstance().addLogAdapter(adapter);
    }

    public static void removeLogAdapter(Class clazz) {
        Printer.getInstance().removeLogAdapter(clazz);
    }

    public static void removeAllLogAdapter() {
        Printer.getInstance().removeAllLogAdapter();
    }

    public static Printer t(String tag) {
        return Printer.getInstance().t(tag);
    }

    public static Printer t(int methodCount) {
        return Printer.getInstance().t(methodCount);
    }

    public static Printer t(String tag, int methodCount) {
        return Printer.getInstance().t(tag, methodCount);
    }

    public static void d(String msg, Object... args) {
        Printer.getInstance().d(msg, args);
    }

    public static void d(String msg, Throwable t, Object... args) {
        Printer.getInstance().d(msg, t, args);
    }

    public static void i(String msg, Object... args) {
        Printer.getInstance().i(msg, args);
    }

    public static void i(String msg, Throwable t, Object... args) {
        Printer.getInstance().i(msg, t, args);
    }

    public static void w(String msg, Object... args) {
        Printer.getInstance().w(msg, args);
    }

    public static void w(String msg, Throwable t, Object... args) {
        Printer.getInstance().w(msg, t, args);
    }

    public static void e(String msg, Object... args) {
        Printer.getInstance().e(msg, args);
    }

    public static void e(String msg, Throwable t, Object... args) {
        Printer.getInstance().e(msg, t, args);
    }
}
