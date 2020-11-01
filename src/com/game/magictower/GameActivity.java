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
import com.game.magictower.res.Assets;
import com.game.magictower.res.GameGraphics;
import com.game.magictower.res.GlobalSoundPool;
import com.game.magictower.res.LiveBitmap;
import com.game.magictower.res.MonsterData;
import com.game.magictower.res.TowerDimen;
import com.game.magictower.res.TowerMap;
import com.game.magictower.util.FileUtil;
import com.game.magictower.util.LogUtil;
import com.game.magictower.widget.BitmapButton;
import com.game.magictower.widget.BitmapButton.onClickListener;
import com.game.magictower.widget.GameScreen;
import com.game.magictower.widget.GameView;

public class GameActivity extends Activity implements GameScreen {
    
    private static final String TAG = "MagicTower:GameActivity";
    
    private Assets assets;
    private GameGraphics graphics;
    private GlobalSoundPool soundPool;
    
    private Game currentGame;
    private Player player;
    private HashMap<Integer, LiveBitmap> imageMap;
    
    private SceneDialog dialog;
    private SceneForecast forecast;
    private SceneBattle battle;
    private SceneJump jumpFloor;
    private SceneShop shop;
    private SceneMessage message;
    
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
        soundPool = GlobalSoundPool.getInstance(this);
        assets = Assets.getInstance();
        setContentView(new GameView(this, graphics, this));
        createButtons();
        setVolumeControlStream(AudioManager.STREAM_MUSIC);
        currentGame = Game.getInstance();
        player = currentGame.player;
        message = new SceneMessage(this, currentGame);
        dialog = new SceneDialog(this, currentGame);
        forecast = new SceneForecast(this, currentGame);
        battle = new SceneBattle(this, currentGame);
        jumpFloor = new SceneJump(this, currentGame);
        shop = new SceneShop(this, currentGame);
        currentGame.dialog = dialog;
        currentGame.message = message;
        resetGame();
        if (getIntent().getBooleanExtra("load", false)) {
            loadGame();
        }
        imageMap = assets.animMap0;
        timer.schedule(timerTask, 1000, 500);
    }
    
    @Override
    protected void onPause() {
        super.onPause();
        LogUtil.d(TAG, "onPause()");
    }
    
    @Override
    protected void onResume() {
        super.onResume();
        LogUtil.d(TAG, "onResume()");
    }
    
    @Override
    protected void onDestroy() {
        super.onDestroy();
        LogUtil.d(TAG, "onDestroy()");
        timer.cancel();
    }
    
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        for (BitmapButton button: activeButtons){
            if (button.onTouch(ev)){
                break;
            }
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
    
    private void resetGame() {
        currentGame.newGame();
    }
    
    private boolean loadGame() {
        String str = FileUtil.read(this, "game");
        if (str == null) {
            resetGame();
            return false;
        }
        currentGame.fromString(str);
        str = FileUtil.read(this, "player");
        if (str == null) {
            resetGame();
            return false;
        }
        currentGame.player.fromString(str);
        str = FileUtil.read(this, "npc");
        if (str == null) {
            resetGame();
            return false;
        }
        currentGame.npcInfo.fromString(str);
        str = FileUtil.read(this, "tower");
        if (str == null) {
            resetGame();
            return false;
        }
        currentGame.mapFromString(str);
        return true;
    }
    
    private boolean saveGame() {
        if (!FileUtil.write(this, "game", currentGame.toString())) {
            return false;
        }
        if (!FileUtil.write(this, "player", currentGame.player.toString())) {
            return false;
        }
        if (!FileUtil.write(this, "npc", currentGame.npcInfo.toString())) {
            return false;
        }
        if (!FileUtil.write(this, "tower", currentGame.mapToString())) {
            return false;
        }
        return true;
    }
    
    private ArrayList<BitmapButton> activeButtons = new ArrayList<BitmapButton>();
    private onClickListener btnClickListener = new onClickListener() {
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
            case BitmapButton.ID_UP:
                if (currentGame.status == Status.Playing) {
                    if (player.getPosY() - 1 < 11 && player.getPosY() - 1 >= 0) {
                        player.setToward(3);
                        interaction(player.getPosX(), player.getPosY() - 1);
                    }
                }
                break;
            case BitmapButton.ID_LEFT:
                if (currentGame.status == Status.Playing) {
                    if (player.getPosX() - 1 < 11 && player.getPosX() - 1 >= 0) {
                        player.setToward(0);
                        interaction(player.getPosX() - 1, player.getPosY());
                    }
                }
                break;
            case BitmapButton.ID_RIGHT:
                if (currentGame.status == Status.Playing) {
                    if (player.getPosX() + 1 < 11 && player.getPosX() + 1 >= 0) {
                        player.setToward(2);
                        interaction(player.getPosX() + 1, player.getPosY());
                    }
                }
                break;
            case BitmapButton.ID_DOWN:
                if (currentGame.status == Status.Playing) {
                    if (player.getPosY() + 1 < 11 && player.getPosY() + 1 >= 0) {
                        player.setToward(1);
                        interaction(player.getPosX(), player.getPosY() + 1);
                    }
                }
                break;
            case BitmapButton.ID_QUIT:
                finish();
                break;
            case BitmapButton.ID_NEW:
                resetGame();
                message.show(R.string.msg_restart);
                break;
            case BitmapButton.ID_SAVE:
                if (saveGame()) {
                    message.show(R.string.save_succeed);
                } else {
                    message.show(R.string.save_failed);
                }
                break;
            case BitmapButton.ID_READ:
                if (loadGame()) {
                    message.show(R.string.read_succeed);
                } else {
                    message.show(R.string.read_failed);
                }
                break;
            case BitmapButton.ID_LOOK:
                if (currentGame.isHasForecast) {
                    forecast.show();
                }
                break;
            case BitmapButton.ID_JUMP:
                if (currentGame.isHasJump) {
                    jumpFloor.show();
                }
                break;
            case BitmapButton.ID_OK:
                break;
            }
        }
    };
    
    private void createButtons() {
        activeButtons.add(BitmapButton.create(graphics, BitmapButton.ID_UP, "▲", true));
        activeButtons.add(BitmapButton.create(graphics, BitmapButton.ID_LEFT, "◀", true));
        activeButtons.add(BitmapButton.create(graphics, BitmapButton.ID_RIGHT, "▶", true));
        activeButtons.add(BitmapButton.create(graphics, BitmapButton.ID_DOWN, "▼", true));
        activeButtons.add(BitmapButton.create(graphics, BitmapButton.ID_QUIT, "Quit", false));
        activeButtons.add(BitmapButton.create(graphics, BitmapButton.ID_NEW, "Restart", false));
        activeButtons.add(BitmapButton.create(graphics, BitmapButton.ID_SAVE, "Save", false));
        activeButtons.add(BitmapButton.create(graphics, BitmapButton.ID_READ, "Read", false));
        activeButtons.add(BitmapButton.create(graphics, BitmapButton.ID_LOOK, "Look", false));
        activeButtons.add(BitmapButton.create(graphics, BitmapButton.ID_JUMP, "Jump", false));
        activeButtons.add(BitmapButton.create(graphics, BitmapButton.ID_OK, "OK", false));
        for (BitmapButton button: activeButtons){
            button.setOnClickListener(btnClickListener);
        }
    }
    
    private void drawActiveButtons(GameGraphics graphics, Canvas canvas){
        for (BitmapButton button: activeButtons){
            button.onPaint(canvas);
        }
    }
    
    @Override
    public void updateUI(GameGraphics graphics, Canvas canvas) {
        if (currentGame==null){
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
        graphics.drawText(canvas, player.getLevel() + "", TowerDimen.LEVEL_LEFT, TowerDimen.LEVEL_TOP);
        graphics.drawText(canvas, player.getHp() + "", TowerDimen.HP_LEFT, TowerDimen.HP_TOP);
        graphics.drawText(canvas, player.getAttack() + "", TowerDimen.ATTACK_LEFT, TowerDimen.ATTACK_TOP);
        graphics.drawText(canvas, player.getDefend() + "", TowerDimen.DEFEND_LEFT, TowerDimen.DEFEND_TOP);
        graphics.drawText(canvas, player.getMoney() + "", TowerDimen.MONEY_LEFT, TowerDimen.MONEY_TOP);
        graphics.drawText(canvas, player.getExp() + "", TowerDimen.EXP_LEFT, TowerDimen.EXP_TOP);

        graphics.drawText(canvas, player.getYkey() + "", TowerDimen.YKEY_LEFT, TowerDimen.YKEY_TOP);
        graphics.drawText(canvas, player.getBkey() + "", TowerDimen.BKEY_LEFT, TowerDimen.BKEY_TOP);
        graphics.drawText(canvas, player.getRkey() + "", TowerDimen.RKEY_LEFT, TowerDimen.RKEY_TOP);
        
        graphics.drawText(canvas, currentGame.currentFloor + "", TowerDimen.FLOOR_LEFT, TowerDimen.FLOOR_TOP);
    }
    
    private void drawTower(GameGraphics graphics, Canvas canvas) {
        for (int x = 0; x < 11; x++) {
            for (int y = 0; y < 11; y++) {
                int id = currentGame.lvMap[currentGame.currentFloor][x][y];
                if (id >= 100) {
                    id = 0;   //monitor items invisible, draw ground
                }
                LiveBitmap bitmap = imageMap.get(id);
                graphics.drawBitmap(canvas, bitmap, TowerDimen.TOWER_LEFT + (y + 6) * TowerDimen.TOWER_GRID_SIZE, TowerDimen.TOWER_TOP + (x + 1) * TowerDimen.TOWER_GRID_SIZE);
            }
        }

        graphics.drawBitmap(canvas, player.getImage(), TowerDimen.TOWER_LEFT + (player.getPosX() + 6) * TowerDimen.TOWER_GRID_SIZE, TowerDimen.TOWER_TOP + (player.getPosY() + 1) * TowerDimen.TOWER_GRID_SIZE);
    }
    
    public void interaction(int x, int y) {
        int id = currentGame.lvMap[currentGame.currentFloor][y][x];
        switch (id) {
            case 0:     // player move
                player.move(x, y);
                break;
            case 1:     // brick wall
                break;
            case 2:     // yellow door
                if (player.getYkey() > 0) {
                    currentGame.lvMap[currentGame.currentFloor][y][x] = 0;
                    player.setYkey(player.getYkey() - 1);
                }
                break;
            case 3:     // blue door
                if (player.getBkey() > 0) {
                    currentGame.lvMap[currentGame.currentFloor][y][x] = 0;
                    player.setBkey(player.getBkey() - 1);
                }
                break;
            case 4:     // red door
                if (player.getRkey() > 0) {
                    currentGame.lvMap[currentGame.currentFloor][y][x] = 0;
                    player.setRkey(player.getRkey() - 1);
                }
                break;
            case 5:     // stone
                break;
            case 6:     // yellow hey
                currentGame.lvMap[currentGame.currentFloor][y][x] = 0;
                player.setYkey(player.getYkey() + 1);
                message.show(R.string.get_yellow_key);
                break;
            case 7:     // blue key
                currentGame.lvMap[currentGame.currentFloor][y][x] = 0;
                player.setBkey(player.getBkey() + 1);
                message.show(R.string.get_bule_key);
                break;
            case 8:     // red key
                currentGame.lvMap[currentGame.currentFloor][y][x] = 0;
                player.setRkey(player.getRkey() + 1);
                message.show(R.string.get_red_key);
                break;
            case 9:     // sapphire
                currentGame.lvMap[currentGame.currentFloor][y][x] = 0;
                player.setDefend(player.getDefend() + 3);
                message.show(R.string.get_sapphire);
                break;
            case 10:    // ruby
                currentGame.lvMap[currentGame.currentFloor][y][x] = 0;
                player.setAttack(player.getAttack() + 3);
                message.show(R.string.get_ruby);
                break;
            case 11:    // red potion
                currentGame.lvMap[currentGame.currentFloor][y][x] = 0;
                player.setHp(player.getHp() + 200);
                message.show(R.string.get_red_potion);
                break;
            case 12:    // blue potion
                currentGame.lvMap[currentGame.currentFloor][y][x] = 0;
                player.setHp(player.getHp() + 500);
                message.show(R.string.get_blue_potion);
                break;
            case 13:    // upstairs
                message.show(R.string.msg_upstairs);
                currentGame.currentFloor++;
                currentGame.maxFloor = Math.max(currentGame.maxFloor, currentGame.currentFloor);
                player.move(TowerMap.initPos[currentGame.currentFloor][0], TowerMap.initPos[currentGame.currentFloor][1]);
                if (currentGame.currentFloor == 21) {
                    currentGame.isHasJump = false;
                }
                break;
            case 14:    // downstairs
                message.show(R.string.msg_downstairs);
                currentGame.currentFloor--;
                player.move(TowerMap.finPos[currentGame.currentFloor][0], TowerMap.finPos[currentGame.currentFloor][1]);
                break;
            case 15:    // barrier not accessible
                break;
            case 16:   // accessible guardrail
                currentGame.lvMap[currentGame.currentFloor][y][x] = 0;
                break;
            case 19:    // sea of fire
                break;
            case 20:    // starry sky
                break;
            case 22:    // shop
                if (currentGame.currentFloor == 3) {
                    shop.show(0, id);
                } else if (currentGame.currentFloor == 11) {
                    shop.show(3, id);
                }
                break;
            case 24:    // fairy
                switch (currentGame.npcInfo.mFairyStatus) {
                case NpcInfo.FAIRY_STATUS_WAIT_PLAYER:
                    dialog.show(0, id);
                    break;
                case NpcInfo.FAIRY_STATUS_WAIT_CROSS:
                    if (currentGame.isHasCross) {
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
                if (currentGame.currentFloor == 2) {
                    dialog.show(4, id);
                } else if (currentGame.currentFloor == 5) {
                    shop.show(1, id);
                } else if (currentGame.currentFloor == 13) {
                    shop.show(5, id);
                } else if (currentGame.currentFloor == 15) {
                    if (player.getExp() >= 500) {
                        dialog.show(3, id);
                    } else {
                        dialog.show(2, id);
                    }
                }
                break;
            case 27:    // businessman
                if (currentGame.currentFloor == 2) {
                    dialog.show(5, id);
                } else if (currentGame.currentFloor == 5) {
                    shop.show(2, id);
                } else if (currentGame.currentFloor == 12) {
                    shop.show(4, id);
                } else if (currentGame.currentFloor == 15) {
                    if (player.getMoney() >= 500) {
                        dialog.show(7, id);
                    } else {
                        dialog.show(6, id);
                    }
                }
                break;
            case 28:    // princess
                dialog.show(9, id);
                break;
            case 30:    // little flying feather
                currentGame.lvMap[currentGame.currentFloor][y][x] = 0;
                player.setLevel(player.getLevel() + 1);
                player.setHp(player.getHp() + 1000);
                player.setAttack(player.getAttack() + 7);
                player.setDefend(player.getDefend() + 7);
                message.show(R.string.get_little_fly);
                break;
            case 31:    // big flying feather
                currentGame.lvMap[currentGame.currentFloor][y][x] = 0;
                player.setLevel(player.getLevel() + 3);
                player.setHp(player.getHp() + 3000);
                player.setAttack(player.getAttack() + 20);
                player.setDefend(player.getDefend() + 20);
                message.show(R.string.get_big_fly);
                break;
            case 32:    // cross
                currentGame.lvMap[currentGame.currentFloor][y][x] = 0;
                currentGame.isHasCross = true;
                message.show(R.string.treasure_cross, R.string.treasure_cross_info, SceneMessage.MODE_ALERT);
                break;
            case 33:    // holy water bottle
                currentGame.lvMap[currentGame.currentFloor][y][x] = 0;
                player.setHp(player.getHp() * 2);
                message.show(R.string.treasure_holy_water, R.string.treasure_holy_water_info, SceneMessage.MODE_ALERT);
                break;
            case 34:    // emblem of light
                currentGame.lvMap[currentGame.currentFloor][y][x] = 0;
                currentGame.isHasForecast = true;
                message.show(R.string.treasure_emblem_light, R.string.treasure_emblem_light_info, SceneMessage.MODE_ALERT);
                break;
            case 35:    // compass of wind
                currentGame.lvMap[currentGame.currentFloor][y][x] = 0;
                currentGame.isHasJump = true;
                message.show(R.string.treasure_compass, R.string.treasure_compass_info, SceneMessage.MODE_ALERT);
                break;
            case 36:    // key box
                currentGame.lvMap[currentGame.currentFloor][y][x] = 0;
                player.setYkey(player.getYkey() + 1);
                player.setBkey(player.getBkey() + 1);
                player.setRkey(player.getRkey() + 1);
                message.show(R.string.get_key_box);
                break;
            case 38:    // hammer
                currentGame.lvMap[currentGame.currentFloor][y][x] = 0;
                currentGame.isHasHammer = true;
                message.show(R.string.treasure_hammer, R.string.treasure_hammer_info, SceneMessage.MODE_ALERT);
                break;
            case 39:    // gold nugget
                currentGame.lvMap[currentGame.currentFloor][y][x] = 0;
                player.setMoney(player.getMoney() + 300);
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
                if (SceneForecast.forecast(player, MonsterData.monsterMap.get(id)).equals("???")
                        || Integer.parseInt(SceneForecast.forecast(player, MonsterData.monsterMap.get(id))) >= player.getHp()) {
                    return;
                } else {
                    battle.show(id, x, y);
                }
                break;
            case 71:    // iron sword
                currentGame.lvMap[currentGame.currentFloor][y][x] = 0;
                player.setAttack(player.getAttack() + 10);
                message.show(R.string.get_iron_shield);
                break;
            case 73:    // steel sword
                currentGame.lvMap[currentGame.currentFloor][y][x] = 0;
                player.setAttack(player.getAttack() + 70);
                message.show(R.string.get_steel_sword);
                break;
            case 75:    // sword of light
                currentGame.lvMap[currentGame.currentFloor][y][x] = 0;
                player.setAttack(player.getAttack() + 150);
                message.show(R.string.get_light_sword);
                break;
            case 76:    // iron shield
                currentGame.lvMap[currentGame.currentFloor][y][x] = 0;
                player.setDefend(player.getDefend() + 10);
                message.show(R.string.get_iron_shield);
                break;
            case 78:    // gold shield
                currentGame.lvMap[currentGame.currentFloor][y][x] = 0;
                player.setDefend(player.getDefend() + 85);
                message.show(R.string.get_gold_shield);
                break;
            case 80:    // light shield
                currentGame.lvMap[currentGame.currentFloor][y][x] = 0;
                player.setDefend(player.getDefend() + 190);
                message.show(R.string.get_light_shield);
                break;
            case 101:
                currentGame.lvMap[currentGame.currentFloor][y][x] = 0;
                dialog.show(12, 59);
                break;
        }
    }
}
