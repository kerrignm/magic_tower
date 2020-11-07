package com.game.magictower;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Canvas;

import com.game.magictower.Game.Status;
import com.game.magictower.model.NpcInfo;
import com.game.magictower.model.TalkInfo;
import com.game.magictower.res.Assets;
import com.game.magictower.res.GameGraphics;
import com.game.magictower.res.GlobalSoundPool;
import com.game.magictower.res.LiveBitmap;
import com.game.magictower.res.TowerDimen;
import com.game.magictower.widget.BaseButton;

public class SceneDialog {
    
    private static final int TK_PLAYER = 0;
    
    private Context mContext;
    
    private Game game;
    
    private int mDialogId;
    private ArrayList<TalkInfo> mTalkList;
    private int mTalker;
    private String mTkName;
    private String[] mMsgs = new String[2];
    private int mSectionCount;
    private int mLineCount;
    
    private LiveBitmap mPlayerIcon = Assets.getInstance().playerMap.get(-2);
    private LiveBitmap mNpcIcn;
    
    public SceneDialog(Context context, Game game) {
        mContext = context;
        this.game = game;
    }
    
    public void show(int dialogId, int npcId) {
        mDialogId = dialogId;
        mSectionCount = 0;
        mLineCount = 0;
        mNpcIcn = Assets.getInstance().animMap0.get(npcId);
        prepareTalkInfo(dialogId);
        getTalkInfo();
        game.status = Status.Dialoguing;
    }
    
    private void prepareTalkInfo(int dialogId) {
        int talker = TK_PLAYER;
        String tkName = null;
        ArrayList<String> msgs = null;
        TalkInfo talkInfo = null;
        ArrayList<TalkInfo> talkList = game.dialogs.get(dialogId);
        mTalkList = new ArrayList<TalkInfo>();
        for (int i = 0; i < talkList.size(); i++) {
            talkInfo = talkList.get(i);
            tkName = talkInfo.mTkName;
            talker = talkInfo.mTalker;
            msgs = new ArrayList<String>();
            tkName += mContext.getResources().getString(R.string.exclamatory_colon);
            for (int k = 0; k < talkInfo.mMsgs.size(); k++) {
                msgs.addAll(GameGraphics.getInstance().splitToLines(talkInfo.mMsgs.get(k), TowerDimen.R_DLG_TEXT.width()));
            }
            mTalkList.add(new TalkInfo(talker, tkName, msgs));
        }
    }
    
    private boolean getTalkInfo() {
        boolean result = true;
        if (mSectionCount < mTalkList.size()) {
            TalkInfo talkInfo = mTalkList.get(mSectionCount);
            mTalker = talkInfo.mTalker;
            mTkName = talkInfo.mTkName;
            if (mLineCount < talkInfo.mMsgs.size() - 1) {
                mMsgs[0] = talkInfo.mMsgs.get(mLineCount);
                mMsgs[1] = talkInfo.mMsgs.get(mLineCount + 1);
                mLineCount += 2;
            } else if (mLineCount < talkInfo.mMsgs.size()) {
                mMsgs[0] = talkInfo.mMsgs.get(mLineCount);
                mMsgs[1] = null;
                mSectionCount += 1;
                mLineCount = 0;
            } else {
                mSectionCount += 1;
                mLineCount = 0;
                result = getTalkInfo();
            }
        } else {
            result = false;
        }
        return result;
    }
    
    public void draw(GameGraphics graphics, Canvas canvas) {
        graphics.drawBitmap(canvas, Assets.getInstance().bkgBlank, null, TowerDimen.R_DLG_BG, null);
        graphics.drawRect(canvas, TowerDimen.R_DLG_BG);
        graphics.drawText(canvas, mTkName, TowerDimen.R_DLG_NAME.left, 
                        TowerDimen.R_DLG_NAME.top + TowerDimen.TEXT_SIZE + (TowerDimen.R_DLG_NAME.height() - TowerDimen.TEXT_SIZE) / 2);
        for (int i = 0; i < mMsgs.length; i++) {
            if (mMsgs[i] != null) {
                graphics.drawText(canvas, mMsgs[i], TowerDimen.R_DLG_TEXT.left, 
                            TowerDimen.R_DLG_TEXT.top + i * TowerDimen.R_DLG_NAME.height() + TowerDimen.TEXT_SIZE + (TowerDimen.R_DLG_NAME.height() - TowerDimen.TEXT_SIZE) / 2);
            }
        }
        if (mTalker == TK_PLAYER) {
            graphics.drawBitmap(canvas, mPlayerIcon, null, TowerDimen.R_DLG_ICON, null);
        } else {
            graphics.drawBitmap(canvas, mNpcIcn, null, TowerDimen.R_DLG_ICON, null);
        }
    }
    
    public void onBtnKey(int btnId) {
        switch (btnId) {
        case BaseButton.ID_OK:
            if (!getTalkInfo()) {
                talkOver();
            }
            break;
        }
    }
    
    public void talkOver() {
        boolean changeStatus = true;
        switch(mDialogId) {
        case 0:
            game.lvMap[game.npcInfo.curFloor][8][5] = 0;
            game.lvMap[game.npcInfo.curFloor][8][4] = 24;
            game.player.setYkey(game.player.getYkey() + 1);
            game.player.setBkey(game.player.getBkey() + 1);
            game.player.setRkey(game.player.getRkey() + 1);
            game.npcInfo.mFairyStatus = NpcInfo.FAIRY_STATUS_WAIT_CROSS;
            break;
        case 1:
            GlobalSoundPool.getInstance().playSound(Assets.getInstance().getSoundId(Assets.SND_ID_FAIRY));
            game.player.setHp(game.player.getHp() * 4 / 3);
            game.player.setAttack(game.player.getAttack() * 4 / 3);
            game.player.setDefend(game.player.getDefend() * 4 / 3);
            game.lvMap[20][7][5] = 13;
            game.npcInfo.mFairyStatus = NpcInfo.FAIRY_STATUS_OVER;
            break;
        case 2:
            break;
        case 3:
            game.player.setAttack(game.player.getAttack() + 120);
            game.player.setExp(game.player.getExp() - 500);
            game.lvMap[15][3][4] = 0;
            break;
        case 4:
            game.message.show(R.string.get_steel_sword);
            game.player.setAttack(game.player.getAttack() + 70);
            game.lvMap[2][10][7] = 0;
            changeStatus = false;
            break;
        case 5:
            game.message.show(R.string.get_steel_shield);
            game.player.setDefend(game.player.getDefend() + 30);
            game.lvMap[2][10][9] = 0;
            changeStatus = false;
            return;
        case 6:
            break;
        case 7:
            game.player.setDefend(game.player.getDefend() + 120);
            game.player.setMoney(game.player.getMoney() - 500);
            game.lvMap[15][3][6] = 0;
            break;
        case 8:
            game.lvMap[2][6][1] = 0;
            GlobalSoundPool.getInstance().playSound(Assets.getInstance().getSoundId(Assets.SND_ID_PICKAXE));
            game.npcInfo.mThiefStatus = NpcInfo.THIEF_STATUS_WAIT_HAMMER;
            break;
        case 9:
            game.lvMap[18][10][10] = 13;
            game.npcInfo.mPrincessStatus = NpcInfo.PRINCESS_STATUS_OVER;
            break;
        case 10:
            break;
        case 11:
            game.lvMap[18][8][5] = 0;
            game.lvMap[18][9][5] = 0;
            game.lvMap[4][0][5] = 0;
            game.npcInfo.mThiefStatus = NpcInfo.THIEF_STATUS_OVER;
            break;
        case 12:
            break;
        case 13:
            break;
        case 14:
            game.message.show(2, null, null, SceneMessage.MODE_AUTO_SCROLL);
            changeStatus = false;
            break;
        }
        if (changeStatus) {
            game.status = Status.Playing;
        }
    }

}
