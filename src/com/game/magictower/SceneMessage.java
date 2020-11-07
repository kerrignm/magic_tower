package com.game.magictower;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.Handler;
import android.os.Message;

import com.game.magictower.Game.Status;
import com.game.magictower.res.Assets;
import com.game.magictower.res.GameGraphics;
import com.game.magictower.res.TowerDimen;
import com.game.magictower.widget.BaseButton;

public class SceneMessage {
    
    public final static int MODE_MSG = 0;
    public final static int MODE_ALERT = 1;
    public final static int MODE_AUTO_SCROLL = 2;
    
    private Context mContext;
    
    private Game game;
    
    private int mId;
    
    private String mTitle;
    private String mMsg;
    private int mMode;
    private ArrayList<String> mInfo;
    private int mMinY;
    private int mMaxY;
    private int mMaxScroll;
    private int mCurScroll;
    private Rect mClip;
    
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
        show(-1, title, msg, mode);
    }
    
    public void show(int id, String title, String msg, int mode) {
        mId = id;
        mTitle = title;
        mMsg = msg;
        mMode = mode;
        game.status = Status.Messaging;
        if (mMode == MODE_MSG) {
            handler.sendEmptyMessageDelayed(MSG_DISPLAY_TIMEOUT, MSGISPLAY_TIMEOUT_DELAY);
        } else {
            if (mId > 0) {
                ArrayList<String> msgs = game.storys.get(mId);
                mInfo = new ArrayList<String>();
                for (int i = 0; i < msgs.size(); i++) {
                    mInfo.addAll(GameGraphics.getInstance().splitToLines(msgs.get(i), TowerDimen.R_ALERT_INFO.width(), GameGraphics.getInstance().bigTextPaint));
                }
            } else {
                mInfo = GameGraphics.getInstance().splitToLines(mMsg, TowerDimen.R_ALERT_INFO.width(), GameGraphics.getInstance().bigTextPaint);
            }
        }
        if (mMode == MODE_AUTO_SCROLL) {
            mMaxScroll = mInfo.size() * TowerDimen.TOWER_GRID_SIZE - (TowerDimen.TOWER_GRID_SIZE - TowerDimen.BIG_TEXT_SIZE);
            mCurScroll = TowerDimen.TOWER_GRID_SIZE * 2 - TowerDimen.R_AUTO_SCROLL_INFO.height();
            mMinY = TowerDimen.R_AUTO_SCROLL_INFO.top + (TowerDimen.TOWER_GRID_SIZE - TowerDimen.BIG_TEXT_SIZE) / 2;
            mMaxY = TowerDimen.R_AUTO_SCROLL_INFO.bottom - (TowerDimen.TOWER_GRID_SIZE - TowerDimen.BIG_TEXT_SIZE) / 2 + TowerDimen.TOWER_GRID_SIZE;
            mClip = new Rect(TowerDimen.R_AUTO_SCROLL_INFO.left, mMinY, TowerDimen.R_AUTO_SCROLL_INFO.right, mMaxY - TowerDimen.TOWER_GRID_SIZE);
        } else if (mMode == MODE_ALERT) {
            TowerDimen.R_ALERT_INFO.bottom = TowerDimen.R_ALERT_INFO.top + TowerDimen.TOWER_GRID_SIZE * mInfo.size();
            TowerDimen.R_ALERT.bottom = TowerDimen.R_ALERT_INFO.bottom + TowerDimen.TOWER_GRID_SIZE / 2;
        }
    }
    
    public void draw(GameGraphics graphics, Canvas canvas) {
        switch (mMode) {
        case MODE_MSG:
            graphics.drawBitmap(canvas, Assets.getInstance().bkgBlank, TowerDimen.R_MSG.left, TowerDimen.R_MSG.top,
                    TowerDimen.R_MSG.width(), TowerDimen.R_MSG.height());
            graphics.drawRect(canvas, TowerDimen.R_MSG);
            graphics.drawTextInCenter(canvas, mMsg, TowerDimen.R_MSG, graphics.bigTextPaint);
            break;
        case MODE_ALERT:
            graphics.drawBitmap(canvas, Assets.getInstance().bkgBlank, TowerDimen.R_ALERT.left, TowerDimen.R_ALERT.top,
                    TowerDimen.R_ALERT.width(), TowerDimen.R_ALERT.height());
            graphics.drawRect(canvas, TowerDimen.R_ALERT);
            graphics.drawTextInCenter(canvas, mTitle, TowerDimen.R_ALERT_TITLE, graphics.bigTextPaint);
            for (int i = 0; i < mInfo.size(); i++) {
                graphics.drawText(canvas, mInfo.get(i), TowerDimen.R_ALERT_INFO.left,
                        TowerDimen.R_ALERT_INFO.top + TowerDimen.TOWER_GRID_SIZE * i + TowerDimen.BIG_TEXT_SIZE + (TowerDimen.TOWER_GRID_SIZE - TowerDimen.BIG_TEXT_SIZE) / 2, graphics.bigTextPaint);
            }
            break;
        case MODE_AUTO_SCROLL:
            graphics.drawBitmap(canvas, Assets.getInstance().bkgBlank, TowerDimen.R_AUTO_SCROLL.left, TowerDimen.R_AUTO_SCROLL.top,
                    TowerDimen.R_AUTO_SCROLL.width(), TowerDimen.R_AUTO_SCROLL.height());
            graphics.drawRect(canvas, TowerDimen.R_AUTO_SCROLL);
            int x = TowerDimen.R_AUTO_SCROLL_INFO.left;
            int y = TowerDimen.R_AUTO_SCROLL_INFO.top;
            canvas.save();
            canvas.clipRect(mClip);
            for (int i = 0; i < mInfo.size(); i++) {
                y = TowerDimen.R_AUTO_SCROLL_INFO.top + TowerDimen.BIG_TEXT_SIZE + TowerDimen.TOWER_GRID_SIZE * (i - 1) + (TowerDimen.TOWER_GRID_SIZE - TowerDimen.BIG_TEXT_SIZE) / 2 - mCurScroll;
                if ((y >= mMinY) &&(y <= mMaxY)) {
                    if (i == 0) {
                        x = TowerDimen.R_AUTO_SCROLL_INFO.left + (TowerDimen.R_AUTO_SCROLL_INFO.width() - GameGraphics.getInstance().getTextBounds(mInfo.get(i), graphics.bigTextPaint).width()) / 2;
                    } else {
                        x = TowerDimen.R_AUTO_SCROLL_INFO.left;
                    }
                    graphics.drawText(canvas, mInfo.get(i), x, y, graphics.bigTextPaint);
                }
            }
            canvas.restore();
            mCurScroll++;
            if (mCurScroll >= mMaxScroll) {
                messageOver();
            }
            break;
        }
    }
    
    public void onBtnKey(int btnId) {
        switch (btnId) {
        case BaseButton.ID_OK:
            if (mMode == MODE_ALERT) {
                game.status = Status.Playing;
            } else if (mMode == MODE_AUTO_SCROLL) {
                messageOver();
            }
            break;
        }
    }
    
    private void messageOver() {
        switch(mId) {
        case 1:
            game.status = Status.Playing;
            break;
        case 2:
            game.gameOver();
            game.status = Status.Playing;
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
