package com.game.magictower;

import java.lang.ref.WeakReference;

import android.content.Context;
import android.graphics.Canvas;
import android.os.Handler;
import android.os.Message;

import com.game.magictower.Game.Status;
import com.game.magictower.res.Assets;
import com.game.magictower.res.GameGraphics;
import com.game.magictower.res.LiveBitmap;
import com.game.magictower.res.MonsterData;
import com.game.magictower.res.TowerDimen;

public class SceneBattle {
    
    private Context mContext;
    
    private Game game;
    private Monster mMonster;
    private boolean mPlayerRound;
    private int mX;
    private int mY;
    private int mHp;
    private LiveBitmap mMstIcon;
    private int mAttack;
    private int mDefend;
    private String mMstHp;
    private String mMstAttack;
    private String mMstDefend;
    private String mPlrHp;
    private String mPlrAttack;
    private String mPlrDefend;
    
    private boolean mMagicAttack;
    
    private Handler handler = new FightHandler(new WeakReference<SceneBattle>(this));
    
    private static final int MSG_ID_FIGHT = 1;
    
    private static final int MSG_DELAY_REMOVE_MSG = 300;
    
    private static final class FightHandler extends Handler {
        private WeakReference<SceneBattle> wk;

        public FightHandler(WeakReference<SceneBattle> wk) {
            super();
            this.wk = wk;
        }
        
        @Override
        public void handleMessage(Message msg) {
            SceneBattle battle = wk.get();
            if (msg.what == MSG_ID_FIGHT && battle != null) {
                battle.attack();
                battle.getHpInfo();
                if (battle.mHp <= 0) {
                    battle.game.player.setExp(battle.game.player.getExp() + battle.mMonster.getExp());
                    battle.game.player.setMoney(battle.game.player.getMoney() + battle.mMonster.getMoney());
                    if ((battle.game.currentFloor == 19) && (battle.mMonster.getId() == 59)) {
                        battle.game.dialog.show(13, 59);
                    } else if ((battle.game.currentFloor == 16) && (battle.mMonster.getId() == 53)) {
                        battle.game.dialog.show(10, 59);
                    } else if ((battle.game.currentFloor == 21) && (battle.mMonster.getId() == 59)) {
                        battle.game.dialog.show(14, 59);
                    } else {
                        battle.game.message.show(battle.mContext.getResources().getString(R.string.get_money)
                                    + battle.mMonster.getExp() + battle.mContext.getResources().getString(R.string.get_exp)
                                    + battle.mMonster.getMoney() + battle.mContext.getResources().getString(R.string.exclamatory_mark));
                    }
                    battle.game.lvMap[battle.game.currentFloor][battle.mY][battle.mX] = 0;
                } else {
                    sendEmptyMessageDelayed(MSG_ID_FIGHT, MSG_DELAY_REMOVE_MSG);
                }
            }
            super.handleMessage(msg);
        }
    };
    
    public SceneBattle(Context context, Game game) {
        mContext = context;
        this.game = game;
    }

    public void show(int id, int x, int y) {
        mMonster = MonsterData.monsterMap.get(id);
        mMstIcon = Assets.getInstance().animMap0.get(id);
        mX = x;
        mY = y;
        mHp = mMonster.getHp();
        mAttack = mMonster.getAttack();
        mDefend = mMonster.getDefend();
        mMstAttack = mAttack + "";
        mMstDefend = mDefend + "";
        mPlrAttack = game.player.getAttack() + "";
        mPlrDefend = game.player.getDefend() + "";
        mPlayerRound = true;
        mMagicAttack = false;
        getHpInfo();
        game.status = Status.Fighting;
        handler.sendEmptyMessageDelayed(MSG_ID_FIGHT, MSG_DELAY_REMOVE_MSG);
    }
    
    private void getHpInfo() {
        mMstHp = mHp + "";
        mPlrHp = game.player.getHp() + "";
    }
    
    private void attack() {
        if (mPlayerRound) {
            if (game.player.getAttack() > mDefend) {
                mHp = mHp - game.player.getAttack() + mDefend;
                if (mHp <= 0) {
                    mHp = 0;
                    if (!mMagicAttack && (mMonster.getId() == 50)) {
                        mMagicAttack = true;
                        game.player.setHp(game.player.getHp() - game.player.getHp() / 4);
                    } else if (!mMagicAttack && (mMonster.getId() == 57)) {
                        mMagicAttack = true;
                        game.player.setHp(game.player.getHp() - game.player.getHp() / 3);
                    }
                }
            }
        } else {
            if (!mMagicAttack && (mMonster.getId() == 50)) {
                mMagicAttack = true;
                game.player.setHp(game.player.getHp() - game.player.getHp() / 4);
            } else if (!mMagicAttack && (mMonster.getId() == 57)) {
                mMagicAttack = true;
                game.player.setHp(game.player.getHp() - game.player.getHp() / 3);
            } else if (mAttack > game.player.getDefend()) {
                game.player.setHp(game.player.getHp() - mAttack + game.player.getDefend());
            }
        }
        mPlayerRound = !mPlayerRound;
    }
    
    public void draw(GameGraphics graphics, Canvas canvas) {
        graphics.drawBitmap(canvas, Assets.getInstance().bkgBattle, null, TowerDimen.R_BATTLE, null);
        graphics.drawBitmap(canvas, mMstIcon, null, TowerDimen.R_BTL_MST_ICON, null);
        graphics.drawText(canvas, mMstHp, TowerDimen.R_BTL_MST_HP.left, TowerDimen.R_BTL_MST_HP.top + TowerDimen.TEXT_SIZE);
        graphics.drawText(canvas, mMstAttack, TowerDimen.R_BTL_MST_ATTACK.left, TowerDimen.R_BTL_MST_ATTACK.top + TowerDimen.TEXT_SIZE);
        graphics.drawText(canvas, mMstDefend, TowerDimen.R_BTL_MST_DEFEND.left, TowerDimen.R_BTL_MST_DEFEND.top + TowerDimen.TEXT_SIZE);
        graphics.drawText(canvas, mPlrHp, TowerDimen.R_BTL_PLR_HP.left, TowerDimen.R_BTL_PLR_HP.top + TowerDimen.TEXT_SIZE);
        graphics.drawText(canvas, mPlrAttack, TowerDimen.R_BTL_PLR_ATTACK.left, TowerDimen.R_BTL_PLR_ATTACK.top + TowerDimen.TEXT_SIZE);
        graphics.drawText(canvas, mPlrDefend, TowerDimen.R_BTL_PLR_DEFEND.left, TowerDimen.R_BTL_PLR_DEFEND.top + TowerDimen.TEXT_SIZE);
    }
}
