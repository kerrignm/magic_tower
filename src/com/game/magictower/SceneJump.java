package com.game.magictower;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;

import com.game.magictower.Game.Status;
import com.game.magictower.res.Assets;
import com.game.magictower.res.GameGraphics;
import com.game.magictower.res.GlobalSoundPool;
import com.game.magictower.res.TowerDimen;
import com.game.magictower.widget.BaseButton;

public class SceneJump {
    
    private Context mContext;

    private Rect[][] mFloorRect;
    private String[][] mFloorName;
    private int mSeclet;
    
    private Game game;
    
    public SceneJump(Context context, Game game) {
        mContext = context;
        this.game = game;
        mFloorRect = new Rect[5][];
        mFloorName = new String[5][];
        for(int i = 0; i < 5; i++) {
            mFloorRect[i] = new Rect[4];
            mFloorName[i] = new String[4];
            for (int j = 0; j < 4; j++) {
                mFloorRect[i][j] = new Rect(TowerDimen.R_JUMP_GRID);
                mFloorRect[i][j].offset(j * TowerDimen.R_JUMP_GRID.width(), i * TowerDimen.R_JUMP_GRID.height());
                mFloorName[i][j] = String.format(mContext.getResources().getString(R.string.jump_ordinal_floor), (j * 5 + i + 1));
            }
        }
    }
    
    public void show() {
        game.status = Status.Jumping;
        mSeclet = 0;/*game.npcInfo.curFloor - 1;
        if (mSeclet < 0) {
            mSeclet = 0;
        }*/
    }
    
    public void draw(GameGraphics graphics, Canvas canvas) {
        graphics.drawBitmap(canvas, Assets.getInstance().bkgBlank, null, TowerDimen.R_JUMP, null);
        graphics.drawRect(canvas, TowerDimen.R_JUMP);
        int y;
        for(int i = 0; i < 5; i++) {
            for (int j = 0; j < 4; j++) {
                y = mFloorRect[i][j].top + TowerDimen.TEXT_SIZE + (mFloorRect[i][j].height() - TowerDimen.TEXT_SIZE) / 2;
                if ((j * 5 + i) == mSeclet) {
                    graphics.drawText(canvas, mFloorName[i][j], mFloorRect[i][j].left, y, graphics.textPaint);
                } else {
                    graphics.drawText(canvas, mFloorName[i][j], mFloorRect[i][j].left, y, graphics.disableTextPaint);
                }
            }
        }
    }
    
    public void onBtnKey(int btnId) {
        switch (btnId) {
        case BaseButton.ID_OK:
            if (mSeclet + 1 > game.npcInfo.maxFloor) {
                game.npcInfo.curFloor = game.npcInfo.maxFloor;
            } else {
                game.npcInfo.curFloor = mSeclet + 1;
            }
            GlobalSoundPool.getInstance().playSound(Assets.getInstance().getSoundId(Assets.SND_ID_ZONE));
            game.player.move(game.tower.initPos[game.npcInfo.curFloor][0], game.tower.initPos[game.npcInfo.curFloor][1]);
            game.status = Status.Playing;
            break;
        case BaseButton.ID_DOWN:
        case BaseButton.ID_UP:
        case BaseButton.ID_LEFT:
        case BaseButton.ID_RIGHT:
            moveSelect(btnId);
            break;
        }
    }
    
    private void moveSelect(int btnId) {
        int i = mSeclet % 5;
        int j = mSeclet / 5;
        switch (btnId) {
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
