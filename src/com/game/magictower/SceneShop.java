package com.game.magictower;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;

import com.game.magictower.Game.Status;
import com.game.magictower.res.Assets;
import com.game.magictower.res.GameGraphics;
import com.game.magictower.res.GlobalSoundPool;
import com.game.magictower.res.LiveBitmap;
import com.game.magictower.res.TowerDimen;
import com.game.magictower.util.RectUtil;
import com.game.magictower.widget.BaseButton;

public class SceneShop {

    ArrayList<String> choices;
    private LiveBitmap imgIcon;
    private Game game;
    private int id = 0;
    private int select = 0;
    private Rect[] mTExtRect = new Rect[4];
    
    public SceneShop(Context context, Game game) {
        this.game = game;
        mTExtRect[0] = TowerDimen.R_SHOP_TEXT;
        mTExtRect[1] = RectUtil.createRect(TowerDimen.R_SHOP_TEXT, 0, TowerDimen.R_SHOP_TEXT.height());
        mTExtRect[2] = RectUtil.createRect(TowerDimen.R_SHOP_TEXT, 0, TowerDimen.R_SHOP_TEXT.height() * 2);
        mTExtRect[3] = RectUtil.createRect(TowerDimen.R_SHOP_TEXT, 0, TowerDimen.R_SHOP_TEXT.height() * 3);
    }

    public void show(int shopId, int npcId) {
        choices = game.shops.get(shopId);
        imgIcon = Assets.getInstance().animMap0.get(npcId);
        this.id = shopId;
        select = 0;
        game.status = Status.Shopping;
    }
    
    public void selected() {
        switch (id) {
        case 0:     //  3 floor
            switch (select) {
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
            switch (select) {
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
            switch (select) {
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
            switch (select) {
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
            switch (select) {
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
            switch (select) {
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
        
    public void onBtnKey(int btnId) {
        switch (btnId) {
        case BaseButton.ID_UP:
            GlobalSoundPool.getInstance().playSound(Assets.getInstance().getSoundId(Assets.SND_ID_CHANGE));
            if (select > 0) {
                select--;
            }
            break;
        case BaseButton.ID_DOWN:
            GlobalSoundPool.getInstance().playSound(Assets.getInstance().getSoundId(Assets.SND_ID_CHANGE));
            if (select < 3) {
                select++;
            }
            break;
        case BaseButton.ID_OK:
            selected();
            break;
        }
    }
    
    public void draw(GameGraphics graphics, Canvas canvas) {
        graphics.drawBitmap(canvas, Assets.getInstance().bkgBlank, null, TowerDimen.R_SHOP, null);
        graphics.drawRect(canvas, TowerDimen.R_SHOP);
        graphics.drawBitmap(canvas, imgIcon, TowerDimen.R_SHOP_ICON.left, TowerDimen.R_SHOP_ICON.top);
        for (int i = 0; i < 4; i++) {
            if (i == select) {
                graphics.drawText(canvas, choices.get(i), mTExtRect[i].left, mTExtRect[i].top + TowerDimen.TEXT_SIZE + (mTExtRect[i].height() - TowerDimen.TEXT_SIZE) / 2);
            } else {
                graphics.drawText(canvas, choices.get(i), mTExtRect[i].left, mTExtRect[i].top + TowerDimen.TEXT_SIZE + (mTExtRect[i].height() - TowerDimen.TEXT_SIZE) / 2, graphics.disableTextPaint);
            }
        }
    }

}
