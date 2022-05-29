package flyproject.flybuff.utils;

import flyproject.flybuff.FlyBuff;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class ConfigUpdater {
    public static void update() {
        FlyBuff buff = FlyBuff.getPlugin(FlyBuff.class);
        FileConfiguration config = YamlConfiguration.loadConfiguration(new File(buff.getDataFolder() + "/config.yml"));
        if (!config.get("config-version").toString().contains("1.2.0")) {
            System.err.println("[FlyBuff-Updater] 由于1.2.0变更过大 将不提供配置更新 请手动修改更新");
            Bukkit.getPluginManager().disablePlugin(Bukkit.getPluginManager().getPlugin("FlyBuff"));
        }
    }
}
