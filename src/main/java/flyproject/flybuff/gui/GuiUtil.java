package flyproject.flybuff.gui;

import flyproject.flybuff.FlyBuff;
import flyproject.flybuff.utils.BHolder;
import flyproject.flybuff.utils.Color;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class GuiUtil {
    public static Inventory create(){
        Inventory inv = Bukkit.createInventory(new BHolder(),6*9, Color.color(FlyBuff.config.getString("title")));
        ItemStack a = new ItemStack(Material.ARROW);
        ItemStack b = new ItemStack(Material.ARROW);
        ItemMeta ai = a.getItemMeta();
        ItemMeta bi = b.getItemMeta();
        ai.setDisplayName(Color.color(FlyBuff.config.getString("nextpage")));
        inv.setItem(45,a);
        return inv;
    }
}
