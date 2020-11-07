package com.game.magictower.widget;

import android.graphics.Canvas;
import android.view.MotionEvent;

public interface BaseView {
    void onPaint(Canvas canvas);
    boolean onTouch(MotionEvent event);
}
