package flyproject.flybuff.nms;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import org.bukkit.craftbukkit.v1_18_R2.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class NMS_1_18_R2 extends NbtManager{
    @Override
    public List<String> getItemBuffs(ItemStack item){
        net.minecraft.world.item.ItemStack i = CraftItemStack.asNMSCopy(item);
        NBTTagCompound nbt= null;
        try {
            nbt = (NBTTagCompound) i.getClass().getMethod("u").invoke(i);
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }
        List<String> list = new ArrayList<>();
        try {
            if ((boolean) nbt.getClass().getMethod("e").invoke(nbt,"FlyBuff")) {
                NBTTagList nlist = (NBTTagList) nbt.getClass().getMethod("c").invoke(nbt, "FlyBuff", 8);
                for (NBTBase n : nlist) {
                    list.add(n.asString());
                }
            }
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public ItemStack addBuff(ItemStack item, String buff) {
        net.minecraft.world.item.ItemStack i = CraftItemStack.asNMSCopy(item);
        NBTTagCompound nbt= null;
        try {
            nbt = (NBTTagCompound) i.getClass().getMethod("u").invoke(i);
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }
        NBTTagList nlist;
        try {
            if ((boolean) nbt.getClass().getMethod("e").invoke(nbt,"FlyBuff")){
                nlist = (NBTTagList) nbt.getClass().getMethod("c").invoke(nbt,"FlyBuff",8);
                nlist.add(NBTTagString.a(buff));
            } else {
                nlist = new NBTTagList();
                nlist.add(NBTTagString.a(buff));
            }
            nbt.getClass().getMethod("a").invoke(nbt,"FlyBuff",nlist);
            i.getClass().getMethod("c").invoke(i,nbt);
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }
        return CraftItemStack.asBukkitCopy(i);
    }

    @Override
    public ItemStack removeBuff(ItemStack item, String buff){
        net.minecraft.world.item.ItemStack i = CraftItemStack.asNMSCopy(item);
        NBTTagCompound nbt= null;
        try {
            nbt = (NBTTagCompound) i.getClass().getMethod("u").invoke(i);
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }
        NBTTagList nlist;
        NBTTagList list = new NBTTagList();
        try {
            if ((boolean) nbt.getClass().getMethod("e").invoke(nbt,"FlyBuff")){
                nlist = (NBTTagList) nbt.getClass().getMethod("c").invoke(nbt,"FlyBuff",8);
                for (int s = 0;s<nlist.size();s++){
                    String str = (String) nlist.getClass().getMethod("j").invoke(nlist,s);
                    if (str.equals(buff)) continue;
                    list.add(NBTTagString.a(str));
                }
            }
            nbt.getClass().getMethod("a").invoke(nbt,"FlyBuff",list);
            i.getClass().getMethod("c").invoke(i,nbt);
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }
        return CraftItemStack.asBukkitCopy(i);
    }
}
