package com.game.magictower;

import java.util.HashSet;

import android.graphics.Canvas;
import android.graphics.Rect;

import com.game.magictower.Game.Status;
import com.game.magictower.res.Assets;
import com.game.magictower.res.GameGraphics;
import com.game.magictower.res.MonsterData;
import com.game.magictower.res.TowerDimen;
import com.game.magictower.widget.BitmapButton;

public class Forecast {
    
    private Game game;
    private HashSet<Integer> mForecastSet = new HashSet<>();
    private Object[] mIds;
    private String[] mHPs;
    private String[] mAttacks;
    private String[] mDefends;
    private String[] mMoneys;
    private String[] mLoses;
    private Rect[][] mRects;
    
    public Forecast(Game game) {
        this.game = game;
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
            int firstAttack = monsterAttack;
            int times = 0;
            if (palyerAttack % monsterHp == 0) {
                times = palyerAttack / monsterHp;
            } else {
                times = palyerAttack / monsterHp + 1;
            }
            if (monster.getId() == 50) {
                firstAttack = player.getHp() / 4;
            } else if (monster.getId() == 57) {
                firstAttack = player.getHp() / 3;
            }
            return firstAttack + (times - 1) * monsterAttack + "";
        }
    }
    
    private void getForecastInfo() {
        mForecastSet.clear();
        for (int x = 0; x < game.lvMap[game.currentFloor].length; x++) {
            for (int y = 0; y < game.lvMap[game.currentFloor][x].length; y++) {
                int id = game.lvMap[game.currentFloor][x][y];
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
                monster = MonsterData.monsterMap.get(mIds[i]);
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
                mLoses[i] = Forecast.forecast(game.player, monster);
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
            graphics.drawTextInCenter(canvas, "名称", TowerDimen.R_FC_NAME.left, TowerDimen.R_FC_NAME.top, TowerDimen.R_FC_NAME.width(), TowerDimen.R_FC_NAME.height());
            graphics.drawTextInCenter(canvas, "生命", TowerDimen.R_FC_HP.left, TowerDimen.R_FC_HP.top, TowerDimen.R_FC_HP.width(), TowerDimen.R_FC_HP.height());
            graphics.drawTextInCenter(canvas, "攻击", TowerDimen.R_FC_ATTACK.left, TowerDimen.R_FC_ATTACK.top, TowerDimen.R_FC_ATTACK.width(), TowerDimen.R_FC_ATTACK.height());
            graphics.drawTextInCenter(canvas, "防御", TowerDimen.R_FC_DEFEND.left, TowerDimen.R_FC_DEFEND.top, TowerDimen.R_FC_DEFEND.width(), TowerDimen.R_FC_DEFEND.height());
            graphics.drawTextInCenter(canvas, "金 · 经：", TowerDimen.R_FC_MONEY.left, TowerDimen.R_FC_MONEY.top, TowerDimen.R_FC_MONEY.width(), TowerDimen.R_FC_MONEY.height());
            graphics.drawTextInCenter(canvas, "损失", TowerDimen.R_FC_LOSE.left, TowerDimen.R_FC_LOSE.top, TowerDimen.R_FC_LOSE.width(), TowerDimen.R_FC_LOSE.height());
            
            Monster monster;
            for (int i = 0; i < mForecastSet.size(); i++) {
                monster = MonsterData.monsterMap.get(mIds[i]);
                graphics.drawBitmap(canvas, Assets.getInstance().animMap0.get(mIds[i]), null, mRects[i][0], null);
                graphics.drawTextInCenter(canvas, monster.getName(), mRects[i][1].left, mRects[i][1].top, mRects[i][1].width(), mRects[i][1].height());
                graphics.drawTextInCenter(canvas, mHPs[i], mRects[i][2].left, mRects[i][2].top, mRects[i][2].width(), mRects[i][2].height());
                graphics.drawTextInCenter(canvas, mAttacks[i], mRects[i][3].left, mRects[i][3].top, mRects[i][3].width(), mRects[i][3].height());
                graphics.drawTextInCenter(canvas, mDefends[i], mRects[i][4].left, mRects[i][4].top, mRects[i][4].width(), mRects[i][4].height());
                graphics.drawTextInCenter(canvas, mMoneys[i], mRects[i][5].left, mRects[i][5].top, mRects[i][5].width(), mRects[i][5].height());
                graphics.drawTextInCenter(canvas, mLoses[i], mRects[i][6].left, mRects[i][6].top, mRects[i][6].width(), mRects[i][6].height());
            }
        }
    }
    
    public void onBtnKey(int btnId) {
        switch (btnId) {
        case BitmapButton.ID_LOOK:
            game.status = Status.Playing;
            break;
        }
    }
}
