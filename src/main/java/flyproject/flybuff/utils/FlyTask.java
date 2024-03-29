package flyproject.flybuff.utils;

import flyproject.flybuff.FlyBuff;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitTask;

public class FlyTask {
    public static BukkitTask runTaskAsync(Runnable task) {
        return Bukkit.getScheduler().runTaskAsynchronously(FlyBuff.instance, task);
    }

    public static void runTask(Runnable task) {
        Bukkit.getScheduler().runTask(FlyBuff.instance, task);
    }
}
