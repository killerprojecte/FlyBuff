package flyproject.flybuff.utils;

import flyproject.flybuff.FlyBuff;

public class ConfigUpdater {
    public static void update(){
        if (FlyBuff.config.get("config-version")==null){
            System.out.println("[配置更新器] 检测到正在使用1.0.1或之前的版本 开始更新");
            FlyBuff.config.set("remove",true);
            FlyBuff.config.set("title","&8[&bBUFF移除面板&8]");
            FlyBuff.config.set("removehelp","&e - 点击移除BUFF");
            FlyBuff.config.set("nospace","&c背包空间不足 无法移除BUFF");
            FlyBuff.config.set("backpage","&f上一页");
            FlyBuff.config.set("nextpage","&f下一页");
            FlyBuff.config.set("config-version","1.1.0");
            FlyBuff buff = FlyBuff.getPlugin(FlyBuff.class);
            buff.reloadConfig();
            FlyBuff.config = buff.getConfig();
            System.out.println("[配置更新器] 更新完成！");
        }
    }
}
