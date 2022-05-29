package flyproject.flybuff.nms;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagString;
import org.bukkit.craftbukkit.v1_17_R1.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class NMS_1_17_R1 extends NbtManager{
    @Override
    public List<String> getItemBuffs(ItemStack item){
        net.minecraft.world.item.ItemStack i = CraftItemStack.asNMSCopy(item);
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
        net.minecraft.world.item.ItemStack i = CraftItemStack.asNMSCopy(item);
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
        net.minecraft.world.item.ItemStack i = CraftItemStack.asNMSCopy(item);
        NBTTagCompound nbt= i.getOrCreateTag();
        NBTTagList nlist;
        NBTTagList list = new NBTTagList();
        if (nbt.hasKey("FlyBuff")){
            nlist = nbt.getList("FlyBuff",8);
            for (int s = 0;s<nlist.size();s++){
                String str = nlist.getString(s);
                if (str.equals(buff)) continue;
                list.add(NBTTagString.a(str));
            }
        }
        nbt.set("FlyBuff",list);
        i.setTag(nbt);
        return CraftItemStack.asBukkitCopy(i);
    }
}
