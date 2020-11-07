package com.game.magictower.model;

import java.util.ArrayList;

public class TalkInfo {
    
    public int mTalker;
    public String mTkName;
    public ArrayList<String> mMsgs;
    
    public TalkInfo(int talker, String tkName, ArrayList<String> msgs) {
        mTalker = talker;
        mTkName = tkName;
        mMsgs = msgs;
    }
}
