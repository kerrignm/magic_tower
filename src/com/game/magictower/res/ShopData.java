package com.game.magictower.res;

public class ShopData {
    public static String[][] choices = {
        {"▶增加 800 点生命（25 金币）", "▷增加 4 点攻击（25 金币）", "▷增加 4 点防御（25 金币）", "▷离开商店"},
        {"▶提升一级（需要 100 点）", "▷增加攻击5（需要 30 点） ", "▷增加防御5（需要 30 点）", "▷离开商店"},
        {"▶购买 1 把黄钥匙（$ 10）", "▷购买 1 把蓝钥匙（$ 50）", "▷购买 1 把红钥匙（$ 100）", "▷离开商店"},
        {"▶增加 4000 点生命（100 金币）", "▷增加 20 点攻击（100 金币）", "▷增加 20 点防御（100 金币）", "▷离开商店"},
        {"▶卖出 1 把黄钥匙（$ 7）", "▷卖出 1 把黄钥匙（$ 35）", "▷卖出 1 把黄钥匙（$ 70）", "▷离开商店"},
        {"▶提升三级（需要 270 点）", "▷增加攻击 17（需要 95 点）", "▷增加防御 17（需要 95 点）", "▷离开商店"}
    };
    
    public static LiveBitmap[] imgIcons = {
        Assets.getInstance().animMap0.get(22),
        Assets.getInstance().animMap0.get(26),
        Assets.getInstance().animMap0.get(27),
        Assets.getInstance().animMap0.get(22),
        Assets.getInstance().animMap0.get(27),
        Assets.getInstance().animMap0.get(26)
    };

}
