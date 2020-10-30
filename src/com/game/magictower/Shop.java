package com.game.magictower;

import android.graphics.Canvas;
import android.graphics.Rect;

import com.game.magictower.Game.Status;
import com.game.magictower.res.Assets;
import com.game.magictower.res.GameGraphics;
import com.game.magictower.res.LiveBitmap;
import com.game.magictower.res.ShopData;
import com.game.magictower.res.TowerDimen;
import com.game.magictower.util.RectUtil;
import com.game.magictower.widget.BitmapButton;

public class Shop {

    private String[] choice;
    private LiveBitmap imgIcon;
    private Game game;
    private Player player;
    private int id = 0;
    private int select = 0;
    private Rect[] mTExtRect = new Rect[4];
    
    public Shop(Game game) {
        this.game = game;
        this.player = game.player;
        mTExtRect[0] = TowerDimen.R_SHOP_TEXT;
        mTExtRect[1] = RectUtil.createRect(TowerDimen.R_SHOP_TEXT, 0, TowerDimen.R_SHOP_TEXT.height());
        mTExtRect[2] = RectUtil.createRect(TowerDimen.R_SHOP_TEXT, 0, TowerDimen.R_SHOP_TEXT.height() * 2);
        mTExtRect[3] = RectUtil.createRect(TowerDimen.R_SHOP_TEXT, 0, TowerDimen.R_SHOP_TEXT.height() * 3);
    }

    public void show(int id) {
        choice = ShopData.choices[id];
        imgIcon = ShopData.imgIcons[id];
        this.id = id;
        select = 0;
        game.status = Status.Shopping;
    }
    
    public void selected() {
        switch (id) {
        case 0:     //  3 floor
            switch (select) {
                case 0:
                    if (player.getMoney() >= 25) {
                        player.setMoney(player.getMoney() - 25);
                        player.setHp(player.getHp() + 800);
                    }
                    break;
                case 1:
                    if (player.getMoney() >= 25) {
                        player.setMoney(player.getMoney() - 25);
                        player.setAttack(player.getAttack() + 4);
                    }
                    break;
                case 2:
                    if (player.getMoney() >= 25) {
                        player.setMoney(player.getMoney() - 25);
                        player.setDefend(player.getDefend() + 4);
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
                    if (player.getExp() >= 100) {
                        player.setLevel(player.getLevel() + 1);
                        player.setExp(player.getExp() - 100);
                        player.setHp(player.getHp() + 1000);
                        player.setAttack(player.getAttack() + 7);
                        player.setDefend(player.getDefend() + 7);
                    }
                    break;
                case 1:
                    if (player.getExp() >= 30) {
                        player.setExp(player.getExp() - 30);
                        player.setAttack(player.getAttack() + 5);
                    }
                    break;
                case 2:
                    if (player.getExp() >= 30) {
                        player.setExp(player.getExp() - 30);
                        player.setDefend(player.getDefend() + 5);
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
                    if (player.getMoney() >= 10) {
                        player.setMoney(player.getMoney() - 10);
                        player.setYkey(player.getYkey() + 1);
                    }
                    break;
                case 1:
                    if (player.getMoney() >= 50) {
                        player.setMoney(player.getMoney() - 50);
                        player.setBkey(player.getBkey() + 1);
                    }
                    break;
                case 2:
                    if (player.getMoney() >= 100) {
                        player.setMoney(player.getMoney() - 100);
                        player.setRkey(player.getRkey() + 1);
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
                    if (player.getMoney() >= 100) {
                        player.setMoney(player.getMoney() - 100);
                        player.setHp(player.getHp() + 4000);
                    }
                    break;
                case 1:
                    if (player.getMoney() >= 100) {
                        player.setMoney(player.getMoney() - 100);
                        player.setAttack(player.getAttack() + 20);
                    }
                    break;
                case 2:
                    if (player.getMoney() >= 100) {
                        player.setMoney(player.getMoney() - 100);
                        player.setDefend(player.getDefend() + 20);
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
                    if (player.getYkey() > 0) {
                        player.setYkey(player.getYkey() - 1);
                        player.setMoney(player.getMoney() + 7);
                    }
                    break;
                case 1:
                    if (player.getBkey() > 0) {
                        player.setBkey(player.getBkey() - 1);
                        player.setMoney(player.getMoney() + 35);
                    }
                    break;
                case 2:
                    if (player.getRkey() > 0) {
                        player.setRkey(player.getRkey() - 1);
                        player.setMoney(player.getMoney() + 70);
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
                    if (player.getExp() >= 270) {
                        player.setLevel(player.getLevel() + 3);
                        player.setExp(player.getExp() - 270);
                        player.setHp(player.getHp() + 3000);
                        player.setAttack(player.getAttack() + 20);
                        player.setDefend(player.getDefend() + 20);
                    }
                    break;
                case 1:
                    if (player.getExp() >= 95) {
                        player.setExp(player.getExp() - 95);
                        player.setAttack(player.getAttack() + 17);
                    }
                    break;
                case 2:
                    if (player.getExp() >= 95) {
                        player.setExp(player.getExp() - 95);
                        player.setDefend(player.getDefend() + 17);
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
        case BitmapButton.ID_UP:
            if (select > 0) {
                select--;
            }
            break;
        case BitmapButton.ID_DOWN:
            if (select < 3) {
                select++;
            }
            break;
        case BitmapButton.ID_OK:
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
                graphics.drawText(canvas, choice[i], mTExtRect[i].left, mTExtRect[i].top + TowerDimen.TEXT_SIZE + (mTExtRect[i].height() - TowerDimen.TEXT_SIZE) / 2);
            } else {
                graphics.drawText(canvas, choice[i], mTExtRect[i].left, mTExtRect[i].top + TowerDimen.TEXT_SIZE + (mTExtRect[i].height() - TowerDimen.TEXT_SIZE) / 2, graphics.disableTextPaint);
            }
        }
    }

}
