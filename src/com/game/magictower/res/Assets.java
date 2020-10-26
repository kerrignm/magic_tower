package com.game.magictower.res;

import java.util.HashMap;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.view.WindowManager;

import com.game.magictower.util.LogUtil;

public final class Assets {
    
    private static final String LOG_TAG = "Assets";

    private static final int PLAYER_LEFT = -1;
    private static final int PLAYER_DOWN = -2;
    private static final int PLAYER_RIGHT = -3;
    private static final int PLAYER_UP = -4;
    
    private static final int[] IMAGE_ID = {
        0,
        1,
        2,
        3,
        4,
        5,
        6,
        7,
        8,
        9,
        10,
        11,
        12,
        13,
        14,
        15,
        //16,
        //17,
        //18,
        19,
        20,
        21,
        22,
        23,
        24,
        25,
        26,
        27,
        28,
        //29,
        30,
        31,
        32,
        33,
        34,
        35,
        36,
        //37,
        38,
        39,
        40,
        41,
        42,
        43,
        44,
        45,
        46,
        47,
        48,
        49,
        50,
        51,
        52,
        53,
        54,
        55,
        56,
        57,
        58,
        59,
        60,
        61,
        62,
        63,
        64,
        65,
        66,
        67,
        68,
        69,
        70,
        71,
        //72,
        73,
        //74,
        75,
        76,
        //77,
        78,
        //79,
        80,
        115
        
    };
    
    private String[] soundstrs = {
        "bgm.mp3",
        "attack.ogg",
        "bomb.ogg",
        "centerFly.ogg",
        "door.ogg",
        "equip.ogg",
        "floor.ogg",
        "item.ogg",
        "jump.ogg",
        "pickaxe.ogg",
        "zone.ogg"
    };
    
    private static Assets sInstance;
    
    public LiveBitmap bkgGame;
    public LiveBitmap bkgBlank;
    public LiveBitmap bkgBattle;
    public LiveBitmap bkgBtnNormal;
    public LiveBitmap bkgBtnPressed;
    
    public HashMap<Integer, LiveBitmap> playerMap = new HashMap<>();
    public HashMap<Integer, LiveBitmap> animMap0 = new HashMap<>();
    public HashMap<Integer, LiveBitmap> animMap1 = new HashMap<>();
    
    public int soundTypeBackground;
    public int soundTypeAttack;
    public int soundTypeBomb;
    public int soundTypeCenterFly;
    public int soundTypeDoor;
    public int soundTypeEquip;
    public int soundTypeFloor;
    public int soundTypeItem;
    public int soundTypeJump;
    public int soundTypePickaxe;
    public int soundTypeZone;
    
    public static final Assets getInstance(){
        if (sInstance == null) {
            throw new RuntimeException("you must call load() method before getInstance()!");
        }
        return sInstance;
    }
    
    public static synchronized void loadAssets(Context context, LoadingProgressListener listener){
        if (sInstance != null) {
            LogUtil.e(LOG_TAG, "Assets resources have been already loaded.Force reloading...");
            sInstance.recycleOldBitmap();
            LogUtil.d(LOG_TAG, "old resources cleared.");
            sInstance = null;
        }
        if (context == null || listener == null) {
            throw new NullPointerException("params cannot be null.");
        }
        sInstance = new Assets();
        sInstance.load(context, listener);
    }
    
    private final synchronized void recycleOldBitmap() {
        recycleBitmap(bkgGame);
        recycleBitmap(bkgBlank);
        recycleBitmap(bkgBattle);
        recycleBitmap(bkgBtnNormal);
        recycleBitmap(bkgBtnPressed);
        
        recycleMapBitmap(playerMap);
        recycleMapBitmap(animMap0);
        recycleMapBitmap(animMap1);
    }
    
    private final void recycleMapBitmap(HashMap<Integer, LiveBitmap> map) {
        for(int key : map.keySet()) {
            recycleBitmap(map.get(key));
            map.remove(key);
        }
    }
    
    private final void recycleBitmap(LiveBitmap bitmap){
        try {
            if (bitmap!=null){
                bitmap.getBitmap().recycle();
            }
        } catch (Exception e) {
            LogUtil.w(LOG_TAG, "exception while recycling bitmap");
            e.printStackTrace();
        }
    }
    
    private final void load(Context context, LoadingProgressListener listener){
        final int totalBitmap = 5 + 4 + IMAGE_ID.length * 2;
        final int totalSoundCount = soundstrs.length;
        int total = totalBitmap + totalSoundCount;
        int loadedBitmap = loadBitmap(0, total, context, listener);
        int loadedRes = loadSound(loadedBitmap, total, context, listener);
        
        if (loadedRes != total) {
            throw new RuntimeException("Resource loaded "+loadedRes+", but it was declared to be "+total);
        }
        listener.onLoadCompleted();
    }
    
    private final int loadSound(final int hasCompleted, final int total,
            Context context, LoadingProgressListener listener) {
        GlobalSoundPool sp = GlobalSoundPool.getInstance(context);
        int completed = hasCompleted;
        for (int i = 0; i < soundstrs.length; i++) {
            soundTypeBackground = sp.loadSound(context, soundstrs[i]);
            notifyProgressChanged(++completed, total, listener);
        }
        return completed;
    }
    
    private final int loadBitmap(final int hasCompleted, final int total,
            Context context,
            LoadingProgressListener listener) {
        if (!(context instanceof Activity)) {
            throw new IllegalArgumentException(
                    "This context must be instance of activity.");
        }
        WindowManager wm = ((Activity) context).getWindowManager();
        Point point = new Point();
        wm.getDefaultDisplay().getSize(point);
        LogUtil.d(LOG_TAG, "window point:" + point.toString());
        
        TowerDimen.init(point.x, point.y);
        
        int completed = hasCompleted;
        
        bkgGame = LiveBitmap.loadBitmap(context, "GameBg.png");
        notifyProgressChanged(++completed, total, listener);
        bkgBlank = LiveBitmap.loadBitmap(context, "BlankBg.png");
        notifyProgressChanged(++completed, total, listener);
        bkgBattle = LiveBitmap.loadBitmap(context, "BattleBg.png");
        notifyProgressChanged(++completed, total, listener);
        bkgBtnNormal = LiveBitmap.loadBitmap(context, "bkg_btn_normal.png");
        notifyProgressChanged(++completed, total, listener);
        bkgBtnPressed = LiveBitmap.loadBitmap(context, "bkg_btn_pressed.png");
        notifyProgressChanged(++completed, total, listener);
       
        playerMap.clear();
        playerMap.put(PLAYER_LEFT, LiveBitmap.loadBitmap(context, "left.png"));
        playerMap.put(PLAYER_RIGHT, LiveBitmap.loadBitmap(context, "right.png"));
        playerMap.put(PLAYER_UP, LiveBitmap.loadBitmap(context, "up.png"));
        playerMap.put(PLAYER_DOWN, LiveBitmap.loadBitmap(context, "down.png"));
        completed += 4;
        notifyProgressChanged(completed, total, listener);
        
        
        for (int i = 0; i < IMAGE_ID.length; i++) {
            animMap0.put(IMAGE_ID[i], LiveBitmap.loadBitmap(context, "map0/" + IMAGE_ID[i] + ".png"));
            ++completed;
        }
        notifyProgressChanged(completed, total, listener);
        for (int i = 0; i < IMAGE_ID.length; i++) {
            animMap1.put(IMAGE_ID[i], LiveBitmap.loadBitmap(context, "map1/" + IMAGE_ID[i] + ".png"));
            ++completed;
        }
        notifyProgressChanged(completed, total, listener);
        
        return completed;
    }

    private final void notifyProgressChanged(int completed, int total,
            LoadingProgressListener listener) {
        int progress = (int) (completed/(double)total*100);
        listener.onProgressChanged(progress);
    }
    
    public interface LoadingProgressListener{
        void onProgressChanged(int progress/*, String currentTaskDescription*/);
        void onLoadCompleted();
    }

}
