package flyproject.flybuff.api;

import flyproject.flybuff.FlyBuff;
import flyproject.flybuff.utils.Color;
import flyproject.flybuff.utils.XMap;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.util.List;

public class BuffAPI {

    public BuffAPI(Plugin plugin){
        FlyBuff.logger.info("[FlyBuff-API] 插件 " + plugin.getName() + " 正在注册FlyBuff-API");
    }

    @Deprecated
    public BuffAPI(Plugin plugin,boolean notify){
        if (notify) FlyBuff.logger.info("[FlyBuff-API] 插件 " + plugin.getName() + " 正在注册FlyBuff-API");
    }

    public boolean containsBuff(ItemStack item, String buffname){
        return FlyBuff.nms.getItemBuffs(item).contains(buffname);
    }

    public void addBuff(ItemStack item, String buffname){
        FlyBuff.nms.addBuff(item,buffname);
    }

    public void removeBuff(ItemStack item, String buffname){
        FlyBuff.nms.removeBuff(item,buffname);
    }

    public long getItemInstalledBuff(ItemStack item){
        int installed = 0;
        if (!item.getItemMeta().hasLore()) return 0L;
        if (item.getItemMeta().getLore().size()==0) return 0L;
        for (String l : item.getItemMeta().getLore()){
            if (XMap.installs.contains(Color.uncolor(l))) installed++;
        }
        installed = installed + FlyBuff.nms.getItemBuffs(item).size();
        return installed;
    }

    public List<String> getBuffs(ItemStack item){
        return FlyBuff.nms.getItemBuffs(item);
    }
}
