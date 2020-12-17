package com.game.magictower.model;

public class Treasure {
    
    private final String title;
    
    private final String describe;
    
    public Treasure(String title, String describe) {

        this.title = title;
        this.describe = describe;
    }
    
    public String getTitle() {
        return title;
    }
    
    public String getDescribe() {
        return describe;
    }

}
