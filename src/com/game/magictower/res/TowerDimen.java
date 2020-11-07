package com.game.magictower.res;

import com.game.magictower.util.LogUtil;
import com.game.magictower.util.RectUtil;

import android.graphics.Rect;

public class TowerDimen {
    
    public static final int BASE_PIX_SIZE = 72;
    public static final int BASE_MARGIN_SIZE = 10;
    public static final int TOWER_ROWS = 13;
    public static final int TOWER_COLUMNS = 18;

    public static int TOWER_GRID_SIZE;
    public static float TOWER_SCALE;
    public static int TOWER_TOP;
    public static int TOWER_LEFT;
    
    public static int TEXT_SIZE;
    public static int BIG_TEXT_SIZE;
    
    
    public static Rect R_TOWER;
    public static Rect R_BTN_U;
    public static Rect R_BTN_L;
    public static Rect R_BTN_R;
    public static Rect R_BTN_D;
    
    public static Rect R_BTN_Q;
    public static Rect R_BTN_N;
    public static Rect R_BTN_S;
    public static Rect R_BTN_A;
    public static Rect R_BTN_K;
    public static Rect R_BTN_J;
    public static Rect R_BTN_OK;
    
    public static Rect R_PLR_LEVEL;
    public static Rect R_PLR_HP;
    public static Rect R_PLR_ATTACK;
    public static Rect R_PLR_DEFEND;
    public static Rect R_PLR_MONEY;
    public static Rect R_PLR_EXP;

    public static Rect R_YKEY;
    public static Rect R_BKEY;
    public static Rect R_RKEY;

    public static Rect R_FLOOR;
    
    public static Rect R_MSG;
    public static Rect R_ALERT;
    public static Rect R_ALERT_TITLE;
    public static Rect R_ALERT_INFO;
    public static Rect R_AUTO_SCROLL;
    public static Rect R_AUTO_SCROLL_INFO;
    
    public static Rect R_SHOP;
    public static Rect R_SHOP_ICON;
    public static Rect R_SHOP_TEXT;
    
    public static Rect R_DLG_BG;
    public static Rect R_DLG_ICON;
    public static Rect R_DLG_NAME;
    public static Rect R_DLG_TEXT;
    
    public static Rect R_FORECAST;
    public static Rect R_FC_ICON;
    public static Rect R_FC_NAME;
    public static Rect R_FC_HP;
    public static Rect R_FC_ATTACK;
    public static Rect R_FC_DEFEND;
    public static Rect R_FC_MONEY;
    public static Rect R_FC_LOSE;
    
    public static Rect R_BATTLE;
    public static Rect R_BTL_MST_ICON;
    public static Rect R_BTL_MST_HP;
    public static Rect R_BTL_MST_ATTACK;
    public static Rect R_BTL_MST_DEFEND;
    public static Rect R_BTL_PLR_HP;
    public static Rect R_BTL_PLR_ATTACK;
    public static Rect R_BTL_PLR_DEFEND;
    
    public static Rect R_JUMP;
    public static Rect R_JUMP_GRID;
    
    public static void init(int width, int height) {
        LogUtil.d("MagicTower:TowerDimen", "init() width=" + width + ",height=" + height);
        TOWER_GRID_SIZE = (height - BASE_MARGIN_SIZE * 2) / TOWER_ROWS;
        TOWER_SCALE = TOWER_GRID_SIZE / (float)BASE_PIX_SIZE;
        LogUtil.d("MagicTower:TowerDimen", "init() TOWER_GRID_SIZE=" + TOWER_GRID_SIZE + ",TOWER_SCALE=" + TOWER_SCALE);
        TOWER_TOP = (height - TOWER_GRID_SIZE * TOWER_ROWS) / 2;
        TOWER_LEFT = (width - TOWER_GRID_SIZE * TOWER_COLUMNS) / 2;
        LogUtil.d("MagicTower:TowerDimen", "init() TOWER_LEFT=" + TOWER_LEFT + ",TOWER_TOP=" + TOWER_TOP);
        R_TOWER = RectUtil.createRect(TOWER_LEFT, TOWER_TOP, TOWER_GRID_SIZE * TOWER_COLUMNS, TOWER_GRID_SIZE * TOWER_ROWS);
        
        TEXT_SIZE = TOWER_GRID_SIZE / 2;
        BIG_TEXT_SIZE = TEXT_SIZE * 3 / 2;

        int btn_margin = 15;
        int btn_size = (TOWER_LEFT - btn_margin * 3) / 3;
        int btn_real_size = btn_size + btn_margin;
        int btn_large_size = (TOWER_LEFT - btn_margin * 3) / 2;

        R_BTN_U = RectUtil.createRect(TOWER_LEFT - btn_real_size * 2, height - btn_real_size * 3 - btn_size, btn_size, btn_size);
        R_BTN_L = RectUtil.createRect(R_BTN_U, -btn_real_size, btn_real_size);
        R_BTN_R = RectUtil.createRect(R_BTN_U, btn_real_size, btn_real_size);
        R_BTN_D = RectUtil.createRect(R_BTN_U, 0, btn_real_size * 2);
        
        R_BTN_Q = RectUtil.createRect(R_TOWER.right + btn_margin, height - btn_real_size * 4 - btn_size / 2, btn_large_size, btn_size);
        R_BTN_N = RectUtil.createRect(R_BTN_Q, btn_large_size + btn_margin, 0);
        R_BTN_S = RectUtil.createRect(R_BTN_Q, 0, btn_real_size);
        R_BTN_A = RectUtil.createRect(R_BTN_N, 0, btn_real_size);
        R_BTN_K = RectUtil.createRect(R_BTN_S, 0, btn_real_size);
        R_BTN_J = RectUtil.createRect(R_BTN_A, 0, btn_real_size);
        R_BTN_OK = RectUtil.createRect(R_TOWER.right + (TOWER_LEFT - btn_large_size) / 2, R_BTN_K.top + btn_real_size, btn_large_size, btn_size);
        
        R_PLR_LEVEL = RectUtil.createRect(TOWER_LEFT + (int)(182 * TOWER_SCALE), TOWER_TOP + (int)(118 * TOWER_SCALE), (int)(108 * TOWER_SCALE), TEXT_SIZE);
        R_PLR_HP = RectUtil.createRect(TOWER_LEFT + (int)(170 * TOWER_SCALE), TOWER_TOP + (int)(183 * TOWER_SCALE), (int)(175 * TOWER_SCALE), TEXT_SIZE);
        R_PLR_ATTACK = RectUtil.createRect(TOWER_LEFT + (int)(170 * TOWER_SCALE), TOWER_TOP + (int)(239 * TOWER_SCALE), (int)(175 * TOWER_SCALE), TEXT_SIZE);
        R_PLR_DEFEND = RectUtil.createRect(TOWER_LEFT + (int)(170 * TOWER_SCALE), TOWER_TOP + (int)(295 * TOWER_SCALE), (int)(175 * TOWER_SCALE), TEXT_SIZE);
        R_PLR_MONEY = RectUtil.createRect(TOWER_LEFT + (int)(170 * TOWER_SCALE), TOWER_TOP + (int)(351 * TOWER_SCALE), (int)(175 * TOWER_SCALE), TEXT_SIZE);
        R_PLR_EXP = RectUtil.createRect(TOWER_LEFT + (int)(170 * TOWER_SCALE), TOWER_TOP + (int)(407 * TOWER_SCALE), (int)(175 * TOWER_SCALE), TEXT_SIZE);

        R_YKEY = RectUtil.createRect(TOWER_LEFT + (int)(175 * TOWER_SCALE), TOWER_TOP + (int)(499 * TOWER_SCALE), (int)(120 * TOWER_SCALE), TEXT_SIZE);
        R_BKEY = RectUtil.createRect(TOWER_LEFT + (int)(175 * TOWER_SCALE), TOWER_TOP + (int)(574 * TOWER_SCALE), (int)(120 * TOWER_SCALE), TEXT_SIZE);
        R_RKEY = RectUtil.createRect(TOWER_LEFT + (int)(175 * TOWER_SCALE), TOWER_TOP + (int)(647 * TOWER_SCALE), (int)(120 * TOWER_SCALE), TEXT_SIZE);

        R_FLOOR = RectUtil.createRect(TOWER_LEFT + (int)(175 * TOWER_SCALE), TOWER_TOP + (int)(721 * TOWER_SCALE), (int)(70 * TOWER_SCALE), TEXT_SIZE);
        
        R_MSG = RectUtil.createRect(R_TOWER.left + TOWER_GRID_SIZE * 6, R_TOWER.top + TOWER_GRID_SIZE * 3, TOWER_GRID_SIZE * 11, TOWER_GRID_SIZE * 2);
        R_ALERT = RectUtil.createRect(R_TOWER.left + TOWER_GRID_SIZE * 6, R_TOWER.top + TOWER_GRID_SIZE * 3, TOWER_GRID_SIZE * 11, TOWER_GRID_SIZE * 4);
        R_ALERT_TITLE = new Rect(R_ALERT.left + TOWER_GRID_SIZE / 2, R_ALERT.top + TOWER_GRID_SIZE / 2, R_ALERT.right - TOWER_GRID_SIZE / 2, R_ALERT.top + TOWER_GRID_SIZE * 3 / 2);
        R_ALERT_INFO = new Rect(R_ALERT_TITLE.left, R_ALERT_TITLE.bottom, R_ALERT_TITLE.right, R_ALERT_TITLE.bottom + TOWER_GRID_SIZE * 2);
        R_AUTO_SCROLL = RectUtil.createRect(R_TOWER.left + TOWER_GRID_SIZE * 6, R_TOWER.top + TOWER_GRID_SIZE * 2, TOWER_GRID_SIZE * 11, TOWER_GRID_SIZE * 9);
        R_AUTO_SCROLL_INFO = new Rect(R_AUTO_SCROLL.left + TOWER_GRID_SIZE / 2, R_AUTO_SCROLL.top + TOWER_GRID_SIZE / 2, R_AUTO_SCROLL.right - TOWER_GRID_SIZE / 2, R_AUTO_SCROLL.bottom - TOWER_GRID_SIZE / 2);
        
        R_SHOP = RectUtil.createRect(R_TOWER.left + TOWER_GRID_SIZE * 7, R_TOWER.top + TOWER_GRID_SIZE * 2, TOWER_GRID_SIZE * 9, TOWER_GRID_SIZE * 5);
        R_SHOP_ICON = RectUtil.createRect(R_SHOP.left + TOWER_GRID_SIZE / 2, R_SHOP.top + TOWER_GRID_SIZE / 2, TOWER_GRID_SIZE, TOWER_GRID_SIZE);
        R_SHOP_TEXT = new Rect(R_SHOP_ICON.right + TOWER_GRID_SIZE / 2, R_SHOP_ICON.top, R_SHOP.right - TOWER_GRID_SIZE / 2, R_SHOP_ICON.bottom);
        
        R_DLG_BG = RectUtil.createRect(R_TOWER.left + TOWER_GRID_SIZE * 7, R_TOWER.top + TOWER_GRID_SIZE * 6, TOWER_GRID_SIZE * 9, TOWER_GRID_SIZE * 4);
        R_DLG_ICON = RectUtil.createRect(R_DLG_BG.left + TOWER_GRID_SIZE / 2, R_DLG_BG.top + TOWER_GRID_SIZE / 2, TOWER_GRID_SIZE, TOWER_GRID_SIZE);
        R_DLG_NAME = new Rect(R_DLG_ICON.right, R_DLG_ICON.top, R_DLG_BG.right - TOWER_GRID_SIZE / 2, R_DLG_ICON.bottom);
        R_DLG_TEXT = new Rect(R_DLG_ICON.right, R_DLG_NAME.bottom, R_DLG_NAME.right, R_DLG_BG.bottom - TOWER_GRID_SIZE / 2);
        
        R_FORECAST = RectUtil.createRect(TOWER_LEFT + TOWER_GRID_SIZE * 6, TOWER_TOP + TOWER_GRID_SIZE, TOWER_GRID_SIZE * 11, TOWER_GRID_SIZE * 11);
        R_FC_ICON = RectUtil.createRect(R_FORECAST.left, R_FORECAST.top, TOWER_GRID_SIZE, TOWER_GRID_SIZE);
        R_FC_NAME = RectUtil.createRect(R_FC_ICON.right, R_FC_ICON.top, TOWER_GRID_SIZE * 2, TOWER_GRID_SIZE);
        R_FC_HP = RectUtil.createRect(R_FC_NAME.right, R_FC_NAME.top, TOWER_GRID_SIZE * 3 / 2, TOWER_GRID_SIZE);
        R_FC_ATTACK = RectUtil.createRect(R_FC_HP.right, R_FC_HP.top, TOWER_GRID_SIZE * 4 / 3, TOWER_GRID_SIZE);
        R_FC_DEFEND = RectUtil.createRect(R_FC_ATTACK.right, R_FC_ATTACK.top, TOWER_GRID_SIZE * 4 / 3, TOWER_GRID_SIZE);
        R_FC_MONEY = RectUtil.createRect(R_FC_DEFEND.right, R_FC_DEFEND.top, TOWER_GRID_SIZE * 5 / 2, TOWER_GRID_SIZE);
        R_FC_LOSE = RectUtil.createRect(R_FC_MONEY.right, R_FC_MONEY.top, TOWER_GRID_SIZE * 4 / 3, TOWER_GRID_SIZE);
        
        int bat_width = (int)(1242 * TOWER_SCALE);
        int bat_height = (int)(541 * TOWER_SCALE);
        int bat_pad_x = (R_TOWER.width() - bat_width) / 2;
        int bat_pad_y = TOWER_GRID_SIZE * 2;
        R_BATTLE = RectUtil.createRect(R_TOWER.left + bat_pad_x, R_TOWER.top + bat_pad_y, bat_width, bat_height);
        R_BTL_MST_ICON = RectUtil.createRect((int)(26 * TOWER_SCALE) + R_BATTLE.left, (int)(68 * TOWER_SCALE) + R_BATTLE.top, TOWER_GRID_SIZE * 3, TOWER_GRID_SIZE * 3);
        R_BTL_MST_HP = RectUtil.createRect((int)(400 * TOWER_SCALE) + R_BATTLE.left, (int)(68 * TOWER_SCALE) + R_BATTLE.top, (int)(120 * TOWER_SCALE), TEXT_SIZE);
        R_BTL_MST_ATTACK = RectUtil.createRect((int)(400 * TOWER_SCALE) + R_BATTLE.left, (int)(194 * TOWER_SCALE) + R_BATTLE.top, (int)(120), TEXT_SIZE);
        R_BTL_MST_DEFEND = RectUtil.createRect((int)(400 * TOWER_SCALE) + R_BATTLE.left, (int)(324 * TOWER_SCALE) + R_BATTLE.top, (int)(120 * TOWER_SCALE), TEXT_SIZE);
        R_BTL_PLR_HP = RectUtil.createRect((int)(725 * TOWER_SCALE) + R_BATTLE.left, (int)(68 * TOWER_SCALE) + R_BATTLE.top, (int)(120 * TOWER_SCALE), TEXT_SIZE);
        R_BTL_PLR_ATTACK = RectUtil.createRect((int)(725 * TOWER_SCALE) + R_BATTLE.left, (int)(194 * TOWER_SCALE) + R_BATTLE.top, (int)(120 * TOWER_SCALE), TEXT_SIZE);
        R_BTL_PLR_DEFEND = RectUtil.createRect((int)(725 * TOWER_SCALE) + R_BATTLE.left, (int)(324 * TOWER_SCALE) + R_BATTLE.top, (int)(120 * TOWER_SCALE), TEXT_SIZE);
        
        R_JUMP = RectUtil.createRect(R_TOWER.left + TOWER_GRID_SIZE * 7, R_TOWER.top + TOWER_GRID_SIZE * 2, TOWER_GRID_SIZE * 9, TOWER_GRID_SIZE * 6);
        R_JUMP_GRID = RectUtil.createRect(R_JUMP.left + TOWER_GRID_SIZE / 2, R_JUMP.top + TOWER_GRID_SIZE / 2, TOWER_GRID_SIZE * 2, TOWER_GRID_SIZE);
    }

}
