package com.game.magictower.scene;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.view.MotionEvent;

import com.game.magictower.Game;
import com.game.magictower.Game.Status;
import com.game.magictower.GameView;
import com.game.magictower.model.ShopInfo;
import com.game.magictower.res.Assets;
import com.game.magictower.res.GlobalSoundPool;
import com.game.magictower.res.TowerDimen;
import com.game.magictower.util.RectUtil;
import com.game.magictower.widget.BaseButton;

public class SceneShop extends BaseScene {
    
    private static final int SHOP_DONE = 1;
    private static final int SHOP_FAIL = 2;
    private static final int SHOP_NONE = 3;

    ArrayList<ShopInfo> choices;
    private Bitmap imgIcon;
    private int mSelected = 0;
    private Rect[] mTextRect = new Rect[4];
    private Rect[] mEdgeRect = new Rect[4];
    private Rect mTouchRect;
    private Rect mBgd;
    
    public SceneShop(GameView parent, Context context, Game game, int id, int x, int y, int w, int h) {
        super(parent, context, game, id, x, y, w, h);
        mTextRect[0] = TowerDimen.R_SHOP_TEXT;
        mTextRect[1] = RectUtil.createRect(TowerDimen.R_SHOP_TEXT, 0, TowerDimen.R_SHOP_TEXT.height());
        mTextRect[2] = RectUtil.createRect(TowerDimen.R_SHOP_TEXT, 0, TowerDimen.R_SHOP_TEXT.height() * 2);
        mTextRect[3] = RectUtil.createRect(TowerDimen.R_SHOP_TEXT, 0, TowerDimen.R_SHOP_TEXT.height() * 3);
        for (int i = 0; i < 4; i++) {
            mEdgeRect[i] = new Rect(mTextRect[i].left, mTextRect[i].top + TowerDimen.LINE_WIDTH, mTextRect[i].right, mTextRect[i].bottom - TowerDimen.LINE_WIDTH);
        }
        mTouchRect = new Rect(TowerDimen.R_SHOP_TEXT.left, TowerDimen.R_SHOP_TEXT.top, TowerDimen.R_SHOP_TEXT.right, TowerDimen.R_SHOP_TEXT.top + TowerDimen.R_SHOP_TEXT.height() * 4);
        mBgd = new Rect(0, 0, TowerDimen.R_SHOP.width(), TowerDimen.R_SHOP.height());
    }

    public void show(int shopId, int npcId) {
        choices = game.shops.get(shopId);
        imgIcon = Assets.getInstance().animMap0.get(npcId);
        mSelected = 0;
        game.status = Status.Shopping;
        parent.requestRender();
    }
    
    @Override
    public boolean onTouch(MotionEvent event) {
        boolean result = false;
        switch (event.getAction()) {
        case MotionEvent.ACTION_DOWN:
            if (inBounds(event)) {
                result = true;
                int selected = ((int)event.getY() - mTouchRect.top) / TowerDimen.R_SHOP_TEXT.height();
                if (selected != mSelected) {
                    mSelected = selected;
                } else {
                    selected();
                }
                parent.requestRender();
            }
            break;
        case MotionEvent.ACTION_MOVE:
           result = true;
            break;
        case MotionEvent.ACTION_UP:
        case MotionEvent.ACTION_POINTER_UP:
        case MotionEvent.ACTION_CANCEL:
        case MotionEvent.ACTION_OUTSIDE:
            result = true;
            parent.requestRender();
            break;
        }
        return result;
    }
    
    @Override
    protected boolean inBounds(MotionEvent event){
        return mTouchRect.contains((int)event.getX(), (int)event.getY());
    }
    
    @Override
    public void onDrawFrame(Canvas canvas) {
        super.onDrawFrame(canvas);
        graphics.drawBitmap(canvas, Assets.getInstance().bkgBlank, mBgd, TowerDimen.R_SHOP, null);
        graphics.drawRect(canvas, TowerDimen.R_SHOP);
        graphics.drawBitmap(canvas, imgIcon, TowerDimen.R_SHOP_ICON.left, TowerDimen.R_SHOP_ICON.top);
        for (int i = 0; i < 4; i++) {
            if (i == mSelected) {
                graphics.textPaint.setStyle(Style.STROKE);
                graphics.drawRect(canvas, mEdgeRect[i], graphics.textPaint);
                graphics.textPaint.setStyle(Style.FILL);
                graphics.drawText(canvas, choices.get(i).getDescribe(), mTextRect[i].left + TowerDimen.LINE_WIDTH * 2,
                        mTextRect[i].top + TowerDimen.TEXT_SIZE + (mTextRect[i].height() - TowerDimen.TEXT_SIZE) / 2);
            } else {
                graphics.disableTextPaint.setStyle(Style.STROKE);
                graphics.drawRect(canvas, mEdgeRect[i], graphics.disableTextPaint);
                graphics.disableTextPaint.setStyle(Style.FILL);
                graphics.drawText(canvas, choices.get(i).getDescribe(), mTextRect[i].left + TowerDimen.LINE_WIDTH * 2,
                        mTextRect[i].top + TowerDimen.TEXT_SIZE + (mTextRect[i].height() - TowerDimen.TEXT_SIZE) / 2, graphics.disableTextPaint);
            }
        }
    }
    
    @Override
    public void onAction(int id) {
        switch (id) {
        case BaseButton.ID_UP:
            GlobalSoundPool.getInstance().playSound(Assets.getInstance().getSoundId(Assets.SND_ID_CHANGE));
            if (mSelected > 0) {
                mSelected--;
            }
            break;
        case BaseButton.ID_DOWN:
            GlobalSoundPool.getInstance().playSound(Assets.getInstance().getSoundId(Assets.SND_ID_CHANGE));
            if (mSelected < 3) {
                mSelected++;
            }
            break;
        case BaseButton.ID_OK:
            selected();
            break;
        }
    }
    
    private void selected() {
        ShopInfo shopInfo = choices.get(mSelected);
        int result = SHOP_FAIL;
        switch (shopInfo.getType()) {
        case ShopInfo.COST_TYPE_MONEY:
            if (game.player.getMoney() >= shopInfo.getValue()) {
                game.player.setMoney(game.player.getMoney() - shopInfo.getValue());
                result = SHOP_DONE;
            }
            break;
        case ShopInfo.COST_TYPE_EXP:
            if (game.player.getExp() >= shopInfo.getValue()) {
                game.player.setExp(game.player.getExp() - shopInfo.getValue());
                result = SHOP_DONE;
            }
            break;
        case ShopInfo.COST_TYPE_YKEY:
            if (game.player.getYKey() >= shopInfo.getValue()) {
                game.player.setYKey(game.player.getYKey() - shopInfo.getValue());
                result = SHOP_DONE;
            }
            break;
        case ShopInfo.COST_TYPE_BKEY:
            if (game.player.getBKey() >= shopInfo.getValue()) {
                game.player.setBKey(game.player.getBKey() - shopInfo.getValue());
                result = SHOP_DONE;
            }
            break;
        case ShopInfo.COST_TYPE_RKEY:
            if (game.player.getRKey() >= shopInfo.getValue()) {
                game.player.setRKey(game.player.getRKey() - shopInfo.getValue());
                result = SHOP_DONE;
            }
            break;
        case ShopInfo.COST_TYPE_NONE:
            result = SHOP_NONE;
            break;
        }
        if (result == SHOP_DONE) {
            GlobalSoundPool.getInstance().playSound(Assets.getInstance().getSoundId(Assets.SND_ID_DONE));
            game.player.update(shopInfo);
        } else if (result == SHOP_FAIL) {
            GlobalSoundPool.getInstance().playSound(Assets.getInstance().getSoundId(Assets.SND_ID_COIN));
        } else {
            GlobalSoundPool.getInstance().playSound(Assets.getInstance().getSoundId(Assets.SND_ID_ZONE));
            game.status = Status.Playing;
        }
    }

}
