package com.game.magictower;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.Context;
import android.content.Intent;

import com.game.magictower.model.Monster;
import com.game.magictower.model.NpcInfo;
import com.game.magictower.model.Player;
import com.game.magictower.model.TalkInfo;
import com.game.magictower.model.Tower;
import com.game.magictower.util.ArrayUtil;
import com.game.magictower.util.FileUtil;
import com.game.magictower.util.JsonUtil;
import com.google.gson.reflect.TypeToken;

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
    
    private Context mContext;
    
    public SceneDialog dialog;
    public SceneMessage message;
    
    public Status status;
    
    public Player player;
    public NpcInfo npcInfo;
    public Tower tower;
    public int[][][] lvMap;
    public HashMap<Integer, ArrayList<String>> storys;
    public HashMap<Integer, ArrayList<String>> shops;
    public HashMap<Integer, ArrayList<TalkInfo>> dialogs;
    public HashMap<Integer, Monster> monsters;
    
    private static Game sInstance;
    
    private Game(Context context) {
        mContext = context;
        player = new Player();
        npcInfo = new NpcInfo();
        tower = JsonUtil.fromJson(FileUtil.loadAssets(mContext, "tower.json"), Tower.class);
        storys = JsonUtil.fromJson(FileUtil.loadAssets(mContext, "story.json"), new TypeToken<HashMap<Integer, ArrayList<String>>>(){}.getType());
        shops = JsonUtil.fromJson(FileUtil.loadAssets(mContext, "shop.json"), new TypeToken<HashMap<Integer, ArrayList<String>>>(){}.getType());
        dialogs = JsonUtil.fromJson(FileUtil.loadAssets(mContext, "dialog.json"), new TypeToken<HashMap<Integer, ArrayList<TalkInfo>>>(){}.getType());
    }
    
    public static final void init(Context context) {
        if (sInstance == null) {
            sInstance = new Game(context);
        }
    }
    
    public static final Game getInstance() {
        return sInstance;
    }
    
    public void newGame() {
        status = Status.Playing;
        player.reset();
        npcInfo.reset();
        lvMap = ArrayUtil.copy(tower.LvMap);
        resetMonster();
        test();
    }
    
    public boolean loadGame() {
        String playerStr = FileUtil.readInternal(mContext, "player.json");
        String npcStr = FileUtil.readInternal(mContext, "npc.json");
        String towerStr = FileUtil.readInternal(mContext, "tower.json");
        if ((playerStr == null) || (npcStr == null) || (towerStr == null)) {
            newGame();
            return false;
        } else {
            player = JsonUtil.fromJson(playerStr, Player.class);
            npcInfo = JsonUtil.fromJson(npcStr, NpcInfo.class);
            lvMap = JsonUtil.fromJson(towerStr, int[][][].class);
            resetMonster();
            return true;
        }
    }
    
    public boolean saveGame() {
        if (!FileUtil.writeInternal(mContext, "player.json", JsonUtil.toJson(player))) {
            return false;
        }
        if (!FileUtil.writeInternal(mContext, "npc.json", JsonUtil.toJson(npcInfo))) {
            return false;
        }
        if (!FileUtil.writeInternal(mContext, "tower.json", JsonUtil.toJson(lvMap))) {
            return false;
        }
        return true;
    }
    
    public void monsterStonger() {
        if (npcInfo.isMonsterStonger) {
            for(int key : monsters.keySet()) {
                monsters.get(key).setStronger();
            }
        }
    }
    
    public void monsterStronest() {
        if (npcInfo.isMonsterStongest) {
            for(int key : monsters.keySet()) {
                monsters.get(key).setStrongest();
            }
        }
    }
    
    public void gameOver() {
        mContext.startActivity(new Intent(mContext, LoadingActivity.class));
        newGame();
    }
    
    private void resetMonster() {
        monsters = JsonUtil.fromJson(FileUtil.loadAssets(mContext, "monster.json"), new TypeToken<HashMap<Integer, Monster>>(){}.getType());
        monsterStonger();
        monsterStronest();
    }
    
    private void test() {
        npcInfo.maxFloor = 20;
        npcInfo.isHasJump = true;
        npcInfo.isHasForecast = true;
        player.setYkey(20);
        player.setBkey(20);
        player.setRkey(20);
        player.setAttack(3000);
        player.setDefend(3000);
        player.setHp(80000);
    }
}
