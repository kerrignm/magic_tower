package com.game.magictower.scene;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

import com.game.magictower.Game;
import com.game.magictower.Game.Status;
import com.game.magictower.GameView;
import com.game.magictower.R;
import com.game.magictower.model.NpcInfo;
import com.game.magictower.model.TalkInfo;
import com.game.magictower.res.Assets;
import com.game.magictower.res.GameGraphics;
import com.game.magictower.res.GlobalSoundPool;
import com.game.magictower.res.TowerDimen;
import com.game.magictower.widget.BaseButton;

public class SceneDialog extends BaseScene {
    
    private static final int TK_PLAYER = 0;
    
    private int mDialogId;
    private ArrayList<TalkInfo> mTalkList;
    private int mTalker;
    private String mTkName;
    private final String[] mMsgs = new String[2];
    private int mSectionCount;
    private int mLineCount;
    
    private final Bitmap mPlayerIcon = Assets.getInstance().playerMap.get(Assets.PLAYER_DOWN);
    private Bitmap mNpcIcon;
    private final Rect mBgd;
    
    public SceneDialog(GameView parent, Context context, Game game, int id, int x, int y, int w, int h) {
        super(parent, context, game, id, x, y, w, h);
        mBgd = new Rect(0, 0, TowerDimen.R_DLG_BG.width(), TowerDimen.R_DLG_BG.height());
    }
    
    public void show(int dialogId, int npcId) {
        mDialogId = dialogId;
        mSectionCount = 0;
        mLineCount = 0;
        mNpcIcon = Assets.getInstance().animMap0.get(npcId);
        prepareTalkInfo(dialogId);
        getTalkInfo();
        game.status = Status.Dialoging;
        parent.requestRender();
    }
    
    @Override
    public void onDrawFrame(Canvas canvas) {
        super.onDrawFrame(canvas);
        graphics.drawBitmap(canvas, Assets.getInstance().bkgBlank, mBgd, TowerDimen.R_DLG_BG, null);
        graphics.drawRect(canvas, TowerDimen.R_DLG_BG);
        graphics.drawText(canvas, mTkName, TowerDimen.R_DLG_NAME.left, 
                        TowerDimen.R_DLG_NAME.top + TowerDimen.TEXT_SIZE + (TowerDimen.R_DLG_NAME.height() - TowerDimen.TEXT_SIZE) / 2);
        for (int i = 0; i < mMsgs.length; i++) {
            if (mMsgs[i] != null) {
                graphics.drawText(canvas, mMsgs[i], TowerDimen.R_DLG_TEXT.left, 
                            TowerDimen.R_DLG_TEXT.top + i * TowerDimen.R_DLG_NAME.height() + TowerDimen.TEXT_SIZE + (TowerDimen.R_DLG_NAME.height() - TowerDimen.TEXT_SIZE) / 2);
            }
        }
        graphics.drawBitmap(canvas, mTalker == TK_PLAYER ? mPlayerIcon : mNpcIcon, null, TowerDimen.R_DLG_ICON, null);
    }
    
    @Override
    public void onAction(int id) {
        if (id == BaseButton.ID_OK) {
            GlobalSoundPool.getInstance().playSound(Assets.getInstance().getSoundId(Assets.SND_ID_DIALOG));
            if (!getTalkInfo()) {
                talkOver();
            }
        }
    }
    
    private void prepareTalkInfo(int dialogId) {
        int talker;
        String tkName;
        ArrayList<String> msgs;
        TalkInfo talkInfo;
        ArrayList<TalkInfo> talkList = game.dialogs.get(dialogId);
        mTalkList = new ArrayList<>();
        for (int i = 0; i < talkList.size(); i++) {
            talkInfo = talkList.get(i);
            tkName = talkInfo.mTkName;
            talker = talkInfo.mTalker;
            msgs = new ArrayList<>();
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
    
    public void talkOver() {
        boolean changeStatus = true;
        switch(mDialogId) {
        case 0:
            GlobalSoundPool.getInstance().playSound(Assets.getInstance().getSoundId(Assets.SND_ID_POWER));
            game.lvMap[game.npcInfo.curFloor][8][5] = 0;
            game.lvMap[game.npcInfo.curFloor][8][4] = 24;
            game.player.setYKey(game.player.getYKey() + 1);
            game.player.setBKey(game.player.getBKey() + 1);
            game.player.setRKey(game.player.getRKey() + 1);
            game.npcInfo.mFairyStatus = NpcInfo.FAIRY_STATUS_WAIT_CROSS;
            break;
        case 1:
            GlobalSoundPool.getInstance().playSound(Assets.getInstance().getSoundId(Assets.SND_ID_POWER));
            game.player.setHp(game.player.getHp() * 4 / 3);
            game.player.setAttack(game.player.getAttack() * 4 / 3);
            game.player.setDefend(game.player.getDefend() * 4 / 3);
            game.lvMap[20][7][5] = 13;
            game.npcInfo.mFairyStatus = NpcInfo.FAIRY_STATUS_OVER;
            break;
        case 2:
            break;
        case 3:
            GlobalSoundPool.getInstance().playSound(Assets.getInstance().getSoundId(Assets.SND_ID_POWER));
            game.player.setAttack(game.player.getAttack() + 120);
            game.player.setExp(game.player.getExp() - 500);
            game.lvMap[15][3][4] = 0;
            break;
        case 4:
            GlobalSoundPool.getInstance().playSound(Assets.getInstance().getSoundId(Assets.SND_ID_POWER));
            parent.showToast(game.items.get(73).getDescribe());
            game.player.setAttack(game.player.getAttack() + game.items.get(73).getAttack());
            game.lvMap[2][10][7] = 0;
            break;
        case 5:
            GlobalSoundPool.getInstance().playSound(Assets.getInstance().getSoundId(Assets.SND_ID_POWER));
            parent.showToast(game.items.get(77).getDescribe());
            game.player.setDefend(game.player.getDefend() + game.items.get(77).getDefend());
            game.lvMap[2][10][9] = 0;
            break;
        case 6:
            break;
        case 7:
            GlobalSoundPool.getInstance().playSound(Assets.getInstance().getSoundId(Assets.SND_ID_POWER));
            game.player.setDefend(game.player.getDefend() + 120);
            game.player.setMoney(game.player.getMoney() - 500);
            game.lvMap[15][3][6] = 0;
            break;
        case 8:
            game.lvMap[2][6][1] = 0;
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
            parent.showMessage(2);
            changeStatus = false;
            break;
        }
        if (changeStatus) {
            game.status = Status.Playing;
        }
    }

}
