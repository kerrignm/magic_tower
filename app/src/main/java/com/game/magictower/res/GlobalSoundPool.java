package com.game.magictower.res;

import java.io.IOException;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.AudioManager;
import android.media.SoundPool;
import android.media.SoundPool.OnLoadCompleteListener;

import com.game.magictower.Settings;
import com.game.magictower.util.LogUtil;

public final class GlobalSoundPool {
    
    private static final String TAG = "MagicTower:GlobalSoundPool";
    
    private static GlobalSoundPool sInstance;
    private final SoundPool soundPool;
    
    private GlobalSoundPool() {
        soundPool = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);
    }
    
    public static GlobalSoundPool getInstance() {
        if (sInstance == null) {
            synchronized (GlobalSoundPool.class) {
                sInstance = new GlobalSoundPool();
            }
        }
        return sInstance;
    }
    
    public void setOnLoadCompleteListener(OnLoadCompleteListener listener) {
        soundPool.setOnLoadCompleteListener(listener);
    }
    
    protected final int loadSound(Context context, String assets) {
        try {
            AssetManager am = context.getAssets();
            AssetFileDescriptor afd = am.openFd("sound/" + assets);
            return soundPool.load(afd, 1);
        } catch (IOException e) {
            LogUtil.e(TAG, "loadSound() IO error assets = " + assets);
        }
        return -1;
    }
    
    public int playSound(int soundId) {
        return playSound(soundId, 0);
    }
    
    public int playSound(int soundId, int loop) {
        if (Settings.isVoiceEnabled()) {
            int priority = 0;
            if (loop < 0) {
                priority = 100;
            } else if (loop > 0) {
                priority = 50;
            }
            return soundPool.play(soundId, 0.5F, 0.5F, priority, loop, 1.0F);
        }
        return -1;
    }
    
    public void pauseSound(int streamId) {
        if (Settings.isVoiceEnabled()) {
            soundPool.pause(streamId);
        }
    }
    
    public void resumeSound(int streamId) {
        if (Settings.isVoiceEnabled()) {
            soundPool.resume(streamId);
        }
    }
    
    public void stopSound(int streamId) {
        if (Settings.isVoiceEnabled()) {
            soundPool.stop(streamId);
        }
    }

}
