package flyproject.flybuff;

import flyproject.flybuff.command.BuffCommand;
import flyproject.flybuff.command.ItemCommand;
import flyproject.flybuff.command.RemoveCommand;
import flyproject.flybuff.gui.GuiClick;
import flyproject.flybuff.listener.ClickWorkbench;
import flyproject.flybuff.thread.PotionSender;
import flyproject.flybuff.utils.*;
import net.milkbowl.vault.economy.Economy;
import org.black_ixx.playerpoints.PlayerPoints;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.util.*;

public final class FlyBuff extends JavaPlugin {
    public static FileConfiguration config;
    public static FileConfiguration item;
    public static boolean hasVault = true;
    public static boolean hasPoints = true;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        saveResource("items.yml",false);
        config = getConfig();
        item = YamlConfiguration.loadConfiguration(new File(getDataFolder() + "/items.yml"));
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
                "\n");
        FlyTask.runTaskAsync(() -> {
            ConfigUpdater.update();
        });
        Bukkit.getPluginManager().registerEvents(new ClickWorkbench(),this);
        Bukkit.getPluginManager().registerEvents(new GuiClick(),this);
        getCommand("flybuff").setExecutor(new BuffCommand());
        getCommand("buffremove").setExecutor(new RemoveCommand());
        getCommand("buffitem").setExecutor(new ItemCommand());
        PotionSender.load();
        if (!setupEconomy()){
            hasVault = false;
            System.err.println("[FlyBuff-Payment] 您的服务器中并没有安装 Vault 插件， 对应的经济功能将被禁用");
        }
        if (!setupPoints()){
            hasPoints = false;
            System.err.println("[FlyBuff-Payment] 您的服务器中并没有安装 PlayerPoints 插件， 对应的经济功能将被禁用");
        }
        if (!hasVault && !hasPoints){
            System.err.println("[FlyBuff-Payment] 您的服务器上没有任何可用的经济插件 经济功能将完全禁用");
        }
        new BukkitRunnable() {
            @Override
            public void run() {
                Version.check();
            }
        }.runTaskLaterAsynchronously(this,1200L);
        // Plugin startup logic
    }

    @Deprecated
    public static void sendPotion(Player p,PotionEffect pe){
        new BukkitRunnable() {
            @Override
            public void run() {
                p.addPotionEffect(pe);
            }
        }.runTask(getPlugin(FlyBuff.class));
    }

    private boolean setupPoints(){
        if (Bukkit.getPluginManager().isPluginEnabled("PlayerPoints")) {
            PaymentCore.points = PlayerPoints.getInstance().getAPI();
            return true;
        } else {
            return false;
        }
    }

    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null || !Bukkit.getPluginManager().isPluginEnabled("Vault")) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        PaymentCore.econ = rsp.getProvider();
        return PaymentCore.econ != null;
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
            if (i==null || i.getType().equals(Material.AIR)) continue;
            ItemMeta meta = i.getItemMeta();
            Map<PotionEffectType,Integer> map = new HashMap<>();
            if (meta.getLore()==null || meta.getLore().size()==0) continue;
            for (String l : config.getConfigurationSection("effect").getKeys(false)){
                String cl = Color.color(l);
                for (String lore : meta.getLore()){
                    if (lore.equals(cl)){
                        for (String pots : config.getStringList("effect." + l)){
                            String[] args = pots.split(":");
                            PotionEffect pe = new PotionEffect(PotionEffectType.getByName(args[0]),10,Integer.parseInt(args[1]) -1);
                            if (list.contains(pe)){
                                continue;
                            } else {
                                list.add(pe);
                            }
                        }
                        break;
                    }
                }
            }
        }
        return list;
    }
    public static ItemStack simpleSkull(ItemStack head, String value) {
        UUID uuid = UUID.nameUUIDFromBytes(value.getBytes());
        return Bukkit.getUnsafe().modifyItemStack(head, "{SkullOwner:{Id:\"" + uuid + "\",Properties:{textures:[{Value:\"" + value + "\"}]}}}");
    }
}
