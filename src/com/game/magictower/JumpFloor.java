package com.game.magictower;

import android.graphics.Canvas;
import android.graphics.Rect;

import com.game.magictower.Game.Status;
import com.game.magictower.res.Assets;
import com.game.magictower.res.GameGraphics;
import com.game.magictower.res.LiveBitmap;
import com.game.magictower.res.TowerDimen;
import com.game.magictower.res.TowerMap;
import com.game.magictower.widget.BitmapButton;

public class JumpFloor {

    private Rect[][] mFloorRect;
    private String[][] mFloorName;
    private int mSeclet;
    
    private LiveBitmap mBg = Assets.getInstance().bkgBlank;
    
    private Game game;
    
    public JumpFloor(Game game) {
        this.game = game;
        mFloorRect = new Rect[5][];
        mFloorName = new String[5][];
        for(int i = 0; i < 5; i++) {
            mFloorRect[i] = new Rect[4];
            mFloorName[i] = new String[4];
            for (int j = 0; j < 4; j++) {
                mFloorRect[i][j] = new Rect(TowerDimen.R_JUMP_GRID);
                mFloorRect[i][j].offset(j * TowerDimen.R_JUMP_GRID.width(), i * TowerDimen.R_JUMP_GRID.height());
                mFloorName[i][j] = "第" + (j * 5 + i + 1) + "层";
            }
        }
    }
    
    public void show() {
        game.status = Status.Jumping;
        mSeclet = game.currentFloor - 1;
        if (mSeclet < 0) {
            mSeclet = 0;
        }
    }
    
    public void draw(GameGraphics graphics, Canvas canvas) {
        graphics.drawBitmap(canvas, mBg, null, TowerDimen.R_JUMP, null);
        int y;
        for(int i = 0; i < 5; i++) {
            for (int j = 0; j < 4; j++) {
                y = mFloorRect[i][j].top + GameGraphics.TEXT_SIZE + (mFloorRect[i][j].height() - GameGraphics.TEXT_SIZE) / 2;
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
        case BitmapButton.ID_OK:
            if (mSeclet + 1 > game.maxFloor) {
                game.currentFloor = game.maxFloor;
            } else {
                game.currentFloor = mSeclet + 1;
            }
            game.player.move(TowerMap.initPos[game.currentFloor][0], TowerMap.initPos[game.currentFloor][1]);
            game.status = Status.Playing;
            break;
        case BitmapButton.ID_DOWN:
        case BitmapButton.ID_UP:
        case BitmapButton.ID_LEFT:
        case BitmapButton.ID_RIGHT:
            moveSelect(btnId);
            break;
        }
    }
    
    private void moveSelect(int btnId) {
        int i = mSeclet % 5;
        int j = mSeclet / 5;
        switch (btnId) {
        case BitmapButton.ID_DOWN:
            i++;
            if (i > 4) {
                i = 0;
            }
            break;
        case BitmapButton.ID_UP:
            i--;
            if (i < 0) {
                i = 4;
            }
            break;
        case BitmapButton.ID_LEFT:
            j--;
            if (j < 0) {
                j = 3;
            }
            break;
        case BitmapButton.ID_RIGHT:
            j++;
            if (j > 3) {
                j = 0;
            }
            break;
        }
        mSeclet = j * 5 + i;
    }
}
