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
                mFloorRect[i][j].offset(j * TowerDimen.TOWER_GRID_SIZE * 3 / 2, i * TowerDimen.TOWER_GRID_SIZE);
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
        
        for(int i = 0; i < 5; i++) {
            for (int j = 0; j < 4; j++) {
                if ((j * 5 + i) == mSeclet) {
                    graphics.drawText(canvas, mFloorName[i][j], mFloorRect[i][j].left, mFloorRect[i][j].top, graphics.textPaint);
                } else {
                    graphics.drawText(canvas, mFloorName[i][j], mFloorRect[i][j].left, mFloorRect[i][j].top, graphics.disableTextPaint);
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
    }
}
