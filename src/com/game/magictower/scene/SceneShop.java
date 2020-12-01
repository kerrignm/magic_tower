package com.game.magictower.scene;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.view.MotionEvent;

import com.game.magictower.Game;
import com.game.magictower.GameView;
import com.game.magictower.Game.Status;
import com.game.magictower.res.Assets;
import com.game.magictower.res.GlobalSoundPool;
import com.game.magictower.res.TowerDimen;
import com.game.magictower.util.RectUtil;
import com.game.magictower.widget.BaseButton;

public class SceneShop extends BaseScene {

    ArrayList<String> choices;
    private Bitmap imgIcon;
    private int id = 0;
    private int mSelected = 0;
    private Rect[] mTextRect = new Rect[4];
    private Rect[] mEdgeRect = new Rect[4];
    private Rect mTouchRect;
    private Rect mBgd;
    
    public SceneShop(GameView parent, Context context, Game game, int id, int x, int y, int w, int h) {
        super(parent, context, game, id, x, y, w, h);
        mTextRect[0] = TowerDimen.R_SHOP_TEXT;
        mTextRect[1] = RectUtil.createRect(TowerDimen.R_SHOP_TEXT, 0, TowerDimen.R_SHOP_TEXT.height());
        mTextRect[2] = RectUtil.createRect(TowerDimen.R_SHOP_TEXT, 0, TowerDimen.R_SHOP_TEXT.height() * 2);
        mTextRect[3] = RectUtil.createRect(TowerDimen.R_SHOP_TEXT, 0, TowerDimen.R_SHOP_TEXT.height() * 3);
        for (int i = 0; i < 4; i++) {
            mEdgeRect[i] = new Rect(mTextRect[i].left, mTextRect[i].top + 5, mTextRect[i].right, mTextRect[i].bottom - 5);
        }
        mTouchRect = new Rect(TowerDimen.R_SHOP_TEXT.left, TowerDimen.R_SHOP_TEXT.top, TowerDimen.R_SHOP_TEXT.right, TowerDimen.R_SHOP_TEXT.top + TowerDimen.R_SHOP_TEXT.height() * 4);
        mBgd = new Rect(0, 0, TowerDimen.R_SHOP.width(), TowerDimen.R_SHOP.height());
    }

    public void show(int shopId, int npcId) {
        choices = game.shops.get(shopId);
        imgIcon = Assets.getInstance().animMap0.get(npcId);
        this.id = shopId;
        mSelected = 0;
        game.status = Status.Shopping;
        parent.requestRender();
    }
    
    @Override
    public boolean onTouch(MotionEvent event) {
        boolean result = false;
        switch (event.getAction()) {
        case MotionEvent.ACTION_DOWN:
            if (inBounds(event)) {
                result = true;
                int selected = ((int)event.getY() - mTouchRect.top) / TowerDimen.R_SHOP_TEXT.height();
                if (selected != mSelected) {
                    mSelected = selected;
                } else {
                    selected();
                }
                parent.requestRender();
            }
            break;
        case MotionEvent.ACTION_MOVE:
           result = true;
            break;
        case MotionEvent.ACTION_UP:
        case MotionEvent.ACTION_POINTER_UP:
        case MotionEvent.ACTION_CANCEL:
        case MotionEvent.ACTION_OUTSIDE:
            result = true;
            parent.requestRender();
            break;
        }
        return result;
    }
    
    @Override
    protected boolean inBounds(MotionEvent event){
        return mTouchRect.contains((int)event.getX(), (int)event.getY());
    }
    
    @Override
    public void onDrawFrame(Canvas canvas) {
        super.onDrawFrame(canvas);
        graphics.drawBitmap(canvas, Assets.getInstance().bkgBlank, mBgd, TowerDimen.R_SHOP, null);
        graphics.drawRect(canvas, TowerDimen.R_SHOP);
        graphics.drawBitmap(canvas, imgIcon, TowerDimen.R_SHOP_ICON.left, TowerDimen.R_SHOP_ICON.top);
        for (int i = 0; i < 4; i++) {
            if (i == mSelected) {
                graphics.textPaint.setStyle(Style.STROKE);
                graphics.drawRect(canvas, mEdgeRect[i], graphics.textPaint);
                graphics.textPaint.setStyle(Style.FILL);
                graphics.drawText(canvas, choices.get(i), mTextRect[i].left + 10, mTextRect[i].top + TowerDimen.TEXT_SIZE + (mTextRect[i].height() - TowerDimen.TEXT_SIZE) / 2);
            } else {
                graphics.disableTextPaint.setStyle(Style.STROKE);
                graphics.drawRect(canvas, mEdgeRect[i], graphics.disableTextPaint);
                graphics.disableTextPaint.setStyle(Style.FILL);
                graphics.drawText(canvas, choices.get(i), mTextRect[i].left + 10, mTextRect[i].top + TowerDimen.TEXT_SIZE + (mTextRect[i].height() - TowerDimen.TEXT_SIZE) / 2, graphics.disableTextPaint);
            }
        }
    }
    
    @Override
    public void onAction(int id) {
        switch (id) {
        case BaseButton.ID_UP:
            GlobalSoundPool.getInstance().playSound(Assets.getInstance().getSoundId(Assets.SND_ID_CHANGE));
            if (mSelected > 0) {
                mSelected--;
            }
            break;
        case BaseButton.ID_DOWN:
            GlobalSoundPool.getInstance().playSound(Assets.getInstance().getSoundId(Assets.SND_ID_CHANGE));
            if (mSelected < 3) {
                mSelected++;
            }
            break;
        case BaseButton.ID_OK:
            selected();
            break;
        }
    }
    
    private void selected() {
        switch (id) {
        case 0:     //  3 floor
            switch (mSelected) {
                case 0:
                    if (game.player.getMoney() >= 25) {
                        GlobalSoundPool.getInstance().playSound(Assets.getInstance().getSoundId(Assets.SND_ID_DONE));
                        game.player.setMoney(game.player.getMoney() - 25);
                        game.player.setHp(game.player.getHp() + 800);
                    } else {
                        GlobalSoundPool.getInstance().playSound(Assets.getInstance().getSoundId(Assets.SND_ID_COIN));
                    }
                    break;
                case 1:
                    if (game.player.getMoney() >= 25) {
                        GlobalSoundPool.getInstance().playSound(Assets.getInstance().getSoundId(Assets.SND_ID_DONE));
                        game.player.setMoney(game.player.getMoney() - 25);
                        game.player.setAttack(game.player.getAttack() + 4);
                    } else {
                        GlobalSoundPool.getInstance().playSound(Assets.getInstance().getSoundId(Assets.SND_ID_COIN));
                    }
                    break;
                case 2:
                    if (game.player.getMoney() >= 25) {
                        GlobalSoundPool.getInstance().playSound(Assets.getInstance().getSoundId(Assets.SND_ID_DONE));
                        game.player.setMoney(game.player.getMoney() - 25);
                        game.player.setDefend(game.player.getDefend() + 4);
                    } else {
                        GlobalSoundPool.getInstance().playSound(Assets.getInstance().getSoundId(Assets.SND_ID_COIN));
                    }
                    break;
                case 3:
                    GlobalSoundPool.getInstance().playSound(Assets.getInstance().getSoundId(Assets.SND_ID_ZONE));
                    game.status = Status.Playing;
                    break;
            }
            break;
        case 1:     // 5 floor
            switch (mSelected) {
                case 0:
                    if (game.player.getExp() >= 100) {
                        GlobalSoundPool.getInstance().playSound(Assets.getInstance().getSoundId(Assets.SND_ID_DONE));
                        game.player.setLevel(game.player.getLevel() + 1);
                        game.player.setExp(game.player.getExp() - 100);
                        game.player.setHp(game.player.getHp() + 1000);
                        game.player.setAttack(game.player.getAttack() + 7);
                        game.player.setDefend(game.player.getDefend() + 7);
                    } else {
                        GlobalSoundPool.getInstance().playSound(Assets.getInstance().getSoundId(Assets.SND_ID_COIN));
                    }
                    break;
                case 1:
                    if (game.player.getExp() >= 30) {
                        GlobalSoundPool.getInstance().playSound(Assets.getInstance().getSoundId(Assets.SND_ID_DONE));
                        game.player.setExp(game.player.getExp() - 30);
                        game.player.setAttack(game.player.getAttack() + 5);
                    } else {
                        GlobalSoundPool.getInstance().playSound(Assets.getInstance().getSoundId(Assets.SND_ID_COIN));
                    }
                    break;
                case 2:
                    if (game.player.getExp() >= 30) {
                        GlobalSoundPool.getInstance().playSound(Assets.getInstance().getSoundId(Assets.SND_ID_DONE));
                        game.player.setExp(game.player.getExp() - 30);
                        game.player.setDefend(game.player.getDefend() + 5);
                    } else {
                        GlobalSoundPool.getInstance().playSound(Assets.getInstance().getSoundId(Assets.SND_ID_COIN));
                    }
                    break;
                case 3:
                    GlobalSoundPool.getInstance().playSound(Assets.getInstance().getSoundId(Assets.SND_ID_ZONE));
                    game.status = Status.Playing;
                    break;
            }
            break;
        case 2:     // 5 floor
            switch (mSelected) {
                case 0:
                    if (game.player.getMoney() >= 10) {
                        GlobalSoundPool.getInstance().playSound(Assets.getInstance().getSoundId(Assets.SND_ID_DONE));
                        game.player.setMoney(game.player.getMoney() - 10);
                        game.player.setYkey(game.player.getYkey() + 1);
                    } else {
                        GlobalSoundPool.getInstance().playSound(Assets.getInstance().getSoundId(Assets.SND_ID_COIN));
                    }
                    break;
                case 1:
                    if (game.player.getMoney() >= 50) {
                        GlobalSoundPool.getInstance().playSound(Assets.getInstance().getSoundId(Assets.SND_ID_DONE));
                        game.player.setMoney(game.player.getMoney() - 50);
                        game.player.setBkey(game.player.getBkey() + 1);
                    } else {
                        GlobalSoundPool.getInstance().playSound(Assets.getInstance().getSoundId(Assets.SND_ID_COIN));
                    }
                    break;
                case 2:
                    if (game.player.getMoney() >= 100) {
                        GlobalSoundPool.getInstance().playSound(Assets.getInstance().getSoundId(Assets.SND_ID_DONE));
                        game.player.setMoney(game.player.getMoney() - 100);
                        game.player.setRkey(game.player.getRkey() + 1);
                    } else {
                        GlobalSoundPool.getInstance().playSound(Assets.getInstance().getSoundId(Assets.SND_ID_COIN));
                    }
                    break;
                case 3:
                    GlobalSoundPool.getInstance().playSound(Assets.getInstance().getSoundId(Assets.SND_ID_ZONE));
                    game.status = Status.Playing;
                    break;
            }
            break;
        case 3:     // 11 floor
            switch (mSelected) {
                case 0:
                    if (game.player.getMoney() >= 100) {
                        GlobalSoundPool.getInstance().playSound(Assets.getInstance().getSoundId(Assets.SND_ID_DONE));
                        game.player.setMoney(game.player.getMoney() - 100);
                        game.player.setHp(game.player.getHp() + 4000);
                    } else {
                        GlobalSoundPool.getInstance().playSound(Assets.getInstance().getSoundId(Assets.SND_ID_COIN));
                    }
                    break;
                case 1:
                    if (game.player.getMoney() >= 100) {
                        GlobalSoundPool.getInstance().playSound(Assets.getInstance().getSoundId(Assets.SND_ID_DONE));
                        game.player.setMoney(game.player.getMoney() - 100);
                        game.player.setAttack(game.player.getAttack() + 20);
                    } else {
                        GlobalSoundPool.getInstance().playSound(Assets.getInstance().getSoundId(Assets.SND_ID_COIN));
                    }
                    break;
                case 2:
                    if (game.player.getMoney() >= 100) {
                        GlobalSoundPool.getInstance().playSound(Assets.getInstance().getSoundId(Assets.SND_ID_DONE));
                        game.player.setMoney(game.player.getMoney() - 100);
                        game.player.setDefend(game.player.getDefend() + 20);
                    } else {
                        GlobalSoundPool.getInstance().playSound(Assets.getInstance().getSoundId(Assets.SND_ID_COIN));
                    }
                    break;
                case 3:
                    GlobalSoundPool.getInstance().playSound(Assets.getInstance().getSoundId(Assets.SND_ID_ZONE));
                    game.status = Status.Playing;
                    break;
            }
            break;
        case 4:     // 12 floor
            switch (mSelected) {
                case 0:
                    if (game.player.getYkey() > 0) {
                        GlobalSoundPool.getInstance().playSound(Assets.getInstance().getSoundId(Assets.SND_ID_DONE));
                        game.player.setYkey(game.player.getYkey() - 1);
                        game.player.setMoney(game.player.getMoney() + 7);
                    } else {
                        GlobalSoundPool.getInstance().playSound(Assets.getInstance().getSoundId(Assets.SND_ID_COIN));
                    }
                    break;
                case 1:
                    if (game.player.getBkey() > 0) {
                        GlobalSoundPool.getInstance().playSound(Assets.getInstance().getSoundId(Assets.SND_ID_DONE));
                        game.player.setBkey(game.player.getBkey() - 1);
                        game.player.setMoney(game.player.getMoney() + 35);
                    } else {
                        GlobalSoundPool.getInstance().playSound(Assets.getInstance().getSoundId(Assets.SND_ID_COIN));
                    }
                    break;
                case 2:
                    if (game.player.getRkey() > 0) {
                        GlobalSoundPool.getInstance().playSound(Assets.getInstance().getSoundId(Assets.SND_ID_DONE));
                            game.player.setRkey(game.player.getRkey() - 1);
                        game.player.setMoney(game.player.getMoney() + 70);
                    } else {
                        GlobalSoundPool.getInstance().playSound(Assets.getInstance().getSoundId(Assets.SND_ID_COIN));
                    }
                    break;
                case 3:
                    GlobalSoundPool.getInstance().playSound(Assets.getInstance().getSoundId(Assets.SND_ID_ZONE));
                    game.status = Status.Playing;
                    break;
            }
            break;
        case 5:     // 13 floor
            switch (mSelected) {
                case 0:
                    if (game.player.getExp() >= 270) {
                        GlobalSoundPool.getInstance().playSound(Assets.getInstance().getSoundId(Assets.SND_ID_DONE));
                        game.player.setLevel(game.player.getLevel() + 3);
                        game.player.setExp(game.player.getExp() - 270);
                        game.player.setHp(game.player.getHp() + 3000);
                        game.player.setAttack(game.player.getAttack() + 20);
                        game.player.setDefend(game.player.getDefend() + 20);
                    } else {
                        GlobalSoundPool.getInstance().playSound(Assets.getInstance().getSoundId(Assets.SND_ID_COIN));
                    }
                    break;
                case 1:
                    if (game.player.getExp() >= 95) {
                        GlobalSoundPool.getInstance().playSound(Assets.getInstance().getSoundId(Assets.SND_ID_DONE));
                        game.player.setExp(game.player.getExp() - 95);
                        game.player.setAttack(game.player.getAttack() + 17);
                    } else {
                        GlobalSoundPool.getInstance().playSound(Assets.getInstance().getSoundId(Assets.SND_ID_COIN));
                    }
                    break;
                case 2:
                    if (game.player.getExp() >= 95) {
                        GlobalSoundPool.getInstance().playSound(Assets.getInstance().getSoundId(Assets.SND_ID_DONE));
                        game.player.setExp(game.player.getExp() - 95);
                        game.player.setDefend(game.player.getDefend() + 17);
                    } else {
                        GlobalSoundPool.getInstance().playSound(Assets.getInstance().getSoundId(Assets.SND_ID_COIN));
                    }
                    break;
                case 3:
                    GlobalSoundPool.getInstance().playSound(Assets.getInstance().getSoundId(Assets.SND_ID_ZONE));
                    game.status = Status.Playing;
                    break;
            }
            break;
        }
    }

}
