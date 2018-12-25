package com.uguke.java.logger;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

/**
 * 功能描述：硬盘缓存
 * @author LeiJue
 * @date 2018/12/25
 */
public class DiskAdapter extends BaseAdapter {

    public DiskAdapter(LoggerStrategy strategy) {
        super(strategy);
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    @Override
    public void log(Level level, String tag, String message) {
        String dir = mStrategy.getSaveDir();
        String name = mStrategy.getSaveName();
        File file = new File(dir, name);
        file.getParentFile().mkdirs();
        String outTag = METHOD_NAMES[level.mCode].toUpperCase() + "/" + tag;
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            FileOutputStream output = new FileOutputStream(file, true);
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(output, "UTF-8"));
            String content = outTag + ": " + message;
            writer.newLine();
            writer.write(content);
            writer.flush();
            writer.close();
            output.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
