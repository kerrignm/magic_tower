package com.game.magictower.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.game.magictower.util.LogUtil;

public abstract class RedrawableView extends SurfaceView implements SurfaceHolder.Callback {

    private static final String TAG = "MagicTower:RedrawableView";
    
    private SurfaceHolder holder;
    private RenderThread renderThread;
    
    public RedrawableView(Context context) {
        super(context);
        this.holder = getHolder();
        this.holder.addCallback(this);
        setFocusable(true);
        setFocusableInTouchMode(true);
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
    
    protected abstract void onDrawFrame(Canvas canvas);
    
    private void redraw() {
        Canvas canvas = null;
        try {
            canvas = holder.lockCanvas();
            if (canvas == null) {
                LogUtil.w(TAG, "cancel drawing on null canvas.");
                return ;
            }
            onDrawFrame(canvas);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (canvas != null) {
                holder.unlockCanvasAndPost(canvas);
            }
        }
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
