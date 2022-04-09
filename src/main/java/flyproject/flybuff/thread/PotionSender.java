package flyproject.flybuff.thread;

import flyproject.flybuff.FlyBuff;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.scheduler.BukkitRunnable;

public class PotionSender {
    public static void load(){
        new BukkitRunnable() {
            @Override
            public void run() {
                for (Player p : Bukkit.getOnlinePlayers()){
                    for (PotionEffect potionEffect : FlyBuff.getPotion(p)){
                        FlyBuff.sendPotion(p,potionEffect);
                    }
                }
            }
        }.runTaskTimerAsynchronously(FlyBuff.getPlugin(FlyBuff.class),10L,0L);
    }
}
