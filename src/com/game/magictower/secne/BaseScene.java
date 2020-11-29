package com.game.magictower.secne;

import java.util.HashMap;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;

import com.game.magictower.Game;
import com.game.magictower.GameView;
import com.game.magictower.R;
import com.game.magictower.res.Assets;
import com.game.magictower.res.TowerDimen;
import com.game.magictower.util.LogUtil;
import com.game.magictower.widget.BaseView;

public abstract class BaseScene extends BaseView {
    
    private static final String TAG = "MagicTower:BaseScene";
    
    private static final boolean DBG_DRAW = false;
    
    protected static boolean sAnimFlag;
    private static HashMap<Integer, Bitmap> animMap;
    
    protected Context mContext;
    
    protected Game game;
    
    protected GameView parent;
    
    private String mLevel;
    private String mNumber;
    private String mSerial;
    private String mLayer;
    private String mPrologue;
    
    private String mHp;
    private String mAttack;
    private String mDefend;
    private String mMoney;
    private String mExp;
    
    public static void updateAni() {
        sAnimFlag = !sAnimFlag;
        if (sAnimFlag) {
            animMap = Assets.getInstance().animMap0;
        } else {
            animMap = Assets.getInstance().animMap1;
        }
    }
    
    public BaseScene(GameView parent, Context context, Game game, int id, int x, int y, int w, int h) {
        super(id, x, y, w, h);
        mContext = context;
        this.parent = parent;
        this.game = game;
        animMap = Assets.getInstance().animMap0;
        sAnimFlag = true;
        
        mLevel = mContext.getResources().getString(R.string.panel_level);
        mNumber = mContext.getResources().getString(R.string.panel_number);
        mSerial = mContext.getResources().getString(R.string.panel_serial);
        mLayer = mContext.getResources().getString(R.string.panel_layer);
        mPrologue = mContext.getResources().getString(R.string.panel_prologue);
        
        mHp = mContext.getResources().getString(R.string.txt_hp);
        mAttack = mContext.getResources().getString(R.string.txt_attack);
        mDefend = mContext.getResources().getString(R.string.txt_defend);
        mMoney = mContext.getResources().getString(R.string.txt_money);
        mExp = mContext.getResources().getString(R.string.txt_exp);
    }
    
    public abstract void onAction(int id);

    public void onDrawFrame(Canvas canvas) {
        if (DBG_DRAW) LogUtil.d(TAG, "onDrawFrame() status = " + game.status.toString());
        canvas.drawColor(Color.BLACK);
        graphics.drawBitmap(canvas, Assets.getInstance().bkgBlank, 0, 0);
        graphics.drawBitmap(canvas, Assets.getInstance().bkgTower, TowerDimen.TOWER_LEFT - TowerDimen.GRID_SIZE / 2, TowerDimen.TOWER_TOP - TowerDimen.GRID_SIZE / 2);
        drawInfoPanel(canvas);
        drawTower(canvas);
    }
    
    private void drawInfoPanel(Canvas canvas) {
        graphics.drawBitmap(canvas, Assets.getInstance().playerMap.get(-2), null, TowerDimen.R_PLR_ICON, null);
        graphics.drawTextInCenter(canvas, game.player.getLevel() + "", TowerDimen.R_PLR_LV_V);
        graphics.drawTextInCenter(canvas, mLevel, TowerDimen.R_PLR_LV_L);

        graphics.drawBitmap(canvas, Assets.getInstance().animMap0.get(6), null, TowerDimen.R_YKEY_ICON, null);
        graphics.drawTextInCenter(canvas, game.player.getYkey() + "", TowerDimen.R_YKEY_V);
        graphics.drawTextInCenter(canvas, mNumber, TowerDimen.R_YKEY_L);
        
        graphics.drawBitmap(canvas, Assets.getInstance().animMap0.get(7), null, TowerDimen.R_BKEY_ICON, null);
        graphics.drawTextInCenter(canvas, game.player.getBkey() + "", TowerDimen.R_BKEY_V);
        graphics.drawTextInCenter(canvas, mNumber, TowerDimen.R_BKEY_L);
        
        graphics.drawBitmap(canvas, Assets.getInstance().animMap0.get(8), null, TowerDimen.R_RKEY_ICON, null);
        graphics.drawTextInCenter(canvas, game.player.getRkey() + "", TowerDimen.R_RKEY_V);
        graphics.drawTextInCenter(canvas, mNumber, TowerDimen.R_RKEY_L);
        
        if (game.npcInfo.curFloor > 0) {
            graphics.drawTextInCenter(canvas, mSerial, TowerDimen.R_FLOOR_S);
            graphics.drawTextInCenter(canvas, game.npcInfo.curFloor + "", TowerDimen.R_FLOOR_V);
            graphics.drawTextInCenter(canvas, mLayer, TowerDimen.R_FLOOR_L);
        } else {
            graphics.drawTextInCenter(canvas, mPrologue, TowerDimen.R_FLOOR_V);
        }
        
        graphics.drawTextInCenter(canvas, mHp, TowerDimen.R_PLR_HP_L);
        graphics.drawTextInCenter(canvas, game.player.getHp() + "", TowerDimen.R_PLR_HP_V);
        
        graphics.drawTextInCenter(canvas, mAttack, TowerDimen.R_PLR_ATTACK_L);
        graphics.drawTextInCenter(canvas, game.player.getAttack() + "", TowerDimen.R_PLR_ATTACK_V);
        
        graphics.drawTextInCenter(canvas, mDefend, TowerDimen.R_PLR_DEFEND_L);
        graphics.drawTextInCenter(canvas, game.player.getDefend() + "", TowerDimen.R_PLR_DEFEND_V);
        
        graphics.drawTextInCenter(canvas, mMoney, TowerDimen.R_PLR_MONEY_L);
        graphics.drawTextInCenter(canvas, game.player.getMoney() + "", TowerDimen.R_PLR_MONEY_V);
        
        graphics.drawTextInCenter(canvas, mExp, TowerDimen.R_PLR_EXP_L);
        graphics.drawTextInCenter(canvas, game.player.getExp() + "", TowerDimen.R_PLR_EXP_V);
    }
    
    private void drawTower(Canvas canvas) {
        if (DBG_DRAW) LogUtil.d(TAG, "drawTower()");
        for (int x = 0; x < 11; x++) {
            for (int y = 0; y < 11; y++) {
                int id = game.lvMap[game.npcInfo.curFloor][x][y];
                if (id >= 100) {
                    id = 0;   //monitor items invisible, draw ground
                }
                graphics.drawBitmap(canvas, animMap.get(id), TowerDimen.TOWER_LEFT + y * TowerDimen.GRID_SIZE, TowerDimen.TOWER_TOP + x * TowerDimen.GRID_SIZE);
            }
        }

        graphics.drawBitmap(canvas, game.player.getImage(), TowerDimen.TOWER_LEFT + game.player.getPosX() * TowerDimen.GRID_SIZE, TowerDimen.TOWER_TOP + game.player.getPosY() * TowerDimen.GRID_SIZE);
    }
}
