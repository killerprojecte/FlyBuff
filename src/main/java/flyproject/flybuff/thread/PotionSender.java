package flyproject.flybuff.thread;

import flyproject.flybuff.FlyBuff;
import flyproject.flybuff.utils.BuffParticle;
import flyproject.flybuff.utils.FlyTask;
import flyproject.flybuff.utils.MathEngine;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.scheduler.BukkitRunnable;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

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
                        String version = Bukkit.getServer().getClass().getPackage()
                                .getName().replace("org.bukkit.craftbukkit.","");
                        if (!(version.equals("v1_8_R1") || version.equals("v1_8_R2") || version.equals("v1_8_R3"))){
                            Particle particle = Particle.valueOf(buffParticle.getParticle());
                            try {
                                Method method = world.getClass().getMethod("spawnParticle", Particle.class, double.class, double.class, double.class, int.class);
                                method.invoke(world,particle,x,y,z,count);
                            } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
                                e.printStackTrace();
                            }
                        } else {
                            world.playEffect(new Location(world,x,y,z), Effect.valueOf(buffParticle.getParticle()),0);
                        }
                    }
                }
            }
        }.runTaskTimerAsynchronously(FlyBuff.getPlugin(FlyBuff.class), FlyBuff.config.getInt("bufftime"), 0L);
    }
}
