package flyproject.flybuff.utils;

import flyproject.flybuff.FlyBuff;
import net.milkbowl.vault.economy.Economy;
import org.black_ixx.playerpoints.PlayerPointsAPI;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.UUID;

public class PaymentCore {
    public static Economy econ = null;
    public static PlayerPointsAPI points = null;
    public static boolean pay(UUID player){
        if (FlyBuff.config.getBoolean("payment.enable")){
            OfflinePlayer ofp = Bukkit.getOfflinePlayer(player);
            Player p = Bukkit.getPlayer(player);
            if (FlyBuff.config.getString("payment.mode").equalsIgnoreCase("money")){
                if (!FlyBuff.hasVault){
                    System.err.println("[FlyBuff-Payment][WARNING] 现在正在尝试进行Vault经济操作 但是您的服务器并没有安装Vault 经济行为已被跳过");
                    return true;
                }
                if (econ.has(ofp,FlyBuff.config.getDouble("payment.cost"))){
                    econ.withdrawPlayer(ofp,FlyBuff.config.getDouble("payment.cost"));
                    p.sendMessage(Color.color(FlyBuff.config.getString("payment.passed").replace("%cost%",String.valueOf(FlyBuff.config.getDouble("payment.cost")))));
                    return true;
                } else {
                    p.sendMessage(Color.color(FlyBuff.config.getString("payment.error").replace("%cost%",String.valueOf(FlyBuff.config.getDouble("payment.cost")))));
                    return false;
                }
            } else {
                if (!FlyBuff.hasPoints){
                    System.err.println("[FlyBuff-Payment][WARNING] 现在正在尝试进行PlayerPoints经济操作 但是您的服务器并没有安装PlayerPoints 经济行为已被跳过");
                    return true;
                }
                if (points.look(ofp.getUniqueId())>=FlyBuff.config.getDouble("payment.cost")){
                    points.take(ofp.getUniqueId(), (int) FlyBuff.config.getDouble("payment.cost"));
                    p.sendMessage(Color.color(FlyBuff.config.getString("payment.passed").replace("%cost%",String.valueOf((int) FlyBuff.config.getDouble("payment.cost")))));
                    return true;
                } else {
                    p.sendMessage(Color.color(FlyBuff.config.getString("payment.error").replace("%cost%",String.valueOf((int) FlyBuff.config.getDouble("payment.cost")))));
                    return false;
                }
            }
        } else {
            return true;
        }
    }
}
