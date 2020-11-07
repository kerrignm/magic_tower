package com.game.magictower.model;

public class NpcInfo {
    
    public static final int FAIRY_STATUS_WAIT_PLAYER = 0;
    public static final int FAIRY_STATUS_WAIT_CROSS = 1;
    public static final int FAIRY_STATUS_OVER = 2;

    public static final int THIEF_STATUS_WAIT_PLAYER = 0;
    public static final int THIEF_STATUS_WAIT_HAMMER = 1;
    public static final int THIEF_STATUS_OVER = 2;

    public static final int PRINCESS_STATUS_WAIT_PLAYER = 0;
    public static final int PRINCESS_STATUS_OVER = 1;
    
    public int mFairyStatus;
    
    public int mThiefStatus;
    
    public int mPrincessStatus;
    
    public int curFloor = 0;
    public int maxFloor = 0;
    
    public boolean isHasCross = false;
    public boolean isHasForecast = false;
    public boolean isHasJump = false;
    public boolean isHasHammer = false;
    
    public boolean isMonsterStonger = false;
    public boolean isMonsterStongest = false;
    
    public NpcInfo() {
        reset();
    }
    
    public void reset() {
        mFairyStatus = FAIRY_STATUS_WAIT_PLAYER;
        mThiefStatus = THIEF_STATUS_WAIT_PLAYER;
        mPrincessStatus = PRINCESS_STATUS_WAIT_PLAYER;
        curFloor = 0;
        maxFloor = 0;
        isHasCross = false;
        isHasForecast = false;
        isHasJump = false;
        isHasHammer = false;
        isMonsterStonger = false;
        isMonsterStongest = false;
    }
}
