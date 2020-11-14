package com.game.magictower.widget;

import android.graphics.Canvas;
import android.graphics.Point;

import com.game.magictower.res.LiveBitmap;

public class BitmapButton extends BaseButton {
    
    protected LiveBitmap bitmap;
    protected Point center;
    
    public BitmapButton(int id, int x, int y, int w, int h, LiveBitmap bitmap, boolean repeat) {
        super(id, x, y, w, h, repeat);
        this.bitmap = bitmap;
        center = new Point(x + w / 2, y + h / 2);
    }
    
    public static BitmapButton create(int id, LiveBitmap bitmap, boolean repeat) {
        return new BitmapButton(id, BaseButton.BTN_RECTS[id].left, BaseButton.BTN_RECTS[id].top, BaseButton.BTN_RECTS[id].width(), BaseButton.BTN_RECTS[id].height(), bitmap, repeat);
    }
    
    @Override
    public void onDrawFrame(Canvas canvas) {
        super.onDrawFrame(canvas);
        graphics.drawBitmapInParentCenter(canvas, bitmap, center);
    }
}
