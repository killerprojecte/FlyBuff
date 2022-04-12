package flyproject.flybuff.gui;

import flyproject.flybuff.FlyBuff;
import flyproject.flybuff.utils.BHolder;
import flyproject.flybuff.utils.Color;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class GuiClick implements Listener {
    @EventHandler
    public void onClick(InventoryClickEvent event){
        if (!(event.getClickedInventory().getHolder() instanceof BHolder)){return;}
        event.setCancelled(true);
        if (event.getCurrentItem()==null || event.getCurrentItem().getType().equals(Material.AIR)) return;
        Player p = (Player) event.getWhoClicked();
        BHolder holder = (BHolder) event.getClickedInventory().getHolder();
        if (event.getSlot()==45 && holder.getItems().size()>54 && !(holder.getPage()==1)){
            int back = holder.getPage() - 1;
            if (back==1){
                event.getClickedInventory().setItem(45,new ItemStack(Material.AIR));
                for (int i = 0;i<45;i++){
                    event.getClickedInventory().setItem(i,holder.getItems().get(i));
                }
            } else {
                int start = back * 45;
                for (int i = start;i<(start + 45);i++){
                    if (holder.getItems().get(i)==null)break;
                    event.getClickedInventory().setItem(i,holder.getItems().get(i));
                }
            }
            holder.setPage(back);
        } else if (event.getSlot()==53 && holder.getItems().size()>54 && !(holder.getPage()==1)){
            int next = holder.getPage() + 1;
            int start = next * 45;
            boolean haspage = true;
            for (int i = start;i<(start + 45);i++){
                if (holder.getItems().get(i)==null && i!=start)break;
                if (holder.getItems().get(i)==null && i==start){
                    haspage = false;
                    break;
                }
                event.getClickedInventory().setItem(i,holder.getItems().get(i));
            }
            if (haspage) holder.setPage(next);
            if (!haspage){
                event.getClickedInventory().setItem(53,new ItemStack(Material.AIR));
            }
            int nnext = next + 1;
            int nstart = nnext * 45;
            if (holder.getItems().get(nstart)==null){
                event.getClickedInventory().setItem(53,new ItemStack(Material.AIR));
            }
        } else {
            if (hasSpace(p)){
                p.getInventory().addItem(tonormal(event.getCurrentItem()));
            } else {
                p.sendMessage(Color.color(FlyBuff.config.getString("nospace")));
            }
        }
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
