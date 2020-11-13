package com.game.magictower;

import java.lang.ref.WeakReference;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ProgressBar;
import android.widget.ToggleButton;

import com.game.magictower.res.Assets;
import com.game.magictower.res.Assets.LoadingProgressListener;
import com.game.magictower.res.GlobalSoundPool;
import com.game.magictower.util.LogUtil;

public class LoadingActivity extends Activity {

    protected static final String TAG = "MagicTower:LoadingActivity";

    private ProgressBar progressBar;
    
    private AsyncTask<Void, Integer, Void> loadTask = new AsyncTask<Void, Integer, Void>() {
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
        };
        
        protected void onProgressUpdate(Integer... values) {
            progressBar.setProgress((int)values[0]);
        };
        
        protected void onPostExecute(Void result) {
            LogUtil.d(TAG, "onPostExecute()");
        };
    };

    private Handler handler = new LoadHandler(new WeakReference<LoadingActivity>(this));
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);
        progressBar = (ProgressBar) findViewById(R.id.loading_prg);
        ToggleButton tgb = (ToggleButton) findViewById(R.id.loading_tgb_voice);
        tgb.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Settings.setVoiceEnabled(isChecked);
            }
        });
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
        
        private WeakReference<LoadingActivity> wk;

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
