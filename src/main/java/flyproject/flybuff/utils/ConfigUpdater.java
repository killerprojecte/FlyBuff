package flyproject.flybuff.utils;

import flyproject.flybuff.FlyBuff;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class ConfigUpdater {
    public static void update() {
        FlyBuff buff = FlyBuff.instance;
        FileConfiguration config = YamlConfiguration.loadConfiguration(new File(buff.getDataFolder() + "/config.yml"));
    }
}
