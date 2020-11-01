package com.game.magictower;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Canvas;

import com.game.magictower.Game.Status;
import com.game.magictower.res.Assets;
import com.game.magictower.res.GameGraphics;
import com.game.magictower.res.LiveBitmap;
import com.game.magictower.res.TowerDimen;
import com.game.magictower.widget.BitmapButton;

public class SceneDialog {
    
    private static final int TK_PLAYER = 0;
    private static final int TK_NPC = 1;
    
    private static final int[] sDialogs = {
        R.array.dialog_0,
        R.array.dialog_1,
        R.array.dialog_2,
        R.array.dialog_3,
        R.array.dialog_4,
        R.array.dialog_5,
        R.array.dialog_6,
        R.array.dialog_7,
        R.array.dialog_8,
        R.array.dialog_9,
        R.array.dialog_10,
        R.array.dialog_11,
        R.array.dialog_12,
        R.array.dialog_13,
        R.array.dialog_14
    };
    
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
        String[] dialogInfos = mContext.getResources().getStringArray(sDialogs[dialogId]);
        String hero = mContext.getResources().getString(R.string.hero);
        String[] dialogInfo = null;
        int talker = TK_PLAYER;
        String tkName = null;
        ArrayList<String> msgs = null;
        mTalkList = new ArrayList<TalkInfo>();
        TalkInfo talkInfo = null;
        for (int i = 0; i < dialogInfos.length; i++) {
            dialogInfo = dialogInfos[i].split(":");
            tkName = dialogInfo[0];
            if (hero.equals(tkName)) {
                talker = TK_PLAYER;
            } else {
                talker = TK_NPC;
            }
            tkName += mContext.getResources().getString(R.string.exclamatory_colon);
            msgs = GameGraphics.getInstance().splitToLines(dialogInfo[1], TowerDimen.R_DLG_TEXT.width());
            talkInfo = new TalkInfo(talker, tkName, msgs);
            mTalkList.add(talkInfo);
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
        case BitmapButton.ID_OK:
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
        case 2:
            break;
        case 3:
            game.player.setAttack(game.player.getAttack() + 120);
            game.player.setExp(game.player.getExp() - 500);
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
            break;
        case 8:
            game.npcInfo.mThiefStatus = NpcInfo.THIEF_STATUS_WAIT_HAMMER;
            break;
        case 9:
            break;
        case 10:
            break;
        case 11:
            game.lvMap[4][0][5] = 0;
            game.npcInfo.mThiefStatus = NpcInfo.THIEF_STATUS_OVER;
            break;
        case 12:
            break;
        case 13:
            break;
        case 14:
            break;
        }
        if (changeStatus) {
            game.status = Status.Playing;
        }
    }
    
    private static class TalkInfo {
        
        public int mTalker;
        public String mTkName;
        public ArrayList<String> mMsgs;
        
        public TalkInfo(int talker, String tkName, ArrayList<String> msgs) {
            mTalker = talker;
            mTkName = tkName;
            mMsgs = msgs;
        }
    }

}
