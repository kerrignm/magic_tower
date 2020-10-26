package com.game.magictower;

public class NpcInfo {
    
    public static final int FAIRY_STATUS_WAIT_PLAYER = 0;
    public static final int FAIRY_STATUS_WAIT_CROSS = 1;
    public static final int FAIRY_STATUS_OVER = 2;

    public static final int THIEF_STATUS_WAIT_PLAYER = 0;
    public static final int THIEF_STATUS_WAIT_HAMMER = 1;
    public static final int THIEF_STATUS_OVER = 2;
    
    public int mFairyStatus;
    
    public int mThiefStatus;
    
    public NpcInfo() {
        reset();
    }
    
    public void reset() {
        mFairyStatus = FAIRY_STATUS_WAIT_PLAYER;
        mThiefStatus = THIEF_STATUS_WAIT_PLAYER;
    }
    
    public String toString() {
        StringBuilder sb = new StringBuilder();
        
        sb.append("mFairyStatus=" + this.mFairyStatus + ",");
        sb.append("mThiefStatus=" + this.mThiefStatus);
        
        return sb.toString();
    }
    
    public void fromString(String str) {
        String[] strs = str.split(",");
        String[] items;
        for (int i = 0; i < strs.length; i++) {
            items = strs[i].split("=");
            if (items[0].equals("mFairyStatus")) {
                this.mFairyStatus = Integer.valueOf(items[1]);
            } else if(items[0].equals("mThiefStatus")) {
                this.mThiefStatus = Integer.valueOf(items[1]);
            }
        }
    }
}
