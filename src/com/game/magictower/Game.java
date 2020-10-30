package com.game.magictower;

import com.game.magictower.res.TowerMap;
import com.game.magictower.util.ArrayUtil;

public class Game {
    
    public static enum Status {
        Playing,
        Fighting,
        Shopping,
        Messaging,
        Dialoguing,
        Looking,
        Jumping;
    }
    
    public Status status;
    
    public int currentFloor = 0;
    public int maxFloor = 0;
    public Dialog dialog;
    public Messag messag;
    
    public boolean isHasCross = false;
    public boolean isHasForecast = false;
    public boolean isHasJump = false;
    public boolean isHasHammer = false;
    
    public Player player;
    public NpcInfo npcInfo;
    public int[][][] lvMap;
    
    private static Game sInstance;
    
    private Game(){
        player = new Player();
        npcInfo = new NpcInfo();
        newGame();
    }
    
    public static final Game getInstance() {
        if (sInstance == null) {
            sInstance = new Game();
        }
        return sInstance;
    }
    
    public void newGame() {
        status = Status.Playing;
        currentFloor = 0;
        maxFloor = 0;
        isHasCross = false;
        isHasForecast = false;
        isHasJump = false;
        isHasHammer = false;
        player.reset();
        npcInfo.reset();
        lvMap = ArrayUtil.copy(TowerMap.LvMap);
        test();
    }
    
    private void test() {
        maxFloor = 20;
        isHasJump = true;
        isHasForecast = true;
    }
    
    public String toString() {
        StringBuilder sb = new StringBuilder();
        
        sb.append("currentFloor=" + this.currentFloor + ",");
        sb.append("maxFloor=" + this.maxFloor + ",");
        sb.append("isHasCross=" + this.isHasCross + ",");
        sb.append("isHasForecast=" + this.isHasForecast + ",");
        sb.append("isHasJump=" + this.isHasJump + ",");
        sb.append("isHasHammer=" + this.isHasHammer);
        
        return sb.toString();
    }
    
    public void fromString(String str) {
        String[] strs = str.split(",");
        String[] items;
        for (int i = 0; i < strs.length; i++) {
            items = strs[i].split("=");
            if (items[0].equals("currentFloor")) {
                this.currentFloor = Integer.valueOf(items[1]);
            } else if(items[0].equals("maxFloor")) {
                this.maxFloor = Integer.valueOf(items[1]);
            } else if(items[0].equals("isHasCross")) {
                this.isHasCross = Boolean.valueOf(items[1]);
            } else if(items[0].equals("isHasForecast")) {
                this.isHasForecast = Boolean.valueOf(items[1]);
            } else if(items[0].equals("isHasJump")) {
                this.isHasJump = Boolean.valueOf(items[1]);
            } else if(items[0].equals("isHasHammer")) {
                this.isHasHammer = Boolean.valueOf(items[1]);
            }
        }
    }
    
    public String mapToString() {
        StringBuilder sb = new StringBuilder();
        ArrayUtil.array2Str3(lvMap, sb);
        return sb.toString();
    }
    
    public void mapFromString(String str) {
        lvMap = ArrayUtil.str2Array3(str);
    }
}
