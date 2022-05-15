package flyproject.flybuff.gui;

import flyproject.flybuff.FlyBuff;
import flyproject.flybuff.utils.BHolder;
import flyproject.flybuff.utils.Color;
import flyproject.flybuff.utils.FlyTask;
import flyproject.flybuff.utils.XMap;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GuiUtil {
    public static Inventory create() {
        Inventory inv = Bukkit.createInventory(new BHolder(1), 6 * 9, Color.color(FlyBuff.config.getString("title")));
        ItemStack a = new ItemStack(Material.ARROW);
        ItemStack b = new ItemStack(Material.ARROW);
        ItemMeta ai = a.getItemMeta();
        ItemMeta bi = b.getItemMeta();
        ai.setDisplayName(Color.color(FlyBuff.config.getString("backpage")));
        bi.setDisplayName(Color.color(FlyBuff.config.getString("nextpage")));
        a.setItemMeta(ai);
        b.setItemMeta(bi);
        inv.setItem(45, a);
        inv.setItem(53, b);
        return inv;
    }

    public static void init(Inventory inv, Player player) {
        ItemStack is = player.getItemInHand();
        ItemMeta im = is.getItemMeta();
        BHolder holder = (BHolder) inv.getHolder();
        if (im.getLore() == null || im.getLore().size() == 0) return;
        FlyTask.runTaskAsync(() -> {
            List<Object[]> items = new ArrayList<>();
            for (String lore : im.getLore()) {
                if (!XMap.gems.contains(Color.uncolor(lore))) continue;
                String key = Color.uncolor(lore);
                if (FlyBuff.item.getString("gems." + key + ".mode").equals("stack")) {
                    items.add(new Object[]{FlyBuff.item.getItemStack("gems." + key + ".itemstack"), key});
                } else {
                    String type = FlyBuff.item.getString("gems." + key + ".type");
                    ItemStack nitem;
                    if (type.equals("PLAYER_HEAD") || type.equals("SKULL_ITEM")) {
                        nitem = new ItemStack(Material.valueOf(type), 1, (short) 3);
                        nitem = FlyBuff.simpleSkull(nitem, FlyBuff.item.getString("gems." + key + ".texture"));
                    } else {
                        nitem = new ItemStack(Material.valueOf(type), 1);
                    }
                    ItemMeta nim = nitem.getItemMeta();
                    String display = Color.color(FlyBuff.item.getString("gems." + key + ".display"));
                    List<String> nlore = new ArrayList<>();
                    for (String nl : FlyBuff.item.getStringList("gems." + key + ".lores")) {
                        nlore.add(Color.color(nl));
                    }
                    nlore.add(Color.color(FlyBuff.config.getString("removehelp")));
                    nim.setDisplayName(display);
                    nim.setLore(nlore);
                    nitem.setItemMeta(nim);
                    items.add(new Object[]{nitem,key});
                }
            }
            inv.setItem(45, new ItemStack(Material.AIR));
            if (items.size() <= 54) {
                inv.setItem(45, new ItemStack(Material.AIR));
                inv.setItem(53, new ItemStack(Material.AIR));
                for (int i = 0; i < items.size(); i++) {
                    inv.setItem(i, (ItemStack) items.get(i)[0]);
                    holder.origin.put(i, (String) items.get(i)[1]);
                }
            } else {
                for (int i = 0; i < 45; i++) {
                    inv.setItem(i, (ItemStack) items.get(i)[0]);
                    holder.origin.put(i, (String) items.get(i)[1]);
                }
            }
            List<ItemStack> isl  = new ArrayList<>();
            for (Object[] obj : items){
                isl.add((ItemStack) obj[0]);
            }
            holder.setItems(isl);
        });
    }
}
