package com.game.magictower.widget;

import android.graphics.Canvas;

import com.game.magictower.res.GameGraphics;

public class TextButton extends BaseButton {
    
    protected String label;
    
    public TextButton(GameGraphics graphics, int id, int x, int y, int w, int h, String label, boolean repeat) {
        super(graphics, id, x, y, w, h, repeat);
        this.label = label;
    }
    
    public static TextButton create(GameGraphics graphics, int id, String label, boolean repeat) {
        return new TextButton(graphics, id, BaseButton.BTN_RECTS[id].left, BaseButton.BTN_RECTS[id].top, BaseButton.BTN_RECTS[id].width(), BaseButton.BTN_RECTS[id].height(), label, repeat);
    }
    
    @Override
    public void onPaint(Canvas canvas) {
        super.onPaint(canvas);
        graphics.drawTextInCenter(canvas, label, x, y, w, h);
    }
}
