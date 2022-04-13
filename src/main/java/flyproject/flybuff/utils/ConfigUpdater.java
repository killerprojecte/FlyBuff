package flyproject.flybuff.utils;

import flyproject.flybuff.FlyBuff;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class ConfigUpdater {
    public static void update(){
        FlyBuff buff = FlyBuff.getPlugin(FlyBuff.class);
        FileConfiguration config = YamlConfiguration.loadConfiguration(new File(buff.getDataFolder() + "/config.yml"));
        if (config.get("config-version")==null){
            System.out.println(Color.color("&a[配置更新器] 检测到正在使用1.0.1或之前的版本 开始更新"));
            config.set("remove",true);
            config.set("title","&8[&bBUFF移除面板&8]");
            config.set("removehelp","&e - 点击移除BUFF");
            config.set("nospace","&c背包空间不足 无法移除BUFF");
            config.set("backpage","&f上一页");
            config.set("nextpage","&f下一页");
            config.set("config-version","1.1.0");
            config.options().header("由于配置需要更新 注释已被消除 请查看MCBBS上的配置文件栏查看格式");
            try {
                config.save(new File(buff.getDataFolder() + "/config.yml"));
            } catch (IOException e) {
                System.err.println(Color.color("&c[配置更新器] 保存失败"));
                return;
            }
            buff.reloadConfig();
            FlyBuff.config = buff.getConfig();
            System.out.println(Color.color("[配置更新器] 更新完成！"));
        }
    }
}
