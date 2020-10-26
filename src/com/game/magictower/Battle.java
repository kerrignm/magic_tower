package com.game.magictower;

import android.graphics.Canvas;
import android.os.Handler;
import android.os.Message;

import com.game.magictower.Game.Status;
import com.game.magictower.res.Assets;
import com.game.magictower.res.GameGraphics;
import com.game.magictower.res.LiveBitmap;
import com.game.magictower.res.MonsterData;
import com.game.magictower.res.TowerDimen;

public class Battle {
    
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
    
    private static final int MSG_ID_FIGHT = 1;
    
    private static final int MSG_DELAY_REMOVE_MSG = 300;
    
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == MSG_ID_FIGHT){
                attack();
                getHpInfo();
                if (mHp <= 0) {
                    game.player.setExp(game.player.getExp() + mMonster.getExp());
                    game.player.setMoney(game.player.getMoney() + mMonster.getMoney());
                    GameActivity.displayMessage("获得金币数" + mMonster.getExp() + " 经验值 " + mMonster.getMoney() + " ！");

                    game.lvMap[game.currentFloor][mY][mX] = 0;
                    game.player.move(mX, mY);
                    game.status = Status.Playing;
                } else {
                    handler.sendEmptyMessageDelayed(MSG_ID_FIGHT, MSG_DELAY_REMOVE_MSG);
                }
            }
            super.handleMessage(msg);
        }
    };
    
    public Battle(Game game) {
        this.game = game;
    }

    public void show(int id, int x, int y) {
        mPlayerRound = true;
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
                }
            }
        } else if (mAttack > game.player.getDefend()) {
            game.player.setHp(game.player.getHp() - mAttack + game.player.getDefend());
        }
        mPlayerRound = !mPlayerRound;
    }
    
    public void draw(GameGraphics graphics, Canvas canvas) {
        graphics.drawBitmap(canvas, Assets.getInstance().bkgBattle, null, TowerDimen.R_BATTLE, null);
        graphics.drawBitmap(canvas, mMstIcon, null, TowerDimen.R_BTL_MST_ICON, null);
        graphics.drawText(canvas, mMstHp, TowerDimen.R_BTL_MST_HP.left, TowerDimen.R_BTL_MST_HP.top);
        graphics.drawText(canvas, mMstAttack, TowerDimen.R_BTL_MST_ATTACK.left, TowerDimen.R_BTL_MST_ATTACK.top);
        graphics.drawText(canvas, mMstDefend, TowerDimen.R_BTL_MST_DEFEND.left, TowerDimen.R_BTL_MST_DEFEND.top);
        graphics.drawText(canvas, mPlrHp, TowerDimen.R_BTL_PLR_HP.left, TowerDimen.R_BTL_PLR_HP.top);
        graphics.drawText(canvas, mPlrAttack, TowerDimen.R_BTL_PLR_ATTACK.left, TowerDimen.R_BTL_PLR_ATTACK.top);
        graphics.drawText(canvas, mPlrDefend, TowerDimen.R_BTL_PLR_DEFEND.left, TowerDimen.R_BTL_PLR_DEFEND.top);
    }
}
