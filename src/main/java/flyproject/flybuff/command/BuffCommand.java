package flyproject.flybuff.command;

import flyproject.flybuff.FlyBuff;
import flyproject.flybuff.utils.Color;
import flyproject.flybuff.utils.XMap;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class BuffCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        sender.sendMessage(Color.color("&a重载成功"));
        FlyBuff buff = FlyBuff.getPlugin(FlyBuff.class);
        buff.reloadConfig();
        FlyBuff.config = buff.getConfig();
        FlyBuff.item = YamlConfiguration.loadConfiguration(new File(buff.getDataFolder() + "/items.yml"));
        XMap.load();
        return true;
    }
}
