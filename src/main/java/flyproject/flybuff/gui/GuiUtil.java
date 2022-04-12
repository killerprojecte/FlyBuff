package flyproject.flybuff.gui;

import flyproject.flybuff.FlyBuff;
import flyproject.flybuff.utils.BHolder;
import flyproject.flybuff.utils.Color;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class GuiUtil {
    public static Inventory create(){
        Inventory inv = Bukkit.createInventory(new BHolder(1),6*9, Color.color(FlyBuff.config.getString("title")));
        ItemStack a = new ItemStack(Material.ARROW);
        ItemStack b = new ItemStack(Material.ARROW);
        ItemMeta ai = a.getItemMeta();
        ItemMeta bi = b.getItemMeta();
        ai.setDisplayName(Color.color(FlyBuff.config.getString("backpage")));
        bi.setDisplayName(Color.color(FlyBuff.config.getString("nextpage")));
        a.setItemMeta(ai);
        b.setItemMeta(bi);
        inv.setItem(45,a);
        inv.setItem(53,b);
        return inv;
    }
    public static void init(Inventory inv, Player player){
        ItemStack is = player.getItemInHand();
        ItemMeta im = is.getItemMeta();
        List<ItemStack> ilist = new ArrayList<>();
        BHolder holder = (BHolder) inv.getHolder();
        if (im.getLore()==null || im.getLore().size()==0) return;
        for (String lore : im.getLore()){
            for (String key : FlyBuff.item.getConfigurationSection("gems").getKeys(false)){
                if (!Color.color(key).equals(lore)) continue;
                if (FlyBuff.item.getString("gems." + key + ".mode").equals("stack")){
                    ilist.add(FlyBuff.item.getItemStack("gems." + key + ".itemstack"));
                } else {
                    String type = FlyBuff.item.getString("gems." + key + ".type");
                    ItemStack nitem;
                    if (type.equals("PLAYER_HEAD") || type.equals("SKULL_ITEM")){
                        nitem = new ItemStack(Material.valueOf(type),1,(short) 3);
                        nitem = FlyBuff.simpleSkull(nitem,FlyBuff.item.getString("gems." + key + ".texture"));
                    } else {
                        nitem = new ItemStack(Material.valueOf(type),1);
                    }
                    ItemMeta nim = nitem.getItemMeta();
                    String display = Color.color(FlyBuff.item.getString("gems." + key + ".display"));
                    List<String> nlore = new ArrayList<>();
                    for (String nl : FlyBuff.item.getStringList("gems." + key + ".lores")){
                        nlore.add(Color.color(nl));
                    }
                    nlore.add(Color.color(FlyBuff.config.getString("removehelp")));
                    nim.setDisplayName(display);
                    nim.setLore(nlore);
                    nitem.setItemMeta(nim);
                    ilist.add(nitem);
                }
            }
        }
        inv.setItem(45,new ItemStack(Material.AIR));
        if (ilist.size()<=54){
            inv.setItem(45,new ItemStack(Material.AIR));
            inv.setItem(53,new ItemStack(Material.AIR));
            for (int i = 0;i<ilist.size();i++){
                inv.setItem(i,ilist.get(i));
            }
        } else {
            for (int i = 0;i<45;i++){
                inv.setItem(i,ilist.get(i));
            }
        }
        holder.setItems(ilist);
    }
}
