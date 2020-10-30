package com.game.magictower;

import java.util.ArrayList;

import android.graphics.Canvas;

import com.game.magictower.Game.Status;
import com.game.magictower.res.Assets;
import com.game.magictower.res.DialogData;
import com.game.magictower.res.GameGraphics;
import com.game.magictower.res.LiveBitmap;
import com.game.magictower.res.TowerDimen;
import com.game.magictower.widget.BitmapButton;

public class Dialog {
    private Game game;
    private int mCount;
    private ArrayList<TalkInfo> mTalkList;
    private int mTalker;
    private String mTkName;
    private String[] mMsgs;
    private int mDialogId;
    
    private LiveBitmap mPlayerIcon = Assets.getInstance().playerMap.get(-2);
    private LiveBitmap mNpcIcn;
    
    public Dialog(Game game) {
        this.game = game;
    }
    
    public void show(int dialogId, int npcId) {
        mDialogId = dialogId;
        mCount = 0;
        mTalkList = DialogData.mMsgsMap.get(dialogId);
        mNpcIcn = Assets.getInstance().animMap0.get(npcId);
        getTalkInfo();
        game.status = Status.Dialoguing;
        
    }
    
    private void getTalkInfo() {
        TalkInfo talkInfo = mTalkList.get(mCount);
        mTalker = talkInfo.mTalker;
        mTkName = talkInfo.mTkName;
        mMsgs = talkInfo.mMsgs;
    }
    
    public void draw(GameGraphics graphics, Canvas canvas) {
        graphics.drawBitmap(canvas, Assets.getInstance().bkgBlank, null, TowerDimen.R_DLG_BG, null);
        graphics.drawRect(canvas, TowerDimen.R_DLG_BG);
        graphics.drawText(canvas, mTkName, TowerDimen.R_DLG_NAME.left, 
                        TowerDimen.R_DLG_NAME.top + GameGraphics.TEXT_SIZE + (TowerDimen.R_DLG_NAME.height() - GameGraphics.TEXT_SIZE) / 2);
        for (int i = 0; i < mMsgs.length; i++) {
            graphics.drawText(canvas, mMsgs[i], TowerDimen.R_DLG_TEXT.left, 
                        TowerDimen.R_DLG_TEXT.top + i * TowerDimen.R_DLG_NAME.height() + GameGraphics.TEXT_SIZE + (TowerDimen.R_DLG_NAME.height() - GameGraphics.TEXT_SIZE) / 2);
        }
        if (mTalker == DialogData.TK_PLAYER) {
            graphics.drawBitmap(canvas, mPlayerIcon, null, TowerDimen.R_DLG_ICON, null);
        } else {
            graphics.drawBitmap(canvas, mNpcIcn, null, TowerDimen.R_DLG_ICON, null);
        }
    }
    
    public void onBtnKey(int btnId) {
        switch (btnId) {
        case BitmapButton.ID_OK:
            mCount++;
            if (mCount < mTalkList.size()) {
                getTalkInfo();
            } else {
                talkOver();
            }
            break;
        }
    }
    
    public void talkOver() {
        switch(mDialogId) {
        case 24:
            game.lvMap[game.currentFloor][8][5] = 0;
            game.lvMap[game.currentFloor][8][4] = 24;
            game.player.setYkey(game.player.getYkey() + 1);
            game.player.setBkey(game.player.getBkey() + 1);
            game.player.setRkey(game.player.getRkey() + 1);
            game.npcInfo.mFairyStatus = NpcInfo.FAIRY_STATUS_WAIT_CROSS;
            break;
        case 1:
            game.player.setHp(game.player.getHp() * 4 / 3);
            game.player.setAttack(game.player.getAttack() * 4 / 3);
            game.player.setDefend(game.player.getDefend() * 4 / 3);
            game.npcInfo.mFairyStatus = NpcInfo.FAIRY_STATUS_OVER;
            break;
        case 3:
            break;
        case 4:
            game.player.setAttack(game.player.getAttack() + 120);
            game.player.setExp(game.player.getExp() - 500);
            break;
        case 5:
            game.messag.show("得到 钢剑 攻击加 70 ！", Messag.MODE_MSG);
            game.player.setAttack(game.player.getAttack() + 70);
            game.lvMap[2][10][7] = 0;
            break;
        case 6:
            game.messag.show("得到 钢盾 防御加 30 ！", Messag.MODE_MSG);
            game.player.setDefend(game.player.getDefend() + 30);
            game.lvMap[2][10][9] = 0;
            break;
        case 7:
            break;
        case 8:
            game.player.setDefend(game.player.getDefend() + 120);
            game.player.setMoney(game.player.getMoney() - 500);
            break;
        case 12:
            game.npcInfo.mThiefStatus = NpcInfo.THIEF_STATUS_WAIT_HAMMER;
            break;
        case 19:
            break;
        case 23:
            break;
        case 26:
            game.lvMap[4][0][5] = 0;
            game.npcInfo.mThiefStatus = NpcInfo.THIEF_STATUS_OVER;
            break;
        case 27:
            break;
        case 28:
            break;
        case 29:
            break;
        }
        game.status = Status.Playing;
    }

}
