package flyproject.flybuff.nms;

import org.bukkit.inventory.ItemStack;

import java.util.List;

public abstract class NbtManager {
    public abstract List<String> getItemBuffs(ItemStack item);

    public abstract ItemStack addBuff(ItemStack item, String buff);

    public abstract ItemStack removeBuff(ItemStack item, String buff);
}
