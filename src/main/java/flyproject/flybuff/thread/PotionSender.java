package flyproject.flybuff.thread;

import flyproject.flybuff.FlyBuff;
import flyproject.flybuff.utils.FlyTask;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.scheduler.BukkitRunnable;

public class PotionSender {
    public static void load() {
        new BukkitRunnable() {
            @Override
            public void run() {
                for (Player p : Bukkit.getOnlinePlayers()) {
                    for (PotionEffect potionEffect : FlyBuff.getPotion(p)) {
                        FlyTask.runTask(() -> {
                            if (p.getActivePotionEffects().contains(potionEffect)) return;
                            p.addPotionEffect(potionEffect);
                        });
                    }
                }
            }
        }.runTaskTimerAsynchronously(FlyBuff.getPlugin(FlyBuff.class), FlyBuff.config.getInt("bufftime"), 0L);
    }
}
