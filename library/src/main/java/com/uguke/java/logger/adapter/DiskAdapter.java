package com.uguke.java.logger.adapter;

import com.uguke.java.logger.strategy.DiskStrategy;
import com.uguke.java.logger.strategy.FormatStrategy;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class DiskAdapter extends BaseAdapter {

    public DiskAdapter(FormatStrategy strategy) {
        super(strategy);
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    @Override
    public void log(int level, String tag, String message) {
        if (!(strategy instanceof DiskStrategy)) {
            return;
        }
        String dir = ((DiskStrategy) strategy).getSaveDir();
        String name = ((DiskStrategy) strategy).getSaveName();

        File file = new File(dir, name);
        file.getParentFile().mkdirs();

        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            FileOutputStream output = new FileOutputStream(file, true);
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(output, "UTF-8"));
            String content = tag + ": " + message;
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
