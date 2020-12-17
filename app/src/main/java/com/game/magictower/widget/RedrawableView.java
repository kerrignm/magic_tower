package com.game.magictower.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.os.Handler;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.game.magictower.util.LogUtil;

public abstract class RedrawableView extends SurfaceView implements SurfaceHolder.Callback, Renderer {

    private static final String TAG = "MagicTower:RedrawableView";
    
    private SurfaceHolder holder;
    private RenderThread renderThread;
    
    private final Object sLock = new Object();
    
    private final Handler handler = new Handler();
    private final Runnable renderRunnable = () -> {
        synchronized(sLock) {
            sLock.notifyAll();
        }
    };
    
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
                synchronized(sLock) {
                    sLock.notifyAll();
                }
                renderThread.join();
            } catch (Exception ignored) {
        
            }
        }
        renderThread = null;
    }
    
    public void requestRender() {
        handler.post(renderRunnable);
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
            LogUtil.e(TAG, "redraw() Exception e = " + e.getMessage());
        } finally {
            if (canvas != null) {
                holder.unlockCanvasAndPost(canvas);
            }
        }
    }
    
    private final class RenderThread extends Thread {
    
        private boolean hasStopped;
        
        public RenderThread() {
            super("RenderThread");
            hasStopped = false;
        }
        
        protected synchronized final void stopThread() {
            hasStopped = true;
        }
        
        public void run() {
            synchronized(sLock) {
                while (!hasStopped) {
                    try {
                        redraw();
                    } catch (Exception e) {
                        LogUtil.e(TAG, "exception while rendering : " + e.getMessage());
                    }
                    try {
                        sLock.wait();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            }
        }
    }
}
