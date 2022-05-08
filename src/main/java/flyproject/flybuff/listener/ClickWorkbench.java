package flyproject.flybuff.listener;

import flyproject.flybuff.FlyBuff;
import flyproject.flybuff.utils.Color;
import flyproject.flybuff.utils.FlyTask;
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
    @EventHandler
    public void onClick(InventoryClickEvent event){
        ItemStack buff = event.getCursor();
        Player p = (Player) event.getWhoClicked();
            if (event.getClick().equals(ClickType.LEFT) && event.getClickedInventory() != null && event.getClickedInventory().getType().equals(InventoryType.WORKBENCH) && buff != null && !buff.getType().equals(Material.AIR) && buff.getItemMeta().getLore()!=null && buff.getItemMeta().getLore().size()!=0 && event.getCurrentItem()!=null && !event.getCurrentItem().getType().equals(Material.AIR)){
                if (whitelist(event.getCurrentItem().getType())){
                    ItemMeta buffim = buff.getItemMeta();
                    ItemStack click = event.getCurrentItem();
                    ItemMeta meta = click.getItemMeta();
                    String place = place(buffim.getLore());
                    if (place==null) return;
                    List<String> lore = new ArrayList<>();
                    if (!(meta.getLore()==null)){
                        lore.addAll(meta.getLore());
                    }
                    if (lore.contains(Color.color(place))){
                        p.sendMessage(Color.color(FlyBuff.config.getString("error")));
                        return;
                    }
                    lore.add(Color.color(place));
                    buff.setAmount(buff.getAmount() - 1);
                    meta.setLore(lore);
                    click.setItemMeta(meta);
                    p.sendMessage(Color.color(FlyBuff.config.getString("finish")));
                    p.playSound(p.getLocation(), Sound.valueOf(FlyBuff.config.getString("sound")),1.0f,1.0f);
                    sort(click);
                } else {
                    p.sendMessage(Color.color(FlyBuff.config.getString("invaild")));
                }
            }
    }
    private static void sort(ItemStack item){
        ItemMeta im = item.getItemMeta();
        List<String> lores = im.getLore();
        List<String> b = new ArrayList<>();
        List<String> c = new ArrayList<>();
        for (String a : lores){
            boolean ok = false;
            for (String key : FlyBuff.config.getConfigurationSection("gem").getKeys(false)){
                String l = Color.color(FlyBuff.config.getString("gem." + key));
                if (a.equals(l)){
                    c.add(l);
                    ok = true;
                    break;
                }
            }
            if (!ok){
                b.add(a);
            }
        }
        b.addAll(c);
        im.setLore(b);
        item.setItemMeta(im);
    }
    private static String place(List<String> lore){
        for (String key : FlyBuff.config.getConfigurationSection("gem").getKeys(false)){
            if (lore.contains(Color.color(key))){
                return FlyBuff.config.getString("gem." + key);
            }
        }
        return null;
    }
    private static boolean whitelist(Material type){
        String n = type.toString();
        boolean back = false;
        for (String a : FlyBuff.config.getStringList("whitelist")){
            if (n.equalsIgnoreCase(a)) {
                back = true;
                break;
            }
        }
        return back;
    }
}
