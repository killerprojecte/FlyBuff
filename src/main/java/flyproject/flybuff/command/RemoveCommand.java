package flyproject.flybuff.command;

import flyproject.flybuff.gui.GuiUtil;
import flyproject.flybuff.utils.BHolder;
import flyproject.flybuff.utils.Color;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class RemoveCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)){
            sender.sendMessage(Color.color("&c该命令需要玩家执行"));
            return false;
        }
        Player p = (Player) sender;
        Inventory inv = GuiUtil.create();
        GuiUtil.init(inv,p);
        p.openInventory(inv);
        return true;
    }
}
