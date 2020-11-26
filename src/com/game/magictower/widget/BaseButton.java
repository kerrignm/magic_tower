package com.game.magictower.widget;

import java.lang.ref.WeakReference;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.Handler;
import android.os.Message;
import android.view.MotionEvent;

import com.game.magictower.res.Assets;
import com.game.magictower.res.GameGraphics;
import com.game.magictower.res.TowerDimen;

public class BaseButton extends BaseView {
    
    public static final int ID_UP = 0;
    public static final int ID_LEFT = 1;
    public static final int ID_RIGHT = 2;
    public static final int ID_DOWN = 3;
    public static final int ID_QUIT = 4;
    public static final int ID_SAVE = 5;
    public static final int ID_READ = 6;
    public static final int ID_LOOK = 7;
    public static final int ID_JUMP = 8;
    public static final int ID_OK = 9;
    
    public static Rect[] BTN_RECTS = {
        TowerDimen.R_BTN_U,
        TowerDimen.R_BTN_L,
        TowerDimen.R_BTN_R,
        TowerDimen.R_BTN_D,
        TowerDimen.R_BTN_Q,
        TowerDimen.R_BTN_S,
        TowerDimen.R_BTN_A,
        TowerDimen.R_BTN_K,
        TowerDimen.R_BTN_J,
        TowerDimen.R_BTN_OK
    };
    protected boolean isPressed;
    protected boolean repeat;
    protected Bitmap bkgNormal;
    protected Bitmap bkgPressed;
    
    public BaseButton(int id, int x, int y, int w, int h, boolean repeat) {
        super(id, x, y, w, h);
        this.repeat = repeat;
        this.bkgNormal = Assets.getInstance().bkgBtnNormal;
        this.bkgPressed = Assets.getInstance().bkgBtnPressed;
    }
    
    public static BaseButton create(GameGraphics graphics, int id, String label, boolean repeat) {
        return new BaseButton(id, BTN_RECTS[id].left, BTN_RECTS[id].top, BTN_RECTS[id].width(), BTN_RECTS[id].height(), repeat);
    }
    
    private Handler handler = new LongHandler(new WeakReference<BaseButton>(this));
    
    private static final int MSG_ID_LONG_PRESS = 10;
    
    private static final int MSG_DELAY_LONG_PRESS = 500;
    private static final int MSG_DELAY_REPEAT_PRESS = 100;
    
    private static final class LongHandler extends Handler {
        private WeakReference<BaseButton> wk;

        public LongHandler(WeakReference<BaseButton> wk) {
            super();
            this.wk = wk;
        }
        
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (BaseView.getForbidTouch()) {
                removeMessages(MSG_ID_LONG_PRESS);

            } else {
                BaseButton button = wk.get();
                if (msg.what == MSG_ID_LONG_PRESS && button != null && button.listener != null && button.isPressed) {
                    button.listener.onClicked(button.getId());
                    sendEmptyMessageDelayed(MSG_ID_LONG_PRESS, MSG_DELAY_REPEAT_PRESS);
                }
            }
        }
    }
    
    @Override
    public boolean onTouch(MotionEvent event) {
        boolean result = false;
        switch (event.getAction()) {
        case MotionEvent.ACTION_DOWN:
            if (inBounds(event)) {
                result = true;
                isPressed = true;
                if (listener != null) {
                    listener.onClicked(getId());
                    if (repeat) {
                        handler.sendEmptyMessageDelayed(MSG_ID_LONG_PRESS, MSG_DELAY_LONG_PRESS);
                    }
                }
            }
            break;
        case MotionEvent.ACTION_UP:
        case MotionEvent.ACTION_POINTER_UP:
        case MotionEvent.ACTION_CANCEL:
        case MotionEvent.ACTION_OUTSIDE:
            result = true;
            isPressed = false;
            handler.removeMessages(MSG_ID_LONG_PRESS);
            break;
        }
        return result;
    }
    
    @Override
    public void onDrawFrame(Canvas canvas) {
        graphics.drawBitmap(canvas, isPressed ? bkgNormal : bkgPressed, null, rect, null);
    }
}
