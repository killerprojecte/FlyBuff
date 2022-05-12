package flyproject.flybuff.utils;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BHolder implements InventoryHolder {
    public Map<Integer, String> origin = new HashMap<>();
    int p;
    List<ItemStack> i;

    public BHolder(int page) {
        p = page;
    }

    @Override
    public Inventory getInventory() {
        return null;
    }

    public int getPage() {
        return p;
    }

    public void setPage(int page) {
        p = page;
    }

    public List<ItemStack> getItems() {
        return i;
    }

    public void setItems(List<ItemStack> items) {
        i = items;
    }
}
