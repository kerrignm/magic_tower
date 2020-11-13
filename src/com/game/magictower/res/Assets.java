package com.game.magictower.res;

import java.util.HashMap;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.media.SoundPool;
import android.media.SoundPool.OnLoadCompleteListener;
import android.view.WindowManager;

import com.game.magictower.util.LogUtil;

public final class Assets {
    
    private static final String TAG = "MagicTower:Assets";

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
        16,
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
        80
        
    };
    
    public static final int SND_ID_ATTACK = 0;
    public static final int SND_ID_CHANGE = 1;
    public static final int SND_ID_COIN = 2;
    public static final int SND_ID_CRIT = 3;
    public static final int SND_ID_DIALOG = 4;
    public static final int SND_ID_DONE = 5;
    public static final int SND_ID_ITEM = 6;
    public static final int SND_ID_MAGIC_3 = 7;
    public static final int SND_ID_MAGIC_4 = 8;
    public static final int SND_ID_POWER = 9;
    public static final int SND_ID_RECORD = 10;
    public static final int SND_ID_STEP = 11;
    public static final int SND_ID_WONDER = 12;
    public static final int SND_ID_ZONE = 13;
    public static final int SND_ID_BGM_1 = 14;
    public static final int SND_ID_BGM_2 = 15;
    public static final int SND_ID_BGM_3 = 16;
    public static final int SND_ID_BGM_4 = 17;
    public static final int SND_ID_BGM_5 = 18;
    
    private static final String[] soundstrs = {
        "attack.ogg",
        "change.ogg",
        "coin.ogg",
        "crit.ogg",
        "dialog.ogg",
        "done.ogg",
        "item.ogg",
        "magic_3.ogg",
        "magic_4.ogg",
        "power.ogg",
        "record.ogg",
        "step.ogg",
        "wonder.ogg",
        "zone.ogg",
        "bgm_1.ogg",
        "bgm_2.ogg",
        "bgm_3.ogg",
        "bgm_4.ogg",
        "bgm_5.ogg"
    };
    
    private static float[] leftPath = {
        0.2f, 0.5f,
        0.8f, 0.2f,
        0.7f, 0.5f,
        0.8f, 0.8f
    };
    
    private static float[] upPath = {
        0.2f, 0.8f,
        0.5f, 0.2f,
        0.8f, 0.8f,
        0.5f, 0.7f
    };
    
    private static float[] rightPath = {
        0.2f, 0.2f,
        0.8f, 0.5f,
        0.2f, 0.8f,
        0.3f, 0.5f
    };
    
    private static float[] downPath = {
        0.2f, 0.2f,
        0.5f, 0.3f,
        0.8f, 0.2f,
        0.5f, 0.8f
    };
    
    private static Assets sInstance;
    
    public LiveBitmap bkgGame;
    public LiveBitmap bkgBlank;
    public LiveBitmap bkgBattle;
    public LiveBitmap bkgBtnNormal;
    public LiveBitmap bkgBtnPressed;
    
    public LiveBitmap leftBtn;
    public LiveBitmap upBtn;
    public LiveBitmap rightBtn;
    public LiveBitmap downBtn;
    
    public HashMap<Integer, LiveBitmap> playerMap = new HashMap<>();
    public HashMap<Integer, LiveBitmap> animMap0 = new HashMap<>();
    public HashMap<Integer, LiveBitmap> animMap1 = new HashMap<>();
    
    private int[] soundIds = new int [soundstrs.length];
    
    public static final Assets getInstance(){
        return sInstance;
    }
    
    public static synchronized void loadAssets(Context context, LoadingProgressListener listener){
        sInstance = new Assets();
        sInstance.load(context, listener);
    }
    
    public int getSoundId(int id) {
        return soundIds[id];
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
        //listener.onLoadCompleted();
    }
    
    private final int loadSound(final int hasCompleted, final int total,
            Context context, LoadingProgressListener listener) {
        GlobalSoundPool sp = GlobalSoundPool.getInstance();
        sp.setOnLoadCompleteListener(new SoundpoolListener(listener, soundstrs.length, hasCompleted, total));
        int completed = hasCompleted;
        for (int i = 0; i < soundstrs.length; i++) {
            soundIds[i] = sp.loadSound(context, soundstrs[i]);
            ++completed;
            //notifyProgressChanged(++completed, total, listener);
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
        LogUtil.d(TAG, "window point:" + point.toString());
        
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
        
        leftBtn = LiveBitmap.creatBitmap(TowerDimen.R_BTN_L.width(),TowerDimen.R_BTN_L.height(), leftPath);
        upBtn = LiveBitmap.creatBitmap(TowerDimen.R_BTN_U.width(),TowerDimen.R_BTN_U.height(), upPath);
        rightBtn = LiveBitmap.creatBitmap(TowerDimen.R_BTN_R.width(),TowerDimen.R_BTN_R.height(), rightPath);
        downBtn = LiveBitmap.creatBitmap(TowerDimen.R_BTN_D.width(),TowerDimen.R_BTN_D.height(), downPath);
       
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
    
    private static class SoundpoolListener implements OnLoadCompleteListener {
        private LoadingProgressListener listener;
        private int count;
        private int totalCount;
        private int completeCount;
        private int completeTotal;
        
        public SoundpoolListener(LoadingProgressListener listener, int totalCount, int completeCount, int completeTotal) {
            count = 0;
            this.totalCount = totalCount;
            this.completeCount = completeCount;
            this.completeTotal = completeTotal;
            this.listener = listener;
        }
        
        public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
            count++;
            Assets.getInstance().notifyProgressChanged(count + completeCount, completeTotal, listener);
            if (count >= totalCount) {
                listener.onLoadCompleted();
            }
        }
    }
    
    public interface LoadingProgressListener {
        void onProgressChanged(int progress/*, String currentTaskDescription*/);
        void onLoadCompleted();
    }

}
