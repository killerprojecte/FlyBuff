package flyproject.flybuff.utils;

import flyproject.flybuff.FlyBuff;

import java.util.ArrayList;
import java.util.List;

public class XMap {
    public static List<String> whitelists;
    public static List<String> gems;
    public static List<String> installs;
    public static void load(){
        whitelists = new ArrayList<>();
        whitelists.addAll(FlyBuff.config.getStringList("whitelist"));
        gems = new ArrayList<>();
        for (String key : FlyBuff.item.getConfigurationSection("gems").getKeys(false)){
            gems.add(key);
        }
        installs = new ArrayList<>();
        for (String key : FlyBuff.config.getConfigurationSection("gem").getKeys(false)){
            installs.add(FlyBuff.config.getString(key));
        }
    }
}
