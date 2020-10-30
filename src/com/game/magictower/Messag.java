package com.game.magictower;

import java.lang.ref.WeakReference;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.Handler;
import android.os.Message;

import com.game.magictower.Game.Status;
import com.game.magictower.res.Assets;
import com.game.magictower.res.GameGraphics;
import com.game.magictower.res.TowerDimen;
import com.game.magictower.widget.BitmapButton;

public class Messag {
    
    public final static int MODE_MSG = 0;
    public final static int MODE_ALERT = 1;
    public final static int MODE_AUTO_SCROLL = 2;
    
    private Game game;
    private String mMsg;
    private Rect msgRect;
    private int mMode;
    
    public Messag(Game game) {
        this.game = game;
    }
    
    public void show(String msg, int mode) {
        mMsg = msg;
        mMode = mode;
        msgRect = GameGraphics.getInstance().getTextBounds(mMsg, GameGraphics.getInstance().bigTextPaint);
        game.status = Status.Messaging;
        if (mMode == MODE_MSG) {
            handler.sendEmptyMessageDelayed(MSG_DISPLAY_TIMEOUT, MSGISPLAY_TIMEOUT_DELAY);
        }
    }
    
    public void draw(GameGraphics graphics, Canvas canvas) {
        graphics.drawBitmap(canvas, Assets.getInstance().bkgBlank, TowerDimen.R_MSG.left, TowerDimen.R_MSG.top,
                TowerDimen.R_MSG.width(), TowerDimen.R_MSG.height());
        graphics.drawText(canvas, mMsg, TowerDimen.R_MSG.left + msgRect.height() / 2,
                TowerDimen.R_MSG.top + msgRect.height() + (TowerDimen.R_MSG.height() - msgRect.height()) / 2, graphics.bigTextPaint);
    }
    
    public void onBtnKey(int btnId) {
        switch (btnId) {
        case BitmapButton.ID_OK:
            if (mMode == MODE_ALERT) {
                game.status = Status.Playing;
            }
            break;
        }
    }
    
    private Handler handler = new MessagHandler(new WeakReference<Messag>(this));
    
    private static final int MSG_DISPLAY_TIMEOUT = 20;
    
    private static final int MSGISPLAY_TIMEOUT_DELAY = 1000;
    
    private static final class MessagHandler extends Handler {
        private WeakReference<Messag> wk;

        public MessagHandler(WeakReference<Messag> wk) {
            super();
            this.wk = wk;
        }
        
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Messag messag = wk.get();
            if (msg.what == MSG_DISPLAY_TIMEOUT && messag != null) {
                messag.game.status = Status.Playing;
             }
        }
    }

}
