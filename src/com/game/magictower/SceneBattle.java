package com.game.magictower;

import java.lang.ref.WeakReference;

import android.content.Context;
import android.graphics.Canvas;
import android.os.Handler;
import android.os.Message;

import com.game.magictower.Game.Status;
import com.game.magictower.model.Monster;
import com.game.magictower.res.Assets;
import com.game.magictower.res.GlobalSoundPool;
import com.game.magictower.res.LiveBitmap;
import com.game.magictower.res.TowerDimen;
import com.game.magictower.util.MathUtil;

public class SceneBattle extends BaseScene {
    
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
    
    public SceneBattle(GameView parent, Context context, Game game, int id, int x, int y, int w, int h) {
        super(parent, context, game, id, x, y, w, h);
    }

    public void show(int id, int x, int y) {
        mMonster = game.monsters.get(id);
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
        handler.sendEmptyMessageDelayed(MSG_ID_FIGHT, MSG_DELAY_FIGHT_MSG);
        parent.requestRender();
    }
    
    @Override
    public void onDrawFrame(Canvas canvas) {
        super.onDrawFrame(canvas);
        graphics.drawBitmap(canvas, Assets.getInstance().bkgBattle, null, TowerDimen.R_BATTLE, null);
        graphics.drawBitmap(canvas, mMstIcon, null, TowerDimen.R_BTL_MST_ICON, null);
        graphics.drawTextInCenter(canvas, mMstHp, TowerDimen.R_BTL_MST_HP);
        graphics.drawTextInCenter(canvas, mMstAttack, TowerDimen.R_BTL_MST_ATTACK);
        graphics.drawTextInCenter(canvas, mMstDefend, TowerDimen.R_BTL_MST_DEFEND);
        graphics.drawTextInCenter(canvas, mPlrHp, TowerDimen.R_BTL_PLR_HP);
        graphics.drawTextInCenter(canvas, mPlrAttack, TowerDimen.R_BTL_PLR_ATTACK);
        graphics.drawTextInCenter(canvas, mPlrDefend, TowerDimen.R_BTL_PLR_DEFEND);
    }
    
    @Override
    public void onAction(int id) {
        
    }
    
    private void getHpInfo() {
        mMstHp = mHp + "";
        mPlrHp = game.player.getHp() + "";
    }
    
    private void attack() {
        if (!mMagicAttack && (mMonster.getMagicDamage() > 0)) {
            mMagicAttack = true;
            game.player.setHp(game.player.getHp() - game.player.getHp() / mMonster.getMagicDamage());
            if (mMonster.getMagicDamage() == 4) {
                GlobalSoundPool.getInstance().playSound(Assets.getInstance().getSoundId(Assets.SND_ID_MAGIC_4));
            } else {
                GlobalSoundPool.getInstance().playSound(Assets.getInstance().getSoundId(Assets.SND_ID_MAGIC_3));
            }
        }
        if (mPlayerRound) {
            if (game.player.getAttack() > mDefend) {
                boolean critical = MathUtil.percent(game.player.getCritRate());
                int attck = game.player.getAttack() - mDefend;
                if (critical) {
                    attck = attck * 2;
                    GlobalSoundPool.getInstance().playSound(Assets.getInstance().getSoundId(Assets.SND_ID_CRIT));
                } else {
                    GlobalSoundPool.getInstance().playSound(Assets.getInstance().getSoundId(Assets.SND_ID_ATTACK));
                }
                if (mHp > attck) {
                    mHp -= attck;
                } else {
                    mHp = 0;
                }
            }
        } else {
            if (mAttack > game.player.getDefend()) {
                game.player.setHp(game.player.getHp() - mAttack + game.player.getDefend());
            }
        }
        mPlayerRound = !mPlayerRound;
        getHpInfo();
        if (mHp <= 0) {
            GlobalSoundPool.getInstance().playSound(Assets.getInstance().getSoundId(Assets.SND_ID_ZONE));
            game.player.setMoney(game.player.getMoney() + mMonster.getMoney());
            game.player.setExp(game.player.getExp() + mMonster.getExp());
            game.lvMap[game.npcInfo.curFloor][mY][mX] = 0;
            handler.sendEmptyMessageDelayed(MSG_ID_FIGHT_OVER, MSG_DELAY_FIGHT_MSG);
        } else {
            handler.sendEmptyMessageDelayed(MSG_ID_FIGHT, MSG_DELAY_FIGHT_MSG);
        }
        parent.requestRender();
    }
    
    private void fightOver() {
        game.status = Status.Playing;
        parent.showToast(String.format(mContext.getResources().getString(R.string.get_money_exp), mMonster.getMoney(), mMonster.getExp()));
        GlobalSoundPool.getInstance().playSound(Assets.getInstance().getSoundId(Assets.SND_ID_POWER));
        if ((game.npcInfo.curFloor == 19) && (mMonster.getId() == 59)) {
            parent.showDialog(13, 59);
        } else if ((game.npcInfo.curFloor == 21) && (mMonster.getId() == 59)) {
            parent.showDialog(14, 59);
        }
        if ((game.npcInfo.curFloor == 16) && (mMonster.getId() == 53)) {
            game.npcInfo.isMonsterStronger = true;
            game.monsterStronger();
        } else if ((game.npcInfo.curFloor == 19) && (mMonster.getId() == 59)) {
            game.npcInfo.isMonsterStrongest = true;
            game.monsterStronest();
        }
        parent.requestRender();
    }
    
    private Handler handler = new FightHandler(new WeakReference<SceneBattle>(this));
    
    private static final int MSG_ID_FIGHT = 1;
    
    private static final int MSG_ID_FIGHT_OVER = 2;
    
    private static final int MSG_DELAY_FIGHT_MSG = 200;
    
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
            } else if (msg.what == MSG_ID_FIGHT_OVER && battle != null) {
                battle.fightOver();
            }
            super.handleMessage(msg);
        }
    };
}
