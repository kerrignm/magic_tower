package com.game.magictower.util;

import android.graphics.Rect;

public final class RectUtil {

    public static Rect createRect(int left, int top, int width, int height) {
        Rect result = new Rect(left, top, left + width, top + height);
        return result;
    }
    
    public static Rect createRect(Rect r, int offsetX, int offsetY) {
        Rect result = new Rect(r);
        result.offset(offsetX, offsetY);
        return result;
    }
}
