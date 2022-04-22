package flyproject.flybuff.gui;

import flyproject.flybuff.FlyBuff;
import flyproject.flybuff.utils.BHolder;
import flyproject.flybuff.utils.Color;
import flyproject.flybuff.utils.FlyTask;
import flyproject.flybuff.utils.PaymentCore;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class GuiClick implements Listener {
    @EventHandler
    public void onClick(InventoryClickEvent event){
        if (event.getClickedInventory()==null) return;
        if (!(event.getClickedInventory().getHolder() instanceof BHolder)){return;}
        event.setCancelled(true);
        if (event.getCurrentItem()==null || event.getCurrentItem().getType().equals(Material.AIR)) return;
        Player p = (Player) event.getWhoClicked();
        Inventory inv = event.getClickedInventory();
        BHolder holder = (BHolder) inv.getHolder();
        FlyTask.runTaskAsync(() -> {
            {
                if (event.getSlot()==45 && holder.getItems().size()>54 && holder.getPage()!=1){
                    int back = holder.getPage() - 1;
                    if (back==1){
                        inv.setItem(45,new ItemStack(Material.AIR));
                        for (int i = 0;i<45;i++){
                            inv.setItem(i,holder.getItems().get(i));
                        }
                    } else {
                        int start = back * 45;
                        for (int i = start;i<(start + 45);i++){
                            if (holder.getItems().size()<=i)break;
                            inv.setItem(i,holder.getItems().get(i));
                        }
                    }
                    holder.setPage(back);
                    ItemStack b = new ItemStack(Material.ARROW);
                    ItemMeta bi = b.getItemMeta();
                    bi.setDisplayName(Color.color(FlyBuff.config.getString("nextpage")));
                    b.setItemMeta(bi);
                    inv.setItem(53,b);
                } else if (event.getSlot()==53 && holder.getItems().size()>54){
                    int next = holder.getPage() + 1;
                    int start = (next * 45) -44;
                    for (int i = 0;i<45;i++){
                        inv.setItem(i,new ItemStack(Material.AIR));
                    }
                    for (int i = start;i<(start+45);i++){
                        if (holder.getItems().size()<=i && i!=start)break;
                        if (holder.getItems().size()<=i && i==start){
                            inv.setItem(53,new ItemStack(Material.AIR));
                            break;
                        }
                        inv.setItem(i - ((next-1)*46),holder.getItems().get(i));
                    }
                    holder.setPage(next);
                    int nnext = next + 1;
                    int nstart = nnext * 45;
                    if (holder.getItems().size()<=nstart){
                        inv.setItem(53,new ItemStack(Material.AIR));
                    }
                    ItemStack a = new ItemStack(Material.ARROW);
                    ItemMeta ai = a.getItemMeta();
                    ai.setDisplayName(Color.color(FlyBuff.config.getString("backpage")));
                    a.setItemMeta(ai);
                    inv.setItem(45,a);
                } else {
                    if (hasSpace(p)){
                        if (((inv.getItem(45)!=null && inv.getItem(45).getType().equals(Material.ARROW)) || inv.getItem(53)!=null && inv.getItem(53).getType().equals(Material.ARROW)) &&  (event.getSlot()==45 || event.getSlot()==46 || event.getSlot() == 47 || event.getSlot() == 48 || event.getSlot() == 49 || event.getSlot() == 50 || event.getSlot() == 51 || event.getSlot() == 52 || event.getSlot() == 53)){
                            event.setCancelled(true);
                            p.closeInventory();
                            p.getInventory().addItem(event.getCurrentItem());
                            return;
                        }
                        if (!PaymentCore.pay(p.getDisplayName())) return;
                        p.getInventory().addItem(tonormal(event.getCurrentItem()));
                        for (String key : FlyBuff.item.getConfigurationSection("gems").getKeys(false)){
                            if (p.getItemInHand().getItemMeta().getLore().contains(Color.color(key))){
                                for (String ls : FlyBuff.item.getStringList("gems." + key + ".lores")){
                                    if (event.getCurrentItem().getItemMeta().getLore().contains(Color.color(ls))){
                                        ItemMeta im = p.getItemInHand().getItemMeta();
                                        List<String> lores = new ArrayList<>();
                                        for (String str : im.getLore()){
                                            if (str.equals(Color.color(key))) continue;
                                            lores.add(str);
                                        }
                                        im.setLore(lores);
                                        p.getItemInHand().setItemMeta(im);
                                    }
                                }
                                break;
                            }
                        }
                        p.closeInventory();
                    } else {
                        p.sendMessage(Color.color(FlyBuff.config.getString("nospace")));
                    }
                }
            }
        });
    }

    private static ItemStack tonormal(ItemStack is){
        ItemMeta im = is.getItemMeta();
        if (im.getLore()==null) return is;
        List<String> lore = new ArrayList<>();
        for (int i =0;i<im.getLore().size() - 1;i++){
            lore.add(im.getLore().get(i));
        }
        im.setLore(lore);
        is.setItemMeta(im);
        return is;
    }

    private static boolean hasSpace(Player p) {
        PlayerInventory inv = p.getInventory();
        int amount = 0;
        for (int i = 0; i < 36; i++) {
            ItemStack is = inv.getItem(i);
            if (is != null && is.getType() != Material.AIR) continue;
            amount++;
        }
        return amount>=1;
    }
}