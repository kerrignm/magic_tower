package com.game.magictower;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.Handler;
import android.os.Message;
import android.view.MotionEvent;

import com.game.magictower.res.Assets;
import com.game.magictower.res.GameGraphics;
import com.game.magictower.res.TowerDimen;
import com.game.magictower.scene.BaseScene;
import com.game.magictower.scene.SceneBattle;
import com.game.magictower.scene.SceneDialog;
import com.game.magictower.scene.SceneForecast;
import com.game.magictower.scene.SceneJump;
import com.game.magictower.scene.SceneMessage;
import com.game.magictower.scene.ScenePlay;
import com.game.magictower.scene.SceneShop;
import com.game.magictower.widget.BaseButton;
import com.game.magictower.widget.BaseView;
import com.game.magictower.widget.BaseView.onClickListener;
import com.game.magictower.widget.BitmapButton;
import com.game.magictower.widget.RedrawableView;
import com.game.magictower.widget.RockerView;
import com.game.magictower.widget.TextButton;

public class GameView extends RedrawableView {
    
    //private static final String TAG = "MagicTower:GameView";
    
    private Context mContext;
    
    private Game game;
    
    private BaseView mFocused;
    
    private ArrayList<BaseView> mChildrens = new ArrayList<BaseView>();
    
    private GameGraphics graphics;
    
    private boolean isToastShowing = false;
    private String mMsg;
    private Rect mMsgBgd;
    
    private ScenePlay scenePlay;
    private SceneBattle sceneBattle;
    private SceneShop sceneShop;
    private SceneMessage sceneMessage;
    private SceneDialog sceneDialog;
    private SceneForecast sceneForecast;
    private SceneJump sceneJump;
    
    public GameView(Context context) {
        super(context);
        mContext = context;
        this.game = Game.getInstance();
        graphics = GameGraphics.getInstance();
        createChildrens();
        scenePlay = new ScenePlay(this, context, game, 30, TowerDimen.R_TOWER.left, TowerDimen.R_TOWER.top, TowerDimen.R_TOWER.width(), TowerDimen.R_TOWER.height());
        sceneBattle = new SceneBattle(this, context, game, 31, TowerDimen.R_TOWER.left, TowerDimen.R_TOWER.top, TowerDimen.R_TOWER.width(), TowerDimen.R_TOWER.height());
        sceneShop = new SceneShop(this, context, game, 32, TowerDimen.R_TOWER.left, TowerDimen.R_TOWER.top, TowerDimen.R_TOWER.width(), TowerDimen.R_TOWER.height());
        sceneMessage = new SceneMessage(this, context, game, 33, TowerDimen.R_TOWER.left, TowerDimen.R_TOWER.top, TowerDimen.R_TOWER.width(), TowerDimen.R_TOWER.height());
        sceneDialog = new SceneDialog(this, context, game, 34, TowerDimen.R_TOWER.left, TowerDimen.R_TOWER.top, TowerDimen.R_TOWER.width(), TowerDimen.R_TOWER.height());
        sceneForecast = new SceneForecast(this, context, game, 35, TowerDimen.R_TOWER.left, TowerDimen.R_TOWER.top, TowerDimen.R_TOWER.width(), TowerDimen.R_TOWER.height());
        sceneJump = new SceneJump(this, context, game, 36, TowerDimen.R_JUMP.left, TowerDimen.R_JUMP.top, TowerDimen.R_JUMP.width(), TowerDimen.R_JUMP.height());
        
        mMsgBgd = new Rect(0, 0, TowerDimen.R_MSG.width(), TowerDimen.R_MSG.height());
    }

    private void createChildrens() {
        addChild(BitmapButton.create(BaseButton.ID_UP, Assets.getInstance().upBtn, true));
        addChild(BitmapButton.create(BaseButton.ID_LEFT, Assets.getInstance().leftBtn, true));
        addChild(BitmapButton.create(BaseButton.ID_RIGHT, Assets.getInstance().rightBtn, true));
        addChild(BitmapButton.create(BaseButton.ID_DOWN, Assets.getInstance().downBtn, true));
        addChild(TextButton.create(BaseButton.ID_QUIT, getResources().getString(R.string.btn_quit), false));
        addChild(TextButton.create(BaseButton.ID_SAVE, getResources().getString(R.string.btn_save), false));
        addChild(TextButton.create(BaseButton.ID_READ, getResources().getString(R.string.btn_load), false));
        addChild(TextButton.create(BaseButton.ID_LOOK, getResources().getString(R.string.btn_look), false));
        addChild(TextButton.create(BaseButton.ID_JUMP, getResources().getString(R.string.btn_jump), false));
        addChild(TextButton.create(BaseButton.ID_OK, getResources().getString(R.string.btn_ok), false));
        addChild(new RockerView(BaseButton.ID_OK + 1, TowerDimen.R_ROCKER.left, TowerDimen.R_ROCKER.top, TowerDimen.R_ROCKER.width(), TowerDimen.R_ROCKER.height()));
    }
    
    private void addChild(BaseView child) {
        mChildrens.add(child);
        child.setOnClickListener(btnClickListener);
        child.setRenderer(this);
    }
    
    private onClickListener btnClickListener = new onClickListener() {
        @Override
        public void onClicked(int id) {
            getScene().onAction(id);
            requestRender();
        }
    };
    
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);
        boolean result = false;
        if (isToastShowing) {
            if (mFocused != null) {
                event.setAction(MotionEvent.ACTION_CANCEL);
                mFocused.onTouch(event);
                result = true;
                mFocused = null;
                requestRender();
            }
            return result;
        }
        switch(event.getAction()) {
        case MotionEvent.ACTION_DOWN:
            if (getScene().onTouch(event)) {
                mFocused = getScene();
                result = true;
                performClick();
            } else {
                for (BaseView child : mChildrens) {
                    if (child.onTouch(event)) {
                        mFocused = child;
                        result = true;
                        performClick();
                        break;
                    }
                }
            }
            break;
        case MotionEvent.ACTION_MOVE:
            if (mFocused != null) {
                result = true;
                mFocused.onTouch(event);
            }
            break;
        case MotionEvent.ACTION_UP:
        case MotionEvent.ACTION_CANCEL:
        case MotionEvent.ACTION_POINTER_UP:
        case MotionEvent.ACTION_OUTSIDE:
            if (mFocused != null) {
                result = true;
                mFocused.onTouch(event);
                mFocused = null;
            }
            break;
        }
        if (result) {
            requestRender();
        }
        return result;
    }
    
    @Override
    public boolean performClick() {
        return super.performClick();
    }
    
    @Override
    public void onDrawFrame(Canvas canvas) {
        drawScene(canvas);
        drawChildrens(canvas);
        drawToast(canvas);
    }
    
    protected void onPause() {
        handler.removeMessages(MSG_ID_ANI);
    }
    
    protected void onResume() {
        handler.removeMessages(MSG_ID_ANI);
        handler.sendEmptyMessageDelayed(MSG_ID_ANI, MSG_DELAY_ANI);
    }
    
    private void drawScene(Canvas canvas) {
        getScene().onDrawFrame(canvas);
    }
    
    private void drawChildrens(Canvas canvas) {
        for (BaseView child : mChildrens) {
            child.onDrawFrame(canvas);
        }
    }
    
    private void drawToast(Canvas canvas) {
        if (isToastShowing) {
            graphics.drawBitmap(canvas, Assets.getInstance().bkgBlank, mMsgBgd, TowerDimen.R_MSG, null);
            graphics.drawRect(canvas, TowerDimen.R_MSG);
            graphics.drawTextInCenter(canvas, mMsg, TowerDimen.R_MSG, graphics.bigTextPaint);
        }
    }
    
    private Handler handler = new MessagHandler(new WeakReference<GameView>(this));
    
    private static final int MSG_ID_DISPLAY_TIMEOUT = 20;
    private static final int MSG_ID_ANI = 30;
    
    private static final int MSG_DELAY_DISPLAY_TIMEOUT = 1000;
    private static final int MSG_DELAY_ANI = 500;
    
    private static final class MessagHandler extends Handler {
        private WeakReference<GameView> wk;

        public MessagHandler(WeakReference<GameView> wk) {
            super();
            this.wk = wk;
        }
        
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            GameView gameview = wk.get();
            if (msg.what == MSG_ID_DISPLAY_TIMEOUT && gameview != null) {
                gameview.isToastShowing = false;
                BaseView.setForbidTouch(false);
                gameview.requestRender();
            } else if (msg.what == MSG_ID_ANI && gameview != null) {
                BaseScene.updateAni();
                gameview.requestRender();
                gameview.handler.sendEmptyMessageDelayed(MSG_ID_ANI, MSG_DELAY_ANI);
            }
        }
    }
    
    private BaseScene getScene() {
        switch(Game.getInstance().status) {
        case Playing:
            return scenePlay;
        case Fighting:
            return sceneBattle;
        case Shopping:
            return sceneShop;
        case Messaging:
            return sceneMessage;
        case Dialoging:
            return sceneDialog;
        case Looking:
            return sceneForecast;
        case Jumping:
            return sceneJump;
        default:
            return null;
        }
    }
    
    public boolean isShowToast() {
        return isToastShowing;
    }
    
    public void showToast(int resId) {
        showToast(mContext.getResources().getString(resId));
    }
    
    public void showToast(String msg) {
        isToastShowing = true;
        BaseView.setForbidTouch(true);
        mMsg = msg;
        handler.sendEmptyMessageDelayed(MSG_ID_DISPLAY_TIMEOUT, MSG_DELAY_DISPLAY_TIMEOUT);
        
    }
    
    public void showBattle(int id, int x, int y) {
        sceneBattle.show(id, x, y);
    }
    
    public void showShop(int shopId, int npcId) {
        sceneShop.show(shopId, npcId);
    }
    
    public void showMessage(String title, String msg) {
        sceneMessage.show(title, msg);
    }
    
    public void showMessage(int id) {
        sceneMessage.show(id);
    }
    
    public void showDialog(int dialogId, int npcId) {
        sceneDialog.show(dialogId, npcId);
    }
    
    public void showForecast() {
        sceneForecast.show();
    }
    
    public void showJump() {
        sceneJump.show();
    }
}
