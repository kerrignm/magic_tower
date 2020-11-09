package com.game.magictower;

import java.util.HashSet;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;

import com.game.magictower.Game.Status;
import com.game.magictower.model.Monster;
import com.game.magictower.model.Player;
import com.game.magictower.res.Assets;
import com.game.magictower.res.GameGraphics;
import com.game.magictower.res.TowerDimen;
import com.game.magictower.widget.BaseButton;

public class SceneForecast {
    
    private Context mContext;
    
    private Game game;
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
    
    public SceneForecast(Context context, Game game) {
        mContext = context;
        this.game = game;
        mName = mContext.getResources().getString(R.string.monster_name);
        mHP = mContext.getResources().getString(R.string.monster_hp);
        mAttack = mContext.getResources().getString(R.string.monster_attack);
        mDefend = mContext.getResources().getString(R.string.monster_defend);
        mMoney = mContext.getResources().getString(R.string.monster_money);
        mLose = mContext.getResources().getString(R.string.monster_lose);
    }
    
    public static String forecast(Player player, Monster monster) {
        if (player.getAttack() <= monster.getDefend()) {
            return "???";
        } else if (player.getDefend() >= monster.getAttack()) {
            int lose = 0;
            if (monster.getId() == 50) {
                lose = player.getHp() / 4;
            } else if (monster.getId() == 57) {
                lose = player.getHp() / 3;
            }
            return lose + "";
        } else {
            int palyerAttack = player.getAttack() - monster.getDefend();
            int monsterHp = monster.getHp();
            int monsterAttack = monster.getAttack() - player.getDefend();
            int lose = 0;
            int times = 0;
            if (monsterHp % palyerAttack == 0) {
                times = monsterHp / palyerAttack;
            } else {
                times = monsterHp / palyerAttack + 1;
            }
            times -= 1;
            if (monster.getId() == 50) {
                lose = player.getHp() / 4;
                if (times > 1) {
                    lose = lose + (times - 1) * monsterAttack;
                }
            } else if (monster.getId() == 57) {
                lose = player.getHp() / 3;
                if (times > 1) {
                    lose = lose + (times - 1) * monsterAttack;
                }
            } else {
                lose = times * monsterAttack;
            }
            return lose + "";
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
            Monster monster;
            mRects = new Rect[mForecastSet.size()][];
            mHPs = new String[mForecastSet.size()];
            mAttacks = new String[mForecastSet.size()];
            mDefends = new String[mForecastSet.size()];
            mMoneys = new String[mForecastSet.size()];
            mLoses = new String[mForecastSet.size()];
            for (int i = 0; i < mForecastSet.size(); i++) {
                mRects[i] = new Rect[7];
                monster = game.monsters.get(mIds[i]);
                mRects[i][0] = new Rect(TowerDimen.R_FC_ICON);
                mRects[i][0].offset(0, (i + 1) * TowerDimen.TOWER_GRID_SIZE);
                mRects[i][1] = new Rect(TowerDimen.R_FC_NAME);
                mRects[i][1].offset(0, (i + 1) * TowerDimen.TOWER_GRID_SIZE);
                mRects[i][2] = new Rect(TowerDimen.R_FC_HP);
                mRects[i][2].offset(0, (i + 1) * TowerDimen.TOWER_GRID_SIZE);
                mRects[i][3] = new Rect(TowerDimen.R_FC_ATTACK);
                mRects[i][3].offset(0, (i + 1) * TowerDimen.TOWER_GRID_SIZE);
                mRects[i][4] = new Rect(TowerDimen.R_FC_DEFEND);
                mRects[i][4].offset(0, (i + 1) * TowerDimen.TOWER_GRID_SIZE);
                mRects[i][5] = new Rect(TowerDimen.R_FC_MONEY);
                mRects[i][5].offset(0, (i + 1) * TowerDimen.TOWER_GRID_SIZE);
                mRects[i][6] = new Rect(TowerDimen.R_FC_LOSE);
                mRects[i][6].offset(0, (i + 1) * TowerDimen.TOWER_GRID_SIZE);
                mHPs[i] = monster.getHp() + "";
                mAttacks[i] = monster.getAttack() + "";
                mDefends[i] = monster.getDefend() + "";
                mMoneys[i] = monster.getMoney() + " · " + monster.getExp();
                mLoses[i] = SceneForecast.forecast(game.player, monster);
            }
        }
}
    
    public void show() {
        getForecastInfo();
        game.status = Status.Looking;
    }
    
    public void draw(GameGraphics graphics, Canvas canvas) {
        graphics.drawBitmap(canvas, Assets.getInstance().bkgBlank, null, TowerDimen.R_FORECAST, null);
        graphics.drawRect(canvas, TowerDimen.R_FORECAST);
        
        if (mForecastSet.size() > 0) {
            graphics.drawTextInCenter(canvas, mName, TowerDimen.R_FC_NAME);
            graphics.drawTextInCenter(canvas, mHP, TowerDimen.R_FC_HP);
            graphics.drawTextInCenter(canvas, mAttack, TowerDimen.R_FC_ATTACK);
            graphics.drawTextInCenter(canvas, mDefend, TowerDimen.R_FC_DEFEND);
            graphics.drawTextInCenter(canvas, mMoney, TowerDimen.R_FC_MONEY);
            graphics.drawTextInCenter(canvas, mLose, TowerDimen.R_FC_LOSE);
            
            Monster monster;
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
    
    public void onBtnKey(int btnId) {
        switch (btnId) {
        case BaseButton.ID_LOOK:
            game.status = Status.Playing;
            break;
        }
    }
}
