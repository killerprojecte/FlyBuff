package flyproject.flybuff.listener;

import flyproject.flybuff.FlyBuff;
import flyproject.flybuff.utils.Color;
import flyproject.flybuff.utils.XMap;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class ClickWorkbench implements Listener {
    private static void sort(ItemStack item) {
        ItemMeta im = item.getItemMeta();
        List<String> lores = im.getLore();
        List<String> b = new ArrayList<>();
        List<String> c = new ArrayList<>();
        for (String a : lores) {
            boolean ok = false;
            for (String key : FlyBuff.config.getConfigurationSection("gem").getKeys(false)) {
                String l = Color.color(FlyBuff.config.getString("gem." + key));
                if (a.equals(l)) {
                    c.add(l);
                    ok = true;
                    break;
                }
            }
            if (!ok) {
                b.add(a);
            }
        }
        b.addAll(c);
        im.setLore(b);
        item.setItemMeta(im);
    }

    private static String place(List<String> lore) {
        for (String key : FlyBuff.config.getConfigurationSection("gem").getKeys(false)) {
            if (lore.contains(Color.color(key))) {
                return FlyBuff.config.getString("gem." + key);
            }
        }
        return null;
    }

    private static boolean whitelist(Material type) {
        String n = type.toString();
        return XMap.whitelists.contains(n.toUpperCase());
    }

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        ItemStack buff = event.getCursor();
        Player p = (Player) event.getWhoClicked();
        if (event.getClick().equals(ClickType.LEFT) && event.getClickedInventory() != null && event.getClickedInventory().getType().equals(InventoryType.valueOf(FlyBuff.config.getString("workspace"))) && buff != null && !buff.getType().equals(Material.AIR) && buff.getItemMeta().getLore() != null && buff.getItemMeta().getLore().size() != 0 && event.getCurrentItem() != null && !event.getCurrentItem().getType().equals(Material.AIR)) {
            if (whitelist(event.getCurrentItem().getType())) {
                ItemMeta buffim = buff.getItemMeta();
                ItemStack click = event.getCurrentItem();
                ItemMeta meta = click.getItemMeta();
                String place = place(buffim.getLore());
                if (place == null) return;
                List<String> lore = new ArrayList<>();
                if (!(meta.getLore() == null)) {
                    lore.addAll(meta.getLore());
                }
                if (lore.contains(Color.color(place))) {
                    p.sendMessage(Color.color(FlyBuff.config.getString("error")));
                    return;
                }
                if (!checklimit(lore)) {
                    p.sendMessage(Color.color(FlyBuff.config.getString("maxinstalled")));
                    return;
                }
                lore.add(Color.color(place));
                buff.setAmount(buff.getAmount() - 1);
                meta.setLore(lore);
                click.setItemMeta(meta);
                p.sendMessage(Color.color(FlyBuff.config.getString("finish")));
                p.playSound(p.getLocation(), Sound.valueOf(FlyBuff.config.getString("sound")), 1.0f, 1.0f);
                sort(click);
            } else {
                if (FlyBuff.config.getStringList("bypass").contains(event.getCurrentItem().getType().toString())) return;
                p.sendMessage(Color.color(FlyBuff.config.getString("invaild")));
            }
        }
    }
    private static boolean checklimit(List<String> lore){
        int installed = 0;
        int limit = FlyBuff.config.getInt("limit");
        if (limit==-1) return true;
        for (String l : lore){
            if (XMap.installs.contains(Color.uncolor(l))) installed++;
        }
        return installed < limit;
    }
}
