package com.game.magictower.widget;

import java.lang.ref.WeakReference;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.Handler;
import android.os.Message;
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
    private final boolean repeat;
    private final LiveBitmap bkgNormal;
    private final LiveBitmap bkgPressed;
    private boolean isPressed;
    
    public BitmapButton(GameGraphics graphics, int id, int x, int y, int w, int h, String label, boolean repeat) {
        this.graphics = graphics;
        this.id = id;
        rect = RectUtil.createRect(x, y, w, h);
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        this.label = label;
        this.repeat = repeat;
        this.bkgNormal = Assets.getInstance().bkgBtnNormal;
        this.bkgPressed = Assets.getInstance().bkgBtnPressed;
    }
    
    public static BitmapButton create(GameGraphics graphics, int id, String label, boolean repeat) {
        return new BitmapButton(graphics, id, BTN_RECTS[id].left, BTN_RECTS[id].top, BTN_RECTS[id].width(), BTN_RECTS[id].height(), label, repeat);
    }
    
    private Handler handler = new LongHandler(new WeakReference<BitmapButton>(this));
    
    private static final int MSG_ID_LONG_PRESS = 10;
    
    private static final int MSG_DELAY_LONG_PRESS = 300;
    private static final int MSG_DELAY_REPEAT_PRESS = 100;
    
    private static final class LongHandler extends Handler {
        private WeakReference<BitmapButton> wk;

        public LongHandler(WeakReference<BitmapButton> wk) {
            super();
            this.wk = wk;
        }
        
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            BitmapButton button = wk.get();
            if (msg.what == MSG_ID_LONG_PRESS && button != null) {
                if (button.listener != null) {
                    button.listener.onClicked(button.getId());
                }
                sendEmptyMessageDelayed(MSG_ID_LONG_PRESS, MSG_DELAY_REPEAT_PRESS);
            }
        }
    }
    
    public final boolean onTouch(MotionEvent event){
        if (inBounds(event)){
//          Log.d("BitmapButton", "event in bounds caught:"+event.toString());
            switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                isPressed = true;
                if (listener != null) {
                    listener.onClicked(getId());
                    if (repeat) {
                        handler.sendEmptyMessageDelayed(MSG_ID_LONG_PRESS, MSG_DELAY_LONG_PRESS);
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                handler.removeMessages(MSG_ID_LONG_PRESS);
                isPressed = false;
                break;
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
        void onClicked(int id);
    }

    public void setOnClickListener(onClickListener listener) {
        this.listener = listener;
    }
}
