package com.game.magictower.scene;

import java.util.HashSet;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;

import com.game.magictower.Game;
import com.game.magictower.Game.Status;
import com.game.magictower.GameView;
import com.game.magictower.R;
import com.game.magictower.model.Monster;
import com.game.magictower.model.Player;
import com.game.magictower.res.Assets;
import com.game.magictower.res.TowerDimen;
import com.game.magictower.widget.BaseButton;

public class SceneForecast extends BaseScene {
    
    private HashSet<Integer> mForecastSet = new HashSet<>();
    private Object[] mIds;
    private String[] mHPs;
    private String[] mAttacks;
    private String[] mDefends;
    private String[] mMoneys;
    private String[] mLoses;
    private Rect[][] mRects;
    
    private String mName;
    private String mHP;
    private String mAttack;
    private String mDefend;
    private String mMoney;
    private String mLose;
    
    private Rect mBgd;
    
    public SceneForecast(GameView parent, Context context, Game game, int id, int x, int y, int w, int h) {
        super(parent, context, game, id, x, y, w, h);
        mName = mContext.getResources().getString(R.string.txt_name);
        mHP = mContext.getResources().getString(R.string.txt_hp);
        mAttack = mContext.getResources().getString(R.string.txt_attack);
        mDefend = mContext.getResources().getString(R.string.txt_defend);
        mMoney = mContext.getResources().getString(R.string.txt_money_exp);
        mLose = mContext.getResources().getString(R.string.txt_lose);
        mBgd = new Rect(0, 0, TowerDimen.R_FORECAST.width(), TowerDimen.R_FORECAST.height());
        
        mRects = new Rect[10][];
        mHPs = new String[10];
        mAttacks = new String[10];
        mDefends = new String[10];
        mMoneys = new String[10];
        mLoses = new String[10];
        for (int i = 0; i < 10; i++) {
            mRects[i] = new Rect[7];
            mRects[i][0] = new Rect(TowerDimen.R_FC_ICON);
            mRects[i][0].offset(0, (i + 1) * TowerDimen.GRID_SIZE);
            mRects[i][1] = new Rect(TowerDimen.R_FC_NAME);
            mRects[i][1].offset(0, (i + 1) * TowerDimen.GRID_SIZE);
            mRects[i][2] = new Rect(TowerDimen.R_FC_HP);
            mRects[i][2].offset(0, (i + 1) * TowerDimen.GRID_SIZE);
            mRects[i][3] = new Rect(TowerDimen.R_FC_ATTACK);
            mRects[i][3].offset(0, (i + 1) * TowerDimen.GRID_SIZE);
            mRects[i][4] = new Rect(TowerDimen.R_FC_DEFEND);
            mRects[i][4].offset(0, (i + 1) * TowerDimen.GRID_SIZE);
            mRects[i][5] = new Rect(TowerDimen.R_FC_MONEY);
            mRects[i][5].offset(0, (i + 1) * TowerDimen.GRID_SIZE);
            mRects[i][6] = new Rect(TowerDimen.R_FC_LOSE);
            mRects[i][6].offset(0, (i + 1) * TowerDimen.GRID_SIZE);
        }
    }
    
    public static String forecast(Player player, Monster monster) {
        if (player.getAttack() <= monster.getDefend()) {
            return "???";
        } else if (player.getDefend() >= monster.getAttack()) {
            int lose = 0;
            if (monster.getMagicDamage() > 0) {
                lose = player.getHp() / monster.getMagicDamage();
            }
            return lose + "";
        } else {
            int palyerAttack = player.getAttack() - monster.getDefend();
            int monsterHp = monster.getHp();
            int monsterAttack = monster.getAttack() - player.getDefend();
            int times = monsterHp / palyerAttack;
            if (monsterHp % palyerAttack != 0) {
                times +=  1;
            }
            int lose = (times - 1) * monsterAttack;
            if (monster.getMagicDamage() > 0) {
                lose += player.getHp() / monster.getMagicDamage();
            }
            return lose + "";
        }
    }
    
    public void show() {
        getForecastInfo();
        if (mForecastSet.size() > 0) {
            game.status = Status.Looking;
        }
        parent.requestRender();
    }
    
    @Override
    public void onDrawFrame(Canvas canvas) {
        super.onDrawFrame(canvas);
        graphics.drawBitmap(canvas, Assets.getInstance().bkgBlank, mBgd, TowerDimen.R_FORECAST, null);
        graphics.drawRect(canvas, TowerDimen.R_FORECAST);
        
        if (mForecastSet.size() > 0) {
            graphics.drawTextInCenter(canvas, mName, TowerDimen.R_FC_NAME);
            graphics.drawTextInCenter(canvas, mHP, TowerDimen.R_FC_HP);
            graphics.drawTextInCenter(canvas, mAttack, TowerDimen.R_FC_ATTACK);
            graphics.drawTextInCenter(canvas, mDefend, TowerDimen.R_FC_DEFEND);
            graphics.drawTextInCenter(canvas, mMoney, TowerDimen.R_FC_MONEY);
            graphics.drawTextInCenter(canvas, mLose, TowerDimen.R_FC_LOSE);
            
            Monster monster = null;
            for (int i = 0; i < mForecastSet.size(); i++) {
                monster = game.monsters.get(mIds[i]);
                graphics.drawBitmap(canvas, Assets.getInstance().animMap0.get(mIds[i]), null, mRects[i][0], null);
                graphics.drawTextInCenter(canvas, monster.getName(), mRects[i][1]);
                graphics.drawTextInCenter(canvas, mHPs[i], mRects[i][2]);
                graphics.drawTextInCenter(canvas, mAttacks[i], mRects[i][3]);
                graphics.drawTextInCenter(canvas, mDefends[i], mRects[i][4]);
                graphics.drawTextInCenter(canvas, mMoneys[i], mRects[i][5]);
                graphics.drawTextInCenter(canvas, mLoses[i], mRects[i][6]);
            }
        }
    }
    
    @Override
    public void onAction(int id) {
        switch (id) {
        case BaseButton.ID_LOOK:
            game.status = Status.Playing;
            break;
        }
    }
    
    private void getForecastInfo() {
        mForecastSet.clear();
        for (int x = 0; x < game.lvMap[game.npcInfo.curFloor].length; x++) {
            for (int y = 0; y < game.lvMap[game.npcInfo.curFloor][x].length; y++) {
                int id = game.lvMap[game.npcInfo.curFloor][x][y];
                if ((id >= 40 && id <= 70) && !mForecastSet.contains(id)) {
                    mForecastSet.add(id);
                }
            }
        }
        if ( mForecastSet.size() > 0) {
            mIds = mForecastSet.toArray();
            Monster monster = null;
            for (int i = 0; i < mForecastSet.size(); i++) {
                monster = game.monsters.get(mIds[i]);
                mHPs[i] = monster.getHp() + "";
                mAttacks[i] = monster.getAttack() + "";
                mDefends[i] = monster.getDefend() + "";
                mMoneys[i] = monster.getMoney() + " · " + monster.getExp();
                mLoses[i] = SceneForecast.forecast(game.player, monster);
            }
        }
    }
}
