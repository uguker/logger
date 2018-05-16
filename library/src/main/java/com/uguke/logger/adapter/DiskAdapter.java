package com.uguke.logger.adapter;

import android.os.Environment;
import android.text.TextUtils;

import com.uguke.logger.constant.Level;
import com.uguke.logger.strategy.DiskStrategy;
import com.uguke.logger.strategy.FormatStrategy;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class DiskAdapter extends LogAdapter {

    private static final String DEFAULT_PATH = Environment.getExternalStorageDirectory() + File.separator + "Logger";
    private static final String DEFAULT_NAME = "logger.txt";

    public DiskAdapter(FormatStrategy strategy) {
        super(strategy);
    }


    @SuppressWarnings("ResultOfMethodCallIgnored")
    @Override
    public void log(Level level, String tag, String message) {
        String path = DEFAULT_PATH;
        String name = DEFAULT_NAME;
        if (strategy instanceof DiskStrategy) {
            String tempDir = ((DiskStrategy) strategy).getSaveDir();
            String tempName = ((DiskStrategy) strategy).getSaveName();
            path = TextUtils.isEmpty(tempDir) ? path : tempDir;
            name = TextUtils.isEmpty(tempName) ? name : tempName;
        }
        File file = new File(path, name);
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
