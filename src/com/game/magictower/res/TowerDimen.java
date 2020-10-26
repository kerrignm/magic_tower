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
    public static int MARGIN_SIZE;
    public static int TOWER_TOP;
    public static int TOWER_LEFT;

    public static int BTN_MARGIN;
    public static int BTN_SIZE;
    public static int BTN_REAL_SIZE;
    public static int BTN_LARGE_SIZE;
    
    public static int TEXT_SIZE;
    
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
    
    public static int LEVEL_LEFT;
    public static int LEVEL_TOP;
    public static int HP_LEFT;
    public static int HP_TOP;
    public static int ATTACK_LEFT;
    public static int ATTACK_TOP;
    public static int DEFEND_LEFT;
    public static int DEFEND_TOP;
    public static int MONEY_LEFT;
    public static int MONEY_TOP;
    public static int EXP_LEFT;
    public static int EXP_TOP;

    public static int YKEY_LEFT;
    public static int YKEY_TOP;
    public static int BKEY_LEFT;
    public static int BKEY_TOP;
    public static int RKEY_LEFT;
    public static int RKEY_TOP;

    public static int FLOOR_LEFT;
    public static int FLOOR_TOP;
    
    public static Rect R_MSG;
    
    public static Rect R_SHOP;
    public static Rect R_SHOP_ICON;
    public static Rect R_SHOP_TEXT;
    
    public static Rect R_DLG_BG_NPC;
    public static Rect R_DLG_ICON_NPC;
    public static Rect R_DLG_NAME_NPC;
    public static Rect R_DLG_TEXT_NPC;
    
    public static Rect R_DLG_BG_PLR;
    public static Rect R_DLG_ICON_PLR;
    public static Rect R_DLG_NAME_PLR;
    public static Rect R_DLG_TEXT_PLR;
    
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
        MARGIN_SIZE = (height - TOWER_GRID_SIZE * TOWER_ROWS) / 2;
        TOWER_TOP = MARGIN_SIZE;
        TOWER_LEFT = (width - TOWER_GRID_SIZE * TOWER_COLUMNS) / 2;
        LogUtil.d("MagicTower:TowerDimen", "init() TOWER_LEFT=" + TOWER_LEFT + ",TOWER_TOP=" + TOWER_TOP);
        R_TOWER = RectUtil.createRect(TOWER_LEFT, TOWER_TOP, TOWER_GRID_SIZE * TOWER_COLUMNS, TOWER_GRID_SIZE * TOWER_ROWS);

        BTN_MARGIN = 15;
        BTN_SIZE = (TOWER_LEFT - BTN_MARGIN * 3) / 3;
        BTN_REAL_SIZE = BTN_SIZE + BTN_MARGIN;
        BTN_LARGE_SIZE = BTN_SIZE + BTN_REAL_SIZE;
        
        TEXT_SIZE = TOWER_GRID_SIZE / 2;

        R_BTN_U = RectUtil.createRect(TOWER_LEFT - BTN_REAL_SIZE * 2, height - BTN_REAL_SIZE * 3,
                        BTN_SIZE, BTN_SIZE);
        R_BTN_L = RectUtil.createRect(R_BTN_U, -BTN_REAL_SIZE, BTN_REAL_SIZE);
        R_BTN_R = RectUtil.createRect(R_BTN_U, BTN_REAL_SIZE, BTN_REAL_SIZE);
        R_BTN_D = RectUtil.createRect(R_BTN_U, 0, BTN_REAL_SIZE * 2);
        
        R_BTN_Q = RectUtil.createRect(R_TOWER.right + BTN_MARGIN, height - BTN_REAL_SIZE * 4,
                    BTN_SIZE, BTN_SIZE);
        R_BTN_N = RectUtil.createRect(R_BTN_Q, BTN_REAL_SIZE, 0);
        R_BTN_S = RectUtil.createRect(R_BTN_Q, 0, BTN_REAL_SIZE);
        R_BTN_A = RectUtil.createRect(R_BTN_N, 0, BTN_REAL_SIZE);
        R_BTN_K = RectUtil.createRect(R_BTN_S, 0, BTN_REAL_SIZE);
        R_BTN_J = RectUtil.createRect(R_BTN_A, 0, BTN_REAL_SIZE);
        R_BTN_OK = RectUtil.createRect(R_BTN_K.left, R_BTN_K.top + BTN_REAL_SIZE, BTN_LARGE_SIZE, BTN_SIZE);
        
        LEVEL_LEFT = (int)(230 * TOWER_SCALE) + TOWER_LEFT;
        LEVEL_TOP = (int)(150 * TOWER_SCALE) + TOWER_TOP;
        HP_LEFT = (int)(220 * TOWER_SCALE) + TOWER_LEFT;
        HP_TOP = (int)(215 * TOWER_SCALE) + TOWER_TOP;
        ATTACK_LEFT = (int)(220 * TOWER_SCALE) + TOWER_LEFT;
        ATTACK_TOP = (int)(270 * TOWER_SCALE) + TOWER_TOP;
        DEFEND_LEFT = (int)(220 * TOWER_SCALE) + TOWER_LEFT;
        DEFEND_TOP = (int)(325 * TOWER_SCALE) + TOWER_TOP;
        MONEY_LEFT = (int)(220 * TOWER_SCALE) + TOWER_LEFT;
        MONEY_TOP = (int)(385 * TOWER_SCALE) + TOWER_TOP;
        EXP_LEFT = (int)(220 * TOWER_SCALE) + TOWER_LEFT;
        EXP_TOP = (int)(440 * TOWER_SCALE) + TOWER_TOP;

        YKEY_LEFT = (int)(240 * TOWER_SCALE) + TOWER_LEFT;
        YKEY_TOP = (int)(530 * TOWER_SCALE) + TOWER_TOP;
        BKEY_LEFT = (int)(240 * TOWER_SCALE) + TOWER_LEFT;
        BKEY_TOP = (int)(605 * TOWER_SCALE) + TOWER_TOP;
        RKEY_LEFT = (int)(240 * TOWER_SCALE) + TOWER_LEFT;
        RKEY_TOP = (int)(680 * TOWER_SCALE) + TOWER_TOP;

        FLOOR_LEFT = (int)(200 * TOWER_SCALE) + TOWER_LEFT;
        FLOOR_TOP = (int)(750 * TOWER_SCALE) + TOWER_TOP;
        
        R_MSG = RectUtil.createRect((int)(10 * TOWER_SCALE), (int)(270 * TOWER_SCALE), 
                TOWER_GRID_SIZE * TOWER_COLUMNS - (int)(20 * TOWER_SCALE), (int)(150 * TOWER_SCALE));
        
        R_SHOP = RectUtil.createRect((int)(550 * TOWER_SCALE), (int)(230 * TOWER_SCALE), (int)(550 * TOWER_SCALE), (int)(250 * TOWER_SCALE));
        R_SHOP_ICON = RectUtil.createRect((int)(20 * TOWER_SCALE) + R_SHOP.left, (int)(20 * TOWER_SCALE) + R_SHOP.top, TOWER_GRID_SIZE, TOWER_GRID_SIZE);
        R_SHOP_TEXT = RectUtil.createRect(R_SHOP.left + (int)(100 * TOWER_SCALE), R_SHOP.top + (int)(20 * TOWER_SCALE),
                (int)(500 * TOWER_SCALE), (int)(250 * TOWER_SCALE));
        
        R_DLG_BG_NPC = RectUtil.createRect((int)(400 * TOWER_SCALE), (int)(310 * TOWER_SCALE), (int)(540 * TOWER_SCALE), (int)(170 * TOWER_SCALE));
        R_DLG_ICON_NPC = RectUtil.createRect(R_DLG_BG_NPC.left + 20, R_DLG_BG_NPC.top + 20, TOWER_GRID_SIZE, TOWER_GRID_SIZE);
        R_DLG_NAME_NPC = new Rect(R_DLG_ICON_NPC.right + 20, R_DLG_ICON_NPC.top, R_DLG_BG_NPC.right - 20, R_DLG_ICON_NPC.top + 40);
        R_DLG_TEXT_NPC = new Rect(R_DLG_NAME_NPC.left, R_DLG_NAME_NPC.bottom, R_DLG_NAME_NPC.right, R_DLG_BG_NPC.bottom - 20);
        
        R_DLG_BG_PLR = RectUtil.createRect((int)(675 * TOWER_SCALE), (int)(560 * TOWER_SCALE), (int)(540 * TOWER_SCALE), (int)(170 * TOWER_SCALE));
        R_DLG_ICON_PLR = RectUtil.createRect(R_DLG_BG_PLR.left + 20, R_DLG_BG_PLR.top + 20, TOWER_GRID_SIZE, TOWER_GRID_SIZE);
        R_DLG_NAME_PLR = new Rect(R_DLG_ICON_PLR.right + 20, R_DLG_ICON_PLR.top, R_DLG_BG_PLR.right - 20, R_DLG_ICON_PLR.top + 40);
        R_DLG_TEXT_PLR = new Rect(R_DLG_NAME_PLR.left, R_DLG_NAME_PLR.bottom, R_DLG_NAME_PLR.right, R_DLG_BG_PLR.bottom - 20);
        
        R_FORECAST = RectUtil.createRect(TOWER_LEFT + TOWER_GRID_SIZE * 6, TOWER_TOP + TOWER_GRID_SIZE, TOWER_GRID_SIZE * 11, TOWER_GRID_SIZE * 11);
        R_FC_ICON = RectUtil.createRect(R_FORECAST.left, R_FORECAST.top, TOWER_GRID_SIZE, TOWER_GRID_SIZE);
        R_FC_NAME = RectUtil.createRect(R_FC_ICON.right, R_FC_ICON.top, TOWER_GRID_SIZE * 3 / 2, TOWER_GRID_SIZE);
        R_FC_HP = RectUtil.createRect(R_FC_NAME.right, R_FC_NAME.top, TOWER_GRID_SIZE * 3 / 2, TOWER_GRID_SIZE);
        R_FC_ATTACK = RectUtil.createRect(R_FC_HP.right, R_FC_HP.top, TOWER_GRID_SIZE * 3 / 2, TOWER_GRID_SIZE);
        R_FC_DEFEND = RectUtil.createRect(R_FC_ATTACK.right, R_FC_ATTACK.top, TOWER_GRID_SIZE * 3 / 2, TOWER_GRID_SIZE);
        R_FC_MONEY = RectUtil.createRect(R_FC_DEFEND.right, R_FC_DEFEND.top, TOWER_GRID_SIZE * 5 / 2, TOWER_GRID_SIZE);
        R_FC_LOSE = RectUtil.createRect(R_FC_MONEY.right, R_FC_MONEY.top, TOWER_GRID_SIZE * 3 / 2, TOWER_GRID_SIZE);
        
        R_BATTLE = RectUtil.createRect((int)(27 * TOWER_SCALE), TOWER_GRID_SIZE * 2, (int)(1242 * TOWER_SCALE), (int)(541 * TOWER_SCALE));
        R_BTL_MST_ICON = RectUtil.createRect((int)(100 * TOWER_SCALE), (int)(120 * TOWER_SCALE), TOWER_GRID_SIZE, TOWER_GRID_SIZE);
        R_BTL_MST_HP = RectUtil.createRect((int)(400 * TOWER_SCALE), (int)(37 * TOWER_SCALE) + TOWER_GRID_SIZE * 2, (int)(300 * TOWER_SCALE), (int)(300 * TOWER_SCALE));
        R_BTL_MST_ATTACK = RectUtil.createRect((int)(400 * TOWER_SCALE), (int)(157 * TOWER_SCALE) + TOWER_GRID_SIZE * 2, (int)(300 * TOWER_SCALE), (int)(300 * TOWER_SCALE));
        R_BTL_MST_DEFEND = RectUtil.createRect((int)(400 * TOWER_SCALE), (int)(291 * TOWER_SCALE) + TOWER_GRID_SIZE * 2, (int)(300 * TOWER_SCALE), (int)(300 * TOWER_SCALE));
        R_BTL_PLR_HP = RectUtil.createRect((int)(785 * TOWER_SCALE), (int)(37 * TOWER_SCALE) + TOWER_GRID_SIZE * 2, (int)(300 * TOWER_SCALE), (int)(300 * TOWER_SCALE));
        R_BTL_PLR_ATTACK = RectUtil.createRect((int)(785 * TOWER_SCALE), (int)(157 * TOWER_SCALE) + TOWER_GRID_SIZE * 2, (int)(300 * TOWER_SCALE), (int)(300 * TOWER_SCALE));
        R_BTL_PLR_DEFEND = RectUtil.createRect((int)(785 * TOWER_SCALE), (int)(291 * TOWER_SCALE) + TOWER_GRID_SIZE * 2, (int)(300 * TOWER_SCALE), (int)(300 * TOWER_SCALE));
        
        R_JUMP = RectUtil.createRect(R_TOWER.left + TOWER_GRID_SIZE * 6, R_TOWER.top + TOWER_GRID_SIZE * 3, TOWER_GRID_SIZE * 6, TOWER_GRID_SIZE * 5);
        R_JUMP_GRID = RectUtil.createRect(R_JUMP.left, R_JUMP.top, TOWER_GRID_SIZE * 3 / 2, TOWER_GRID_SIZE);
    }

}
