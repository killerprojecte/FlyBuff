package flyproject.flybuff.utils;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class BHolder implements InventoryHolder {
    int p;
    List<ItemStack> i;
    @Override
    public Inventory getInventory() {
        return null;
    }

    public BHolder(int page){
        p = page;
    }

    public int getPage(){return p;}

    public List<ItemStack> getItems(){
        return i;
    }
    public void setItems(List<ItemStack> items){
        i = items;
    }
    public void setPage(int page){
        p = page;
    }
}
