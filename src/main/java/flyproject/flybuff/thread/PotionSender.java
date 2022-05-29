package flyproject.flybuff.thread;

import flyproject.flybuff.FlyBuff;
import flyproject.flybuff.utils.BuffParticle;
import flyproject.flybuff.utils.FlyTask;
import flyproject.flybuff.utils.MathEngine;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.Particle;
import org.bukkit.World;
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
                    for (BuffParticle buffParticle : FlyBuff.getParticles(p)){
                        World world = Bukkit.getWorld(PlaceholderAPI.setPlaceholders(p,buffParticle.getWorld()));
                        Particle particle = Particle.valueOf(buffParticle.getParticle());
                        String xstr = buffParticle.getX();
                        String ystr = buffParticle.getY();
                        String zstr = buffParticle.getZ();
                        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI")!=null){
                            xstr = PlaceholderAPI.setPlaceholders(p,xstr);
                            ystr = PlaceholderAPI.setPlaceholders(p,ystr);
                            zstr = PlaceholderAPI.setPlaceholders(p,zstr);
                        }
                        double x = Double.parseDouble(MathEngine.format(xstr));
                        double y = Double.parseDouble(MathEngine.format(ystr));
                        double z = Double.parseDouble(MathEngine.format(zstr));
                        int count = buffParticle.getCount();
                        world.spawnParticle(particle,x,y,z,count);
                    }
                }
            }
        }.runTaskTimerAsynchronously(FlyBuff.getPlugin(FlyBuff.class), FlyBuff.config.getInt("bufftime"), 0L);
    }
}
