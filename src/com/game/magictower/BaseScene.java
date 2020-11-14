package com.game.magictower;

import java.util.HashMap;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;

import com.game.magictower.res.Assets;
import com.game.magictower.res.LiveBitmap;
import com.game.magictower.res.TowerDimen;
import com.game.magictower.util.LogUtil;
import com.game.magictower.widget.BaseView;

public abstract class BaseScene extends BaseView {
    
    private static final String TAG = "MagicTower:BaseScene";
    
    private static final boolean DBG_DRAW = false;
    
    protected Context mContext;
    
    protected Game game;
    
    protected GameView parent;
    
    protected boolean animFlag;
    private long lastTime;
    private HashMap<Integer, LiveBitmap> animMap;
    
    public BaseScene(GameView parent, Context context, Game game, int id, int x, int y, int w, int h) {
        super(id, x, y, w, h);
        mContext = context;
        this.parent = parent;
        this.game = game;
        animMap = Assets.getInstance().animMap0;
        lastTime = System.currentTimeMillis();
        animFlag = true;
    }
    
    public abstract void onAction(int id);

    public void onDrawFrame(Canvas canvas) {
        if (DBG_DRAW) LogUtil.d(TAG, "onDrawFrame() status = " + game.status.toString());
        canvas.drawColor(Color.BLACK);
        graphics.drawBitmap(canvas, Assets.getInstance().bkgGame, TowerDimen.TOWER_LEFT, TowerDimen.TOWER_TOP);
        drawInfoPanel(canvas);
        drawTower(canvas);
        updateAnim();
    }
    
    private void updateAnim() {
        if (System.currentTimeMillis() - lastTime >= 500) {
            lastTime = System.currentTimeMillis();
            animFlag = !animFlag;
        }
        if (animFlag) {
            animMap = Assets.getInstance().animMap0;
        } else {
            animMap = Assets.getInstance().animMap1;
        }
    }
    
    private void drawInfoPanel(Canvas canvas) {
        graphics.drawTextInCenter(canvas, game.player.getLevel() + "", TowerDimen.R_PLR_LEVEL);
        graphics.drawTextInCenter(canvas, game.player.getHp() + "", TowerDimen.R_PLR_HP);
        graphics.drawTextInCenter(canvas, game.player.getAttack() + "", TowerDimen.R_PLR_ATTACK);
        graphics.drawTextInCenter(canvas, game.player.getDefend() + "", TowerDimen.R_PLR_DEFEND);
        graphics.drawTextInCenter(canvas, game.player.getMoney() + "", TowerDimen.R_PLR_MONEY);
        graphics.drawTextInCenter(canvas, game.player.getExp() + "", TowerDimen.R_PLR_EXP);

        graphics.drawTextInCenter(canvas, game.player.getYkey() + "", TowerDimen.R_YKEY);
        graphics.drawTextInCenter(canvas, game.player.getBkey() + "", TowerDimen.R_BKEY);
        graphics.drawTextInCenter(canvas, game.player.getRkey() + "", TowerDimen.R_RKEY);
        
        graphics.drawTextInCenter(canvas, game.npcInfo.curFloor + "", TowerDimen.R_FLOOR);
    }
    
    private void drawTower(Canvas canvas) {
        if (DBG_DRAW) LogUtil.d(TAG, "drawTower()");
        for (int x = 0; x < 11; x++) {
            for (int y = 0; y < 11; y++) {
                int id = game.lvMap[game.npcInfo.curFloor][x][y];
                if (id >= 100) {
                    id = 0;   //monitor items invisible, draw ground
                }
                LiveBitmap bitmap = animMap.get(id);
                graphics.drawBitmap(canvas, bitmap, TowerDimen.TOWER_LEFT + (y + 6) * TowerDimen.TOWER_GRID_SIZE, TowerDimen.TOWER_TOP + (x + 1) * TowerDimen.TOWER_GRID_SIZE);
            }
        }

        graphics.drawBitmap(canvas, game.player.getImage(), TowerDimen.TOWER_LEFT + (game.player.getPosX() + 6) * TowerDimen.TOWER_GRID_SIZE, TowerDimen.TOWER_TOP + (game.player.getPosY() + 1) * TowerDimen.TOWER_GRID_SIZE);
    }
}
