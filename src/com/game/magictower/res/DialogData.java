package com.game.magictower.res;

import java.util.ArrayList;
import java.util.HashMap;

import com.game.magictower.TalkInfo;

public final class DialogData {
    
    public static final int TK_PLAYER = 0;
    public static final int TK_NPC = 1;
    
    public static HashMap<Integer, ArrayList<TalkInfo>> mMsgsMap = new HashMap<>();
    
    static {
        ArrayList<TalkInfo> talkList = new ArrayList<TalkInfo>();
        talkList.add(new TalkInfo(TK_PLAYER, "勇士", new String[]{"······"}));
        talkList.add(new TalkInfo(TK_NPC, "仙子", new String[]{"你醒了!"}));
        talkList.add(new TalkInfo(TK_PLAYER, "勇士", new String[]{"······", "你是谁？我在哪里？"}));
        talkList.add(new TalkInfo(TK_NPC, "仙子", new String[]{"我是这里的仙子，刚才你被这里的小怪打昏了。"}));
        talkList.add(new TalkInfo(TK_PLAYER, "勇士", new String[]{"······", "剑，剑，我的剑呢？"}));
        talkList.add(new TalkInfo(TK_NPC, "仙子", new String[]{"你的剑被他们抢走了，我只来得及将你救出来。"}));
        talkList.add(new TalkInfo(TK_PLAYER, "勇士", new String[]{"那，公主呢？我是来救公主的。"}));
        talkList.add(new TalkInfo(TK_NPC, "仙子", new String[]{"公主还在里面，你这样进去是打不过里面的小怪的。"}));
        talkList.add(new TalkInfo(TK_PLAYER, "勇士", new String[]{"那我怎么办，我答应了国王一定要把公主救出来", "的，那我现在应该怎么办呢？"}));
        talkList.add(new TalkInfo(TK_NPC, "仙子", new String[]{"放心吧，我把我的力量借给你，你就可以打赢那些小", "怪了。不过，你的先去帮我去找一样东西，找到了再来这"}));
        talkList.add(new TalkInfo(TK_NPC, "仙子", new String[]{"里找我。"}));
        talkList.add(new TalkInfo(TK_PLAYER, "勇士", new String[]{"找东西？找什么东西？"}));
        talkList.add(new TalkInfo(TK_NPC, "仙子", new String[]{"是一个十字架，中间有一颗红色的宝石。"}));
        talkList.add(new TalkInfo(TK_PLAYER, "勇士", new String[]{"那个东西有什么用吗？"}));
        talkList.add(new TalkInfo(TK_NPC, "仙子", new String[]{"我本是这座塔守护者，可不久前，从北方来了一批恶", "魔，他们占领了这座塔，并将我的魔力封在了这个十字"}));
        talkList.add(new TalkInfo(TK_NPC, "仙子", new String[]{"架里面，如果你能将它带出塔来，那我的魔力便会慢慢", "地恢复，到那时我便可以把力量借给你去救公主了。"}));
        talkList.add(new TalkInfo(TK_NPC, "仙子", new String[]{"要记住，只有用我的魔力才可以打开二十一层的门。"}));
        talkList.add(new TalkInfo(TK_PLAYER, "勇士", new String[]{"······", "    好吧，我试试看。"}));
        talkList.add(new TalkInfo(TK_NPC, "仙子", new String[]{"刚才我去看过了，你的剑被放在三楼，你的盾在五楼", "上，而那个十字架被放在七楼。要到七楼，你的先取回"}));
        talkList.add(new TalkInfo(TK_NPC, "仙子", new String[]{"你的剑和盾。另外在塔里的其他楼层", "上，还有一些存放了好几百年的宝剑和宝物，如果得到"}));
        talkList.add(new TalkInfo(TK_NPC, "仙子", new String[]{"它们，对于你对付这里面的怪物将有很大的帮助。"}));
        talkList.add(new TalkInfo(TK_PLAYER, "勇士", new String[]{"······", "    可是，我怎么进去呢?"}));
        talkList.add(new TalkInfo(TK_NPC, "仙子", new String[]{"我这里有三把钥匙，你先拿去，在塔里面还有很多这", "样的钥匙，你一定要珍惜使用。"}));
        talkList.add(new TalkInfo(TK_NPC, "仙子", new String[]{"勇敢的去吧，勇士！"}));
        mMsgsMap.put(24, talkList);
        
        talkList = new ArrayList<TalkInfo>();
        talkList.add(new TalkInfo(TK_PLAYER, "勇士", new String[]{"仙子，我已经将那个十字架找到了。"}));
        talkList.add(new TalkInfo(TK_NPC, "仙子", new String[]{"你做得很好。", "那么现在我就开始授予"}));
        talkList.add(new TalkInfo(TK_NPC, "仙子", new String[]{"你更强的力量！", "咪啦哆咪哔······"}));
        talkList.add(new TalkInfo(TK_NPC, "仙子", new String[]{"好了，我已经将你现在的能力提升了！", "记住：如果你没有足够的实力的话，不要去第二十一"}));
        talkList.add(new TalkInfo(TK_NPC, "仙子", new String[]{"层！在那一层里，你所有宝物的法力都会失去作用！"}));
        mMsgsMap.put(1, talkList);
                        
        talkList = new ArrayList<TalkInfo>();
        talkList.add(new TalkInfo(TK_PLAYER, "勇士", new String[]{""}));
        talkList.add(new TalkInfo(TK_NPC, "老人", new String[]{"你好，勇敢的孩子，你终于来到这里了。", "我将给你一个非常好的宝物，它可以使你的攻击"}));
        talkList.add(new TalkInfo(TK_NPC, "老人", new String[]{"力提升 120 点，但这必须得用你的 500 点经验", "来进行交换，考虑一下子吧！"}));
        talkList.add(new TalkInfo(TK_PLAYER, "勇士", new String[]{"这，可我现在还没有那么多的经验。"}));
        talkList.add(new TalkInfo(TK_NPC, "老人", new String[]{"那等你的经验够了而且想要的时候再来吧！"}));
        mMsgsMap.put(3, talkList);
        
        talkList = new ArrayList<TalkInfo>();
        talkList.add(new TalkInfo(TK_PLAYER, "勇士", new String[]{""}));
        talkList.add(new TalkInfo(TK_NPC, "老人", new String[]{"你好，勇敢的孩子，你终于来到这里了。", "我将给你一个非常好的宝物，它可以使你的攻击"}));
        talkList.add(new TalkInfo(TK_NPC, "老人", new String[]{"力提升 120 点，但这必须得用你的 500 点经验", "来进行交换，考虑一下子吧！"}));
        talkList.add(new TalkInfo(TK_PLAYER, "勇士", new String[]{"好吧，那就将那把剑给我吧！"}));
        talkList.add(new TalkInfo(TK_NPC, "老人", new String[]{"那好吧，这把剑就给你了！"}));
        mMsgsMap.put(4, talkList);
        
        talkList = new ArrayList<TalkInfo>();
        talkList.add(new TalkInfo(TK_PLAYER, "勇士", new String[]{""}));
        talkList.add(new TalkInfo(TK_NPC, "老人", new String[]{"年青人，你终于来了！"}));
        talkList.add(new TalkInfo(TK_PLAYER, "勇士", new String[]{"您怎么了？"}));
        talkList.add(new TalkInfo(TK_NPC, "老人", new String[]{"我已经快封不住它了，请你将这个东西交给彩蝶", "仙子，她会告诉你这是什么东西，有什么用的！"}));
        talkList.add(new TalkInfo(TK_NPC, "老人", new String[]{"快去吧，再迟就来不及了！"}));
        mMsgsMap.put(5, talkList);
        
        talkList = new ArrayList<TalkInfo>();
        talkList.add(new TalkInfo(TK_PLAYER, "勇士", new String[]{"您已经得救了！"}));
        talkList.add(new TalkInfo(TK_NPC, "商人", new String[]{"哦，是嘛！真是太感谢你了！", "我是个商人，不知为什么被抓到这里来了。"}));
        talkList.add(new TalkInfo(TK_PLAYER, "勇士", new String[]{"快走吧，现在你已经自由了。"}));
        talkList.add(new TalkInfo(TK_NPC, "商人", new String[]{"哦，对对对，我已经自由了。", "那这个东西就给你吧，本来我是准备卖钱的。"}));
        talkList.add(new TalkInfo(TK_NPC, "商人", new String[]{"相信它对你一定很有帮助！"}));
        mMsgsMap.put(6, talkList);
        
        talkList = new ArrayList<TalkInfo>();
        talkList.add(new TalkInfo(TK_PLAYER, "勇士", new String[]{""}));
        talkList.add(new TalkInfo(TK_NPC, "老人", new String[]{"啊哈，欢迎你的到来！", "我这里有一件对你来说"}));
        talkList.add(new TalkInfo(TK_NPC, "老人", new String[]{"非常好的宝物，只要你出得起钱，我就卖给你。"}));
        talkList.add(new TalkInfo(TK_PLAYER, "勇士", new String[]{"什么宝物？要多少钱？"}));
        talkList.add(new TalkInfo(TK_NPC, "老人", new String[]{"是这个游戏里最好的盾牌，防御值可以增加 120 ", "点，而你只要出 500 个金币就可以买下。"}));
        talkList.add(new TalkInfo(TK_NPC, "老人", new String[]{"怎么样？你有 500 个金币吗？"}));
        talkList.add(new TalkInfo(TK_PLAYER, "勇士", new String[]{"······", "    现在还没有。"}));
        talkList.add(new TalkInfo(TK_NPC, "老人", new String[]{"那等你有了而且想要的时候再来找我吧！"}));
        mMsgsMap.put(7, talkList);
        
        talkList = new ArrayList<TalkInfo>();
        talkList.add(new TalkInfo(TK_PLAYER, "勇士", new String[]{""}));
        talkList.add(new TalkInfo(TK_NPC, "老人", new String[]{"啊哈，欢迎你的到来！", "我这里有一件对你来说"}));
        talkList.add(new TalkInfo(TK_NPC, "老人", new String[]{"非常好的宝物，只要你出得起钱，我就卖给你。"}));
        talkList.add(new TalkInfo(TK_PLAYER, "勇士", new String[]{"什么宝物？要多少钱？"}));
        talkList.add(new TalkInfo(TK_NPC, "老人", new String[]{"是这个游戏里最好的盾牌，防御值可以增加 120 ", "点，而你只要出 500 个金币就可以买下。"}));
        talkList.add(new TalkInfo(TK_NPC, "老人", new String[]{"怎么样？你有 500 个金币吗？"}));
        talkList.add(new TalkInfo(TK_PLAYER, "勇士", new String[]{"我有 500 个金币。"}));
        talkList.add(new TalkInfo(TK_NPC, "老人", new String[]{"好，成交！"}));
        mMsgsMap.put(8, talkList);
        
        talkList = new ArrayList<TalkInfo>();
        talkList.add(new TalkInfo(TK_PLAYER, "勇士", new String[]{"你已经得救了! "}));
        talkList.add(new TalkInfo(TK_NPC, "小偷", new String[]{"啊，那真是太好了，我又可以在这里面寻宝了！", "哦，还没有自我介绍，我叫杰克，是这附近有名"}));
        talkList.add(new TalkInfo(TK_NPC, "小偷", new String[]{"的小偷，什么金银财宝我样样都偷过。", "不过这次运气可不是太好，刚进来就被抓了。"}));
        talkList.add(new TalkInfo(TK_NPC, "小偷", new String[]{"现在你帮我打开了门，那我就帮你做一件事吧。"}));
        talkList.add(new TalkInfo(TK_PLAYER, "勇士", new String[]{"快走吧，外面还有很多怪物，我可能顾不上你。"}));
        talkList.add(new TalkInfo(TK_NPC, "小偷", new String[]{"不，不，不会有事的。", "快说吧，叫我做什么？"}));
        talkList.add(new TalkInfo(TK_PLAYER, "勇士", new String[]{"······", "你会开门吗？"}));
        talkList.add(new TalkInfo(TK_NPC, "小偷", new String[]{"那当然。"}));
        talkList.add(new TalkInfo(TK_PLAYER, "勇士", new String[]{"那就请你帮我打开第二层的门吧！"}));
        talkList.add(new TalkInfo(TK_NPC, "小偷", new String[]{"那个简单，不过，如果你能帮我找到一把嵌了红", "宝石的铁榔头的话，我还帮你打通第十八层的路。"}));
        talkList.add(new TalkInfo(TK_PLAYER, "勇士", new String[]{"嵌了红宝石的铁榔头？好吧，我帮你找找。"}));
        talkList.add(new TalkInfo(TK_NPC, "小偷", new String[]{"非常地感谢。一会我便会将第二层的门打开。", "如果你找到那个铁榔头的话，还是来这里找我！"}));
        mMsgsMap.put(12, talkList);
        
        talkList = new ArrayList<TalkInfo>();
        talkList.add(new TalkInfo(TK_PLAYER, "勇士", new String[]{"公主！你得救了"}));
        talkList.add(new TalkInfo(TK_NPC, "公主", new String[]{"啊，你是来救我的吗？"}));
        talkList.add(new TalkInfo(TK_PLAYER, "勇士", new String[]{"是的，我是奉国王的命令来救你的。", "请你快随我出去吧！"}));
        talkList.add(new TalkInfo(TK_NPC, "公主", new String[]{"不，我还不想走。"}));
        talkList.add(new TalkInfo(TK_PLAYER, "勇士", new String[]{"为什么？这里到处都是恶魔。"}));
        talkList.add(new TalkInfo(TK_NPC, "公主", new String[]{"正是因为这里面到处都是恶魔，所以才不可以就", "这样出去，我要看着那个恶魔被杀死！"}));
        talkList.add(new TalkInfo(TK_NPC, "公主", new String[]{"英雄的勇士，如果你能够将那个大恶魔杀死。我", "就和你一起出去！"}));
        talkList.add(new TalkInfo(TK_PLAYER, "勇士", new String[]{"大恶魔？我已经杀死了一个魔王！ "}));
        talkList.add(new TalkInfo(TK_NPC, "公主", new String[]{"大恶魔在这座塔的最顶层，你杀死的可能是一个", "小队长之类的恶魔"}));
        talkList.add(new TalkInfo(TK_PLAYER, "勇士", new String[]{"好，那你等着，等我杀了那个恶魔再来这里", "找你！"}));
        talkList.add(new TalkInfo(TK_NPC, "公主", new String[]{"大恶魔比你刚才杀死的那个厉害多了。", "而且他还会变身，变身后的魔王他的攻击力和防"}));
        talkList.add(new TalkInfo(TK_NPC, "公主", new String[]{"御力都会提升至少一半以上，你得小心！", "请一定要杀死大魔王！"}));
        mMsgsMap.put(19, talkList);
        
        talkList = new ArrayList<TalkInfo>();
        talkList.add(new TalkInfo(TK_PLAYER, "勇士", new String[]{"······"}));
        talkList.add(new TalkInfo(TK_NPC, "恶魔", new String[]{"停止吧！愚蠢的人类！"}));
        talkList.add(new TalkInfo(TK_PLAYER, "勇士", new String[]{"该停止的是你！魔王。快说，公主关在哪里？"}));
        talkList.add(new TalkInfo(TK_NPC, "恶魔", new String[]{"等你打赢我再说吧！"}));
        mMsgsMap.put(23, talkList);
        
        talkList = new ArrayList<TalkInfo>();
        talkList.add(new TalkInfo(TK_PLAYER, "勇士", new String[]{"哈，快看，我找到了什么！"}));
        talkList.add(new TalkInfo(TK_NPC, "小偷", new String[]{"太好了，这个东西果然是在这里。", "好吧，我这就去帮你修好第十八层的路面。"}));
        mMsgsMap.put(26, talkList);
        
        talkList = new ArrayList<TalkInfo>();
        talkList.add(new TalkInfo(TK_PLAYER, "勇士", new String[]{"大魔王，你的死期到了！"}));
        talkList.add(new TalkInfo(TK_NPC, "恶魔", new String[]{"哈哈哈······", "你也真是有意思，别以"}));
        talkList.add(new TalkInfo(TK_NPC, "恶魔", new String[]{"为蝶仙那家伙给了你力量你就可以打败我，想打败", "我你还早着呢！"}));
        talkList.add(new TalkInfo(TK_PLAYER, "勇士", new String[]{"废话少说，去死吧！"}));
        mMsgsMap.put(27, talkList);
        
        talkList = new ArrayList<TalkInfo>();
        talkList.add(new TalkInfo(TK_NPC, "恶魔", new String[]{"看不出你还有两下子，有本领的话来 21 楼。", "在那里，你就可以见识到我真正的实力了！"}));
        mMsgsMap.put(28, talkList);
        
        talkList = new ArrayList<TalkInfo>();
        talkList.add(new TalkInfo(TK_NPC, "恶魔", new String[]{"啊······", "怎么可能，我怎么可能", "会被你打败呢！"}));
        talkList.add(new TalkInfo(TK_NPC, "恶魔", new String[]{"不，不要这样······"}));
        mMsgsMap.put(29, talkList);
    }

}
