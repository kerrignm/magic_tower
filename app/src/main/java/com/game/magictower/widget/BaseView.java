package com.game.magictower.widget;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.MotionEvent;

import com.game.magictower.res.GameGraphics;
import com.game.magictower.util.RectUtil;

public abstract class BaseView {
    
    private static boolean sIsForbidTouch;

    protected GameGraphics graphics;
    
    protected onClickListener listener;
    
    protected Renderer renderer;
    
    protected Rect rect;
    protected final int id;
    protected final int x;
    protected final int y;
    protected final int w;
    protected final int h;
    
    public BaseView(int id, int x, int y, int w, int h) {
        graphics = GameGraphics.getInstance();
        this.id = id;
        rect = RectUtil.createRect(x, y, w, h);
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
    }
    
    public static void setForbidTouch(boolean forbid) {
        sIsForbidTouch = forbid;
    }
    
    public static boolean getForbidTouch() {
        return sIsForbidTouch;
    }
    
    public abstract void onDrawFrame(Canvas canvas);
    
    public boolean onTouch(MotionEvent event) {
        return false;
    }
    
    protected boolean inBounds(MotionEvent event) {
        return rect.contains((int)event.getX(), (int)event.getY());
    }
    
    public int getId() {
        return id;
    }
    
    public void setRenderer(Renderer renderer) {
        this.renderer = renderer;
    }
    
    public interface onClickListener{
        void onClicked(int id);
    }

    public void setOnClickListener(onClickListener listener) {
        this.listener = listener;
    }
}
