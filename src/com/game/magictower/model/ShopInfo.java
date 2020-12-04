package com.game.magictower.model;

public class ShopInfo {
    
    public static final int COST_TYPE_NONE = 0;
    public static final int COST_TYPE_MONEY = 1;
    public static final int COST_TYPE_EXP = 2;
    public static final int COST_TYPE_YKEY = 3;
    public static final int COST_TYPE_BKEY = 4;
    public static final int COST_TYPE_RKEY = 5;
    
    private int costType;
    
    private int costValue;
    
    private int level;
    
    private int hp;
    
    private int money;
    
    private int attack;
    
    private int defend;
    
    private int ykey;
    
    private int bkey;
    
    private int rkey;
    
    private String describe;
    
    public ShopInfo() {
        
    }
    
    public int getType() {
        return costType;
    }
    
    public int getValue() {
        return costValue;
    }
    
    public int getLevel() {
        return level;
    }
    
    public int getHp() {
        return hp;
    }
    
    public int getMoney() {
        return money;
    }
    
    public int getAttack() {
        return attack;
    }
    
    public int getDefend() {
        return defend;
    }
    
    public int getYKey() {
        return ykey;
    }
    
    public int getBKey() {
        return bkey;
    }
    
    public int getRKey() {
        return rkey;
    }
    
    public String getDescribe() {
        return describe;
    }

}
