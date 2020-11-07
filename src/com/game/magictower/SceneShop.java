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
import com.game.magictower.widget.TextButton;

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
                        game.player.setMoney(game.player.getMoney() - 25);
                        game.player.setHp(game.player.getHp() + 800);
                    }
                    break;
                case 1:
                    if (game.player.getMoney() >= 25) {
                        game.player.setMoney(game.player.getMoney() - 25);
                        game.player.setAttack(game.player.getAttack() + 4);
                    }
                    break;
                case 2:
                    if (game.player.getMoney() >= 25) {
                        game.player.setMoney(game.player.getMoney() - 25);
                        game.player.setDefend(game.player.getDefend() + 4);
                    }
                    break;
                case 3:
                    game.status = Status.Playing;
                    break;
            }
            break;
        case 1:     // 5 floor
            switch (select) {
                case 0:
                    if (game.player.getExp() >= 100) {
                        game.player.setLevel(game.player.getLevel() + 1);
                        game.player.setExp(game.player.getExp() - 100);
                        game.player.setHp(game.player.getHp() + 1000);
                        game.player.setAttack(game.player.getAttack() + 7);
                        game.player.setDefend(game.player.getDefend() + 7);
                        GlobalSoundPool.getInstance().playSound(Assets.getInstance().getSoundId(Assets.SND_ID_LEVEL));
                    }
                    break;
                case 1:
                    if (game.player.getExp() >= 30) {
                        game.player.setExp(game.player.getExp() - 30);
                        game.player.setAttack(game.player.getAttack() + 5);
                    }
                    break;
                case 2:
                    if (game.player.getExp() >= 30) {
                        game.player.setExp(game.player.getExp() - 30);
                        game.player.setDefend(game.player.getDefend() + 5);
                    }
                    break;
                case 3:
                    game.status = Status.Playing;
                    break;
            }
            break;
        case 2:     // 5 floor
            switch (select) {
                case 0:
                    if (game.player.getMoney() >= 10) {
                        game.player.setMoney(game.player.getMoney() - 10);
                        game.player.setYkey(game.player.getYkey() + 1);
                    }
                    break;
                case 1:
                    if (game.player.getMoney() >= 50) {
                        game.player.setMoney(game.player.getMoney() - 50);
                        game.player.setBkey(game.player.getBkey() + 1);
                    }
                    break;
                case 2:
                    if (game.player.getMoney() >= 100) {
                        game.player.setMoney(game.player.getMoney() - 100);
                        game.player.setRkey(game.player.getRkey() + 1);
                    }
                    break;
                case 3:
                    game.status = Status.Playing;
                    break;
            }
            break;
        case 3:     // 11 floor
            switch (select) {
                case 0:
                    if (game.player.getMoney() >= 100) {
                        game.player.setMoney(game.player.getMoney() - 100);
                        game.player.setHp(game.player.getHp() + 4000);
                    }
                    break;
                case 1:
                    if (game.player.getMoney() >= 100) {
                        game.player.setMoney(game.player.getMoney() - 100);
                        game.player.setAttack(game.player.getAttack() + 20);
                    }
                    break;
                case 2:
                    if (game.player.getMoney() >= 100) {
                        game.player.setMoney(game.player.getMoney() - 100);
                        game.player.setDefend(game.player.getDefend() + 20);
                    }
                    break;
                case 3:
                    game.status = Status.Playing;
                    break;
            }
            break;
        case 4:     // 12 floor
            switch (select) {
                case 0:
                    if (game.player.getYkey() > 0) {
                        game.player.setYkey(game.player.getYkey() - 1);
                        game.player.setMoney(game.player.getMoney() + 7);
                    }
                    break;
                case 1:
                    if (game.player.getBkey() > 0) {
                        game.player.setBkey(game.player.getBkey() - 1);
                        game.player.setMoney(game.player.getMoney() + 35);
                    }
                    break;
                case 2:
                    if (game.player.getRkey() > 0) {
                        game.player.setRkey(game.player.getRkey() - 1);
                        game.player.setMoney(game.player.getMoney() + 70);
                    }
                    break;
                case 3:
                    game.status = Status.Playing;
                    break;
            }
            break;
        case 5:     // 13 floor
            switch (select) {
                case 0:
                    if (game.player.getExp() >= 270) {
                        game.player.setLevel(game.player.getLevel() + 3);
                        game.player.setExp(game.player.getExp() - 270);
                        game.player.setHp(game.player.getHp() + 3000);
                        game.player.setAttack(game.player.getAttack() + 20);
                        game.player.setDefend(game.player.getDefend() + 20);
                    }
                    break;
                case 1:
                    if (game.player.getExp() >= 95) {
                        game.player.setExp(game.player.getExp() - 95);
                        game.player.setAttack(game.player.getAttack() + 17);
                    }
                    break;
                case 2:
                    if (game.player.getExp() >= 95) {
                        game.player.setExp(game.player.getExp() - 95);
                        game.player.setDefend(game.player.getDefend() + 17);
                    }
                    break;
                case 3:
                    game.status = Status.Playing;
                    break;
            }
            break;
        }
    }
        
    public void onBtnKey(int btnId) {
        switch (btnId) {
        case TextButton.ID_UP:
            if (select > 0) {
                select--;
            }
            break;
        case TextButton.ID_DOWN:
            if (select < 3) {
                select++;
            }
            break;
        case TextButton.ID_OK:
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
