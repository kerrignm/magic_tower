package com.game.magictower;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.Paint.Style;

import com.game.magictower.Game.Status;
import com.game.magictower.res.Assets;
import com.game.magictower.res.GlobalSoundPool;
import com.game.magictower.res.TowerDimen;
import com.game.magictower.widget.BaseButton;

public class SceneJump extends BaseScene {

    private Rect[][] mFloorRect;
    private Rect[][] mEdgeRect;
    private String[][] mFloorName;
    private int mSeclet;
    
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
    }
    
    public void show() {
        game.status = Status.Jumping;
        mSeclet = 0;
    }
    
    @Override
    public void onDrawFrame(Canvas canvas) {
        super.onDrawFrame(canvas);
        graphics.drawBitmap(canvas, Assets.getInstance().bkgBlank, null, TowerDimen.R_JUMP, null);
        graphics.drawRect(canvas, TowerDimen.R_JUMP);
        for(int i = 0; i < 5; i++) {
            for (int j = 0; j < 4; j++) {
                if ((j * 5 + i) == mSeclet) {
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
            if (mSeclet + 1 <= game.npcInfo.maxFloor) {
                game.npcInfo.curFloor = mSeclet + 1;
            }
            GlobalSoundPool.getInstance().playSound(Assets.getInstance().getSoundId(Assets.SND_ID_ZONE));
            game.player.move(game.tower.initPos[game.npcInfo.curFloor][0], game.tower.initPos[game.npcInfo.curFloor][1]);
            game.changeMusic();
            game.status = Status.Playing;
            break;
        case BaseButton.ID_DOWN:
        case BaseButton.ID_UP:
        case BaseButton.ID_LEFT:
        case BaseButton.ID_RIGHT:
            moveSelect(id);
            break;
        }
    }
    
    private void moveSelect(int id) {
        int i = mSeclet % 5;
        int j = mSeclet / 5;
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
        mSeclet = j * 5 + i;
    }
}
