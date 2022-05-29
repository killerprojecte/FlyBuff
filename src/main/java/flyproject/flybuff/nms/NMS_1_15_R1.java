package flyproject.flybuff.nms;

import net.minecraft.server.v1_15_R1.NBTBase;
import net.minecraft.server.v1_15_R1.NBTTagCompound;
import net.minecraft.server.v1_15_R1.NBTTagList;
import net.minecraft.server.v1_15_R1.NBTTagString;
import org.bukkit.craftbukkit.v1_15_R1.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class NMS_1_15_R1 extends NbtManager{
    @Override
    public List<String> getItemBuffs(ItemStack item){
        net.minecraft.server.v1_15_R1.ItemStack i = CraftItemStack.asNMSCopy(item);
        NBTTagCompound nbt= i.getOrCreateTag();
        List<String> list = new ArrayList<>();
        if (nbt.hasKey("FlyBuff")){
            NBTTagList nlist = nbt.getList("FlyBuff",8);
            for (NBTBase n : nlist){
                list.add(n.asString());
            }
        }
        return list;
    }

    @Override
    public ItemStack addBuff(ItemStack item, String buff) {
        net.minecraft.server.v1_15_R1.ItemStack i = CraftItemStack.asNMSCopy(item);
        NBTTagCompound nbt= i.getOrCreateTag();
        NBTTagList nlist;
        if (nbt.hasKey("FlyBuff")){
            nlist = nbt.getList("FlyBuff",8);
            nlist.add(NBTTagString.a(buff));
        } else {
            nlist = new NBTTagList();
            nlist.add(NBTTagString.a(buff));
        }
        nbt.set("FlyBuff",nlist);
        i.setTag(nbt);
        return CraftItemStack.asBukkitCopy(i);
    }

    @Override
    public ItemStack removeBuff(ItemStack item, String buff){
        net.minecraft.server.v1_15_R1.ItemStack i = CraftItemStack.asNMSCopy(item);
        NBTTagCompound nbt= i.getOrCreateTag();
        NBTTagList nlist;
        if (nbt.hasKey("FlyBuff")){
            nlist = nbt.getList("FlyBuff",8);
            nlist.remove(buff);
        } else {
            nlist = new NBTTagList();
        }
        nbt.set("FlyBuff",nlist);
        i.setTag(nbt);
        return CraftItemStack.asBukkitCopy(i);
    }
}
