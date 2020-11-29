package com.game.magictower.secne;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint.Style;
import android.graphics.Point;
import android.graphics.Rect;
import android.view.MotionEvent;

import com.game.magictower.Game;
import com.game.magictower.Game.Status;
import com.game.magictower.GameView;
import com.game.magictower.R;
import com.game.magictower.res.Assets;
import com.game.magictower.res.GlobalSoundPool;
import com.game.magictower.res.TowerDimen;
import com.game.magictower.widget.BaseButton;

public class SceneJump extends BaseScene {

    private Rect[][] mFloorRect;
    private Rect[][] mEdgeRect;
    private String[][] mFloorName;
    private int mSelected;
    private Rect mTouchRect = new Rect(TowerDimen.R_JUMP.left + TowerDimen.GRID_SIZE / 2, TowerDimen.R_JUMP.top + TowerDimen.GRID_SIZE / 2,
                                    TowerDimen.R_JUMP.right - TowerDimen.GRID_SIZE / 2, TowerDimen.R_JUMP.bottom - TowerDimen.GRID_SIZE / 2);
    
    private Rect mBgd;
    
    public SceneJump(GameView parent, Context context, Game game, int id, int x, int y, int w, int h) {
        super(parent, context, game, id, x, y, w, h);
        mFloorRect = new Rect[5][];
        mEdgeRect = new Rect[5][];
        mFloorName = new String[5][];
        for(int i = 0; i < 5; i++) {
            mFloorRect[i] = new Rect[4];
            mEdgeRect[i] = new Rect[4];
            mFloorName[i] = new String[4];
            for (int j = 0; j < 4; j++) {
                mFloorRect[i][j] = new Rect(TowerDimen.R_JUMP_GRID);
                mFloorRect[i][j].offset(j * TowerDimen.R_JUMP_GRID.width(), i * TowerDimen.R_JUMP_GRID.height());
                mEdgeRect[i][j] = new Rect(mFloorRect[i][j].left + 5, mFloorRect[i][j].top + 5, mFloorRect[i][j].right - 5, mFloorRect[i][j].bottom - 5);
                mFloorName[i][j] = String.format(mContext.getResources().getString(R.string.jump_ordinal_floor), (j * 5 + i + 1));
            }
        }
        mBgd = new Rect(0, 0, TowerDimen.R_JUMP.width(), TowerDimen.R_JUMP.height());
    }
    
    public void show() {
        game.status = Status.Jumping;
        mSelected = 0;
        parent.requestRender();
    }
    
    @Override
    public boolean onTouch(MotionEvent event) {
        boolean result = false;
        switch (event.getAction()) {
        case MotionEvent.ACTION_DOWN:
            if (inBounds(event)) {
                result = true;
                Point point = getTouchGrid((int)event.getX(), (int)event.getY());
                int selected = point.x * 5 + point.y;
                if (selected != mSelected) {
                    mSelected = selected;
                } else {
                    doJump();
                }
                parent.requestRender();
            }
            break;
        case MotionEvent.ACTION_MOVE:
           result = true;
            break;
        case MotionEvent.ACTION_UP:
        case MotionEvent.ACTION_POINTER_UP:
        case MotionEvent.ACTION_CANCEL:
        case MotionEvent.ACTION_OUTSIDE:
            result = true;
            parent.requestRender();
            break;
        }
        return result;
    }
    
    @Override
    protected boolean inBounds(MotionEvent event){
        boolean result = mTouchRect.contains((int)event.getX(), (int)event.getY());
        return result;
    }
    
    private Point getTouchGrid(int x, int y) {
        Point point = new Point();
        point.x = (x - TowerDimen.R_JUMP_GRID.left) / TowerDimen.R_JUMP_GRID.width();
        point.y = (y - TowerDimen.R_JUMP_GRID.top) / TowerDimen.R_JUMP_GRID.height();
        return point;
    }
    
    @Override
    public void onDrawFrame(Canvas canvas) {
        super.onDrawFrame(canvas);
        graphics.drawBitmap(canvas, Assets.getInstance().bkgBlank, mBgd, TowerDimen.R_JUMP, null);
        graphics.drawRect(canvas, TowerDimen.R_JUMP);
        for(int i = 0; i < 5; i++) {
            for (int j = 0; j < 4; j++) {
                if ((j * 5 + i) == mSelected) {
                    graphics.textPaint.setStyle(Style.STROKE);
                    graphics.drawRect(canvas, mEdgeRect[i][j], graphics.textPaint);
                    graphics.textPaint.setStyle(Style.FILL);
                    graphics.drawTextInCenter(canvas, mFloorName[i][j], mFloorRect[i][j], graphics.textPaint);
                } else {
                    graphics.disableTextPaint.setStyle(Style.STROKE);
                    graphics.drawRect(canvas, mEdgeRect[i][j], graphics.disableTextPaint);
                    graphics.disableTextPaint.setStyle(Style.FILL);
                    graphics.drawTextInCenter(canvas, mFloorName[i][j], mFloorRect[i][j], graphics.disableTextPaint);
                }
            }
        }
    }
    
    @Override
    public void onAction(int id) {
        switch (id) {
        case BaseButton.ID_OK:
            doJump();
            break;
        case BaseButton.ID_DOWN:
        case BaseButton.ID_UP:
        case BaseButton.ID_LEFT:
        case BaseButton.ID_RIGHT:
            moveSelect(id);
            break;
        }
    }
    
    private void doJump() {
        if (mSelected + 1 <= game.npcInfo.maxFloor) {
            game.npcInfo.curFloor = mSelected + 1;
        }
        GlobalSoundPool.getInstance().playSound(Assets.getInstance().getSoundId(Assets.SND_ID_ZONE));
        game.player.move(game.tower.initPos[game.npcInfo.curFloor][0], game.tower.initPos[game.npcInfo.curFloor][1]);
        game.changeMusic();
        game.status = Status.Playing;
    }
    
    private void moveSelect(int id) {
        int i = mSelected % 5;
        int j = mSelected / 5;
        switch (id) {
        case BaseButton.ID_DOWN:
            if (i < 4) {
                i++;
                GlobalSoundPool.getInstance().playSound(Assets.getInstance().getSoundId(Assets.SND_ID_DONE));
            } else {
                GlobalSoundPool.getInstance().playSound(Assets.getInstance().getSoundId(Assets.SND_ID_COIN));
            }
            break;
        case BaseButton.ID_UP:
            if (i > 0) {
                i--;
                GlobalSoundPool.getInstance().playSound(Assets.getInstance().getSoundId(Assets.SND_ID_DONE));
            } else {
                GlobalSoundPool.getInstance().playSound(Assets.getInstance().getSoundId(Assets.SND_ID_COIN));
            }
            break;
        case BaseButton.ID_LEFT:
            if (j > 0) {
                j--;
                GlobalSoundPool.getInstance().playSound(Assets.getInstance().getSoundId(Assets.SND_ID_DONE));
            } else {
                GlobalSoundPool.getInstance().playSound(Assets.getInstance().getSoundId(Assets.SND_ID_COIN));
            }
            break;
        case BaseButton.ID_RIGHT:
            if (j < 3) {
                j++;
                GlobalSoundPool.getInstance().playSound(Assets.getInstance().getSoundId(Assets.SND_ID_DONE));
            } else {
                GlobalSoundPool.getInstance().playSound(Assets.getInstance().getSoundId(Assets.SND_ID_COIN));
            }
            break;
        }
        mSelected = j * 5 + i;
    }
}
