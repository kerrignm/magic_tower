package com.game.magictower;

import android.content.Context;
import android.graphics.Canvas;

import com.game.magictower.Game.Status;
import com.game.magictower.model.NpcInfo;
import com.game.magictower.res.Assets;
import com.game.magictower.res.GlobalSoundPool;
import com.game.magictower.util.LogUtil;
import com.game.magictower.widget.BaseButton;

public class ScenePlay extends BaseScene {
    
    private static final String TAG = "MagicTower:ScenePlay";
    
    public ScenePlay(GameView parent, Context context, Game game, int id, int x, int y, int w, int h) {
        super(parent, context, game, id, x, y, w, h);
    }

    public void show() {
        game.status = Status.Playing;
    }
    
    @Override
    public void onDrawFrame(Canvas canvas) {
        super.onDrawFrame(canvas);
    }
    
    @Override
    public void onAction(int id) {
        switch (id) {
        case BaseButton.ID_UP:
            if (game.status == Status.Playing) {
                game.checkTest(id);
                if (game.player.getPosY() - 1 < 11 && game.player.getPosY() - 1 >= 0) {
                    game.player.setToward(3);
                    interaction(game.player.getPosX(), game.player.getPosY() - 1);
                }
            }
            break;
        case BaseButton.ID_LEFT:
            if (game.status == Status.Playing) {
                game.checkTest(id);
                if (game.player.getPosX() - 1 < 11 && game.player.getPosX() - 1 >= 0) {
                    game.player.setToward(0);
                    interaction(game.player.getPosX() - 1, game.player.getPosY());
                }
            }
            break;
        case BaseButton.ID_RIGHT:
            if (game.status == Status.Playing) {
                game.checkTest(id);
                if (game.player.getPosX() + 1 < 11 && game.player.getPosX() + 1 >= 0) {
                    game.player.setToward(2);
                    interaction(game.player.getPosX() + 1, game.player.getPosY());
                }
            }
            break;
        case BaseButton.ID_DOWN:
            if (game.status == Status.Playing) {
                game.checkTest(id);
                if (game.player.getPosY() + 1 < 11 && game.player.getPosY() + 1 >= 0) {
                    game.player.setToward(1);
                    interaction(game.player.getPosX(), game.player.getPosY() + 1);
                }
            }
            break;
        case BaseButton.ID_QUIT:
            game.quitGame();
            break;
        case BaseButton.ID_NEW:
            game.newGame();
            parent.showToast(R.string.msg_restart);
            GlobalSoundPool.getInstance().playSound(Assets.getInstance().getSoundId(Assets.SND_ID_RECORD));
            game.changeMusic();
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
                parent.showMessage(R.string.treasure_cross, R.string.treasure_cross_info, SceneMessage.MODE_ALERT);
                break;
            case 33:    // holy water bottle
                GlobalSoundPool.getInstance().playSound(Assets.getInstance().getSoundId(Assets.SND_ID_WONDER));
                game.lvMap[game.npcInfo.curFloor][y][x] = 0;
                parent.showMessage(R.string.treasure_holy_water, R.string.treasure_holy_water_info, SceneMessage.MODE_ALERT);
                game.player.setHp(game.player.getHp() * 2);
                break;
            case 34:    // emblem of light
                GlobalSoundPool.getInstance().playSound(Assets.getInstance().getSoundId(Assets.SND_ID_WONDER));
                game.lvMap[game.npcInfo.curFloor][y][x] = 0;
                game.npcInfo.isHasForecast = true;
                parent.showMessage(R.string.treasure_emblem_light, R.string.treasure_emblem_light_info, SceneMessage.MODE_ALERT);
                break;
            case 35:    // compass of wind
                GlobalSoundPool.getInstance().playSound(Assets.getInstance().getSoundId(Assets.SND_ID_WONDER));
                game.lvMap[game.npcInfo.curFloor][y][x] = 0;
                game.npcInfo.isHasJump = true;
                parent.showMessage(R.string.treasure_compass, R.string.treasure_compass_info, SceneMessage.MODE_ALERT);
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
                parent.showMessage(R.string.treasure_hammer, R.string.treasure_hammer_info, SceneMessage.MODE_ALERT);
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
}