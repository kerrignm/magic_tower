package com.game.magictower.scene;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.os.Handler;
import android.os.Message;
import android.view.MotionEvent;

import com.game.magictower.Game;
import com.game.magictower.Game.Status;
import com.game.magictower.GameView;
import com.game.magictower.R;
import com.game.magictower.astar.AStarPath;
import com.game.magictower.astar.AStarPoint;
import com.game.magictower.astar.ObstacleFilter;
import com.game.magictower.model.NpcInfo;
import com.game.magictower.res.Assets;
import com.game.magictower.res.GlobalSoundPool;
import com.game.magictower.res.TowerDimen;
import com.game.magictower.util.LogUtil;
import com.game.magictower.util.RectUtil;
import com.game.magictower.widget.BaseButton;

public class ScenePlay extends BaseScene {
    
    private static final String TAG = "MagicTower:ScenePlay";
    
    private Rect[][] mPathRects;
    
    private AStarPath astarPath;
    
    private GridFilter gridFilter = new GridFilter();
    
    private Point targetPoint = new Point();
    private Point touchPoint = new Point();
    private ArrayList<AStarPoint> stepList = new ArrayList<AStarPoint>();
    private boolean canReach = false;
    private boolean startQutoStep = false;
    
    private Paint mTouchPaint;
    private Paint mPathPaint;
    
    public ScenePlay(GameView parent, Context context, Game game, int id, int x, int y, int w, int h) {
        super(parent, context, game, id, x, y, w, h);
        mPathRects = new Rect[TowerDimen.GRID_NUMS][TowerDimen.GRID_NUMS];
        for(int i = 0; i < mPathRects.length; i++) {
            for (int j = 0; j < mPathRects[i].length; j++) {
                mPathRects[i][j] = RectUtil.createRect(TowerDimen.R_TOUCH_GRID, j * TowerDimen.GRID_SIZE, i * TowerDimen.GRID_SIZE);
            }
        }
        astarPath = new AStarPath(TowerDimen.GRID_NUMS, TowerDimen.GRID_NUMS);
        astarPath.setFilter(gridFilter);
        
        mTouchPaint = new Paint();
        mTouchPaint.setAntiAlias(true);
        mTouchPaint.setARGB(255, 0xcc, 0x66, 0x00);
        mTouchPaint.setStyle(Style.STROKE);
        mTouchPaint.setStrokeWidth(5);
        mTouchPaint.setTypeface(Typeface.DEFAULT_BOLD);
        mPathPaint = new Paint();
        mPathPaint.setAntiAlias(true);
        mPathPaint.setARGB(255, 0xcc, 0x66, 0x00);
        mPathPaint.setStyle(Style.STROKE);
        mPathPaint.setStrokeWidth(5);
        mPathPaint.setTypeface(Typeface.DEFAULT_BOLD);
        mPathPaint.setPathEffect(new DashPathEffect(new float[] {10, 5}, 0));
    }

    public void show() {
        game.status = Status.Playing;
        parent.requestRender();
    }
    
    @Override
    public boolean onTouch(MotionEvent event) {
        boolean result = false;
        switch (event.getAction()) {
        case MotionEvent.ACTION_DOWN:
            if (inBounds(event)) {
                result = true;
                getTouchGrid((int)event.getX(), (int)event.getY());
                if (!canReach || (!targetPoint.equals(touchPoint))) {
                    if (canInteraction(touchPoint.x, touchPoint.y)) {
                        gridFilter.setTarget(touchPoint.x, touchPoint.y);
                        AStarPoint astarPoint = astarPath.getPath(game.player.getPosX(), game.player.getPosY(), touchPoint.x, touchPoint.y, game.lvMap[game.npcInfo.curFloor]);
                        if (astarPoint == null) {
                            clearTouchStep();
                        } else {
                            AStarPoint current = astarPoint;
                            stepList.clear();
                            while (current.getFather() != null) {
                                stepList.add(current);
                                current = current.getFather();
                            }
                            canReach = true;
                            targetPoint.x = touchPoint.x;
                            targetPoint.y = touchPoint.y;
                        }
                    } else {
                        clearTouchStep();
                    }
                } else {
                    startQutoStep = true;
                    handler.sendEmptyMessageDelayed(MSG_ID_AUTO_STEP, MSG_DELAY_AUTO_STEP);
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
    
    private class GridFilter implements ObstacleFilter {
        
        private int targetX;
        private int targetY;
        
        public GridFilter() {
            targetX = 0;
            targetY = 0;
        }
        
        public boolean isObstacle(int value, int x, int y) {
            boolean result = false;
            if ((x == targetX) && (y == targetY)) {
                if (!canInteraction(x, y)) {
                    result = true;
                }
            } else if ((value > 0) && (value < 100)) {
                result = true; 
            }
            //LogUtil.d(TAG, "isObstacle() x = " + x + ", y = " + y + ", targetX = " + targetX + ", targetY = " + targetY + ", result = " + result);
            return result;
        }
        
        public void setTarget(int x, int y) {
            targetX = x;
            targetY = y;
        }
    }
    
    private void clearTouchStep() {
        canReach = false;
        startQutoStep = false;
        targetPoint.x = targetPoint.y = -1;
        stepList.clear();
    }
    
    private void getTouchGrid(int x, int y) {
        touchPoint.x = (x - rect.left) / TowerDimen.GRID_SIZE;
        touchPoint.y = (y - rect.top) / TowerDimen.GRID_SIZE;
    }
    
    private void autoStep() {
        if (canReach) {
            if (stepList.size() > 0) {
                AStarPoint current = stepList.remove(stepList.size() - 1);
                if (current.getY() < game.player.getPosY()) {
                    game.player.setToward(3);
                } else if (current.getY() > game.player.getPosY()) {
                    game.player.setToward(1);
                } else if (current.getX() > game.player.getPosX()) {
                    game.player.setToward(2);
                } else {
                    game.player.setToward(0);
                }
                if (game.lvMap[game.npcInfo.curFloor][current.getY()][current.getX()] == 0) {
                    handler.sendEmptyMessageDelayed(MSG_ID_AUTO_STEP, MSG_DELAY_AUTO_STEP);
                } else {
                    clearTouchStep();
                }
                interaction(current.getX(), current.getY());
            } else {
                if (targetPoint.y < game.player.getPosY()) {
                    game.player.setToward(3);
                } else if (targetPoint.y > game.player.getPosY()) {
                    game.player.setToward(1);
                } else if (targetPoint.x > game.player.getPosX()) {
                    game.player.setToward(2);
                } else {
                    game.player.setToward(0);
                }
                interaction(targetPoint.x, targetPoint.y);
                clearTouchStep();
            }
            parent.requestRender();
        }
    }
    
    private void drawGrid(Canvas canvas) {
        if (canReach) {
            if (targetPoint != null) {
                Rect r = RectUtil.createRect(rect.left + targetPoint.x * TowerDimen.GRID_SIZE,
                            rect.top + targetPoint.y * TowerDimen.GRID_SIZE,
                            TowerDimen.GRID_SIZE, TowerDimen.GRID_SIZE);
                graphics.drawRect(canvas, r, mTouchPaint);
            }
            AStarPoint current = null;
            int i = stepList.size();
            while (i > 0) {
                current = stepList.get(i - 1);
                graphics.drawRect(canvas, mPathRects[current.getY()][current.getX()], mPathPaint);
                i--;
            }
        }
    }
    
    @Override
    public void onDrawFrame(Canvas canvas) {
        super.onDrawFrame(canvas);
        drawGrid(canvas);
    }
    
    @Override
    public void onAction(int id) {
        if (id != BaseButton.ID_OK) {
            clearTouchStep();
        }
        switch (id) {
        case BaseButton.ID_UP:
            if (game.status == Status.Playing) {
                game.checkTest(id);
                if (game.player.getPosY() - 1 < TowerDimen.GRID_NUMS && game.player.getPosY() - 1 >= 0) {
                    game.player.setToward(3);
                    interaction(game.player.getPosX(), game.player.getPosY() - 1);
                }
            }
            break;
        case BaseButton.ID_LEFT:
            if (game.status == Status.Playing) {
                game.checkTest(id);
                if (game.player.getPosX() - 1 < TowerDimen.GRID_NUMS && game.player.getPosX() - 1 >= 0) {
                    game.player.setToward(0);
                    interaction(game.player.getPosX() - 1, game.player.getPosY());
                }
            }
            break;
        case BaseButton.ID_RIGHT:
            if (game.status == Status.Playing) {
                game.checkTest(id);
                if (game.player.getPosX() + 1 < TowerDimen.GRID_NUMS && game.player.getPosX() + 1 >= 0) {
                    game.player.setToward(2);
                    interaction(game.player.getPosX() + 1, game.player.getPosY());
                }
            }
            break;
        case BaseButton.ID_DOWN:
            if (game.status == Status.Playing) {
                game.checkTest(id);
                if (game.player.getPosY() + 1 < TowerDimen.GRID_NUMS && game.player.getPosY() + 1 >= 0) {
                    game.player.setToward(1);
                    interaction(game.player.getPosX(), game.player.getPosY() + 1);
                }
            }
            break;
        case BaseButton.ID_QUIT:
            game.quitGame();
            break;
        case BaseButton.ID_SAVE:
            if (game.saveGame()) {
                parent.showToast(R.string.save_succeed);
            } else {
                parent.showToast(R.string.save_failed);
            }
            GlobalSoundPool.getInstance().playSound(Assets.getInstance().getSoundId(Assets.SND_ID_RECORD));
            break;
        case BaseButton.ID_READ:
            if (game.loadGame()) {
                parent.showToast(R.string.read_succeed);
            } else {
                parent.showToast(R.string.read_failed);
            }
            GlobalSoundPool.getInstance().playSound(Assets.getInstance().getSoundId(Assets.SND_ID_RECORD));
            game.changeMusic();
            break;
        case BaseButton.ID_LOOK:
            if (game.npcInfo.isHasForecast) {
                parent.showForecast();
            }
            break;
        case BaseButton.ID_JUMP:
            if (game.npcInfo.isHasJump) {
                parent.showJump();
            }
            break;
        case BaseButton.ID_OK:
                if (canReach && !startQutoStep) {
                    autoStep();
                }
            break;
        }
    }
    
    private void interaction(int x, int y) {
        int id = game.lvMap[game.npcInfo.curFloor][y][x];
        LogUtil.d(TAG, "interaction() id = " + id);
        switch (id) {
            case 0:     // game.player move
                game.player.move(x, y);
                GlobalSoundPool.getInstance().playSound(Assets.getInstance().getSoundId(Assets.SND_ID_STEP));
                break;
            case 1:     // brick wall
                break;
            case 2:     // yellow door
                if (game.player.getYkey() > 0) {
                    GlobalSoundPool.getInstance().playSound(Assets.getInstance().getSoundId(Assets.SND_ID_ZONE));
                    game.lvMap[game.npcInfo.curFloor][y][x] = 0;
                    game.player.setYkey(game.player.getYkey() - 1);
                }
                break;
            case 3:     // blue door
                if (game.player.getBkey() > 0) {
                    GlobalSoundPool.getInstance().playSound(Assets.getInstance().getSoundId(Assets.SND_ID_ZONE));
                    game.lvMap[game.npcInfo.curFloor][y][x] = 0;
                    game.player.setBkey(game.player.getBkey() - 1);
                }
                break;
            case 4:     // red door
                if (game.player.getRkey() > 0) {
                    GlobalSoundPool.getInstance().playSound(Assets.getInstance().getSoundId(Assets.SND_ID_ZONE));
                    game.lvMap[game.npcInfo.curFloor][y][x] = 0;
                    game.player.setRkey(game.player.getRkey() - 1);
                }
                break;
            case 5:     // stone
                break;
            case 6:     // yellow hey
                GlobalSoundPool.getInstance().playSound(Assets.getInstance().getSoundId(Assets.SND_ID_ITEM));
                game.lvMap[game.npcInfo.curFloor][y][x] = 0;
                game.player.setYkey(game.player.getYkey() + 1);
                parent.showToast(R.string.get_yellow_key);
                break;
            case 7:     // blue key
                GlobalSoundPool.getInstance().playSound(Assets.getInstance().getSoundId(Assets.SND_ID_ITEM));
                game.lvMap[game.npcInfo.curFloor][y][x] = 0;
                game.player.setBkey(game.player.getBkey() + 1);
                parent.showToast(R.string.get_bule_key);
                break;
            case 8:     // red key
                GlobalSoundPool.getInstance().playSound(Assets.getInstance().getSoundId(Assets.SND_ID_ITEM));
                game.lvMap[game.npcInfo.curFloor][y][x] = 0;
                game.player.setRkey(game.player.getRkey() + 1);
                parent.showToast(R.string.get_red_key);
                break;
            case 9:     // sapphire
                GlobalSoundPool.getInstance().playSound(Assets.getInstance().getSoundId(Assets.SND_ID_ITEM));
                game.lvMap[game.npcInfo.curFloor][y][x] = 0;
                game.player.setDefend(game.player.getDefend() + 3);
                parent.showToast(R.string.get_sapphire);
                break;
            case 10:    // ruby
                GlobalSoundPool.getInstance().playSound(Assets.getInstance().getSoundId(Assets.SND_ID_ITEM));
                game.lvMap[game.npcInfo.curFloor][y][x] = 0;
                game.player.setAttack(game.player.getAttack() + 3);
                parent.showToast(R.string.get_ruby);
                break;
            case 11:    // red potion
                GlobalSoundPool.getInstance().playSound(Assets.getInstance().getSoundId(Assets.SND_ID_ITEM));
                game.lvMap[game.npcInfo.curFloor][y][x] = 0;
                game.player.setHp(game.player.getHp() + 200);
                parent.showToast(R.string.get_red_potion);
                break;
            case 12:    // blue potion
                GlobalSoundPool.getInstance().playSound(Assets.getInstance().getSoundId(Assets.SND_ID_ITEM));
                game.lvMap[game.npcInfo.curFloor][y][x] = 0;
                game.player.setHp(game.player.getHp() + 500);
                parent.showToast(R.string.get_blue_potion);
                break;
            case 13:    // upstairs
                parent.showToast(R.string.msg_upstairs);
                GlobalSoundPool.getInstance().playSound(Assets.getInstance().getSoundId(Assets.SND_ID_ZONE));
                game.npcInfo.curFloor++;
                game.changeMusic();
                game.npcInfo.maxFloor = Math.max(game.npcInfo.maxFloor, game.npcInfo.curFloor);
                game.player.move(game.tower.initPos[game.npcInfo.curFloor][0], game.tower.initPos[game.npcInfo.curFloor][1]);
                if (game.npcInfo.curFloor == 21) {
                    game.npcInfo.isHasJump = false;
                }
                break;
            case 14:    // downstairs
                parent.showToast(R.string.msg_downstairs);
                GlobalSoundPool.getInstance().playSound(Assets.getInstance().getSoundId(Assets.SND_ID_ZONE));
                game.npcInfo.curFloor--;
                game.changeMusic();
                game.player.move(game.tower.finPos[game.npcInfo.curFloor][0], game.tower.finPos[game.npcInfo.curFloor][1]);
                break;
            case 15:    // barrier not accessible
                break;
            case 16:   // accessible guardrail
                GlobalSoundPool.getInstance().playSound(Assets.getInstance().getSoundId(Assets.SND_ID_ZONE));
                game.lvMap[game.npcInfo.curFloor][y][x] = 0;
                break;
            case 19:    // sea of fire
                break;
            case 20:    // starry sky
                break;
            case 22:    // shop
                if (game.npcInfo.curFloor == 3) {
                    parent.showShop(0, id);
                } else if (game.npcInfo.curFloor == 11) {
                    parent.showShop(3, id);
                }
                break;
            case 24:    // fairy
                switch (game.npcInfo.mFairyStatus) {
                case NpcInfo.FAIRY_STATUS_WAIT_PLAYER:
                    parent.showDialog(0, id);
                    break;
                case NpcInfo.FAIRY_STATUS_WAIT_CROSS:
                    if (game.npcInfo.isHasCross) {
                        parent.showDialog(1, id);
                    }
                    break;
                }
                break;
            case 25:    // thief
                switch (game.npcInfo.mThiefStatus) {
                case NpcInfo.THIEF_STATUS_WAIT_PLAYER:
                    parent.showDialog(8, id);
                    break;
                case NpcInfo.THIEF_STATUS_WAIT_HAMMER:
                    parent.showDialog(11, id);
                    break;
                }
                break;
            case 26:    // old man
                if (game.npcInfo.curFloor == 2) {
                    parent.showDialog(4, id);
                } else if (game.npcInfo.curFloor == 5) {
                    parent.showShop(1, id);
                } else if (game.npcInfo.curFloor == 13) {
                    parent.showShop(5, id);
                } else if (game.npcInfo.curFloor == 15) {
                    if (game.player.getExp() >= 500) {
                        parent.showDialog(3, id);
                    } else {
                        parent.showDialog(2, id);
                    }
                }
                break;
            case 27:    // businessman
                if (game.npcInfo.curFloor == 2) {
                    parent.showDialog(5, id);
                } else if (game.npcInfo.curFloor == 5) {
                    parent.showShop(2, id);
                } else if (game.npcInfo.curFloor == 12) {
                    parent.showShop(4, id);
                } else if (game.npcInfo.curFloor == 15) {
                    if (game.player.getMoney() >= 500) {
                        parent.showDialog(7, id);
                    } else {
                        parent.showDialog(6, id);
                    }
                }
                break;
            case 28:    // princess
                if (game.npcInfo.mPrincessStatus == NpcInfo.PRINCESS_STATUS_WAIT_PLAYER) {
                    parent.showDialog(9, id);
                }
                break;
            case 30:    // little flying feather
                GlobalSoundPool.getInstance().playSound(Assets.getInstance().getSoundId(Assets.SND_ID_ITEM));
                game.lvMap[game.npcInfo.curFloor][y][x] = 0;
                game.player.setLevel(game.player.getLevel() + 1);
                game.player.setHp(game.player.getHp() + 1000);
                game.player.setAttack(game.player.getAttack() + 10);
                game.player.setDefend(game.player.getDefend() + 10);
                parent.showToast(R.string.get_little_fly);
                break;
            case 31:    // big flying feather
                GlobalSoundPool.getInstance().playSound(Assets.getInstance().getSoundId(Assets.SND_ID_ITEM));
                game.lvMap[game.npcInfo.curFloor][y][x] = 0;
                game.player.setLevel(game.player.getLevel() + 3);
                game.player.setHp(game.player.getHp() + 3000);
                game.player.setAttack(game.player.getAttack() + 30);
                game.player.setDefend(game.player.getDefend() + 30);
                parent.showToast(R.string.get_big_fly);
                break;
            case 32:    // cross
                GlobalSoundPool.getInstance().playSound(Assets.getInstance().getSoundId(Assets.SND_ID_WONDER));
                game.lvMap[game.npcInfo.curFloor][y][x] = 0;
                game.npcInfo.isHasCross = true;
                parent.showMessage(R.string.treasure_cross, R.string.treasure_cross_info);
                break;
            case 33:    // holy water bottle
                GlobalSoundPool.getInstance().playSound(Assets.getInstance().getSoundId(Assets.SND_ID_WONDER));
                game.lvMap[game.npcInfo.curFloor][y][x] = 0;
                parent.showMessage(R.string.treasure_holy_water, R.string.treasure_holy_water_info);
                game.player.setHp(game.player.getHp() * 2);
                break;
            case 34:    // emblem of light
                GlobalSoundPool.getInstance().playSound(Assets.getInstance().getSoundId(Assets.SND_ID_WONDER));
                game.lvMap[game.npcInfo.curFloor][y][x] = 0;
                game.npcInfo.isHasForecast = true;
                parent.showMessage(R.string.treasure_emblem_light, R.string.treasure_emblem_light_info);
                break;
            case 35:    // compass of wind
                GlobalSoundPool.getInstance().playSound(Assets.getInstance().getSoundId(Assets.SND_ID_WONDER));
                game.lvMap[game.npcInfo.curFloor][y][x] = 0;
                game.npcInfo.isHasJump = true;
                parent.showMessage(R.string.treasure_compass, R.string.treasure_compass_info);
                break;
            case 36:    // key box
                GlobalSoundPool.getInstance().playSound(Assets.getInstance().getSoundId(Assets.SND_ID_ITEM));
                game.lvMap[game.npcInfo.curFloor][y][x] = 0;
                game.player.setYkey(game.player.getYkey() + 1);
                game.player.setBkey(game.player.getBkey() + 1);
                game.player.setRkey(game.player.getRkey() + 1);
                parent.showToast(R.string.get_key_box);
                break;
            case 38:    // hammer
                GlobalSoundPool.getInstance().playSound(Assets.getInstance().getSoundId(Assets.SND_ID_WONDER));
                game.lvMap[game.npcInfo.curFloor][y][x] = 0;
                game.npcInfo.isHasHammer = true;
                parent.showMessage(R.string.treasure_hammer, R.string.treasure_hammer_info);
                break;
            case 39:    // gold nugget
                GlobalSoundPool.getInstance().playSound(Assets.getInstance().getSoundId(Assets.SND_ID_ITEM));
                game.lvMap[game.npcInfo.curFloor][y][x] = 0;
                game.player.setMoney(game.player.getMoney() + 300);
                parent.showToast(R.string.get_gold_block);
                break;
            case 40:    // monster
            case 41:    // monster
            case 42:    // monster
            case 43:    // monster
            case 44:    // monster
            case 45:    // monster
            case 46:    // monster
            case 47:    // monster
            case 48:    // monster
            case 49:    // monster
            case 50:    // monster
            case 51:    // monster
            case 52:    // monster
            case 53:    // monster
            case 54:    // monster
            case 55:    // monster
            case 56:    // monster
            case 57:    // monster
            case 58:    // monster
            case 59:    // monster
            case 60:    // monster
            case 61:    // monster
            case 62:    // monster
            case 63:    // monster
            case 64:    // monster
            case 65:    // monster
            case 66:    // monster
            case 67:    // monster
            case 68:    // monster
            case 69:    // monster
            case 70:    // monster
                if (SceneForecast.forecast(game.player, game.monsters.get(id)).equals("???")
                        || Integer.parseInt(SceneForecast.forecast(game.player, game.monsters.get(id))) >= game.player.getHp()) {
                    return;
                } else {
                    parent.showBattle(id, x, y);
                }
                break;
            case 71:    // iron sword
                GlobalSoundPool.getInstance().playSound(Assets.getInstance().getSoundId(Assets.SND_ID_ITEM));
                game.lvMap[game.npcInfo.curFloor][y][x] = 0;
                game.player.setAttack(game.player.getAttack() + 10);
                parent.showToast(R.string.get_iron_shield);
                break;
            case 73:    // steel sword
                GlobalSoundPool.getInstance().playSound(Assets.getInstance().getSoundId(Assets.SND_ID_ITEM));
                game.lvMap[game.npcInfo.curFloor][y][x] = 0;
                game.player.setAttack(game.player.getAttack() + 70);
                parent.showToast(R.string.get_steel_sword);
                break;
            case 75:    // sword of light
                GlobalSoundPool.getInstance().playSound(Assets.getInstance().getSoundId(Assets.SND_ID_ITEM));
                game.lvMap[game.npcInfo.curFloor][y][x] = 0;
                game.player.setAttack(game.player.getAttack() + 150);
                parent.showToast(R.string.get_light_sword);
                break;
            case 76:    // iron shield
                GlobalSoundPool.getInstance().playSound(Assets.getInstance().getSoundId(Assets.SND_ID_ITEM));
                game.lvMap[game.npcInfo.curFloor][y][x] = 0;
                game.player.setDefend(game.player.getDefend() + 10);
                parent.showToast(R.string.get_iron_shield);
                break;
            case 78:    // gold shield
                GlobalSoundPool.getInstance().playSound(Assets.getInstance().getSoundId(Assets.SND_ID_ITEM));
                game.lvMap[game.npcInfo.curFloor][y][x] = 0;
                game.player.setDefend(game.player.getDefend() + 85);
                parent.showToast(R.string.get_gold_shield);
                break;
            case 80:    // light shield
                GlobalSoundPool.getInstance().playSound(Assets.getInstance().getSoundId(Assets.SND_ID_ITEM));
                game.lvMap[game.npcInfo.curFloor][y][x] = 0;
                game.player.setDefend(game.player.getDefend() + 190);
                parent.showToast(R.string.get_light_shield);
                break;
            case 101:
                game.lvMap[game.npcInfo.curFloor][y][x] = 0;
                parent.showDialog(10, 53);
                break;
            case 102:
                game.lvMap[game.npcInfo.curFloor][y][x] = 0;
                parent.showDialog(12, 59);
                break;
        }
    }
    
    private boolean canInteraction(int x, int y) {
        int id = game.lvMap[game.npcInfo.curFloor][y][x];
        boolean result = true;
        switch (id) {
            case 1:     // brick wall
            case 5:     // stone
            case 15:    // barrier not accessible
            case 20:    // starry sky
                result = false;
                break;
            default:
                break;
        }
        //LogUtil.d(TAG, "canInteraction() id = " + id + ", x = " + x + ", y = " + y + ", result = " + result);
        return result;
    }
    
    private Handler handler = new PlayHandler(new WeakReference<ScenePlay>(this));
    
    private static final int MSG_ID_AUTO_STEP = 1;
    
    private static final int MSG_DELAY_AUTO_STEP = 50;
    
    private static final class PlayHandler extends Handler {
        private WeakReference<ScenePlay> wk;

        public PlayHandler(WeakReference<ScenePlay> wk) {
            super();
            this.wk = wk;
        }
        
        @Override
        public void handleMessage(Message msg) {
            ScenePlay scenePlay = wk.get();
            if (msg.what == MSG_ID_AUTO_STEP && scenePlay != null) {
                scenePlay.autoStep();
            }
            super.handleMessage(msg);
        }
    };
}