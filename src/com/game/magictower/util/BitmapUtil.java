package com.game.magictower.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Build;

public final class BitmapUtil {
    
    @TargetApi(19)
    public static final int getBitmapSize(Bitmap bitmap){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){       //API 19
            return bitmap.getAllocationByteCount();
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1){//API 12
            return bitmap.getByteCount();
        }
        return bitmap.getRowBytes() * bitmap.getHeight();               //earlier version
    }
    
    public static final Bitmap scaleBitmap(Bitmap bitmap, int width, int height) {
        if (width <=0 || height <=0 ){
            throw new IllegalArgumentException("target size must be positive!");
        }
        int w = bitmap.getWidth();
        int h = bitmap.getHeight();
        Matrix matrix = new Matrix();
        float scaleWidth = ((float) width / w);
        float scaleHeight = ((float) height / h);
        matrix.postScale(scaleWidth, scaleHeight);
        Bitmap newbmp = Bitmap.createBitmap(bitmap, 0, 0, w, h, matrix, true);
        bitmap.recycle();
        return newbmp;
    }
    
    public static final Bitmap scaleBitmap(Bitmap bitmap, float scalew,
            float scaleh) {
        if (scalew <=0 || scaleh <=0 ){
            throw new IllegalArgumentException("scale rate must be positive!");
        }
        if (MathUtil.equals(scalew, 1.0f) && MathUtil.equals(scaleh, 1.0f)) {
            return bitmap;
        }
        Matrix matrix = new Matrix();
        matrix.postScale(scalew, scaleh);
        Bitmap newbmp = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
                bitmap.getHeight(), matrix, true);
        bitmap.recycle();
        return newbmp;
    }
    
    public static Bitmap creatBitmap(int width, int height, Path path) {
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas();
        canvas.setBitmap(bitmap);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(0xaaffffff);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawColor(0);
        canvas.drawPath(path, paint);
        return bitmap;
    }
    
    public static void saveBitmapToFile(String path, Bitmap bitmap) {
        saveBitmapToFile(path, bitmap, CompressFormat.PNG, 100);
    }

    public static void saveBitmapToFile(String path, Bitmap bitmap,
            CompressFormat format, int quality) {
        File dir = new File(path).getParentFile();
        if (!dir.exists()) {
            dir.mkdirs();
        }
        File photoFile = new File(path);
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(photoFile);
            if (bitmap != null) {
                if (bitmap.compress(format, quality, fos)) {
                    fos.flush();
                }
            }
        } catch (FileNotFoundException e) {
            photoFile.delete();
            e.printStackTrace();
        } catch (IOException e) {
            photoFile.delete();
            e.printStackTrace();
        } finally {
            try {
                if (fos!=null){
                    fos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
