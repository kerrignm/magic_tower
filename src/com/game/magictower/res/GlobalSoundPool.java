package com.game.magictower.res;

import java.io.IOException;

import com.game.magictower.Settings;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.AudioManager;
import android.media.SoundPool;

public final class GlobalSoundPool {
    
    private static GlobalSoundPool sInstance;
    private SoundPool soundPool;
    
    private GlobalSoundPool(Context context){
        soundPool = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);
    }
    
    public static GlobalSoundPool getInstance(Context context) {
        if (sInstance == null) {
            synchronized (GlobalSoundPool.class) {
                sInstance = new GlobalSoundPool(context);
            }
        }
        return sInstance;
    }
    
    protected final int loadSound(Context context, String assets) {
        try {
            AssetManager am = context.getAssets();
            AssetFileDescriptor afd = am.openFd("sound/" + assets);
            return soundPool.load(afd, 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return -1;
    }
    
    public void playSound(int soundId) {
        if (Settings.isVoiceEnabled()) {
            soundPool.play(soundId, 0.5F, 0.5F, 0, 0, 1.0F);
        }
    }

}
