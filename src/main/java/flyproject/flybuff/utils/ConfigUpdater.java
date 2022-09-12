package flyproject.flybuff.utils;

import flyproject.flybuff.FlyBuff;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class ConfigUpdater {
    public static void update() {
        FlyBuff buff = FlyBuff.instance;
        if (buff.getConfig().getString("config-version").equals("1.2.0")){
            buff.getLogger().warning("尝试自动更新配置文件...");
            buff.getLogger().warning("正在将配置版本1.2.0升级至1.3.0");
            buff.getConfig().set("jsasync",true);
            buff.getConfig().set("config-version",true);
            buff.saveConfig();
            buff.reloadConfig();
            buff.getLogger().warning("配置自动更新完成! 注释请前往MCBBS或Wiki查看");
        }
    }
}
