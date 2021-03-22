package com.game.magictower;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.ToggleButton;

import com.game.magictower.res.Assets;
import com.game.magictower.res.Assets.LoadingProgressListener;
import com.game.magictower.res.GlobalSoundPool;
import com.game.magictower.util.LogUtil;

import java.lang.ref.WeakReference;

public class LoadingActivity extends Activity {

    protected static final String TAG = "MagicTower:LoadingActivity";

    private ProgressBar progressBar;
    
    @SuppressLint("StaticFieldLeak")
    private final AsyncTask<Void, Integer, Void> loadTask = new AsyncTask<Void, Integer, Void>() {
        protected Void doInBackground(Void... params) {
            Assets.loadAssets(LoadingActivity.this, new LoadingProgressListener() {

                @Override
                public void onProgressChanged(int progress) {
                    publishProgress(progress);
                }

                @Override
                public void onLoadCompleted() {
                    LogUtil.d(TAG, "onLoadCompleted()");
                    handler.sendEmptyMessage(LoadHandler.MSG_LOADING_COMPLETE);
                }
            });
            return null;
        }

        protected void onProgressUpdate(Integer... values) {
            progressBar.setProgress(values[0]);
        }

        protected void onPostExecute(Void result) {
            LogUtil.d(TAG, "onPostExecute()");
        }
    };

    private final Handler handler = new LoadHandler(new WeakReference<>(this));
    
    @TargetApi(Build.VERSION_CODES.R)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            WindowManager.LayoutParams params = getWindow().getAttributes();
            params.layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES;
            getWindow().setAttributes(params);
        }
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN

        );
        progressBar = findViewById(R.id.loading_prg);
        ToggleButton tgb = findViewById(R.id.loading_tgb_voice);
        tgb.setOnCheckedChangeListener((buttonView, isChecked) -> Settings.setVoiceEnabled(isChecked));
        Settings.setVoiceEnabled(tgb.isChecked());
        if (Assets.getInstance() == null) {
            handler.sendEmptyMessageDelayed(LoadHandler.MSG_START_LOADING, 500);   //Splash show time
        } else {
            progressBar.setVisibility(View.INVISIBLE);
            findViewById(R.id.loading_ll_btns).setVisibility(View.VISIBLE);
        }
    }
    
    private static final class LoadHandler extends Handler {
        
        public static final int MSG_START_LOADING = 1;
        public static final int MSG_LOADING_COMPLETE = 2;
        public static final int MSG_START_GAME = 3;
        public static final int MSG_LOAD_GAME = 4;
        
        private final WeakReference<LoadingActivity> wk;

        public LoadHandler(WeakReference<LoadingActivity> wk) {
            super();
            this.wk = wk;
        }
        
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            LoadingActivity activity = wk.get();
            if (msg.what == MSG_START_LOADING && activity != null) {
                activity.loadTask.execute();
            } else if (msg.what == MSG_LOADING_COMPLETE && activity != null) {
                activity.progressBar.setVisibility(View.INVISIBLE);
                activity.findViewById(R.id.loading_ll_btns).setVisibility(View.VISIBLE);
            } else if (msg.what == MSG_START_GAME && activity != null) {
                activity.startGame(false);
            } else if (msg.what == MSG_LOAD_GAME && activity != null) {
                activity.startGame(true);
            }
        }
    }
    
    @SuppressLint("NonConstantResourceId")
    public void onClick(View v){
        switch (v.getId()) {
        case R.id.loading_btn_startgame:
            GlobalSoundPool.getInstance().playSound(Assets.getInstance().getSoundId(Assets.SND_ID_COIN));
            handler.sendEmptyMessageDelayed(LoadHandler.MSG_START_GAME, 1000);
            break;
        case R.id.loading_btn_loadgame:
            GlobalSoundPool.getInstance().playSound(Assets.getInstance().getSoundId(Assets.SND_ID_COIN));
            handler.sendEmptyMessageDelayed(LoadHandler.MSG_LOAD_GAME, 1000);
            break;
        default:
            break;
        }
    }
    
    private void startGame(boolean load) {
        startActivity(GameActivity.getIntent(LoadingActivity.this, load));
        finish();
    }

}
