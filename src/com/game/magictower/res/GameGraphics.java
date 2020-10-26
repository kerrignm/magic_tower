package com.game.magictower.res;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.os.CountDownTimer;

public final class GameGraphics {
    
    private static final int TEXT_SIZE = TowerDimen.TOWER_GRID_SIZE / 2;
    private Rect srcRect = new Rect();
    private Rect dstRect = new Rect();
    
    private AutoDecendAlphaPaint alphaPaint;
    public Paint textPaint;
    public Paint disableTextPaint;
    
    private static GameGraphics sInstance;

    public static GameGraphics getInstance(){
        if (sInstance == null) {
            sInstance = new GameGraphics();
        }
        return sInstance;
    }
    private GameGraphics() {
        alphaPaint = new AutoDecendAlphaPaint();
        textPaint = new Paint();
        disableTextPaint = new Paint();
        initTextPaintEffect(alphaPaint);
        initTextPaintEffect(textPaint);
        initDisableTextPaintEffect(disableTextPaint);
        
    }
    
    private void initTextPaintEffect(Paint paint){
        paint.setAntiAlias(true);
        paint.setARGB(255, 255, 255, 255);
        paint.setTextSize(TEXT_SIZE);
        paint.setStrokeWidth(5);
        paint.setTypeface(Typeface.DEFAULT_BOLD);
    }
    
    private void initDisableTextPaintEffect(Paint paint){
        paint.setAntiAlias(true);
        paint.setARGB(255, 200, 200, 200);
        paint.setTextSize(TEXT_SIZE);
        paint.setStrokeWidth(5);
        paint.setTypeface(Typeface.DEFAULT_BOLD);
    }
    
    public Rect getTextBounds(String text) {
        Rect textBounds = new Rect();
        textPaint.getTextBounds(text, 0, text.length(), textBounds);
        return textBounds;
    }
    
    public void drawBitmap(Canvas canvas, LiveBitmap bitmap, int x, int y, int srcX, int srcY,
            int srcWidth, int srcHeight) {
        srcRect.left = srcX;
        srcRect.top = srcY;
        srcRect.right = srcX + srcWidth;
        srcRect.bottom = srcY + srcHeight;

        dstRect.left = x;
        dstRect.top = y;
        dstRect.right = x + srcWidth;
        dstRect.bottom = y + srcHeight;

        canvas.drawBitmap(bitmap.getBitmap(), srcRect, dstRect, null);
    }

    public void drawBitmap(Canvas canvas, LiveBitmap bitmap, Rect src, Rect dst, Paint paint) {
        canvas.drawBitmap(bitmap.getBitmap(), src, dst, paint);
    }

    public final void setAlpha(int alpha){
        //getCorrespondingPaint(player).setCurrentAlpha(alpha);
    }
    
    public final int getCurrentAlpha(){
        return 0;//getCorrespondingPaint(player).getCurrentAlpha();
    }
    
    private final AutoDecendAlphaPaint getCorrespondingPaint(){
        return alphaPaint;
    }

    public void drawBitmapUsingAlpha(Canvas canvas,
            LiveBitmap bitmap, int x, int y) {
        canvas.drawBitmap(bitmap.getBitmap(), x, y,
                getCorrespondingPaint());
    }
    
    public void drawBitmap(Canvas canvas, LiveBitmap bitmap, int x, int y) {
        canvas.drawBitmap(bitmap.getBitmap(), x, y, null);//����ҪPaint����
    }

    public void drawBitmap(Canvas canvas, LiveBitmap bitmap, int x, int y, int srcWidth,
            int srcHeight) {
        dstRect.left = x;
        dstRect.top = y;
        dstRect.right = x + srcWidth;
        dstRect.bottom = y + srcHeight;
        canvas.drawBitmap(bitmap.getBitmap(), null, dstRect, null);
    }

    public void drawBitmapInParentCenter(Canvas canvas, LiveBitmap bitmap, Point center) {
        int x = center.x - (int) (bitmap.getRawWidth() / 2 + 0.5f);
        int y = center.y - (int) (bitmap.getRawHeight() / 2 + 0.5f);
        drawBitmap(canvas, bitmap, x, y);
    }
    
    public void drawNumericText(Canvas canvas, LiveBitmap numbeBitmap, String msg, int x, int y) {
        if (!msg.matches("[0-9 ]*")){
            throw new IllegalArgumentException("drawable msg should contain only numbers and spaces. msg="+msg);
        }
        int len = msg.length();
        for (int i = 0; i < len; i++) {
            char character = msg.charAt(i);

            if (character == ' ') {
                x += 20;                        //����
                continue;
            }

            int srcX = (character - '0') * 17;
            int srcWidth = 17;                  //�ֿ�
                
            drawBitmap(canvas, numbeBitmap, x, y, srcX, 0, srcWidth, 21);
            x += srcWidth;
        }
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
    
    public void drawTextInCenter(Canvas canvas, String msg, int x, int y, int w, int h) {
        if (canvas == null || msg == null) {
            return;
        }
        int textWidth = getTextBounds(msg).width();
        int textHeight = getTextBounds(msg).height();
        //LogUtil.d("GameGraphics", "drawTextInCenter() msg=" + msg + ", textWidth=" + textWidth + ", textHeight=" + textHeight);
        x = x + (w - textWidth) / 2;
        y = y + (h - TEXT_SIZE) / 2 + textHeight;
        canvas.drawText(msg, x, y, textPaint);
    }
    
    public void drawTextUsingAlpha(Canvas canvas, String msg,
            int x, int y) {
        if (canvas == null || msg == null) {
            return;
        }
        canvas.drawText(msg, x, y,
                getCorrespondingPaint());
    }

    public Point getCenter(LiveBitmap pixmap, float x, float y)
    {
        int centerX = (int) (x + pixmap.getRawWidth() / 2 + 0.5f);
        int centerY = (int) (y + pixmap.getRawHeight() / 2 + 0.5f);
        return new Point(centerX, centerY);
    }
    
    protected static final class AutoDecendAlphaPaint extends Paint{
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
        public void setCurrentAlpha(int alpha){
            if (alpha<0 || alpha > 255){
                throw new IllegalArgumentException("wrong alpha value " + alpha);
            }
            currentAlpha = alpha;
            setAlpha(currentAlpha);
            if (alphaDecendTimer!=null){
                alphaDecendTimer.cancel();
            }
            if (currentAlpha==0){
                return;
            }
           
            alphaDecendTimer = new CountDownTimer( 16*alpha/4,16) {
                
                @Override
                public void onTick(long millisUntilFinished) {
                    //from 255 to 0 need 64 times��about 1s
                    currentAlpha -= 4;
                    if (currentAlpha<=0){
                        currentAlpha=0;
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
