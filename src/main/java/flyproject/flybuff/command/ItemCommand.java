package flyproject.flybuff.command;

import flyproject.flybuff.FlyBuff;
import flyproject.flybuff.utils.Color;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;

public class ItemCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(Color.color("&c该命令需要玩家执行"));
        }
        Player p = (Player) sender;
        if (args.length != 2) {
            sender.sendMessage(Color.color("&c参数错误"));
            sender.sendMessage(Color.color("&c/buffitem <nbt/lore> <id>"));
            return false;
        }
        if (p.getItemInHand() == null || p.getItemInHand().getType().equals(Material.AIR)) {
            sender.sendMessage(Color.color("&c手上物品不能为空"));
            return true;
        }
        if (args[0].equals("lore")) {
            FlyBuff.item.set("gems." + args[1] + ".mode", "stack");
            FlyBuff.item.set("gems." + args[1] + ".itemstack", p.getItemInHand());
            FlyBuff buff = FlyBuff.getPlugin(FlyBuff.class);
            try {
                FlyBuff.item.save(new File(buff.getDataFolder() + "/items.yml"));
            } catch (IOException e) {
                sender.sendMessage(Color.color("&c保存文件遇到问题 请检查控制台"));
                e.printStackTrace();
            }
            FlyBuff.item = YamlConfiguration.loadConfiguration(new File(buff.getDataFolder() + "/items.yml"));
            sender.sendMessage(Color.color("&a保存成功！ItemStack数据已储存至路径\"nbtgem." + args[1] + "\""));
        } else if (args[0].equals("nbt")) {
            FlyBuff.item.set("nbtgem." + args[1] + ".mode", "stack");
            FlyBuff.item.set("nbtgem." + args[1] + ".itemstack", p.getItemInHand());
            FlyBuff buff = FlyBuff.getPlugin(FlyBuff.class);
            try {
                FlyBuff.item.save(new File(buff.getDataFolder() + "/items.yml"));
            } catch (IOException e) {
                sender.sendMessage(Color.color("&c保存文件遇到问题 请检查控制台"));
                e.printStackTrace();
            }
            FlyBuff.item = YamlConfiguration.loadConfiguration(new File(buff.getDataFolder() + "/items.yml"));
            sender.sendMessage(Color.color("&a保存成功！ItemStack数据已储存至路径\"nbtgem." + args[1] + "\""));
        } else {
            sender.sendMessage(Color.color("&c参数错误"));
            sender.sendMessage(Color.color("&c/buffitem <nbt/lore> <id>"));
            return false;
        }
        return true;
    }
}
