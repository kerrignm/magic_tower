package com.game.magictower.model;

public class BaseInfo {
    
    protected int level;
    
    protected int hp;
    
    protected int money;
    
    protected int attack;
    
    protected int defend;
    
    protected int ykey;
    
    protected int bkey;
    
    protected int rkey;
    
    public BaseInfo() {
        
    }
    
    public int getLevel() {
        return level;
    }
    
    public void setLevel(int level) {
        this.level = level;
    }
    
    public int getHp() {
        return hp;
    }
    
    public void setHp(int hp) {
        this.hp = hp;
    }
    
    public int getMoney() {
        return money;
    }
    
    public void setMoney(int money) {
        this.money = money;
    }
    
    public int getAttack() {
        return attack;
    }
    
    public void setAttack(int attack) {
        this.attack = attack;
    }
    
    public int getDefend() {
        return defend;
    }
    
    public void setDefend(int defend) {
        this.defend = defend;
    }
    
    public int getYKey() {
        return ykey;
    }
    
    public void setYKey(int ykey) {
        this.ykey = ykey;
    }
    
    public int getBKey() {
        return bkey;
    }
    
    public void setBKey(int bkey) {
        this.bkey = bkey;
    }
    
    public int getRKey() {
        return rkey;
    }
    
    public void setRKey(int rkey) {
        this.rkey = rkey;
    }
    
    public void update(BaseInfo info) {
        if (info.getLevel() > 0) {
            setLevel(getLevel() + info.getLevel());
        }
        if (info.getHp() > 0) {
            setHp(getHp() + info.getHp());
        }
        if (info.getMoney() > 0) {
            setMoney(getMoney() + info.getMoney());
        }
        if (info.getAttack() > 0) {
            setAttack(getAttack() + info.getAttack());
        }
        if (info.getDefend() > 0) {
            setDefend(getDefend() + info.getDefend());
        }
        if (info.getYKey() > 0) {
            setYKey(getYKey() + info.getYKey());
        }
        if (info.getBKey() > 0) {
            setBKey(getBKey() + info.getBKey());
        }
        if (info.getRKey() > 0) {
            setRKey(getRKey() + info.getRKey());
        }
    }

}
