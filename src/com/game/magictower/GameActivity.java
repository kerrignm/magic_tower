package com.game.magictower;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.media.AudioManager;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.WindowManager;

import com.game.magictower.Game.Status;
import com.game.magictower.model.NpcInfo;
import com.game.magictower.res.Assets;
import com.game.magictower.res.GameGraphics;
import com.game.magictower.res.GlobalSoundPool;
import com.game.magictower.res.LiveBitmap;
import com.game.magictower.res.TowerDimen;
import com.game.magictower.util.LogUtil;
import com.game.magictower.widget.BaseButton;
import com.game.magictower.widget.BaseView;
import com.game.magictower.widget.BaseView.onClickListener;
import com.game.magictower.widget.BitmapButton;
import com.game.magictower.widget.GameScreen;
import com.game.magictower.widget.GameView;
import com.game.magictower.widget.RockerView;
import com.game.magictower.widget.TextButton;

public class GameActivity extends Activity implements GameScreen, GameControler {
    
    private static final String TAG = "MagicTower:GameActivity";
    
    private Assets assets;
    private GameGraphics graphics;
    
    private Game currentGame;
    private HashMap<Integer, LiveBitmap> imageMap;
    
    private SceneDialog dialog;
    private SceneForecast forecast;
    private SceneBattle battle;
    private SceneJump jumpFloor;
    private SceneShop shop;
    private SceneMessage message;
    
    private int musicId = -1;
    private int streamId = -1;
    
    private boolean animFlag = true;
    
    protected static Intent getIntent(Context context, boolean load){
        Intent intent = new Intent(context, GameActivity.class);
        intent.putExtra("load", load);
        return intent;
    }
    
    private Timer timer = new Timer();
    private TimerTask timerTask = new TimerTask() {
        @Override
        public void run() {
            if (animFlag) {
                imageMap = assets.animMap0;
            } else {
                imageMap = assets.animMap1;
            }
            animFlag = !animFlag;
        }
    };
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtil.d(TAG, "onCreate()");
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        graphics = GameGraphics.getInstance();
        assets = Assets.getInstance();
        setContentView(new GameView(this, graphics, this));
        createButtons();
        setVolumeControlStream(AudioManager.STREAM_MUSIC);
        Game.init(this, this);
        currentGame = Game.getInstance();
        message = new SceneMessage(this, currentGame);
        dialog = new SceneDialog(this, currentGame);
        forecast = new SceneForecast(this, currentGame);
        battle = new SceneBattle(this, currentGame);
        jumpFloor = new SceneJump(this, currentGame);
        shop = new SceneShop(this, currentGame);
        currentGame.dialog = dialog;
        currentGame.message = message;
        currentGame.newGame();
        if (getIntent().getBooleanExtra("load", false)) {
            currentGame.loadGame();
            playBackgroundMusic(getMuisicId(currentGame.npcInfo.curFloor));
        } else {
            message.show(1, null, null, SceneMessage.MODE_AUTO_SCROLL);
            playBackgroundMusic(getMuisicId(-1));
        }
        imageMap = assets.animMap0;
        timer.schedule(timerTask, 1000, 500);
    }
    
    @Override
    protected void onPause() {
        super.onPause();
        if (Settings.isVoiceEnabled()) {
            pauseBackgroundMusic();
        }
        LogUtil.d(TAG, "onPause()");
    }
    
    @Override
    protected void onResume() {
        super.onResume();
        if (Settings.isVoiceEnabled()) {
            resumeBackgroundMusic();
        }
        LogUtil.d(TAG, "onResume()");
    }
    
    @Override
    protected void onDestroy() {
        super.onDestroy();
        LogUtil.d(TAG, "onDestroy()");
        if (Settings.isVoiceEnabled()) {
            stopBackgroundMusic();
        }
        Game.destroy();
        timer.cancel();
    }
    
    private void playBackgroundMusic(int id) {
        if (id != musicId) {
            musicId = id;
            streamId = GlobalSoundPool.getInstance().playSound(id, -1);
        }
    }
    
    private void pauseBackgroundMusic() {
        if (streamId > 0) {
            GlobalSoundPool.getInstance().pauseSound(streamId);
        }
    }
    
    private void resumeBackgroundMusic() {
        if (streamId > 0) {
            GlobalSoundPool.getInstance().resumeSound(streamId);
        }
    }
    
    private void stopBackgroundMusic() {
        if (streamId > 0) {
            GlobalSoundPool.getInstance().stopSound(streamId);
        }
    }
    
    private void changeBackgroundMusic(int floor) {
        int id = getMuisicId(floor);
        if (id != musicId) {
            stopBackgroundMusic();
            playBackgroundMusic(id);
        }
    }
    
    private int getMuisicId(int floor) {
        int id = 0;
        if (floor == 0) {
            id = Assets.SND_ID_BGM_2;
        } else if ((floor >= 1) && (floor <= 7)) {
            id = Assets.SND_ID_BGM_3;
        } else if ((floor >= 8) && (floor <= 14)) {
            id = Assets.SND_ID_BGM_4;
        } else if ((floor >= 15) && (floor <= 18)) {
            id = Assets.SND_ID_BGM_5;
        } else {
            id = Assets.SND_ID_BGM_1;
        }
        id = Assets.getInstance().getSoundId(id);
        return id;
    }
    
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (BaseView.getFocusView() == null) {
            if ((ev.getAction() & MotionEvent.ACTION_MASK) != MotionEvent.ACTION_MOVE) {
                for (BaseView view: activeViews){
                    if (view.onTouch(ev)){
                        break;
                    }
                }
            }
        } else {
            BaseView.getFocusView().onTouch(ev);
        }
        return super.dispatchTouchEvent(ev);
    }
    
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK) {
            return true;
        }
        return false;
    }
    
    public void changeMusic() {
        changeBackgroundMusic(currentGame.npcInfo.curFloor);
    }
    
    public void gameOver() {
        startActivity(new Intent(this, LoadingActivity.class));
        finish();
    }
    
    private ArrayList<BaseView> activeViews = new ArrayList<BaseView>();
    private onClickListener clickListener = new onClickListener() {
        @Override
        public void onClicked(int id) {
            switch (currentGame.status) {
            case Playing:
                playBtnKey(id);
                break;
            case Fighting:
                break;
            case Shopping:
                shop.onBtnKey(id);
                break;
            case Messaging:
                message.onBtnKey(id);
                break;
            case Dialoguing:
                dialog.onBtnKey(id);
                break;
            case Looking:
                forecast.onBtnKey(id);
                break;
            case Jumping:
                jumpFloor.onBtnKey(id);
                break;
            }
        }
        
        private void playBtnKey(int btnId){
            switch (btnId) {
            case BaseButton.ID_UP:
                if (currentGame.status == Status.Playing) {
                    currentGame.checkTest(btnId);
                    if (currentGame.player.getPosY() - 1 < 11 && currentGame.player.getPosY() - 1 >= 0) {
                        currentGame.player.setToward(3);
                        interaction(currentGame.player.getPosX(), currentGame.player.getPosY() - 1);
                    }
                }
                break;
            case BaseButton.ID_LEFT:
                if (currentGame.status == Status.Playing) {
                    currentGame.checkTest(btnId);
                    if (currentGame.player.getPosX() - 1 < 11 && currentGame.player.getPosX() - 1 >= 0) {
                        currentGame.player.setToward(0);
                        interaction(currentGame.player.getPosX() - 1, currentGame.player.getPosY());
                    }
                }
                break;
            case BaseButton.ID_RIGHT:
                if (currentGame.status == Status.Playing) {
                    currentGame.checkTest(btnId);
                    if (currentGame.player.getPosX() + 1 < 11 && currentGame.player.getPosX() + 1 >= 0) {
                        currentGame.player.setToward(2);
                        interaction(currentGame.player.getPosX() + 1, currentGame.player.getPosY());
                    }
                }
                break;
            case BaseButton.ID_DOWN:
                if (currentGame.status == Status.Playing) {
                    currentGame.checkTest(btnId);
                    if (currentGame.player.getPosY() + 1 < 11 && currentGame.player.getPosY() + 1 >= 0) {
                        currentGame.player.setToward(1);
                        interaction(currentGame.player.getPosX(), currentGame.player.getPosY() + 1);
                    }
                }
                break;
            case BaseButton.ID_QUIT:
                finish();
                break;
            case BaseButton.ID_NEW:
                currentGame.newGame();
                message.show(R.string.msg_restart);
                GlobalSoundPool.getInstance().playSound(Assets.getInstance().getSoundId(Assets.SND_ID_RECORD));
                changeBackgroundMusic(currentGame.npcInfo.curFloor);
                break;
            case BaseButton.ID_SAVE:
                if (currentGame.saveGame()) {
                    message.show(R.string.save_succeed);
                } else {
                    message.show(R.string.save_failed);
                }
                GlobalSoundPool.getInstance().playSound(Assets.getInstance().getSoundId(Assets.SND_ID_RECORD));
                break;
            case BaseButton.ID_READ:
                if (currentGame.loadGame()) {
                    message.show(R.string.read_succeed);
                } else {
                    message.show(R.string.read_failed);
                }
                GlobalSoundPool.getInstance().playSound(Assets.getInstance().getSoundId(Assets.SND_ID_RECORD));
                changeBackgroundMusic(currentGame.npcInfo.curFloor);
                break;
            case BaseButton.ID_LOOK:
                if (currentGame.npcInfo.isHasForecast) {
                    forecast.show();
                }
                break;
            case BaseButton.ID_JUMP:
                if (currentGame.npcInfo.isHasJump) {
                    jumpFloor.show();
                }
                break;
            case BaseButton.ID_OK:
                break;
            }
        }
    };
    
    private void createButtons() {
        activeViews.add(BitmapButton.create(graphics, BaseButton.ID_UP, assets.upBtn, true));
        activeViews.add(BitmapButton.create(graphics, BaseButton.ID_LEFT, assets.leftBtn, true));
        activeViews.add(BitmapButton.create(graphics, BaseButton.ID_RIGHT, assets.rightBtn, true));
        activeViews.add(BitmapButton.create(graphics, BaseButton.ID_DOWN, assets.downBtn, true));
        activeViews.add(TextButton.create(graphics, BaseButton.ID_QUIT, getResources().getString(R.string.btn_quit), false));
        activeViews.add(TextButton.create(graphics, BaseButton.ID_NEW, getResources().getString(R.string.btn_new), false));
        activeViews.add(TextButton.create(graphics, BaseButton.ID_SAVE, getResources().getString(R.string.btn_save), false));
        activeViews.add(TextButton.create(graphics, BaseButton.ID_READ, getResources().getString(R.string.btn_load), false));
        activeViews.add(TextButton.create(graphics, BaseButton.ID_LOOK, getResources().getString(R.string.btn_look), false));
        activeViews.add(TextButton.create(graphics, BaseButton.ID_JUMP, getResources().getString(R.string.btn_jump), false));
        activeViews.add(TextButton.create(graphics, BaseButton.ID_OK, getResources().getString(R.string.btn_ok), false));
        activeViews.add(new RockerView(graphics, BaseButton.ID_OK, TowerDimen.R_ROCKER.left, TowerDimen.R_ROCKER.top, TowerDimen.R_ROCKER.width(), TowerDimen.R_ROCKER.height()));
        for (BaseView view: activeViews){
            view.setOnClickListener(clickListener);
        }
    }
    
    private void drawActiveButtons(GameGraphics graphics, Canvas canvas){
        for (BaseView view: activeViews){
            view.onPaint(canvas);
        }
    }
    
    @Override
    public void updateUI(GameGraphics graphics, Canvas canvas) {
        if (currentGame == null) {
            LogUtil.w(TAG, "updateUI called before game instance created.");
            return ;
        }
        drawActiveButtons(graphics, canvas);
        drawInfoPanel(graphics, canvas);
        drawTower(graphics, canvas);
        
        switch(currentGame.status) {
        case Playing:
            break;
        case Fighting:
            battle.draw(graphics, canvas);
            break;
        case Shopping:
            shop.draw(graphics, canvas);
            break;
        case Messaging:
            message.draw(graphics, canvas);
            break;
        case Dialoguing:
            dialog.draw(graphics, canvas);
            break;
        case Looking:
            forecast.draw(graphics, canvas);
            break;
        case Jumping:
            jumpFloor.draw(graphics, canvas);
            break;
        }
     }
    
    private void drawInfoPanel(GameGraphics graphics, Canvas canvas) {
        graphics.drawTextInCenter(canvas, currentGame.player.getLevel() + "", TowerDimen.R_PLR_LEVEL);
        graphics.drawTextInCenter(canvas, currentGame.player.getHp() + "", TowerDimen.R_PLR_HP);
        graphics.drawTextInCenter(canvas, currentGame.player.getAttack() + "", TowerDimen.R_PLR_ATTACK);
        graphics.drawTextInCenter(canvas, currentGame.player.getDefend() + "", TowerDimen.R_PLR_DEFEND);
        graphics.drawTextInCenter(canvas, currentGame.player.getMoney() + "", TowerDimen.R_PLR_MONEY);
        graphics.drawTextInCenter(canvas, currentGame.player.getExp() + "", TowerDimen.R_PLR_EXP);

        graphics.drawTextInCenter(canvas, currentGame.player.getYkey() + "", TowerDimen.R_YKEY);
        graphics.drawTextInCenter(canvas, currentGame.player.getBkey() + "", TowerDimen.R_BKEY);
        graphics.drawTextInCenter(canvas, currentGame.player.getRkey() + "", TowerDimen.R_RKEY);
        
        graphics.drawTextInCenter(canvas, currentGame.npcInfo.curFloor + "", TowerDimen.R_FLOOR);
    }
    
    private void drawTower(GameGraphics graphics, Canvas canvas) {
        for (int x = 0; x < 11; x++) {
            for (int y = 0; y < 11; y++) {
                int id = currentGame.lvMap[currentGame.npcInfo.curFloor][x][y];
                if (id >= 100) {
                    id = 0;   //monitor items invisible, draw ground
                }
                LiveBitmap bitmap = imageMap.get(id);
                graphics.drawBitmap(canvas, bitmap, TowerDimen.TOWER_LEFT + (y + 6) * TowerDimen.TOWER_GRID_SIZE, TowerDimen.TOWER_TOP + (x + 1) * TowerDimen.TOWER_GRID_SIZE);
            }
        }

        graphics.drawBitmap(canvas, currentGame.player.getImage(), TowerDimen.TOWER_LEFT + (currentGame.player.getPosX() + 6) * TowerDimen.TOWER_GRID_SIZE, TowerDimen.TOWER_TOP + (currentGame.player.getPosY() + 1) * TowerDimen.TOWER_GRID_SIZE);
    }
    
    public void interaction(int x, int y) {
        int id = currentGame.lvMap[currentGame.npcInfo.curFloor][y][x];
        switch (id) {
            case 0:     // currentGame.player move
                currentGame.player.move(x, y);
                GlobalSoundPool.getInstance().playSound(Assets.getInstance().getSoundId(Assets.SND_ID_STEP));
                break;
            case 1:     // brick wall
                break;
            case 2:     // yellow door
                if (currentGame.player.getYkey() > 0) {
                    GlobalSoundPool.getInstance().playSound(Assets.getInstance().getSoundId(Assets.SND_ID_ZONE));
                    currentGame.lvMap[currentGame.npcInfo.curFloor][y][x] = 0;
                    currentGame.player.setYkey(currentGame.player.getYkey() - 1);
                }
                break;
            case 3:     // blue door
                if (currentGame.player.getBkey() > 0) {
                    GlobalSoundPool.getInstance().playSound(Assets.getInstance().getSoundId(Assets.SND_ID_ZONE));
                    currentGame.lvMap[currentGame.npcInfo.curFloor][y][x] = 0;
                    currentGame.player.setBkey(currentGame.player.getBkey() - 1);
                }
                break;
            case 4:     // red door
                if (currentGame.player.getRkey() > 0) {
                    GlobalSoundPool.getInstance().playSound(Assets.getInstance().getSoundId(Assets.SND_ID_ZONE));
                    currentGame.lvMap[currentGame.npcInfo.curFloor][y][x] = 0;
                    currentGame.player.setRkey(currentGame.player.getRkey() - 1);
                }
                break;
            case 5:     // stone
                break;
            case 6:     // yellow hey
                GlobalSoundPool.getInstance().playSound(Assets.getInstance().getSoundId(Assets.SND_ID_ITEM));
                currentGame.lvMap[currentGame.npcInfo.curFloor][y][x] = 0;
                currentGame.player.setYkey(currentGame.player.getYkey() + 1);
                message.show(R.string.get_yellow_key);
                break;
            case 7:     // blue key
                GlobalSoundPool.getInstance().playSound(Assets.getInstance().getSoundId(Assets.SND_ID_ITEM));
                currentGame.lvMap[currentGame.npcInfo.curFloor][y][x] = 0;
                currentGame.player.setBkey(currentGame.player.getBkey() + 1);
                message.show(R.string.get_bule_key);
                break;
            case 8:     // red key
                GlobalSoundPool.getInstance().playSound(Assets.getInstance().getSoundId(Assets.SND_ID_ITEM));
                currentGame.lvMap[currentGame.npcInfo.curFloor][y][x] = 0;
                currentGame.player.setRkey(currentGame.player.getRkey() + 1);
                message.show(R.string.get_red_key);
                break;
            case 9:     // sapphire
                GlobalSoundPool.getInstance().playSound(Assets.getInstance().getSoundId(Assets.SND_ID_ITEM));
                currentGame.lvMap[currentGame.npcInfo.curFloor][y][x] = 0;
                currentGame.player.setDefend(currentGame.player.getDefend() + 3);
                message.show(R.string.get_sapphire);
                break;
            case 10:    // ruby
                GlobalSoundPool.getInstance().playSound(Assets.getInstance().getSoundId(Assets.SND_ID_ITEM));
                currentGame.lvMap[currentGame.npcInfo.curFloor][y][x] = 0;
                currentGame.player.setAttack(currentGame.player.getAttack() + 3);
                message.show(R.string.get_ruby);
                break;
            case 11:    // red potion
                GlobalSoundPool.getInstance().playSound(Assets.getInstance().getSoundId(Assets.SND_ID_ITEM));
                currentGame.lvMap[currentGame.npcInfo.curFloor][y][x] = 0;
                currentGame.player.setHp(currentGame.player.getHp() + 200);
                message.show(R.string.get_red_potion);
                break;
            case 12:    // blue potion
                GlobalSoundPool.getInstance().playSound(Assets.getInstance().getSoundId(Assets.SND_ID_ITEM));
                currentGame.lvMap[currentGame.npcInfo.curFloor][y][x] = 0;
                currentGame.player.setHp(currentGame.player.getHp() + 500);
                message.show(R.string.get_blue_potion);
                break;
            case 13:    // upstairs
                message.show(R.string.msg_upstairs);
                GlobalSoundPool.getInstance().playSound(Assets.getInstance().getSoundId(Assets.SND_ID_ZONE));
                currentGame.npcInfo.curFloor++;
                changeBackgroundMusic(currentGame.npcInfo.curFloor);
                currentGame.npcInfo.maxFloor = Math.max(currentGame.npcInfo.maxFloor, currentGame.npcInfo.curFloor);
                currentGame.player.move(currentGame.tower.initPos[currentGame.npcInfo.curFloor][0], currentGame.tower.initPos[currentGame.npcInfo.curFloor][1]);
                if (currentGame.npcInfo.curFloor == 21) {
                    currentGame.npcInfo.isHasJump = false;
                }
                break;
            case 14:    // downstairs
                message.show(R.string.msg_downstairs);
                GlobalSoundPool.getInstance().playSound(Assets.getInstance().getSoundId(Assets.SND_ID_ZONE));
                currentGame.npcInfo.curFloor--;
                changeBackgroundMusic(currentGame.npcInfo.curFloor);
                currentGame.player.move(currentGame.tower.finPos[currentGame.npcInfo.curFloor][0], currentGame.tower.finPos[currentGame.npcInfo.curFloor][1]);
                break;
            case 15:    // barrier not accessible
                break;
            case 16:   // accessible guardrail
                GlobalSoundPool.getInstance().playSound(Assets.getInstance().getSoundId(Assets.SND_ID_ZONE));
                currentGame.lvMap[currentGame.npcInfo.curFloor][y][x] = 0;
                break;
            case 19:    // sea of fire
                break;
            case 20:    // starry sky
                break;
            case 22:    // shop
                if (currentGame.npcInfo.curFloor == 3) {
                    shop.show(0, id);
                } else if (currentGame.npcInfo.curFloor == 11) {
                    shop.show(3, id);
                }
                break;
            case 24:    // fairy
                switch (currentGame.npcInfo.mFairyStatus) {
                case NpcInfo.FAIRY_STATUS_WAIT_PLAYER:
                    dialog.show(0, id);
                    break;
                case NpcInfo.FAIRY_STATUS_WAIT_CROSS:
                    if (currentGame.npcInfo.isHasCross) {
                        dialog.show(1, id);
                    }
                    break;
                }
                break;
            case 25:    // thief
                switch (currentGame.npcInfo.mThiefStatus) {
                case NpcInfo.THIEF_STATUS_WAIT_PLAYER:
                    dialog.show(8, id);
                    break;
                case NpcInfo.THIEF_STATUS_WAIT_HAMMER:
                    dialog.show(11, id);
                    break;
                }
                break;
            case 26:    // old man
                if (currentGame.npcInfo.curFloor == 2) {
                    dialog.show(4, id);
                } else if (currentGame.npcInfo.curFloor == 5) {
                    shop.show(1, id);
                } else if (currentGame.npcInfo.curFloor == 13) {
                    shop.show(5, id);
                } else if (currentGame.npcInfo.curFloor == 15) {
                    if (currentGame.player.getExp() >= 500) {
                        dialog.show(3, id);
                    } else {
                        dialog.show(2, id);
                    }
                }
                break;
            case 27:    // businessman
                if (currentGame.npcInfo.curFloor == 2) {
                    dialog.show(5, id);
                } else if (currentGame.npcInfo.curFloor == 5) {
                    shop.show(2, id);
                } else if (currentGame.npcInfo.curFloor == 12) {
                    shop.show(4, id);
                } else if (currentGame.npcInfo.curFloor == 15) {
                    if (currentGame.player.getMoney() >= 500) {
                        dialog.show(7, id);
                    } else {
                        dialog.show(6, id);
                    }
                }
                break;
            case 28:    // princess
                if (currentGame.npcInfo.mPrincessStatus == NpcInfo.PRINCESS_STATUS_WAIT_PLAYER) {
                    dialog.show(9, id);
                }
                break;
            case 30:    // little flying feather
                GlobalSoundPool.getInstance().playSound(Assets.getInstance().getSoundId(Assets.SND_ID_ITEM));
                currentGame.lvMap[currentGame.npcInfo.curFloor][y][x] = 0;
                currentGame.player.setLevel(currentGame.player.getLevel() + 1);
                currentGame.player.setHp(currentGame.player.getHp() + 1000);
                currentGame.player.setAttack(currentGame.player.getAttack() + 10);
                currentGame.player.setDefend(currentGame.player.getDefend() + 10);
                message.show(R.string.get_little_fly);
                break;
            case 31:    // big flying feather
                GlobalSoundPool.getInstance().playSound(Assets.getInstance().getSoundId(Assets.SND_ID_ITEM));
                currentGame.lvMap[currentGame.npcInfo.curFloor][y][x] = 0;
                currentGame.player.setLevel(currentGame.player.getLevel() + 3);
                currentGame.player.setHp(currentGame.player.getHp() + 3000);
                currentGame.player.setAttack(currentGame.player.getAttack() + 30);
                currentGame.player.setDefend(currentGame.player.getDefend() + 30);
                message.show(R.string.get_big_fly);
                break;
            case 32:    // cross
                GlobalSoundPool.getInstance().playSound(Assets.getInstance().getSoundId(Assets.SND_ID_WONDER));
                currentGame.lvMap[currentGame.npcInfo.curFloor][y][x] = 0;
                currentGame.npcInfo.isHasCross = true;
                message.show(R.string.treasure_cross, R.string.treasure_cross_info, SceneMessage.MODE_ALERT);
                break;
            case 33:    // holy water bottle
                GlobalSoundPool.getInstance().playSound(Assets.getInstance().getSoundId(Assets.SND_ID_WONDER));
                currentGame.lvMap[currentGame.npcInfo.curFloor][y][x] = 0;
                message.show(R.string.treasure_holy_water, R.string.treasure_holy_water_info, SceneMessage.MODE_ALERT);
                currentGame.player.setHp(currentGame.player.getHp() * 2);
                break;
            case 34:    // emblem of light
                GlobalSoundPool.getInstance().playSound(Assets.getInstance().getSoundId(Assets.SND_ID_WONDER));
                currentGame.lvMap[currentGame.npcInfo.curFloor][y][x] = 0;
                currentGame.npcInfo.isHasForecast = true;
                message.show(R.string.treasure_emblem_light, R.string.treasure_emblem_light_info, SceneMessage.MODE_ALERT);
                break;
            case 35:    // compass of wind
                GlobalSoundPool.getInstance().playSound(Assets.getInstance().getSoundId(Assets.SND_ID_WONDER));
                currentGame.lvMap[currentGame.npcInfo.curFloor][y][x] = 0;
                currentGame.npcInfo.isHasJump = true;
                message.show(R.string.treasure_compass, R.string.treasure_compass_info, SceneMessage.MODE_ALERT);
                break;
            case 36:    // key box
                GlobalSoundPool.getInstance().playSound(Assets.getInstance().getSoundId(Assets.SND_ID_ITEM));
                currentGame.lvMap[currentGame.npcInfo.curFloor][y][x] = 0;
                currentGame.player.setYkey(currentGame.player.getYkey() + 1);
                currentGame.player.setBkey(currentGame.player.getBkey() + 1);
                currentGame.player.setRkey(currentGame.player.getRkey() + 1);
                message.show(R.string.get_key_box);
                break;
            case 38:    // hammer
                GlobalSoundPool.getInstance().playSound(Assets.getInstance().getSoundId(Assets.SND_ID_WONDER));
                currentGame.lvMap[currentGame.npcInfo.curFloor][y][x] = 0;
                currentGame.npcInfo.isHasHammer = true;
                message.show(R.string.treasure_hammer, R.string.treasure_hammer_info, SceneMessage.MODE_ALERT);
                break;
            case 39:    // gold nugget
                GlobalSoundPool.getInstance().playSound(Assets.getInstance().getSoundId(Assets.SND_ID_ITEM));
                currentGame.lvMap[currentGame.npcInfo.curFloor][y][x] = 0;
                currentGame.player.setMoney(currentGame.player.getMoney() + 300);
                message.show(R.string.get_gold_block);
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
                if (SceneForecast.forecast(currentGame.player, currentGame.monsters.get(id)).equals("???")
                        || Integer.parseInt(SceneForecast.forecast(currentGame.player, currentGame.monsters.get(id))) >= currentGame.player.getHp()) {
                    return;
                } else {
                    battle.show(id, x, y);
                }
                break;
            case 71:    // iron sword
                GlobalSoundPool.getInstance().playSound(Assets.getInstance().getSoundId(Assets.SND_ID_ITEM));
                currentGame.lvMap[currentGame.npcInfo.curFloor][y][x] = 0;
                currentGame.player.setAttack(currentGame.player.getAttack() + 10);
                message.show(R.string.get_iron_shield);
                break;
            case 73:    // steel sword
                GlobalSoundPool.getInstance().playSound(Assets.getInstance().getSoundId(Assets.SND_ID_ITEM));
                currentGame.lvMap[currentGame.npcInfo.curFloor][y][x] = 0;
                currentGame.player.setAttack(currentGame.player.getAttack() + 70);
                message.show(R.string.get_steel_sword);
                break;
            case 75:    // sword of light
                GlobalSoundPool.getInstance().playSound(Assets.getInstance().getSoundId(Assets.SND_ID_ITEM));
                currentGame.lvMap[currentGame.npcInfo.curFloor][y][x] = 0;
                currentGame.player.setAttack(currentGame.player.getAttack() + 150);
                message.show(R.string.get_light_sword);
                break;
            case 76:    // iron shield
                GlobalSoundPool.getInstance().playSound(Assets.getInstance().getSoundId(Assets.SND_ID_ITEM));
                currentGame.lvMap[currentGame.npcInfo.curFloor][y][x] = 0;
                currentGame.player.setDefend(currentGame.player.getDefend() + 10);
                message.show(R.string.get_iron_shield);
                break;
            case 78:    // gold shield
                GlobalSoundPool.getInstance().playSound(Assets.getInstance().getSoundId(Assets.SND_ID_ITEM));
                currentGame.lvMap[currentGame.npcInfo.curFloor][y][x] = 0;
                currentGame.player.setDefend(currentGame.player.getDefend() + 85);
                message.show(R.string.get_gold_shield);
                break;
            case 80:    // light shield
                GlobalSoundPool.getInstance().playSound(Assets.getInstance().getSoundId(Assets.SND_ID_ITEM));
                currentGame.lvMap[currentGame.npcInfo.curFloor][y][x] = 0;
                currentGame.player.setDefend(currentGame.player.getDefend() + 190);
                message.show(R.string.get_light_shield);
                break;
            case 101:
                currentGame.lvMap[currentGame.npcInfo.curFloor][y][x] = 0;
                dialog.show(10, 53);
                break;
            case 102:
                currentGame.lvMap[currentGame.npcInfo.curFloor][y][x] = 0;
                dialog.show(12, 59);
                break;
        }
    }
}
