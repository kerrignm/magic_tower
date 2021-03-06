package com.game.magictower.res;

import java.util.ArrayList;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.os.CountDownTimer;

public final class GameGraphics {
    
    private final Rect srcRect = new Rect();
    private final Rect dstRect = new Rect();
    
    private final AutoDecendAlphaPaint alphaPaint;
    public Paint textPaint;
    public Paint disableTextPaint;
    public Paint bigTextPaint;
    public Paint rectPaint;
    
    private static GameGraphics sInstance;

    public static GameGraphics getInstance() {
        if (sInstance == null) {
            sInstance = new GameGraphics();
        }
        return sInstance;
    }
    private GameGraphics() {
        alphaPaint = new AutoDecendAlphaPaint();
        textPaint = new Paint();
        disableTextPaint = new Paint();
        bigTextPaint = new Paint();
        rectPaint = new Paint();
        initTextPaintEffect(alphaPaint);
        initTextPaintEffect(textPaint);
        initDisableTextPaintEffect(disableTextPaint);
        initBigTextPaintEffect(bigTextPaint);
        initRectPaintEffect(rectPaint);
        
    }
    
    private void initTextPaintEffect(Paint paint) {
        paint.setAntiAlias(true);
        paint.setColor(TowerDimen.TEXT_COLOR);
        paint.setTextSize(TowerDimen.TEXT_SIZE);
        paint.setStrokeWidth(TowerDimen.LINE_WIDTH);
        paint.setTypeface(Typeface.DEFAULT_BOLD);
    }
    
    private void initDisableTextPaintEffect(Paint paint) {
        paint.setAntiAlias(true);
        paint.setColor(TowerDimen.DISABLE_TEXT_COLOR);
        paint.setTextSize(TowerDimen.TEXT_SIZE);
        paint.setStrokeWidth(TowerDimen.LINE_WIDTH);
        paint.setTypeface(Typeface.DEFAULT_BOLD);
    }
    
    private void initBigTextPaintEffect(Paint paint) {
        paint.setAntiAlias(true);
        paint.setColor(TowerDimen.TEXT_COLOR);
        paint.setTextSize(TowerDimen.BIG_TEXT_SIZE);
        paint.setStrokeWidth(TowerDimen.LINE_WIDTH);
        paint.setTypeface(Typeface.DEFAULT_BOLD);
    }
    
    private void initRectPaintEffect(Paint paint) {
        paint.setAntiAlias(true);
        paint.setColor(TowerDimen.EDGE_LINE_COLOR);
        paint.setStyle(Style.STROKE);
        paint.setStrokeWidth(TowerDimen.LINE_WIDTH);
        paint.setTypeface(Typeface.DEFAULT_BOLD);
    }
    
    public void drawBitmap(Canvas canvas, Bitmap bitmap, int x, int y, int srcX, int srcY,
            int srcWidth, int srcHeight) {
        srcRect.left = srcX;
        srcRect.top = srcY;
        srcRect.right = srcX + srcWidth;
        srcRect.bottom = srcY + srcHeight;

        dstRect.left = x;
        dstRect.top = y;
        dstRect.right = x + srcWidth;
        dstRect.bottom = y + srcHeight;

        canvas.drawBitmap(bitmap, srcRect, dstRect, null);
    }

    public void drawBitmap(Canvas canvas, Bitmap bitmap, Rect src, Rect dst, Paint paint) {
        canvas.drawBitmap(bitmap, src, dst, paint);
    }

    public final void setAlpha(int alpha) {
        //getCorrespondingPaint(player).setCurrentAlpha(alpha);
    }
    
    public final int getCurrentAlpha(){
        return 0;//getCorrespondingPaint(player).getCurrentAlpha();
    }
    
    private AutoDecendAlphaPaint getCorrespondingPaint() {
        return alphaPaint;
    }

    public void drawBitmapUsingAlpha(Canvas canvas,
            Bitmap bitmap, int x, int y) {
        canvas.drawBitmap(bitmap, x, y,
                getCorrespondingPaint());
    }
    
    public void drawBitmap(Canvas canvas, Bitmap bitmap, int x, int y) {
        canvas.drawBitmap(bitmap, x, y, null);
    }

    public void drawBitmap(Canvas canvas, Bitmap bitmap, int x, int y, int srcWidth,
            int srcHeight) {
        dstRect.left = x;
        dstRect.top = y;
        dstRect.right = x + srcWidth;
        dstRect.bottom = y + srcHeight;
        canvas.drawBitmap(bitmap, null, dstRect, null);
    }

    public void drawBitmapInParentCenter(Canvas canvas, Bitmap bitmap, Point center) {
        drawBitmap(canvas, bitmap, center.x - (int) (bitmap.getWidth() / 2 + 0.5f), center.y - (int) (bitmap.getHeight() / 2 + 0.5f));
    }

    public void drawRect(Canvas canvas, Rect rect) {
        drawRect(canvas, rect, rectPaint);
    }

    public void drawRect(Canvas canvas, Rect rect, Paint paint) {
        canvas.drawRect(rect, paint);
    }
    
    public void drawText(Canvas canvas, String msg, int x, int y) {
        if (canvas == null || msg == null) {
            return;
        }
        canvas.drawText(msg, x, y, textPaint);
    }
    
    public void drawText(Canvas canvas, String msg, int x, int y, Paint paint) {
        if (canvas == null || msg == null) {
            return;
        }
        canvas.drawText(msg, x, y, paint);
    }
    
    public void drawTextInCenter(Canvas canvas, String msg, Rect rect) {
        drawTextInCenter(canvas, msg, rect.left, rect.top, rect.width(), rect.height());
    }
    
    public void drawTextInCenter(Canvas canvas, String msg, int x, int y, int w, int h) {
        drawTextInCenter(canvas, msg, x, y, w, h, textPaint);
    }
    
    public void drawTextInCenter(Canvas canvas, String msg, Rect rect, Paint paint) {
        drawTextInCenter(canvas, msg, rect.left, rect.top, rect.width(), rect.height(), paint);
    }
    
    public void drawTextInCenter(Canvas canvas, String msg, int x, int y, int w, int h, Paint paint) {
        if (canvas == null || msg == null) {
            return;
        }
        canvas.drawText(msg, x + (w - (int)paint.measureText(msg)) / 2, y + (h - (int)paint.getTextSize()) / 2 + (int)paint.getTextSize(), paint);
    }
    
    public void drawTextUsingAlpha(Canvas canvas, String msg,
            int x, int y) {
        if (canvas == null || msg == null) {
            return;
        }
        canvas.drawText(msg, x, y,
        getCorrespondingPaint());
    }
    
    public ArrayList<String> splitToLines(String str, int width) {
        return splitToLines(str, width, textPaint);
    }
    
    public ArrayList<String> splitToLines(String str, int width, Paint paint) {
        ArrayList<String> result = new ArrayList<>();
        float[] widths = new float[str.length()];
        paint.getTextWidths(str, widths);
        String subStr;
        int index = 0;
        float lineLen = 0f;
        for (int i = 0; i < widths.length; ) {
            if (str.charAt(i) == '\n') {
                if (index < i) {
                    subStr = str.substring(index, i);
                    result.add(subStr);
                }
                index = i + 1;
                lineLen = 0;
                i += 1;
            } else if (str.charAt(i) >= '0' && str.charAt(i) <= '9') {
                float wordLen = 0f;
                int j = i;
                while (j < widths.length && str.charAt(j) >= '0' && str.charAt(j) <= '9') {
                    wordLen += widths[j];
                    j++;
                }
                if ((int)wordLen >= width) {
                    if (index < i) {
                        subStr = str.substring(index, i);
                        result.add(subStr);
                    }
                    subStr = str.substring(i, j);
                    result.add(subStr);
                    index = j;
                    lineLen = 0;
                } else if ((int)(lineLen + wordLen) > width) {
                    subStr = str.substring(index, i);
                    result.add(subStr);
                    index = i;
                    lineLen = wordLen;
                } else {
                    lineLen += wordLen;
                }
                i = j;
            } else {
                if ((int)(lineLen + widths[i]) > width) {
                    subStr = str.substring(index, i);
                    result.add(subStr);
                    index = i;
                    lineLen = widths[i];
                } else {
                    lineLen += widths[i];
                }
                i += 1;
            }
        }
        if (index < widths.length) {
            subStr = str.substring(index);
            result.add(subStr);
        }
        return result;
    }
    
    protected static final class AutoDecendAlphaPaint extends Paint {
        private int currentAlpha;
        private CountDownTimer alphaDecendTimer;    //subduction Alpha
        
        public AutoDecendAlphaPaint() {
            currentAlpha = 0;
            setAlpha(currentAlpha);
        }
        
        /**
         * @deprecated using {@link #getCurrentAlpha()} instead.
         */
        @Deprecated
        @Override
        public int getAlpha() {
            return super.getAlpha();
        }
        
        /**
         * @deprecated using {@link #setCurrentAlpha(int)} instead.
         */
        @Deprecated
        @Override
        public void setAlpha(int a) {
            super.setAlpha(a);
        }
        
        public int getCurrentAlpha(){
            return currentAlpha;
        }
        
        /**
         * Set current alpha value.
         * @param alpha The alpha value to set.
         */
        public void setCurrentAlpha(int alpha) {
            if (alpha < 0 || alpha > 255) {
                throw new IllegalArgumentException("wrong alpha value " + alpha);
            }
            currentAlpha = alpha;
            setAlpha(currentAlpha);
            if (alphaDecendTimer != null) {
                alphaDecendTimer.cancel();
            }
            if (currentAlpha == 0) {
                return;
            }
           
            alphaDecendTimer = new CountDownTimer( 16 * alpha / 4, 16) {
                
                @Override
                public void onTick(long millisUntilFinished) {
                    //from 255 to 0 need 64 times��about 1s
                    currentAlpha -= 4;
                    if (currentAlpha <= 0) {
                        currentAlpha = 0;
                    }
                    setAlpha(currentAlpha);
                    cancel();
                    alphaDecendTimer = null;
                }
                
                @Override
                public void onFinish() {
                    if (alphaDecendTimer != null){
                        currentAlpha = 0;
                        alphaDecendTimer.cancel();
                        alphaDecendTimer = null;
                    }
                }
            };
            alphaDecendTimer.start();
        }
    }

}
