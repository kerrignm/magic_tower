package com.game.magictower.widget;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.MotionEvent;

import com.game.magictower.res.GameGraphics;
import com.game.magictower.util.RectUtil;

public abstract class BaseView {
    
    public abstract void onPaint(Canvas canvas);

    private static BaseView mFocused;
    
    
    protected GameGraphics graphics;
    
    protected onClickListener listener;
    
    protected Rect rect;
    protected final int id;
    protected final int x;
    protected final int y;
    protected final int w;
    protected final int h;
    
    public BaseView(GameGraphics graphics, int id, int x, int y, int w, int h) {
        this.graphics = graphics;
        this.id = id;
        rect = RectUtil.createRect(x, y, w, h);
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
    }
    
    public boolean onTouch(MotionEvent event) {
        switch (event.getAction()) {
        case MotionEvent.ACTION_DOWN:
            if (inBounds(event)) {
                setFocusView(this);
            }
            break;
        case MotionEvent.ACTION_UP:
        case MotionEvent.ACTION_POINTER_UP:
        case MotionEvent.ACTION_CANCEL:
            setFocusView(null);
            break;
        }
        return false;
    }
    
    protected boolean inBounds(MotionEvent event){
        boolean result = rect.contains((int)event.getX(), (int)event.getY());
        return result;
    }
    
    public int getId() {
        return id;
    }
    
    public static void setFocusView(BaseView focus) {
        mFocused = focus;
    }
    
    public static BaseView getFocusView() {
        return mFocused;
    }
    
    public interface onClickListener{
        void onClicked(int id);
    }

    public void setOnClickListener(onClickListener listener) {
        this.listener = listener;
    }
}
