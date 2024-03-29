package flyproject.flybuff;

import flyproject.flybuff.command.BuffCommand;
import flyproject.flybuff.command.ItemCommand;
import flyproject.flybuff.command.RemoveCommand;
import flyproject.flybuff.gui.GuiClick;
import flyproject.flybuff.hook.Mythic4Hook;
import flyproject.flybuff.hook.Mythic5Hook;
import flyproject.flybuff.hook.MythicHook;
import flyproject.flybuff.listener.ClickWorkbench;
import flyproject.flybuff.listener.MythicListener;
import flyproject.flybuff.nms.*;
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
import org.openjdk.nashorn.api.scripting.NashornScriptEngineFactory;

import javax.script.ScriptEngineManager;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;
import java.util.logging.Logger;

import static flyproject.flybuff.utils.ResourceX.saveFile;

public final class FlyBuff extends JavaPlugin {
    public static FileConfiguration config;
    public static FileConfiguration item;
    public static FileConfiguration particle;
    public static boolean hasVault = true;
    public static boolean hasPoints = true;
    public static NbtManager nms;
    public static Logger logger;
    public static MythicHook mythicHook;
    public static FlyBuff instance;
    public static ScriptEngineManager scriptEngineManager;

    @Deprecated
    public static void sendPotion(Player p, PotionEffect pe) {
        new BukkitRunnable() {
            @Override
            public void run() {
                p.addPotionEffect(pe);
            }
        }.runTask(getPlugin(FlyBuff.class));
    }

    public static List<PotionEffect> getPotion(Player player) {
        List<PotionEffect> list = new ArrayList<>();
        Inventory inv = player.getInventory();
        ItemStack i1 = inv.getItem(36);
        ItemStack i2 = inv.getItem(37);
        ItemStack i3 = inv.getItem(38);
        ItemStack i4 = inv.getItem(39);
        ItemStack i5 = inv.getItem(40);
        ItemStack i6 = player.getItemInHand();
        ItemStack[] is = {i1, i2, i3, i4, i5, i6};
        for (ItemStack i : is) {
            if (i == null || i.getType().equals(Material.AIR)) continue;
            ItemMeta meta = i.getItemMeta();
            Map<PotionEffectType, Integer> map = new HashMap<>();
            if (meta.getLore() != null && meta.getLore().size() != 0) {
                for (String str : meta.getLore()) {
                    if (!XMap.lore.contains(Color.uncolor(str))) continue;
                    for (String pots : config.getStringList("effect." + Color.uncolor(str))) {
                        if (!pots.startsWith("[buff] ")) continue;
                        pots = pots.substring(7);
                        String[] args = pots.split(":");
                        int time = 10;
                        if (args.length == 3) time = Integer.parseInt(args[2]);
                        PotionEffect pe = new PotionEffect(PotionEffectType.getByName(args[0]), time, Integer.parseInt(args[1]) - 1);
                        if (!list.contains(pe)) {
                            list.add(pe);
                        }
                    }
                }
            }
            for (String nbt : FlyBuff.nms.getItemBuffs(i)) {
                if (!XMap.nbt_lore.contains(nbt)) continue;
                for (String pots : config.getStringList("nbteffect." + nbt)) {
                    if (!pots.startsWith("[buff] ")) continue;
                    pots = pots.substring(7);
                    String[] args = pots.split(":");
                    int time = 10;
                    if (args.length == 3) time = Integer.parseInt(args[2]);
                    PotionEffect pe = new PotionEffect(PotionEffectType.getByName(args[0]), time, Integer.parseInt(args[1]) - 1);
                    if (!list.contains(pe)) {
                        list.add(pe);
                    }
                }
            }
        }
        return list;
    }

    public static List<String> getAttackSkills(Player player) {
        List<String> list = new ArrayList<>();
        Inventory inv = player.getInventory();
        ItemStack i1 = inv.getItem(36);
        ItemStack i2 = inv.getItem(37);
        ItemStack i3 = inv.getItem(38);
        ItemStack i4 = inv.getItem(39);
        ItemStack i5 = inv.getItem(40);
        ItemStack i6 = player.getItemInHand();
        ItemStack[] is = {i1, i2, i3, i4, i5, i6};
        for (ItemStack i : is) {
            if (i == null || i.getType().equals(Material.AIR)) continue;
            ItemMeta meta = i.getItemMeta();
            if (meta.getLore() != null && meta.getLore().size() != 0) {
                for (String str : meta.getLore()) {
                    if (!XMap.lore.contains(Color.uncolor(str))) continue;
                    for (String pots : config.getStringList("effect." + Color.uncolor(str))) {
                        if (!pots.startsWith("[attack-skill] ")) continue;
                        pots = pots.substring(15);
                        if (!list.contains(pots)) {
                            list.add(pots);
                        }
                    }
                }
            }
            for (String nbt : FlyBuff.nms.getItemBuffs(i)) {
                if (!XMap.nbt_lore.contains(nbt)) continue;
                for (String pots : config.getStringList("nbteffect." + nbt)) {
                    if (!pots.startsWith("[attack-skill] ")) continue;
                    pots = pots.substring(15);
                    if (!list.contains(pots)) {
                        list.add(pots);
                    }
                }
            }
        }
        return list;
    }

    public static List<String> getMineSkills(Player player) {
        List<String> list = new ArrayList<>();
        Inventory inv = player.getInventory();
        ItemStack i1 = inv.getItem(36);
        ItemStack i2 = inv.getItem(37);
        ItemStack i3 = inv.getItem(38);
        ItemStack i4 = inv.getItem(39);
        ItemStack i5 = inv.getItem(40);
        ItemStack i6 = player.getItemInHand();
        ItemStack[] is = {i1, i2, i3, i4, i5, i6};
        for (ItemStack i : is) {
            if (i == null || i.getType().equals(Material.AIR)) continue;
            ItemMeta meta = i.getItemMeta();
            if (meta.getLore() != null && meta.getLore().size() != 0) {
                for (String str : meta.getLore()) {
                    if (!XMap.lore.contains(Color.uncolor(str))) continue;
                    for (String pots : config.getStringList("effect." + Color.uncolor(str))) {
                        if (!pots.startsWith("[mine-skill] ")) continue;
                        pots = pots.substring(13);
                        if (!list.contains(pots)) {
                            list.add(pots);
                        }
                    }
                }
            }
            for (String nbt : FlyBuff.nms.getItemBuffs(i)) {
                if (!XMap.nbt_lore.contains(nbt)) continue;
                for (String pots : config.getStringList("nbteffect." + nbt)) {
                    if (!pots.startsWith("[mine-skill] ")) continue;
                    pots = pots.substring(13);
                    if (!list.contains(pots)) {
                        list.add(pots);
                    }
                }
            }
        }
        return list;
    }

    public static List<String> getPlaceSkills(Player player) {
        List<String> list = new ArrayList<>();
        Inventory inv = player.getInventory();
        ItemStack i1 = inv.getItem(36);
        ItemStack i2 = inv.getItem(37);
        ItemStack i3 = inv.getItem(38);
        ItemStack i4 = inv.getItem(39);
        ItemStack i5 = inv.getItem(40);
        ItemStack i6 = player.getItemInHand();
        ItemStack[] is = {i1, i2, i3, i4, i5, i6};
        for (ItemStack i : is) {
            if (i == null || i.getType().equals(Material.AIR)) continue;
            ItemMeta meta = i.getItemMeta();
            if (meta.getLore() != null && meta.getLore().size() != 0) {
                for (String str : meta.getLore()) {
                    if (!XMap.lore.contains(Color.uncolor(str))) continue;
                    for (String pots : config.getStringList("effect." + Color.uncolor(str))) {
                        if (!pots.startsWith("[place-skill] ")) continue;
                        pots = pots.substring(14);
                        if (!list.contains(pots)) {
                            list.add(pots);
                        }
                    }
                }
            }
            for (String nbt : FlyBuff.nms.getItemBuffs(i)) {
                if (!XMap.nbt_lore.contains(nbt)) continue;
                for (String pots : config.getStringList("nbteffect." + nbt)) {
                    if (!pots.startsWith("[place-skill] ")) continue;
                    pots = pots.substring(14);
                    if (!list.contains(pots)) {
                        list.add(pots);
                    }
                }
            }
        }
        return list;
    }

    public static List<String> getRClickSkills(Player player) {
        List<String> list = new ArrayList<>();
        Inventory inv = player.getInventory();
        ItemStack i1 = inv.getItem(36);
        ItemStack i2 = inv.getItem(37);
        ItemStack i3 = inv.getItem(38);
        ItemStack i4 = inv.getItem(39);
        ItemStack i5 = inv.getItem(40);
        ItemStack i6 = player.getItemInHand();
        ItemStack[] is = {i1, i2, i3, i4, i5, i6};
        for (ItemStack i : is) {
            if (i == null || i.getType().equals(Material.AIR)) continue;
            ItemMeta meta = i.getItemMeta();
            if (meta.getLore() != null && meta.getLore().size() != 0) {
                for (String str : meta.getLore()) {
                    if (!XMap.lore.contains(Color.uncolor(str))) continue;
                    for (String pots : config.getStringList("effect." + Color.uncolor(str))) {
                        if (!pots.startsWith("[rclick-skill] ")) continue;
                        pots = pots.substring(15);
                        if (!list.contains(pots)) {
                            list.add(pots);
                        }
                    }
                }
            }
            for (String nbt : FlyBuff.nms.getItemBuffs(i)) {
                if (!XMap.nbt_lore.contains(nbt)) continue;
                for (String pots : config.getStringList("nbteffect." + nbt)) {
                    if (!pots.startsWith("[rclick-skill] ")) continue;
                    pots = pots.substring(15);
                    if (!list.contains(pots)) {
                        list.add(pots);
                    }
                }
            }
        }
        return list;
    }

    public static List<String> getBowHitSkills(Player player) {
        List<String> list = new ArrayList<>();
        Inventory inv = player.getInventory();
        ItemStack i1 = inv.getItem(36);
        ItemStack i2 = inv.getItem(37);
        ItemStack i3 = inv.getItem(38);
        ItemStack i4 = inv.getItem(39);
        ItemStack i5 = inv.getItem(40);
        ItemStack i6 = player.getItemInHand();
        ItemStack[] is = {i1, i2, i3, i4, i5, i6};
        for (ItemStack i : is) {
            if (i == null || i.getType().equals(Material.AIR)) continue;
            ItemMeta meta = i.getItemMeta();
            if (meta.getLore() != null && meta.getLore().size() != 0) {
                for (String str : meta.getLore()) {
                    if (!XMap.lore.contains(Color.uncolor(str))) continue;
                    for (String pots : config.getStringList("effect." + Color.uncolor(str))) {
                        if (!pots.startsWith("[bowhit-skill] ")) continue;
                        pots = pots.substring(15);
                        if (!list.contains(pots)) {
                            list.add(pots);
                        }
                    }
                }
            }
            for (String nbt : FlyBuff.nms.getItemBuffs(i)) {
                if (!XMap.nbt_lore.contains(nbt)) continue;
                for (String pots : config.getStringList("nbteffect." + nbt)) {
                    if (!pots.startsWith("[bowhit-skill] ")) continue;
                    pots = pots.substring(15);
                    if (!list.contains(pots)) {
                        list.add(pots);
                    }
                }
            }
        }
        return list;
    }

    public static List<String[]> getJSBuffs(Player player) {
        List<String[]> list = new ArrayList<>();
        Inventory inv = player.getInventory();
        ItemStack i1 = inv.getItem(36);
        ItemStack i2 = inv.getItem(37);
        ItemStack i3 = inv.getItem(38);
        ItemStack i4 = inv.getItem(39);
        ItemStack i5 = inv.getItem(40);
        ItemStack i6 = player.getItemInHand();
        ItemStack[] is = {i1, i2, i3, i4, i5, i6};
        for (ItemStack i : is) {
            if (i == null || i.getType().equals(Material.AIR)) continue;
            ItemMeta meta = i.getItemMeta();
            if (meta.getLore() != null && meta.getLore().size() != 0) {
                for (String str : meta.getLore()) {
                    if (!XMap.lore.contains(Color.uncolor(str))) continue;
                    for (String pots : config.getStringList("effect." + Color.uncolor(str))) {
                        if (!pots.startsWith("[js] ")) continue;
                        pots = pots.substring(5);
                        if (!list.contains(pots)) {
                            list.add(new String[]{pots, Color.uncolor(str)});
                        }
                    }
                }
            }
            for (String nbt : FlyBuff.nms.getItemBuffs(i)) {
                if (!XMap.nbt_lore.contains(nbt)) continue;
                for (String pots : config.getStringList("nbteffect." + nbt)) {
                    if (!pots.startsWith("[js] ")) continue;
                    pots = pots.substring(5);
                    if (!list.contains(pots)) {
                        list.add(new String[]{pots, nbt});
                    }
                }
            }
        }
        return list;
    }

    public static List<BuffParticle> getParticles(Player player) {
        List<BuffParticle> list = new ArrayList<>();
        Inventory inv = player.getInventory();
        ItemStack i1 = inv.getItem(36);
        ItemStack i2 = inv.getItem(37);
        ItemStack i3 = inv.getItem(38);
        ItemStack i4 = inv.getItem(39);
        ItemStack i5 = inv.getItem(40);
        ItemStack i6 = player.getItemInHand();
        ItemStack[] is = {i1, i2, i3, i4, i5, i6};
        for (ItemStack i : is) {
            if (i == null || i.getType().equals(Material.AIR)) continue;
            ItemMeta meta = i.getItemMeta();
            if (meta.getLore() != null && meta.getLore().size() != 0) {
                for (String lore : meta.getLore()) {
                    if (XMap.particles.containsKey(lore)) {
                        list.addAll(XMap.particles.get(lore));
                    }
                }
            }
            for (String nbt : FlyBuff.nms.getItemBuffs(i)) {
                if (!XMap.nbt_particles.containsKey(nbt)) continue;
                list.addAll(XMap.nbt_particles.get(nbt));
            }

        }
        return list;
    }

    public static ItemStack simpleSkull(ItemStack head, String value) {
        UUID uuid = UUID.nameUUIDFromBytes(value.getBytes());
        return Bukkit.getUnsafe().modifyItemStack(head, "{SkullOwner:{Id:\"" + uuid + "\",Properties:{textures:[{Value:\"" + value + "\"}]}}}");
    }

    public static boolean isPreview() {
        return FlyBuff.instance.getDescription().getVersion().endsWith("SNAPSHOT") || FlyBuff.instance.getDescription().getVersion().endsWith("BETA") || FlyBuff.instance.getDescription().getVersion().endsWith("ALPHA") || FlyBuff.instance.getDescription().getVersion().contains("RC") || FlyBuff.instance.getDescription().getVersion().contains("PRE");
    }

    private static void logLogo(String text) {
        for (String line : text.split("\n")) {
            logger.info(line);
        }
    }

    private void setupNMS() {
        String version = Bukkit.getServer().getClass().getPackage()
                .getName().replace("org.bukkit.craftbukkit.", "");
        logger.info("[FlyBuff] 服务器版本: " + version + " 正在尝试初始化NMS组件");
        switch (version) {
            case "v1_8_R1": {
                nms = new NMS_1_8_R1();
                break;
            }
            case "v1_8_R2": {
                nms = new NMS_1_8_R2();
                break;
            }
            case "v1_8_R3": {
                nms = new NMS_1_8_R3();
                break;
            }
            case "v1_9_R1": {
                nms = new NMS_1_9_R1();
                break;
            }
            case "v1_9_R2": {
                nms = new NMS_1_9_R2();
                break;
            }
            case "v1_10_R1": {
                nms = new NMS_1_10_R1();
                break;
            }
            case "v1_11_R1": {
                nms = new NMS_1_11_R1();
                break;
            }
            case "v1_12_R1": {
                nms = new NMS_1_12_R1();
                break;
            }
            case "v1_13_R1": {
                nms = new NMS_1_13_R1();
                break;
            }
            case "v1_13_R2": {
                nms = new NMS_1_13_R2();
                break;
            }
            case "v1_14_R1": {
                nms = new NMS_1_14_R1();
                break;
            }
            case "v1_15_R1": {
                nms = new NMS_1_15_R1();
                break;
            }
            case "v1_16_R1": {
                nms = new NMS_1_16_R1();
                break;
            }
            case "v1_16_R2": {
                nms = new NMS_1_16_R2();
                break;
            }
            case "v1_16_R3": {
                nms = new NMS_1_16_R3();
                break;
            }
            case "v1_17_R1": {
                nms = new NMS_1_17_R1();
                break;
            }
            case "v1_18_R1": {
                nms = new NMS_1_18_R1();
                break;
            }
            case "v1_18_R2": {
                nms = new NMS_1_18_R2();
                break;
            }
            case "v1_19_R1": {
                nms = new NMS_1_19_R1();
                break;
            }
            default: {
                logger.warning("[FlyBuff] 无法找到对应服务器版本的NMS映射组件 请联系作者!");
                break;
            }
        }
    }

    @Override
    public void onEnable() {
        if (!getDescription().getAuthors().contains("FlyProject")) {
            getLogger().info("You are using unoffical release version");
            getLogger().info("The version you are using violates the open source license");
            getLogger().info("Plugin Disabled...");
            setEnabled(false);
            return;
        }
        instance = this;
        logger = Bukkit.getServer().getLogger();
        saveDefaultConfig();
        config = getConfig();
        scriptEngineManager = new ScriptEngineManager();
        if (Double.parseDouble(System.getProperty("java.class.version"))<55.0){
            JavaScriptEngine.engine = "javascript";
        } else {
            scriptEngineManager.registerEngineName("flybuffJSEngine", new NashornScriptEngineFactory());
        }
        if (getServer().getVersion().contains("1.12") || getServer().getVersion().contains("1.11") || getServer().getVersion().contains("1.10") || getServer().getVersion().contains("1.9") || getServer().getVersion().contains("1.8") || getServer().getVersion().contains("1.7")) {
            saveFile("items-low.yml", false);
            File il = new File(getDataFolder() + "/items-low.yml");
            try {
                Files.copy(il.toPath(), new File(getDataFolder() + "/items.yml").toPath());
            } catch (IOException ignored) {
            }
        } else {
            saveFile("items.yml", false);
        }
        saveFile("particle.yml", false);
        saveFile("js/example.js", false);
        item = YamlConfiguration.loadConfiguration(new File(getDataFolder() + "/items.yml"));
        particle = YamlConfiguration.loadConfiguration(new File(getDataFolder() + "/particle.yml"));
        logLogo("\n" +
                "    ________      ____        ________\n" +
                "   / ____/ /_  __/ __ )__  __/ __/ __/\n" +
                "  / /_  / / / / / __  / / / / /_/ /_  \n" +
                " / __/ / / /_/ / /_/ / /_/ / __/ __/  \n" +
                "/_/   /_/\\__, /_____/\\__,_/_/ /_/     \n" +
                "        /____/                        \n\n" +
                "(c) Copyright 2022 FlyProject\n" +
                "This software using GPLv3 Open Source License\n" +
                "FlyProject(QQ: 3491932059) All Rights Reserved\n" +
                "Github: https://github.com/killerprojecte/FlyBuff\n" +
                "Version: " + getDescription().getVersion() + "\n" +
                "Type: " + (isPreview() ? "Preview" : "Release") + "\n" +
                "Using Modified Bukkit Release Version May make some unknown problems\n" +
                "Not supported (Forge + Bukkit) (Fabric + Bukkit) & other similar"
        );
        setupNMS();
        FlyTask.runTaskAsync(ConfigUpdater::update);
        Bukkit.getPluginManager().registerEvents(new ClickWorkbench(), this);
        Bukkit.getPluginManager().registerEvents(new GuiClick(), this);
        Bukkit.getPluginManager().registerEvents(new MythicListener(), this);
        getCommand("flybuff").setExecutor(new BuffCommand());
        getCommand("buffremove").setExecutor(new RemoveCommand());
        getCommand("buffitem").setExecutor(new ItemCommand());
        PotionSender.load();
        if (!setupEconomy()) {
            hasVault = false;
            System.err.println("[FlyBuff-Payment] 您的服务器中并没有安装 Vault 插件， 对应的经济功能将被禁用");
        }
        if (!setupPoints()) {
            hasPoints = false;
            System.err.println("[FlyBuff-Payment] 您的服务器中并没有安装 PlayerPoints 插件， 对应的经济功能将被禁用");
        }
        if (!hasVault && !hasPoints) {
            System.err.println("[FlyBuff-Payment] 您的服务器上没有任何可用的经济插件 经济功能将完全禁用");
        }
        new BukkitRunnable() {
            @Override
            public void run() {
                Version.check();
            }
        }.runTaskLaterAsynchronously(this, 1200L);
        XMap.load();
        if (Bukkit.getPluginManager().getPlugin("MythicMobs") != null) {
            String mythicversion = Bukkit.getPluginManager().getPlugin("MythicMobs").getDescription().getVersion();
            if (mythicversion.startsWith("4.")) {
                mythicHook = new Mythic4Hook();
            } else if (mythicversion.startsWith("5.")) {
                mythicHook = new Mythic5Hook();
            } else {
                getLogger().warning("You are using un-support MythicMobs Version!");
            }
        }
        new Metrics(this, 16381);
        //JavaScriptEngine.runScript("example", "unknow", null, "test");
        // Plugin startup logic
    }

    private boolean setupPoints() {
        if (Bukkit.getPluginManager().isPluginEnabled("PlayerPoints")) {
            PaymentCore.points = ((PlayerPoints) Bukkit.getPluginManager().getPlugin("PlayerPoints")).getAPI();
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

    public void disable() {
        setEnabled(false);
    }

    public static void excuteCommand(String cmd){
        Bukkit.getScheduler().runTask(FlyBuff.instance,() -> {
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(),cmd);
        });
    }
}
