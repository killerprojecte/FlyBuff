package flyproject.flybuff;

import flyproject.flybuff.command.BuffCommand;
import flyproject.flybuff.listener.ClickWorkbench;
import flyproject.flybuff.thread.PotionSender;
import flyproject.flybuff.utils.Color;
import flyproject.licenseplugin.LicensePlugin;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

public final class FlyBuff extends JavaPlugin {
    public static FileConfiguration config;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        config = getConfig();
        System.out.println("\n" +
                "    ________      ____        ________\n" +
                "   / ____/ /_  __/ __ )__  __/ __/ __/\n" +
                "  / /_  / / / / / __  / / / / /_/ /_  \n" +
                " / __/ / / /_/ / /_/ / /_/ / __/ __/  \n" +
                "/_/   /_/\\__, /_____/\\__,_/_/ /_/     \n" +
                "        /____/                        \n\n" +
                "(c) Copyright 2022 FlyProject\n" +
                "This software using GPLv3 Open Source License\n" +
                "Author All Rights Reserved\n" +
                "Github: https://github.com/killerprojecte/FlyBuff\n" +
                "Version: " + getDescription().getVersion() +
                "  This is a beta version please not share it\n");
        Bukkit.getScheduler().runTaskLaterAsynchronously(this,() -> {
            if (Bukkit.getPluginManager().getPlugin("FlyLicense")==null || !Bukkit.getPluginManager().getPlugin("FlyLicense").isEnabled()){
                System.err.println("[FlyBuff] 非法访问: 无法访问认证模块");
                Bukkit.shutdown();
            }
            if (!LicensePlugin.fuckyou.contains("FlyBuff")){
                System.err.println("[FlyBuff] 非法访问: 无法访问认证模块");
                Bukkit.shutdown();
            }
            PotionSender.load();
            Bukkit.getPluginManager().registerEvents(new ClickWorkbench(),this);
            getCommand("flybuff").setExecutor(new BuffCommand());
        },1200L);
        // Plugin startup logic

    }

    public static void sendPotion(Player p,PotionEffect pe){
        new BukkitRunnable() {
            @Override
            public void run() {
                p.addPotionEffect(pe);
            }
        }.runTask(getPlugin(FlyBuff.class));
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static List<PotionEffect> getPotion(Player player){
        List<PotionEffect> list = new ArrayList<>();
        Inventory inv = player.getInventory();
        ItemStack i1 = inv.getItem(36);
        ItemStack i2 = inv.getItem(37);
        ItemStack i3 = inv.getItem(38);
        ItemStack i4 = inv.getItem(39);
        ItemStack i5 = inv.getItem(40);
        ItemStack i6 = player.getItemInHand();
        ItemStack[] is = {i1,i2,i3,i4,i5,i6};
        for (ItemStack i : is){
            ItemMeta meta = i.getItemMeta();
            if (meta.getLore()==null || meta.getLore().size()==0) continue;
            for (String l : config.getConfigurationSection("effect").getKeys(false)){
                String cl = Color.color(l);
                for (String lore : meta.getLore()){
                    if (lore.equals(cl)){
                        for (String pots : config.getStringList("effect." + l)){
                            String[] args = pots.split(":");
                            list.add(new PotionEffect(PotionEffectType.getByName(args[0]),3600,Integer.parseInt(args[1])));
                        }
                        break;
                    }
                }
            }
        }
        return list;
    }
}
