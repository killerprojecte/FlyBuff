package flyproject.flybuff.nms;

import net.minecraft.server.v1_9_R1.NBTTagCompound;
import net.minecraft.server.v1_9_R1.NBTTagList;
import net.minecraft.server.v1_9_R1.NBTTagString;
import org.bukkit.craftbukkit.v1_9_R1.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class NMS_1_9_R1 extends NbtManager{
    @Override
    public List<String> getItemBuffs(ItemStack item){
        net.minecraft.server.v1_9_R1.ItemStack i = CraftItemStack.asNMSCopy(item);
        NBTTagCompound nbt= i.getTag();
        if (nbt==null){
            nbt = new NBTTagCompound();
        }
        List<String> list = new ArrayList<>();
        if (nbt.hasKey("FlyBuff")){
            NBTTagList nlist = nbt.getList("FlyBuff",8);
            for (int s = 0;s<nlist.size();s++){
                list.add(nlist.getString(s));
            }
        }
        return list;
    }

    @Override
    public ItemStack addBuff(ItemStack item, String buff) {
        net.minecraft.server.v1_9_R1.ItemStack i = CraftItemStack.asNMSCopy(item);
        NBTTagCompound nbt= i.getTag();
        if (nbt==null){
            nbt = new NBTTagCompound();
        }
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
