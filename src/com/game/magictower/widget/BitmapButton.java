package com.game.magictower.widget;

import android.graphics.Canvas;
import android.graphics.Point;

import com.game.magictower.res.GameGraphics;
import com.game.magictower.res.LiveBitmap;

public class BitmapButton extends BaseButton {
    
    protected LiveBitmap bitmap;
    protected Point center;
    
    public BitmapButton(GameGraphics graphics, int id, int x, int y, int w, int h, LiveBitmap bitmap, boolean repeat) {
        super(graphics, id, x, y, w, h, repeat);
        this.bitmap = bitmap;
        center = new Point(x + w / 2, y + h / 2);
    }
    
    public static BitmapButton create(GameGraphics graphics, int id, LiveBitmap bitmap, boolean repeat) {
        return new BitmapButton(graphics, id, BaseButton.BTN_RECTS[id].left, BaseButton.BTN_RECTS[id].top, BaseButton.BTN_RECTS[id].width(), BaseButton.BTN_RECTS[id].height(), bitmap, repeat);
    }
    
    @Override
    public void onPaint(Canvas canvas) {
        super.onPaint(canvas);
        graphics.drawBitmapInParentCenter(canvas, bitmap, center);
    }
}
