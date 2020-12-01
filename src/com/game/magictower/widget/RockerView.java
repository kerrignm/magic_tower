package com.game.magictower.widget;

import java.lang.ref.WeakReference;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.os.Handler;
import android.os.Message;
import android.view.MotionEvent;

import com.game.magictower.util.MathUtil;

public class RockerView extends BaseView {
    
    //private static final String TAG = "MagicTower:RockerView";
    
    private Point rocker;
    
    private int innerRadius;
    private int outerRadius;
    
    private int centerX;
    private int centerY;
    
    private int action = -1;
    
    private Handler handler = new RockerHandler(new WeakReference<RockerView>(this));
    
    private Paint paint = new Paint();
    
    public RockerView( int id, int x, int y, int w, int h) {
        super(id, x, y, w, h);
        centerX = x + w / 2;
        centerY = y + h / 2;
        rocker = new Point(centerX, centerY);
        innerRadius = w / 3;
        outerRadius = innerRadius * 2;
        paint.setAntiAlias(true);
    }
    
    @Override
    public boolean onTouch(MotionEvent event) {
        boolean result = false;
        switch (event.getAction()) {
        case MotionEvent.ACTION_DOWN:
            if (inBounds(event)) {
                result = true;
                rocker.x = (int)event.getX();
                rocker.y = (int)event.getY();
            }
            break;
        case MotionEvent.ACTION_MOVE:
            result = true;
            if (!inBounds(event)) {
                double rad = MathUtil.getRad(centerX, centerY, (int)event.getX(), (int)event.getY());
                rocker.x = (int)(outerRadius * Math.cos(rad)) + centerX;
                rocker.y = (int)(outerRadius * Math.sin(rad)) + centerY;
                if ((listener != null) && (action < 0)) {
                    handler.sendEmptyMessageDelayed(MSG_ID_ACTION_PRESS, MSG_DELAY_ACTION_PRESS);
                }
                if (Math.abs(rad) <= Math.PI / 4) {
                    action = BaseButton.ID_RIGHT;
                } else if (Math.abs(rad) >= Math.PI * 3 / 4) {
                    action = BaseButton.ID_LEFT;
                } else if ((rad > Math.PI / 4) && (rad < Math.PI * 3 / 4)) {
                    action = BaseButton.ID_DOWN;
                } else {
                    action = BaseButton.ID_UP;
                }
            } else {
                rocker.x = (int)event.getX();
                rocker.y = (int)event.getY();
                action = -1;
                handler.removeMessages(MSG_ID_ACTION_PRESS);
            }
            break;
        case MotionEvent.ACTION_UP:
        case MotionEvent.ACTION_POINTER_UP:
        case MotionEvent.ACTION_CANCEL:
        case MotionEvent.ACTION_OUTSIDE:
            result = true;
            rocker.x = centerX;
            rocker.y = centerY;
            handler.removeMessages(MSG_ID_ACTION_PRESS);
            action = -1;
            break;
        }
        return result;
    }
    
    @Override
    public void onDrawFrame(Canvas canvas) {
        paint.setColor(0x70ffffff);
        canvas.drawCircle(centerX, centerY, outerRadius, paint);
        paint.setColor(0xaaffffff);
        canvas.drawCircle(rocker.x, rocker.y, innerRadius, paint);
    }
    
    @Override
    protected boolean inBounds(MotionEvent event) {
        return MathUtil.distance(centerX, centerY, (int)event.getX(), (int)event.getY()) < outerRadius;
    }

    public int getAction() {
        return action;
    }
    
    private static final int MSG_ID_ACTION_PRESS = 10;
    
    private static final int MSG_DELAY_ACTION_PRESS = 100;
    
    private static final class RockerHandler extends Handler {
        private WeakReference<RockerView> wk;

        public RockerHandler(WeakReference<RockerView> wk) {
            super();
            this.wk = wk;
        }
        
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (BaseView.getForbidTouch()) {
                removeMessages(MSG_ID_ACTION_PRESS);

            } else {
                RockerView rocker = wk.get();
                if (msg.what == MSG_ID_ACTION_PRESS && rocker != null && rocker.listener != null) {
                    if (rocker.getAction() >= 0) {
                        rocker.listener.onClicked(rocker.getAction());
                        sendEmptyMessageDelayed(MSG_ID_ACTION_PRESS, MSG_DELAY_ACTION_PRESS);
                    }
                }
            }
        }
    }
}
