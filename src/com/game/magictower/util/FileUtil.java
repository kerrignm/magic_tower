package com.game.magictower.util;

import java.io.FileInputStream;
import java.io.FileOutputStream;

import android.content.Context;

public class FileUtil {
    
    public static final int BUF_SIZE = 4 * 1024;
    
    public static String read(Context context, String fileName) {
        try {
            FileInputStream fis = context.openFileInput(fileName);
            byte[] buffer = new byte[BUF_SIZE];
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
    
    public static boolean write(Context context, String fileName, String str){
        if(str == null) return false;
        try {
            FileOutputStream fos = context.openFileOutput(fileName, Context.MODE_PRIVATE);
            fos.write(str.getBytes());
            fos.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
