package flyproject.flybuff.nms;

import net.minecraft.server.v1_13_R2.NBTBase;
import net.minecraft.server.v1_13_R2.NBTTagCompound;
import net.minecraft.server.v1_13_R2.NBTTagList;
import net.minecraft.server.v1_13_R2.NBTTagString;
import org.bukkit.craftbukkit.v1_13_R2.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class NMS_1_13_R2 extends NbtManager{
    @Override
    public List<String> getItemBuffs(ItemStack item){
        net.minecraft.server.v1_13_R2.ItemStack i = CraftItemStack.asNMSCopy(item);
        NBTTagCompound nbt= i.getOrCreateTag();
        List<String> list = new ArrayList<>();
        if (nbt.hasKey("FlyBuff")){
            NBTTagList nlist = nbt.getList("FlyBuff",8);
            for (NBTBase n : nlist){
                list.add(n.toString());
            }
        }
        return list;
    }

    @Override
    public ItemStack addBuff(ItemStack item, String buff) {
        net.minecraft.server.v1_13_R2.ItemStack i = CraftItemStack.asNMSCopy(item);
        NBTTagCompound nbt= i.getOrCreateTag();
        NBTTagList nlist;
        if (nbt.hasKey("FlyBuff")){
            nlist = nbt.getList("FlyBuff",8);
            nlist.add(new NBTTagString(buff));
        } else {
            nlist = new NBTTagList();
            nlist.add(new NBTTagString(buff));
        }
        nbt.set("FlyBuff",nlist);
        i.setTag(nbt);
        return CraftItemStack.asBukkitCopy(i);
    }
}
