package com.game.magictower.res;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Point;
import android.media.SoundPool;
import android.media.SoundPool.OnLoadCompleteListener;
import android.os.Build;
import android.view.WindowManager;

import com.game.magictower.util.BitmapUtil;
import com.game.magictower.util.LogUtil;

import java.util.HashMap;

public final class Assets {
    
    private static final String TAG = "MagicTower:Assets";

    public static final int PLAYER_LEFT = 0;
    public static final int PLAYER_DOWN = 1;
    public static final int PLAYER_RIGHT = 2;
    public static final int PLAYER_UP = 3;
    
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
    public static final int SND_ID_MAGIC_2 = 7;
    public static final int SND_ID_MAGIC_3 = 8;
    public static final int SND_ID_MAGIC_4 = 9;
    public static final int SND_ID_POWER = 10;
    public static final int SND_ID_RECORD = 11;
    public static final int SND_ID_STEP = 12;
    public static final int SND_ID_WONDER = 13;
    public static final int SND_ID_ZONE = 14;
    public static final int SND_ID_BGM_1 = 15;
    public static final int SND_ID_BGM_2 = 16;
    public static final int SND_ID_BGM_3 = 17;
    public static final int SND_ID_BGM_4 = 18;
    public static final int SND_ID_BGM_5 = 19;
    
    private static final String[] soundstrs = {
        "attack.ogg",
        "change.ogg",
        "coin.ogg",
        "crit.ogg",
        "dialog.ogg",
        "done.ogg",
        "item.ogg",
        "magic_2.ogg",
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
    
    private static final float[] leftPath = {
        0.2f, 0.5f,
        0.8f, 0.2f,
        0.7f, 0.5f,
        0.8f, 0.8f
    };
    
    private static final float[] upPath = {
        0.2f, 0.8f,
        0.5f, 0.2f,
        0.8f, 0.8f,
        0.5f, 0.7f
    };
    
    private static final float[] rightPath = {
        0.2f, 0.2f,
        0.8f, 0.5f,
        0.2f, 0.8f,
        0.3f, 0.5f
    };
    
    private static final float[] downPath = {
        0.2f, 0.2f,
        0.5f, 0.3f,
        0.8f, 0.2f,
        0.5f, 0.8f
    };
    
    private static Assets sInstance;
    
    public Bitmap bkgTower;
    public Bitmap bkgBlank;
    public Bitmap bkgBattle;
    public Bitmap bkgBtnNormal;
    public Bitmap bkgBtnPressed;
    
    public Bitmap leftBtn;
    public Bitmap upBtn;
    public Bitmap rightBtn;
    public Bitmap downBtn;
    
    public HashMap<Integer, Bitmap> playerMap = new HashMap<>();
    public HashMap<Integer, Bitmap> animMap0 = new HashMap<>();
    public HashMap<Integer, Bitmap> animMap1 = new HashMap<>();
    
    private final int[] soundIds = new int [soundstrs.length];
    
    public static Assets getInstance() {
        return sInstance;
    }
    
    public static synchronized void loadAssets(Context context, LoadingProgressListener listener) {
        sInstance = new Assets();
        sInstance.load(context, listener);
    }
    
    public int getSoundId(int id) {
        return soundIds[id];
    }
    
    private void load(Context context, LoadingProgressListener listener){
        final int totalBitmap = 5 + 4 + IMAGE_ID.length * 2;
        final int totalSoundCount = soundstrs.length;
        int total = totalBitmap + totalSoundCount;
        int loadedBitmap = loadBitmap(0, total, context, listener);
        int loadedRes = loadSound(loadedBitmap, total, context, listener);
        
        if (loadedRes != total) {
            throw new RuntimeException("Resource loaded " + loadedRes + ", but it was declared to be " + total);
        }
        //listener.onLoadCompleted();
    }
    
    private int loadSound(final int hasCompleted, final int total,
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
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            wm.getDefaultDisplay().getRealSize(point);
        } else {
            wm.getDefaultDisplay().getSize(point);
        }
        LogUtil.d(TAG, "window point:" + point.toString());
        
        TowerDimen.init(point.x, point.y);
        
        int completed = hasCompleted;
        
        bkgTower = BitmapUtil.loadBitmap(context, "bkg_tower.png");
        notifyProgressChanged(++completed, total, listener);
        bkgBlank = BitmapUtil.loadBitmap(context, "bkg_blank.png");
        notifyProgressChanged(++completed, total, listener);
        bkgBattle = BitmapUtil.loadBitmap(context, "bkg_battle.png");
        notifyProgressChanged(++completed, total, listener);
        bkgBtnNormal = BitmapUtil.loadBitmap(context, "bkg_btn_normal.png");
        notifyProgressChanged(++completed, total, listener);
        bkgBtnPressed = BitmapUtil.loadBitmap(context, "bkg_btn_pressed.png");
        notifyProgressChanged(++completed, total, listener);
        
        leftBtn = BitmapUtil.creatBitmap(TowerDimen.R_BTN_L.width(),TowerDimen.R_BTN_L.height(), leftPath);
        upBtn = BitmapUtil.creatBitmap(TowerDimen.R_BTN_U.width(),TowerDimen.R_BTN_U.height(), upPath);
        rightBtn = BitmapUtil.creatBitmap(TowerDimen.R_BTN_R.width(),TowerDimen.R_BTN_R.height(), rightPath);
        downBtn = BitmapUtil.creatBitmap(TowerDimen.R_BTN_D.width(),TowerDimen.R_BTN_D.height(), downPath);
        
        Bitmap blankBitmap = Bitmap.createBitmap(point.x, point.y, Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas();
        canvas.setBitmap(blankBitmap);
        int column = point.x / bkgBlank.getWidth();
        if (point.x % bkgBlank.getWidth() != 0) {
            column += 1;
        }
        int row = point.y / bkgBlank.getHeight();
        if (point.y % bkgBlank.getHeight() != 0) {
            row += 1;
        }
        int x;
        int y;
        for (int i =0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                x = j * bkgBlank.getWidth();
                y = i * bkgBlank.getHeight();
                canvas.drawBitmap(bkgBlank, x, y, null);
            }
        }
        bkgBlank = blankBitmap;
        /*String fileName = context.getExternalFilesDir(null).getPath() + File.separator + "bkg_blank.png";
        BitmapUtil.saveBitmapToFile(fileName, blankBitmap);*/
       
        playerMap.clear();
        playerMap.put(PLAYER_LEFT, BitmapUtil.loadBitmap(context, "left.png"));
        playerMap.put(PLAYER_RIGHT, BitmapUtil.loadBitmap(context, "right.png"));
        playerMap.put(PLAYER_UP, BitmapUtil.loadBitmap(context, "up.png"));
        playerMap.put(PLAYER_DOWN, BitmapUtil.loadBitmap(context, "down.png"));
        completed += 4;
        notifyProgressChanged(completed, total, listener);


        for (int value : IMAGE_ID) {
            animMap0.put(value, BitmapUtil.loadBitmap(context, "map0/" + value + ".png"));
            ++completed;
        }
        notifyProgressChanged(completed, total, listener);
        for (int value : IMAGE_ID) {
            animMap1.put(value, BitmapUtil.loadBitmap(context, "map1/" + value + ".png"));
            ++completed;
        }
        notifyProgressChanged(completed, total, listener);
        
        return completed;
    }

    private void notifyProgressChanged(int completed, int total,
                                       LoadingProgressListener listener) {
        int progress = (int) (completed / (double)total * 100);
        listener.onProgressChanged(progress);
    }
    
    private static class SoundpoolListener implements OnLoadCompleteListener {
        private final LoadingProgressListener listener;
        private int count;
        private final int totalCount;
        private final int completeCount;
        private final int completeTotal;
        
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
