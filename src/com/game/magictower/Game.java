package com.game.magictower;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.Context;

import com.game.magictower.model.ItemInfo;
import com.game.magictower.model.Monster;
import com.game.magictower.model.NpcInfo;
import com.game.magictower.model.Player;
import com.game.magictower.model.ShopInfo;
import com.game.magictower.model.TalkInfo;
import com.game.magictower.model.Tower;
import com.game.magictower.model.Treasure;
import com.game.magictower.util.ArrayUtil;
import com.game.magictower.util.FileUtil;
import com.game.magictower.util.JsonUtil;
import com.game.magictower.widget.BaseButton;
import com.google.gson.reflect.TypeToken;

public class Game {
    
    public static enum Status {
        Playing,
        Fighting,
        Shopping,
        Messaging,
        Dialoging,
        Looking,
        Jumping;
    }
    
    private Context mContext;
    
    private GameControler mControler;
    
    public Status status;
    
    public Player player;
    public NpcInfo npcInfo;
    public Tower tower;
    public int[][][] lvMap;
    public HashMap<Integer, ArrayList<String>> storys;
    public HashMap<Integer, ArrayList<ShopInfo>> shops;
    public HashMap<Integer, ArrayList<TalkInfo>> dialogs;
    public HashMap<Integer, Monster> monsters;
    public HashMap<Integer, ItemInfo> items;
    public HashMap<Integer, Treasure> treasures;
    
    private static Game sInstance;
    
    private boolean testFlag = true;
    private int index = 0;
    private int cnt = 0;
    private int[] sn = {
            BaseButton.ID_LEFT,
            BaseButton.ID_LEFT,
            BaseButton.ID_RIGHT,
            BaseButton.ID_RIGHT
    };
    
    private Game(Context context, GameControler controler) {
        mContext = context;
        mControler = controler;
        player = new Player();
        npcInfo = new NpcInfo();
        tower = JsonUtil.fromJson(FileUtil.loadAssets(mContext, "tower.json"), Tower.class);
        storys = JsonUtil.fromJson(FileUtil.loadAssets(mContext, "story.json"), new TypeToken<HashMap<Integer, ArrayList<String>>>(){}.getType());
        shops = JsonUtil.fromJson(FileUtil.loadAssets(mContext, "shop.json"), new TypeToken<HashMap<Integer, ArrayList<ShopInfo>>>(){}.getType());
        dialogs = JsonUtil.fromJson(FileUtil.loadAssets(mContext, "dialog.json"), new TypeToken<HashMap<Integer, ArrayList<TalkInfo>>>(){}.getType());
        items = JsonUtil.fromJson(FileUtil.loadAssets(mContext, "item.json"), new TypeToken<HashMap<Integer, ItemInfo>>(){}.getType());
        treasures = JsonUtil.fromJson(FileUtil.loadAssets(mContext, "treasure.json"), new TypeToken<HashMap<Integer, Treasure>>(){}.getType());
    }
    
    public static final void init(Context context, GameControler controler) {
        if (sInstance == null) {
            sInstance = new Game(context, controler);
        }
    }
    
    public static final void destroy() {
        sInstance = null;
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
        resetTest();
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
    
    public void monsterStronger() {
        if (npcInfo.isMonsterStronger) {
            for(int key : monsters.keySet()) {
                monsters.get(key).setStronger();
            }
        }
    }
    
    public void monsterStrongest() {
        if (npcInfo.isMonsterStrongest) {
            for(int key : monsters.keySet()) {
                monsters.get(key).setStrongest();
            }
        }
    }
    
    public void gameOver() {
        mControler.gameOver();
    }
    
    public void quitGame() {
        mControler.quitGame();
    }
    
    public void changeMusic() {
        mControler.changeMusic();
    }
    
    private void resetMonster() {
        monsters = JsonUtil.fromJson(FileUtil.loadAssets(mContext, "monster.json"), new TypeToken<HashMap<Integer, Monster>>(){}.getType());
        monsterStronger();
        monsterStrongest();
    }
    
    public void checkTest(int id) {
        if (testFlag) {
            if (index < sn.length) {
                if (id == sn[index]) {
                    cnt++;
                }
                index++;
            }
            if (index == sn.length) {
                if (cnt == sn.length) {
                    npcInfo.maxFloor = 20;
                    npcInfo.isHasJump = true;
                    npcInfo.isHasForecast = true;
                    player.setYKey(20);
                    player.setBKey(20);
                    player.setRKey(20);
                    player.setAttack(3000);
                    player.setDefend(3000);
                    player.setHp(80000);
                    player.setMoney(3000);
                    player.setExp(3000);
                }
                testFlag = false;
            }
        }
    }
    
    private void resetTest() {
        testFlag = true;
        index = 0;
        cnt = 0;
    }
}
