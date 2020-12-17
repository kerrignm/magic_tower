package com.game.magictower.model;

public class ShopInfo extends BaseInfo {
    
    public static final int COST_TYPE_NONE = 0;
    public static final int COST_TYPE_MONEY = 1;
    public static final int COST_TYPE_EXP = 2;
    public static final int COST_TYPE_YKEY = 3;
    public static final int COST_TYPE_BKEY = 4;
    public static final int COST_TYPE_RKEY = 5;
    
    protected int costType;
    
    protected int costValue;
    
    protected String describe;
    
    public ShopInfo() {
        super();
    }
    
    public int getType() {
        return costType;
    }
    
    public int getValue() {
        return costValue;
    }
    
    public String getDescribe() {
        return describe;
    }

}
