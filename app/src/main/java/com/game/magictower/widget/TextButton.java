package com.game.magictower.widget;

import android.graphics.Canvas;

public class TextButton extends BaseButton {
    
    protected String label;
    
    public TextButton(int id, int x, int y, int w, int h, String label, boolean repeat) {
        super(id, x, y, w, h, repeat);
        this.label = label;
    }
    
    public static TextButton create(int id, String label, boolean repeat) {
        return new TextButton(id, BaseButton.BTN_RECTS[id].left, BaseButton.BTN_RECTS[id].top, BaseButton.BTN_RECTS[id].width(), BaseButton.BTN_RECTS[id].height(), label, repeat);
    }
    
    @Override
    public void onDrawFrame(Canvas canvas) {
        super.onDrawFrame(canvas);
        graphics.drawTextInCenter(canvas, label, x, y, w, h);
    }
}
