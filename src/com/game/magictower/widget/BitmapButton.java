package com.game.magictower.widget;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.MotionEvent;

import com.game.magictower.res.Assets;
import com.game.magictower.res.GameGraphics;
import com.game.magictower.res.LiveBitmap;
import com.game.magictower.res.TowerDimen;
import com.game.magictower.util.LogUtil;
import com.game.magictower.util.RectUtil;

public final class BitmapButton implements BitmapView {
    
    public static final int ID_UP = 0;
    public static final int ID_LEFT = 1;
    public static final int ID_RIGHT = 2;
    public static final int ID_DOWN = 3;
    public static final int ID_QUIT = 4;
    public static final int ID_NEW = 5;
    public static final int ID_SAVE = 6;
    public static final int ID_READ = 7;
    public static final int ID_LOOK = 8;
    public static final int ID_JUMP = 9;
    public static final int ID_OK = 10;
    
    private static Rect[] BTN_RECTS = {
        TowerDimen.R_BTN_U,
        TowerDimen.R_BTN_L,
        TowerDimen.R_BTN_R,
        TowerDimen.R_BTN_D,
        TowerDimen.R_BTN_Q,
        TowerDimen.R_BTN_N,
        TowerDimen.R_BTN_S,
        TowerDimen.R_BTN_A,
        TowerDimen.R_BTN_K,
        TowerDimen.R_BTN_J,
        TowerDimen.R_BTN_OK
    };
    
    private onClickListener listener;
    private final GameGraphics graphics;
    private Rect rect;
    private final int id;
    private final int x;
    private final int y;
    private final int w;
    private final int h;
    private final String label;
    private final LiveBitmap bkgNormal;
    private final LiveBitmap bkgPressed;
    private boolean isPressed;
    
    public BitmapButton(GameGraphics graphics, int id, int x, int y, int w, int h, String label) {
        this.graphics = graphics;
        this.id = id;
        rect = RectUtil.createRect(x, y, w, h);
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        this.label = label;
        this.bkgNormal = Assets.getInstance().bkgBtnNormal;
        this.bkgPressed = Assets.getInstance().bkgBtnPressed;
    }
    
    public static BitmapButton create(GameGraphics graphics, int id, String label) {
        return new BitmapButton(graphics, id, BTN_RECTS[id].left, BTN_RECTS[id].top, BTN_RECTS[id].width(), BTN_RECTS[id].height(), label);
    }
    
    public final boolean onTouch(MotionEvent event){
        if (inBounds(event)){
//          Log.d("BitmapButton", "event in bounds caught:"+event.toString());
            if (event.getAction()==MotionEvent.ACTION_DOWN){
                isPressed = true;
            }
            else if (event.getAction()==MotionEvent.ACTION_UP){
                isPressed = false;
                if (listener!=null){
                    listener.onClicked(this);
                    return true;
                }
            }
        }
        return false;
    }
    
    @Override
    public void onPaint(Canvas canvas) {
        LiveBitmap btnBkg = isPressed ? bkgNormal : bkgPressed;
        graphics.drawBitmap(canvas, btnBkg, null, rect, null);
        graphics.drawTextInCenter(canvas, label, x, y, w, h);
    }
    
    private boolean inBounds(MotionEvent event){
        boolean result = rect.contains((int)event.getX(), (int)event.getY());
        if (result) {
            LogUtil.d("MagicTower:BitmapButton", "inBounds() id=" + id + ", rect=" + rect.toString() + ", x=" + x + ", y=" + y);
        }
        return result;
    }
    
    public int getId() {
        return id;
    }
    
    public interface onClickListener{
        void onClicked(BitmapButton btn);
    }

    public void setOnClickListener(onClickListener listener) {
        this.listener = listener;
    }
}
