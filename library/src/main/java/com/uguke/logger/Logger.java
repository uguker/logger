package com.uguke.logger;

import com.uguke.logger.adapter.LogAdapter;
import com.uguke.logger.constant.Level;

public final class Logger {

    private Logger() {
        throw new UnsupportedOperationException("Can't instantiate me...");
    }

    private static class Holder {
        static final MultiPrinter instance = PrinterImpl.getInstance();

    }

    public static void addLogAdapter(LogAdapter adapter) {
        PrinterImpl.getInstance().addLogAdapter(adapter);
    }

    public static void removeLogAdapter(Class clazz) {
        PrinterImpl.getInstance().removeLogAdapter(clazz);
    }

    public static void removeAllLogAdapter() {
        PrinterImpl.getInstance().removeAllLogAdapter();
    }

    public static MultiPrinter t(String tag) {
        PrinterImpl.getInstance().t(tag);
        return Holder.instance;
    }

    public static MultiPrinter t(int methodCount) {
        PrinterImpl.getInstance().t(methodCount);
        return Holder.instance;
    }

    public static MultiPrinter t(String tag, int methodCount) {
        PrinterImpl.getInstance().t(tag, methodCount);
        return Holder.instance;
    }

    public static void v(String msg, Object... args) {
        PrinterImpl.getInstance().v(msg, args);
    }

    public static void v(String msg, Throwable t, Object... args) {
        PrinterImpl.getInstance().v(msg, t, args);
    }

    public static void v(Object msg) {
        PrinterImpl.getInstance().v(msg);
    }

    public static void v(Object msg, Throwable t) {
        PrinterImpl.getInstance().v(msg, t);
    }

    public static void d(String msg, Object... args) {
        PrinterImpl.getInstance().d(msg, args);
    }

    public static void d(String msg, Throwable t, Object... args) {
        PrinterImpl.getInstance().d(msg, t, args);
    }

    public static void d(Object msg) {
        PrinterImpl.getInstance().d(msg);
    }

    public static void d(Object msg, Throwable t) {
        PrinterImpl.getInstance().d(msg, t);
    }

    public static void i(String msg, Object... args) {
        PrinterImpl.getInstance().i(msg, args);
    }

    public static void i(String msg, Throwable t, Object... args) {
        PrinterImpl.getInstance().i(msg, t, args);
    }

    public static void i(Object msg) {
        PrinterImpl.getInstance().i(msg);
    }

    public static void i(Object msg, Throwable t) {
        PrinterImpl.getInstance().i(msg, t);
    }

    public static void w(String msg, Object... args) {
        PrinterImpl.getInstance().w(msg, args);
    }

    public static void w(String msg, Throwable t, Object... args) {
        PrinterImpl.getInstance().w(msg, t, args);
    }

    public static void w(Object msg) {
        PrinterImpl.getInstance().w(msg);
    }

    public static void w(Object msg, Throwable t) {
        PrinterImpl.getInstance().w(msg, t);
    }

    public static void e(String msg, Object... args) {
        PrinterImpl.getInstance().e(msg, args);
    }

    public static void e(String msg, Throwable t, Object... args) {
        PrinterImpl.getInstance().e(msg, t, args);
    }

    public static void e(Object msg) {
        PrinterImpl.getInstance().e(msg);
    }

    public static void e(Object msg, Throwable t) {
        PrinterImpl.getInstance().e(msg, t);
    }

    public static void wtf(String msg, Object... args) {
        PrinterImpl.getInstance().wtf(msg, args);
    }

    public static void wtf(String msg, Throwable t, Object... args) {
        PrinterImpl.getInstance().wtf(msg, t, args);
    }

    public static void wtf(Object msg) {
        PrinterImpl.getInstance().wtf(msg);
    }

    public static void wtf(Object msg, Throwable t) {
        PrinterImpl.getInstance().wtf(msg, t);
    }

    public static void json(Level level, String msg) {
        PrinterImpl.getInstance().json(level, msg);
    }

    public static void json(String msg) {
        PrinterImpl.getInstance().json(msg);
    }

}
