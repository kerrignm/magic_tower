package com.game.magictower;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.WindowManager;

import com.game.magictower.res.Assets;
import com.game.magictower.res.GlobalSoundPool;
import com.game.magictower.secne.SceneMessage;
import com.game.magictower.util.FileUtil;
import com.game.magictower.util.LogUtil;

public class GameActivity extends Activity implements GameControler {
    
    private static final String TAG = "MagicTower:GameActivity";
    
    private Game currentGame;
    
    private GameView gameView;
    
    private int musicId = -1;
    private int streamId = -1;
    
    protected static Intent getIntent(Context context, boolean load){
        Intent intent = new Intent(context, GameActivity.class);
        intent.putExtra("load", load);
        return intent;
    }
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtil.d(TAG, "onCreate()");
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        Game.init(this, this);
        currentGame = Game.getInstance();
        currentGame.newGame();
        gameView = new GameView(this);
        setContentView(gameView);
        setVolumeControlStream(AudioManager.STREAM_MUSIC);
        if (getIntent().getBooleanExtra("load", false)) {
            currentGame.loadGame();
            playBackgroundMusic(getMuisicId(currentGame.npcInfo.curFloor));
        } else {
            gameView.showMessage(1, null, null, SceneMessage.MODE_AUTO_SCROLL);
            playBackgroundMusic(getMuisicId(-1));
        }
    }
    
    @Override
    protected void onPause() {
        super.onPause();
        if (Settings.isVoiceEnabled()) {
            pauseBackgroundMusic();
        }
        gameView.onPause();
        LogUtil.d(TAG, "onPause()");
    }
    
    @Override
    protected void onResume() {
        super.onResume();
        if (Settings.isVoiceEnabled()) {
            resumeBackgroundMusic();
        }
        gameView.onResume();
        LogUtil.d(TAG, "onResume()");
    }
    
    @Override
    protected void onDestroy() {
        super.onDestroy();
        LogUtil.d(TAG, "onDestroy()");
        if (Settings.isVoiceEnabled()) {
            stopBackgroundMusic();
        }
        Game.destroy();
    }
    
    private void playBackgroundMusic(int id) {
        if (id != musicId) {
            musicId = id;
            streamId = GlobalSoundPool.getInstance().playSound(id, -1);
            if (streamId <= 0) {
                FileUtil.writeExternal(this, "fatal_log.txt", "playBackgroundMusic() error musicId = " + musicId + ", streamId = " + streamId + "\n", true);
            }
        }
    }
    
    private void pauseBackgroundMusic() {
        if (streamId > 0) {
            GlobalSoundPool.getInstance().pauseSound(streamId);
        }
    }
    
    private void resumeBackgroundMusic() {
        if (streamId > 0) {
            GlobalSoundPool.getInstance().resumeSound(streamId);
        }
    }
    
    private void stopBackgroundMusic() {
        if (streamId > 0) {
            GlobalSoundPool.getInstance().stopSound(streamId);
        }
    }
    
    private void changeBackgroundMusic(int floor) {
        int id = getMuisicId(floor);
        if (id != musicId) {
            stopBackgroundMusic();
            playBackgroundMusic(id);
        }
    }
    
    private int getMuisicId(int floor) {
        int id = 0;
        if (floor == 0) {
            id = Assets.SND_ID_BGM_2;
        } else if ((floor >= 1) && (floor <= 7)) {
            id = Assets.SND_ID_BGM_3;
        } else if ((floor >= 8) && (floor <= 14)) {
            id = Assets.SND_ID_BGM_4;
        } else if ((floor >= 15) && (floor <= 18)) {
            id = Assets.SND_ID_BGM_5;
        } else {
            id = Assets.SND_ID_BGM_1;
        }
        id = Assets.getInstance().getSoundId(id);
        return id;
    }
    
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK) {
            return true;
        }
        return false;
    }
    
    public void gameOver() {
        startActivity(new Intent(this, LoadingActivity.class));
        finish();
    }
    
    public void quitGame() {
        finish();
    }
    
    public void changeMusic() {
        changeBackgroundMusic(currentGame.npcInfo.curFloor);
    }
}
