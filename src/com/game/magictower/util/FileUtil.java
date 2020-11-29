package com.game.magictower.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;

import android.content.Context;

public class FileUtil {
    
    private static final int BUF_SIZE = 8 * 1024;
    
    private static byte[] buffer = new byte[BUF_SIZE];
    
    public static String readInternal(Context context, String fileName) {
        try {
            FileInputStream fis = context.openFileInput(fileName);
            int hasRead = 0;
            StringBuilder sb = new StringBuilder();
            while ((hasRead = fis.read(buffer)) != -1) {
                sb.append(new String(buffer, 0, hasRead));
            }
            fis.close();
            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
        } 
        return null;
    }
    
    public static String readExternal(Context context, String fileName) {
        fileName = context.getExternalFilesDir(null).getPath() + File.separator + fileName;
        try {
            FileInputStream fis = new FileInputStream(fileName);
            int hasRead = 0;
            StringBuilder sb = new StringBuilder();
            while ((hasRead = fis.read(buffer)) != -1) {
                sb.append(new String(buffer, 0, hasRead));
            }
            fis.close();
            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
        } 
        return null;
    }
    
    public static boolean writeInternal(Context context, String fileName, String content) {
        if(content == null) return false;
        try {
            FileOutputStream fos = context.openFileOutput(fileName, Context.MODE_PRIVATE);
            fos.write(content.getBytes());
            fos.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public static boolean writeExternal(Context context, String fileName, String content) {
        return writeExternal(context, fileName, content, false);
    }
    
    public static boolean writeExternal(Context context, String fileName, String content, boolean append) {
        if(content == null) return false;
        File file = new File(context.getExternalFilesDir(null).getPath() + File.separator + fileName);
        try {
            if (append) {
                RandomAccessFile raf = new RandomAccessFile(file, "rw");
                raf.seek(file.length());
                raf.write(content.getBytes());
                raf.close();
            } else {
                FileOutputStream fos = new FileOutputStream(file);
                fos.write(content.getBytes());
                fos.close();
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static String loadAssets(Context context, String name) {
        InputStream is = null;
        StringBuilder sb = null;
        int byteCount = 0;
        try {
            is = context.getAssets().open(name);
            sb = new StringBuilder();
            while ((byteCount = is.read(buffer)) != -1) {
                sb.append(new String(buffer, 0, byteCount));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        if (sb != null) {
            return sb.toString();
        }
        return null;
    }
}
