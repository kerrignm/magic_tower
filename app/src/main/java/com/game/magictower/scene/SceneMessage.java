package com.game.magictower.scene;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Shader;
import android.graphics.Typeface;

import com.game.magictower.Game;
import com.game.magictower.GameView;
import com.game.magictower.Game.Status;
import com.game.magictower.res.Assets;
import com.game.magictower.res.GameGraphics;
import com.game.magictower.res.TowerDimen;
import com.game.magictower.widget.BaseButton;

public class SceneMessage extends BaseScene {
    
    public final static int MODE_ALERT = 1;
    public final static int MODE_AUTO_SCROLL = 2;
    
    private int mId;
    
    private String mTitle;
    private int mMode;
    private ArrayList<String> mInfo;
    private int mMinY;
    private int mMaxY;
    private int mMaxScroll;
    private int mCurScroll;
    private Rect mClip;
    private final Rect mAlertBgd;
    private final Rect mScrollBgd;
    
    private final Paint shaderPaint = new Paint();
    private final LinearGradient fadeOutShader;
    private final LinearGradient fadeInShader;
    
    public SceneMessage(GameView parent, Context context, Game game, int id, int x, int y, int w, int h) {
        super(parent, context, game, id, x, y, w, h);
        shaderPaint.setAntiAlias(true);
        shaderPaint.setColor(TowerDimen.TEXT_COLOR);
        shaderPaint.setTextSize(TowerDimen.BIG_TEXT_SIZE);
        shaderPaint.setStrokeWidth(TowerDimen.LINE_WIDTH);
        shaderPaint.setStyle(Paint.Style.FILL);
        shaderPaint.setTypeface(Typeface.DEFAULT_BOLD);
        fadeOutShader = new LinearGradient(0, TowerDimen.R_AUTO_SCROLL_INFO.top + TowerDimen.GRID_SIZE, 0, TowerDimen.R_AUTO_SCROLL_INFO.top, Color.WHITE, 0, Shader.TileMode.CLAMP);
        fadeInShader = new LinearGradient(0, TowerDimen.R_AUTO_SCROLL_INFO.bottom, 0, TowerDimen.R_AUTO_SCROLL_INFO.bottom - TowerDimen.GRID_SIZE, 0, Color.WHITE, Shader.TileMode.CLAMP);
        mAlertBgd = new Rect(0, 0, TowerDimen.R_ALERT.width(), TowerDimen.R_ALERT.height());
        mScrollBgd = new Rect(0, 0, TowerDimen.R_AUTO_SCROLL.width(), TowerDimen.R_AUTO_SCROLL.height());
    }
    
    public void show(String title, String msg) {
        show(-1, title, msg, MODE_ALERT);
    }
    
    public void show(int id) {
        show(id, null, null, MODE_AUTO_SCROLL);
    }
    
    public void show(int id, String title, String msg, int mode) {
        mId = id;
        mTitle = title;
        mMode = mode;
        game.status = Status.Messaging;
        if (mMode == MODE_ALERT) {
            mInfo = GameGraphics.getInstance().splitToLines(msg, TowerDimen.R_ALERT_INFO.width(), GameGraphics.getInstance().bigTextPaint);
            TowerDimen.R_ALERT_INFO.bottom = TowerDimen.R_ALERT_INFO.top + TowerDimen.GRID_SIZE * mInfo.size();
            TowerDimen.R_ALERT.bottom = TowerDimen.R_ALERT_INFO.bottom + TowerDimen.GRID_SIZE / 2;
        } else {
            ArrayList<String> msgs = game.storys.get(mId);
            mInfo = new ArrayList<>();
            for (int i = 0; i < msgs.size(); i++) {
                mInfo.addAll(GameGraphics.getInstance().splitToLines(msgs.get(i), TowerDimen.R_ALERT_INFO.width(), GameGraphics.getInstance().bigTextPaint));
            }
            mMaxScroll = mInfo.size() * TowerDimen.GRID_SIZE - (TowerDimen.GRID_SIZE - TowerDimen.BIG_TEXT_SIZE);
            mCurScroll = TowerDimen.GRID_SIZE * 2 - TowerDimen.R_AUTO_SCROLL_INFO.height();
            mMinY = TowerDimen.R_AUTO_SCROLL_INFO.top + (TowerDimen.GRID_SIZE - TowerDimen.BIG_TEXT_SIZE) / 2;
            mMaxY = TowerDimen.R_AUTO_SCROLL_INFO.bottom - (TowerDimen.GRID_SIZE - TowerDimen.BIG_TEXT_SIZE) / 2 + TowerDimen.GRID_SIZE;
            mClip = new Rect(TowerDimen.R_AUTO_SCROLL_INFO.left, mMinY, TowerDimen.R_AUTO_SCROLL_INFO.right, mMaxY - TowerDimen.GRID_SIZE);
        }
        parent.requestRender();
    }
    
    @Override
    public void onDrawFrame(Canvas canvas) {
        super.onDrawFrame(canvas);
        switch (mMode) {
        case MODE_ALERT:
            graphics.drawBitmap(canvas, Assets.getInstance().bkgBlank, mAlertBgd, TowerDimen.R_ALERT, null);
            graphics.drawRect(canvas, TowerDimen.R_ALERT);
            graphics.drawTextInCenter(canvas, mTitle, TowerDimen.R_ALERT_TITLE, graphics.bigTextPaint);
            for (int i = 0; i < mInfo.size(); i++) {
                graphics.drawText(canvas, mInfo.get(i), TowerDimen.R_ALERT_INFO.left,
                        TowerDimen.R_ALERT_INFO.top + TowerDimen.GRID_SIZE * i + TowerDimen.BIG_TEXT_SIZE + (TowerDimen.GRID_SIZE - TowerDimen.BIG_TEXT_SIZE) / 2, graphics.bigTextPaint);
            }
            break;
        case MODE_AUTO_SCROLL:
            graphics.drawBitmap(canvas, Assets.getInstance().bkgBlank, mScrollBgd, TowerDimen.R_AUTO_SCROLL, null);
            graphics.drawRect(canvas, TowerDimen.R_AUTO_SCROLL);
            int x;
            int y;
            canvas.save();
            canvas.clipRect(mClip);
            for (int i = 0; i < mInfo.size(); i++) {
                y = TowerDimen.R_AUTO_SCROLL_INFO.top + TowerDimen.BIG_TEXT_SIZE + TowerDimen.GRID_SIZE * i + (TowerDimen.GRID_SIZE - TowerDimen.BIG_TEXT_SIZE) / 2 - mCurScroll;
                if ((y >= mMinY) && (y <= mMaxY)) {
                    if (i == 0) {
                        x = TowerDimen.R_AUTO_SCROLL_INFO.left + (TowerDimen.R_AUTO_SCROLL_INFO.width() - (int)graphics.bigTextPaint.measureText(mInfo.get(i))) / 2;
                    } else {
                        x = TowerDimen.R_AUTO_SCROLL_INFO.left;
                    }
                    if (y <= TowerDimen.R_AUTO_SCROLL_INFO.top + TowerDimen.GRID_SIZE * 2) {
                        shaderPaint.setShader(fadeOutShader);
                        graphics.drawText(canvas, mInfo.get(i), x, y, shaderPaint);
                    } else if (y >= TowerDimen.R_AUTO_SCROLL_INFO.bottom - TowerDimen.GRID_SIZE * 2) {
                        shaderPaint.setShader(fadeInShader);
                        graphics.drawText(canvas, mInfo.get(i), x, y, shaderPaint);
                    } else {
                        graphics.drawText(canvas, mInfo.get(i), x, y, graphics.bigTextPaint);
                    }
                }
            }
            canvas.restore();
            mCurScroll++;
            parent.requestRender();
            if (mCurScroll >= mMaxScroll) {
                messageOver();
            }
            break;
        }
    }
    
    @Override
    public void onAction(int id) {
        if (id == BaseButton.ID_OK) {
            if (mMode == MODE_ALERT) {
                game.status = Status.Playing;
            } else if (mMode == MODE_AUTO_SCROLL) {
                messageOver();
            }
        }
    }
    
    private void messageOver() {
        switch (mId) {
        case 1:
            game.status = Status.Playing;
            break;
        case 2:
            game.gameOver();
            game.status = Status.Playing;
            break;
        }
        parent.requestRender();
    }

}
