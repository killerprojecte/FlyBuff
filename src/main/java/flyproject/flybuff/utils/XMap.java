package flyproject.flybuff.utils;

import flyproject.flybuff.FlyBuff;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class XMap {
    public static List<String> whitelists;
    public static List<String> gems;
    public static List<String> installs;
    public static Map<String,List<BuffParticle>> particles;
    public static void load(){
        whitelists = new ArrayList<>();
        for (String type : FlyBuff.config.getStringList("whitelist")){
            whitelists.add(type.toLowerCase());
        }
        gems = new ArrayList<>();
        for (String key : FlyBuff.item.getConfigurationSection("gems").getKeys(false)){
            gems.add(key);
        }
        installs = new ArrayList<>();
        for (String key : FlyBuff.config.getConfigurationSection("gem").getKeys(false)){
            installs.add(FlyBuff.config.getString("gem." + key));
        }
        particles = new HashMap<>();
        for (String effects : FlyBuff.config.getConfigurationSection("effect").getKeys(false)){
            List<BuffParticle> list = new ArrayList<>();
            for (String buff : FlyBuff.config.getStringList("effect." + effects)){
                if (!buff.startsWith("[particle] ")) continue;
                buff = buff.substring(11);
                String path = "particles." + buff + ".";
                BuffParticle bp = new BuffParticle(FlyBuff.particle.getString(path + "type"),FlyBuff.particle.getString(path + "x"),FlyBuff.particle.getString(path + "y"),FlyBuff.particle.getString(path + "z"),FlyBuff.particle.getString(path + "world"),FlyBuff.particle.getInt("count"));
                list.add(bp);
            }
            particles.put(Color.color(effects),list);
        }
    }
}
