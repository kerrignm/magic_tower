package com.game.magictower;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import android.content.Context;
import android.graphics.Canvas;
import android.os.Handler;
import android.os.Message;

import com.game.magictower.Game.Status;
import com.game.magictower.res.Assets;
import com.game.magictower.res.GameGraphics;
import com.game.magictower.res.TowerDimen;
import com.game.magictower.widget.BitmapButton;

public class SceneMessage {
    
    public final static int MODE_MSG = 0;
    public final static int MODE_ALERT = 1;
    public final static int MODE_AUTO_SCROLL = 2;
    
    private Context mContext;
    
    private Game game;
    private String mTitle;
    private String mMsg;
    private int mMode;
    ArrayList<String> mInfo;
    private int mOffset;
    
    public SceneMessage(Context context, Game game) {
        mContext = context;
        this.game = game;
    }
    
    public void show(int msgId) {
        show(0, msgId, MODE_MSG);
    }
    
    public void show(String msg) {
        show(null, msg, MODE_MSG);
    }
    
    public void show(int titleId, int msgId, int mode) {
        if (titleId > 0) {
            show(mContext.getResources().getString(titleId), mContext.getResources().getString(msgId), mode);
        } else {
            show(null, mContext.getResources().getString(msgId), mode);
        }
    }
    
    public void show(String title, String msg, int mode) {
        mTitle = title;
        mMsg = msg;
        mMode = mode;
        game.status = Status.Messaging;
        if (mMode == MODE_MSG) {
            handler.sendEmptyMessageDelayed(MSG_DISPLAY_TIMEOUT, MSGISPLAY_TIMEOUT_DELAY);
        } else {
            mInfo = GameGraphics.getInstance().splitToLines(mMsg, TowerDimen.R_ALERT_INFO.width(), GameGraphics.getInstance().bigTextPaint);
        }
    }
    
    public void draw(GameGraphics graphics, Canvas canvas) {
        switch (mMode) {
        case MODE_MSG:
            graphics.drawBitmap(canvas, Assets.getInstance().bkgBlank, TowerDimen.R_MSG.left, TowerDimen.R_MSG.top,
                    TowerDimen.R_MSG.width(), TowerDimen.R_MSG.height());
            graphics.drawText(canvas, mMsg, TowerDimen.R_MSG.left + TowerDimen.TOWER_GRID_SIZE / 2,
                    TowerDimen.R_MSG.top + TowerDimen.BIG_TEXT_SIZE + (TowerDimen.R_MSG.height() - TowerDimen.BIG_TEXT_SIZE) / 2, graphics.bigTextPaint);
            break;
        case MODE_ALERT:
            graphics.drawBitmap(canvas, Assets.getInstance().bkgBlank, TowerDimen.R_ALERT.left, TowerDimen.R_ALERT.top,
                    TowerDimen.R_ALERT.width(), TowerDimen.R_ALERT.height());
            graphics.drawTextInCenter(canvas, mTitle, TowerDimen.R_ALERT_TITLE.left, TowerDimen.R_ALERT_TITLE.top,
                    TowerDimen.R_ALERT_TITLE.width(), TowerDimen.R_ALERT_TITLE.height(), graphics.bigTextPaint);
            for (int i = 0; i < mInfo.size(); i++) {
                graphics.drawText(canvas, mInfo.get(i), TowerDimen.R_ALERT_INFO.left + TowerDimen.TOWER_GRID_SIZE / 2,
                        TowerDimen.R_ALERT_INFO.top + TowerDimen.TOWER_GRID_SIZE * i + TowerDimen.BIG_TEXT_SIZE + (TowerDimen.TOWER_GRID_SIZE - TowerDimen.BIG_TEXT_SIZE) / 2, graphics.bigTextPaint);
            }
            
            break;
        case MODE_AUTO_SCROLL:
            break;
        }
    }
    
    public void onBtnKey(int btnId) {
        switch (btnId) {
        case BitmapButton.ID_OK:
            if (mMode == MODE_ALERT || mMode == MODE_AUTO_SCROLL) {
                game.status = Status.Playing;
            }
            break;
        }
    }
    
    private Handler handler = new MessagHandler(new WeakReference<SceneMessage>(this));
    
    private static final int MSG_DISPLAY_TIMEOUT = 20;
    
    private static final int MSGISPLAY_TIMEOUT_DELAY = 1000;
    
    private static final class MessagHandler extends Handler {
        private WeakReference<SceneMessage> wk;

        public MessagHandler(WeakReference<SceneMessage> wk) {
            super();
            this.wk = wk;
        }
        
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            SceneMessage messag = wk.get();
            if (msg.what == MSG_DISPLAY_TIMEOUT && messag != null) {
                messag.game.status = Status.Playing;
             }
        }
    }

}
