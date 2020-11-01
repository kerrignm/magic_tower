package com.game.magictower.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.game.magictower.res.Assets;
import com.game.magictower.res.GameGraphics;
import com.game.magictower.res.TowerDimen;
import com.game.magictower.util.LogUtil;

public class GameView extends SurfaceView implements RedrawableView,
        SurfaceHolder.Callback {

    private static final String TAG = "MagicTower:GameView";
    
    private SurfaceHolder holder;
    private GameGraphics graphics;
    private GameScreen gamescreen;
    private RenderThread renderThread;
    
    public GameView(Context context, GameGraphics graphics, GameScreen listener) {
        super(context);
        if (graphics==null){
            throw new NullPointerException("graphics object cannot be null.");
        }
        this.graphics = graphics;
        this.holder = getHolder();
        this.holder.addCallback(this);
        this.gamescreen = listener;
    }
    
    @Override
    public void redraw() {
        Canvas canvas = null;
        try {
            canvas = holder.lockCanvas();
            if (canvas==null){
                LogUtil.w(TAG, "cancel drawing on null canvas.");
                return ;
            }
            canvas.drawColor(Color.BLACK);
            graphics.drawBitmap(canvas, Assets.getInstance().bkgGame, TowerDimen.TOWER_LEFT, TowerDimen.TOWER_TOP);
            if (gamescreen!=null){
                gamescreen.updateUI(graphics, canvas);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (canvas != null) {
                holder.unlockCanvasAndPost(canvas);
            }
        }
    }
    
    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width,
        int height) {
    }
    
    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        LogUtil.d(TAG, "surfaceCreated.");
        this.holder = getHolder();
        renderThread = new RenderThread();
        renderThread.start();
    }
    
    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        LogUtil.d(TAG, "surfaceDestroyed.");
        if (renderThread != null && renderThread.isAlive()) {
            try {
                renderThread.stopThread();
                renderThread.join();
            } catch (Exception e) {
        
            }
        }
        renderThread = null;
    }
    
    private final class RenderThread extends Thread{
    
        private boolean hasStopped;
        
        public RenderThread() {
            super("RenderThread");
            hasStopped = false;
        }
        
        protected synchronized final void stopThread(){
            hasStopped = true;
        }
        
        public void run() {
            long startTime, endTime, freeTime;
            while (!hasStopped) {
                try {
                    startTime = System.currentTimeMillis();
                    redraw();
                    endTime = System.currentTimeMillis();
                    freeTime = 17 - (endTime - startTime);
                    if (freeTime > 0) {
                        sleep(freeTime);
                    }
                } catch (Exception e) {
                    LogUtil.w(TAG, "exception while rendering:" + e.getMessage());
                }
            }
        };
    }
}
