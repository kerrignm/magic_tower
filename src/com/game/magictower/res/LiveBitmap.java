package com.game.magictower.res;

import java.io.IOException;

import com.game.magictower.util.BitmapUtil;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public final class LiveBitmap {
    
    private Bitmap bitmap;
    private final int rawWidth;
    private final int rawHeight;

    private LiveBitmap(Bitmap bitmap, int rawWidth, int rawHeight) {
        super();
        this.bitmap = bitmap;
        this.rawWidth = rawWidth;
        this.rawHeight = rawHeight;
    }

    protected static final LiveBitmap loadBitmap(Context context, String assets) {
        LiveBitmap instance = null;
        AssetManager am = context.getAssets();
        try {
            Bitmap bitmap = BitmapFactory.decodeStream(am.open("image/" + assets));
            int rawWidth = bitmap.getWidth();
            int rawHeight = bitmap.getHeight();
            bitmap = BitmapUtil.scaleBitmap(bitmap, TowerDimen.TOWER_SCALE, TowerDimen.TOWER_SCALE);
            instance = new LiveBitmap(bitmap, rawWidth, rawHeight);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return instance;
    }
    
    protected static final LiveBitmap loadBitmap(Context context, String assets,
            int width, int height) {
        LiveBitmap instance = null;
        AssetManager am = context.getAssets();
        try {
            Bitmap bitmap = BitmapFactory.decodeStream(am.open(assets));
            int rawWidth = bitmap.getWidth();
            int rawHeight = bitmap.getHeight();
            if (width != 0 && height != 0) {
                bitmap = BitmapUtil.scaleBitmap(bitmap, width, height);
            }
            instance = new LiveBitmap(bitmap, rawWidth, rawHeight);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return instance;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public int getRawWidth() {
        return rawWidth;
    }

    public int getRawHeight() {
        return rawHeight;
    }
    
    public int getWidth(){
        return bitmap.getWidth();
    }
    
    public int getHeight(){
        return bitmap.getHeight();
    }
    
    public void scaleTo(float scalew, float scaleh){
        bitmap = BitmapUtil.scaleBitmap(bitmap, scalew, scaleh);
    }
    
    public void scaleTo(int width, int height){
        bitmap = BitmapUtil.scaleBitmap(bitmap, width, height);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("LiveBitmap [getRawWidth()=").append(getRawWidth())
                .append(", getRawHeight()=").append(getRawHeight())
                .append(", getWidth()=").append(getWidth())
                .append(", getHeight()=").append(getHeight()).append("]");
        return builder.toString();
    }

}
