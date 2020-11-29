package com.game.magictower.res;

import com.game.magictower.util.LogUtil;
import com.game.magictower.util.RectUtil;

import android.graphics.Rect;

public class TowerDimen {
    
    public static final int BASE_PIX_SIZE = 72;
    public static final int GRID_NUMS = 11;

    public static int GRID_SIZE;
    public static float TOWER_SCALE;
    public static int TOWER_TOP;
    public static int TOWER_LEFT;
    
    public static int TEXT_SIZE;
    public static int BIG_TEXT_SIZE;
    
    public static Rect R_TOWER;
    
    public static Rect R_TOUCH_GRID;
    
    public static Rect R_BTN_U;
    public static Rect R_BTN_L;
    public static Rect R_BTN_R;
    public static Rect R_BTN_D;
    
    public static Rect R_ROCKER;
    
    public static Rect R_BTN_K;
    public static Rect R_BTN_J;
    public static Rect R_BTN_OK;
    public static Rect R_BTN_Q;
    public static Rect R_BTN_S;
    public static Rect R_BTN_A;
    
    public static Rect R_PLR_ICON;
    public static Rect R_PLR_LV_V;
    public static Rect R_PLR_LV_L;

    public static Rect R_YKEY_ICON;
    public static Rect R_YKEY_V;
    public static Rect R_YKEY_L;
    
    public static Rect R_BKEY_ICON;
    public static Rect R_BKEY_V;
    public static Rect R_BKEY_L;
    
    public static Rect R_RKEY_ICON;
    public static Rect R_RKEY_V;
    public static Rect R_RKEY_L;

    public static Rect R_FLOOR_S;
    public static Rect R_FLOOR_V;
    public static Rect R_FLOOR_L;
    
    public static Rect R_PLR_HP_L;
    public static Rect R_PLR_HP_V;
    public static Rect R_PLR_ATTACK_L;
    public static Rect R_PLR_ATTACK_V;
    public static Rect R_PLR_DEFEND_L;
    public static Rect R_PLR_DEFEND_V;
    public static Rect R_PLR_MONEY_L;
    public static Rect R_PLR_MONEY_V;
    public static Rect R_PLR_EXP_L;
    public static Rect R_PLR_EXP_V;
    
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
    public static Rect R_BTL_PLR_ICON;
    public static Rect R_BTL_MST_ICON;
    
    public static Rect R_BTL_PLR_HP_L;
    public static Rect R_BTL_PLR_HP_V;
    public static Rect R_BTL_PLR_ATTACK_L;
    public static Rect R_BTL_PLR_ATTACK_V;
    public static Rect R_BTL_PLR_DEFEND_L;
    public static Rect R_BTL_PLR_DEFEND_V;
    
    public static Rect R_BTL_MST_HP_V;
    public static Rect R_BTL_MST_HP_L;
    public static Rect R_BTL_MST_ATTACK_V;
    public static Rect R_BTL_MST_ATTACK_L;
    public static Rect R_BTL_MST_DEFEND_V;
    public static Rect R_BTL_MST_DEFEND_L;
    
    public static Rect R_JUMP;
    public static Rect R_JUMP_GRID;
    
    public static void init(int width, int height) {
        LogUtil.d("MagicTower:TowerDimen", "init() width=" + width + ",height=" + height);
        GRID_SIZE = width / (GRID_NUMS + 1);
        TOWER_SCALE = GRID_SIZE / (float)BASE_PIX_SIZE;
        LogUtil.d("MagicTower:TowerDimen", "init() TOWER_GRID_SIZE=" + GRID_SIZE + ",TOWER_SCALE=" + TOWER_SCALE);
        TOWER_TOP = (height - GRID_SIZE * GRID_NUMS) / 2;
        TOWER_LEFT = (width - GRID_SIZE * GRID_NUMS) / 2;
        LogUtil.d("MagicTower:TowerDimen", "init() TOWER_LEFT=" + TOWER_LEFT + ",TOWER_TOP=" + TOWER_TOP);
        R_TOWER = RectUtil.createRect(TOWER_LEFT, TOWER_TOP, GRID_SIZE * GRID_NUMS, GRID_SIZE * GRID_NUMS);
        
        R_TOUCH_GRID = RectUtil.createRect(R_TOWER.left + GRID_SIZE * 3 / 10, R_TOWER.top + GRID_SIZE * 3 / 10, GRID_SIZE * 4 / 10, GRID_SIZE * 4 / 10);
        
        TEXT_SIZE = GRID_SIZE / 2;
        BIG_TEXT_SIZE = TEXT_SIZE * 3 / 2;

        int btn_margin = GRID_SIZE * 3 / 10;
        int btn_size = GRID_SIZE * 12 / 10;
        int btn_real_size = btn_size + btn_margin;
        int btn_large_size = (GRID_SIZE * 5 - btn_margin * 3) / 2;

        R_BTN_U = RectUtil.createRect(TOWER_LEFT + btn_margin + btn_real_size, R_TOWER.bottom + GRID_SIZE / 2 + btn_margin, btn_size, btn_size);
        R_BTN_L = RectUtil.createRect(R_BTN_U, -btn_real_size, btn_real_size);
        R_BTN_R = RectUtil.createRect(R_BTN_U, btn_real_size, btn_real_size);
        R_BTN_D = RectUtil.createRect(R_BTN_U, 0, btn_real_size * 2);
        
        R_ROCKER = RectUtil.createRect(R_BTN_U, 0, btn_real_size);
        
        R_BTN_OK = RectUtil.createRect(R_TOWER.right - (btn_large_size + btn_margin) * 2, R_TOWER.bottom + GRID_SIZE / 2 + btn_margin, btn_large_size, btn_size);
        R_BTN_J = RectUtil.createRect(R_BTN_OK, btn_large_size + btn_margin, 0);
        R_BTN_K = RectUtil.createRect(R_BTN_OK, 0, btn_real_size);
        R_BTN_Q = RectUtil.createRect(R_BTN_J, 0, btn_real_size);
        R_BTN_S = RectUtil.createRect(R_BTN_K, 0, btn_real_size);
        R_BTN_A = RectUtil.createRect(R_BTN_Q, 0, btn_real_size);
        
        int txt_margin = GRID_SIZE / 10;
        int txt_height = GRID_SIZE * 8 / 10;
        
        R_PLR_ICON = RectUtil.createRect(TOWER_LEFT, TOWER_TOP - GRID_SIZE / 2 - (txt_height + txt_margin) * 5, txt_height, txt_height);
        R_PLR_LV_V = RectUtil.createRect(R_PLR_ICON.right, R_PLR_ICON.top, GRID_SIZE * 2, txt_height);
        R_PLR_LV_L = RectUtil.createRect(R_PLR_LV_V.right, R_PLR_LV_V.top, txt_height, txt_height);

        R_YKEY_ICON = RectUtil.createRect(TOWER_LEFT, R_PLR_ICON.bottom + txt_margin, txt_height, txt_height);
        R_YKEY_V = RectUtil.createRect(R_YKEY_ICON.right, R_YKEY_ICON.top, GRID_SIZE * 2, txt_height);
        R_YKEY_L = RectUtil.createRect(R_YKEY_V.right, R_YKEY_V.top, txt_height, txt_height);
        
        R_BKEY_ICON = RectUtil.createRect(TOWER_LEFT, R_YKEY_ICON.bottom + txt_margin, txt_height, txt_height);
        R_BKEY_V = RectUtil.createRect(R_BKEY_ICON.right, R_BKEY_ICON.top, GRID_SIZE * 2, txt_height);
        R_BKEY_L = RectUtil.createRect(R_BKEY_V.right, R_BKEY_V.top, txt_height, txt_height);
        
        R_RKEY_ICON = RectUtil.createRect(TOWER_LEFT, R_BKEY_ICON.bottom + txt_margin, txt_height, txt_height);
        R_RKEY_V = RectUtil.createRect(R_RKEY_ICON.right, R_RKEY_ICON.top, GRID_SIZE * 2, txt_height);
        R_RKEY_L = RectUtil.createRect(R_RKEY_V.right, R_RKEY_V.top, txt_height, txt_height);

        R_FLOOR_S = RectUtil.createRect(TOWER_LEFT, R_RKEY_ICON.bottom + txt_margin, txt_height, txt_height);
        R_FLOOR_V = RectUtil.createRect(R_FLOOR_S.right, R_FLOOR_S.top, GRID_SIZE * 2, txt_height);
        R_FLOOR_L = RectUtil.createRect(R_FLOOR_V.right, R_FLOOR_V.top, txt_height, txt_height);
        
        
        int txt_left = R_TOWER.right - txt_height * 2 - GRID_SIZE * 2;
        
        R_PLR_HP_L = RectUtil.createRect(txt_left, TOWER_TOP - GRID_SIZE / 2 - (txt_height + txt_margin) * 5, txt_height * 2, txt_height);
        R_PLR_HP_V = RectUtil.createRect(R_PLR_HP_L.right, R_PLR_HP_L.top, GRID_SIZE * 2, txt_height);
        R_PLR_ATTACK_L = RectUtil.createRect(txt_left, R_PLR_HP_L.bottom + txt_margin, txt_height * 2, txt_height);
        R_PLR_ATTACK_V = RectUtil.createRect(R_PLR_ATTACK_L.right, R_PLR_ATTACK_L.top, GRID_SIZE * 2, txt_height);
        R_PLR_DEFEND_L = RectUtil.createRect(txt_left, R_PLR_ATTACK_L.bottom + txt_margin, txt_height * 2, txt_height);
        R_PLR_DEFEND_V = RectUtil.createRect(R_PLR_DEFEND_L.right, R_PLR_DEFEND_L.top, GRID_SIZE * 2, txt_height);
        R_PLR_MONEY_L = RectUtil.createRect(txt_left, R_PLR_DEFEND_L.bottom + txt_margin, txt_height * 2, txt_height);
        R_PLR_MONEY_V = RectUtil.createRect(R_PLR_MONEY_L.right, R_PLR_MONEY_L.top, GRID_SIZE * 2, txt_height);
        R_PLR_EXP_L = RectUtil.createRect(txt_left, R_PLR_MONEY_L.bottom + txt_margin, txt_height * 2, txt_height);
        R_PLR_EXP_V = RectUtil.createRect(R_PLR_EXP_L.right, R_PLR_EXP_L.top, GRID_SIZE * 2, txt_height);
        
        R_MSG = RectUtil.createRect(R_TOWER.left, R_TOWER.top + GRID_SIZE * 3, GRID_SIZE * 11, GRID_SIZE * 2);
        R_ALERT = RectUtil.createRect(R_TOWER.left, R_TOWER.top + GRID_SIZE * 3, GRID_SIZE * 11, GRID_SIZE * 4);
        R_ALERT_TITLE = new Rect(R_ALERT.left + GRID_SIZE / 2, R_ALERT.top + GRID_SIZE / 2, R_ALERT.right - GRID_SIZE / 2, R_ALERT.top + GRID_SIZE * 3 / 2);
        R_ALERT_INFO = new Rect(R_ALERT_TITLE.left, R_ALERT_TITLE.bottom, R_ALERT_TITLE.right, R_ALERT_TITLE.bottom + GRID_SIZE * 2);
        R_AUTO_SCROLL = RectUtil.createRect(R_TOWER.left, R_TOWER.top + GRID_SIZE, GRID_SIZE * 11, GRID_SIZE * 9);
        R_AUTO_SCROLL_INFO = new Rect(R_AUTO_SCROLL.left + GRID_SIZE / 2, R_AUTO_SCROLL.top + GRID_SIZE / 2, R_AUTO_SCROLL.right - GRID_SIZE / 2, R_AUTO_SCROLL.bottom - GRID_SIZE / 2);
        
        R_SHOP = RectUtil.createRect(R_TOWER.left + GRID_SIZE / 2, R_TOWER.top + GRID_SIZE * 2, GRID_SIZE * 10, GRID_SIZE * 5);
        R_SHOP_ICON = RectUtil.createRect(R_SHOP.left + GRID_SIZE / 2, R_SHOP.top + GRID_SIZE / 2, GRID_SIZE, GRID_SIZE);
        R_SHOP_TEXT = new Rect(R_SHOP_ICON.right + GRID_SIZE / 2, R_SHOP_ICON.top, R_SHOP.right - GRID_SIZE / 2, R_SHOP_ICON.bottom);
        
        R_DLG_BG = RectUtil.createRect(R_TOWER.left + GRID_SIZE, R_TOWER.top + GRID_SIZE * 6, GRID_SIZE * 9, GRID_SIZE * 4);
        R_DLG_ICON = RectUtil.createRect(R_DLG_BG.left + GRID_SIZE / 2, R_DLG_BG.top + GRID_SIZE / 2, GRID_SIZE, GRID_SIZE);
        R_DLG_NAME = new Rect(R_DLG_ICON.right, R_DLG_ICON.top, R_DLG_BG.right - GRID_SIZE / 2, R_DLG_ICON.bottom);
        R_DLG_TEXT = new Rect(R_DLG_ICON.right, R_DLG_NAME.bottom, R_DLG_NAME.right, R_DLG_BG.bottom - GRID_SIZE / 2);
        
        R_FORECAST = RectUtil.createRect(TOWER_LEFT, TOWER_TOP, GRID_SIZE * 11, GRID_SIZE * 11);
        R_FC_ICON = RectUtil.createRect(R_FORECAST.left, R_FORECAST.top, GRID_SIZE, GRID_SIZE);
        R_FC_NAME = RectUtil.createRect(R_FC_ICON.right, R_FC_ICON.top, GRID_SIZE * 2, GRID_SIZE);
        R_FC_HP = RectUtil.createRect(R_FC_NAME.right, R_FC_NAME.top, GRID_SIZE * 3 / 2, GRID_SIZE);
        R_FC_ATTACK = RectUtil.createRect(R_FC_HP.right, R_FC_HP.top, GRID_SIZE * 4 / 3, GRID_SIZE);
        R_FC_DEFEND = RectUtil.createRect(R_FC_ATTACK.right, R_FC_ATTACK.top, GRID_SIZE * 4 / 3, GRID_SIZE);
        R_FC_MONEY = RectUtil.createRect(R_FC_DEFEND.right, R_FC_DEFEND.top, GRID_SIZE * 5 / 2, GRID_SIZE);
        R_FC_LOSE = RectUtil.createRect(R_FC_MONEY.right, R_FC_MONEY.top, GRID_SIZE * 4 / 3, GRID_SIZE);
        
        R_BATTLE = RectUtil.createRect(R_TOWER.left + GRID_SIZE / 2, R_TOWER.top + GRID_SIZE, GRID_SIZE * 10, GRID_SIZE * 7);
        R_BTL_PLR_ICON = RectUtil.createRect(R_BATTLE.left + GRID_SIZE, R_BATTLE.top + GRID_SIZE, GRID_SIZE * 2, GRID_SIZE * 2);
        R_BTL_MST_ICON = RectUtil.createRect(R_BATTLE.left + GRID_SIZE * 7, R_BATTLE.top + GRID_SIZE, GRID_SIZE * 2, GRID_SIZE * 2);
        
        R_BTL_PLR_HP_L = RectUtil.createRect(R_BTL_PLR_ICON.left, R_BTL_PLR_ICON.bottom, GRID_SIZE * 3 / 2, GRID_SIZE);
        R_BTL_PLR_HP_V = RectUtil.createRect(R_BTL_PLR_HP_L.right, R_BTL_PLR_HP_L.top, GRID_SIZE * 2, GRID_SIZE);
        R_BTL_PLR_ATTACK_L = RectUtil.createRect(R_BTL_PLR_HP_L.left, R_BTL_PLR_HP_L.bottom, GRID_SIZE * 3 / 2, GRID_SIZE);
        R_BTL_PLR_ATTACK_V = RectUtil.createRect(R_BTL_PLR_ATTACK_L.right, R_BTL_PLR_ATTACK_L.top, GRID_SIZE * 2, GRID_SIZE);
        R_BTL_PLR_DEFEND_L = RectUtil.createRect(R_BTL_PLR_ATTACK_L.left, R_BTL_PLR_ATTACK_L.bottom, GRID_SIZE * 3 / 2, GRID_SIZE);
        R_BTL_PLR_DEFEND_V = RectUtil.createRect(R_BTL_PLR_DEFEND_L.right, R_BTL_PLR_DEFEND_L.top, GRID_SIZE * 2, GRID_SIZE);
        
        R_BTL_MST_HP_V = RectUtil.createRect(R_BTL_PLR_HP_V.right + GRID_SIZE, R_BTL_MST_ICON.bottom, GRID_SIZE * 2, GRID_SIZE);
        R_BTL_MST_HP_L = RectUtil.createRect(R_BTL_MST_HP_V.right, R_BTL_MST_HP_V.top, GRID_SIZE * 3 / 2, GRID_SIZE);
        R_BTL_MST_ATTACK_V = RectUtil.createRect(R_BTL_MST_HP_V.left, R_BTL_MST_HP_V.bottom, GRID_SIZE * 2, GRID_SIZE);
        R_BTL_MST_ATTACK_L = RectUtil.createRect(R_BTL_MST_ATTACK_V.right, R_BTL_MST_ATTACK_V.top, GRID_SIZE * 3 / 2, GRID_SIZE);
        R_BTL_MST_DEFEND_V = RectUtil.createRect(R_BTL_MST_ATTACK_V.left, R_BTL_MST_ATTACK_V.bottom, GRID_SIZE * 2, GRID_SIZE);
        R_BTL_MST_DEFEND_L = RectUtil.createRect(R_BTL_MST_DEFEND_V.right, R_BTL_MST_DEFEND_V.top, GRID_SIZE * 3 / 2, GRID_SIZE);
        
        R_JUMP = RectUtil.createRect(R_TOWER.left + GRID_SIZE, R_TOWER.top + GRID_SIZE * 2, GRID_SIZE * 9, GRID_SIZE * 6);
        R_JUMP_GRID = RectUtil.createRect(R_JUMP.left + GRID_SIZE / 2, R_JUMP.top + GRID_SIZE / 2, GRID_SIZE * 2, GRID_SIZE);
    }

}
