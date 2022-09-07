package flyproject.flybuff.listener;

import flyproject.flybuff.FlyBuff;
import flyproject.flybuff.hook.SkillType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import java.util.List;

public class MythicListener implements Listener {
    @EventHandler
    public void onEntityDamaged(EntityDamageByEntityEvent event){
        if (event.isCancelled()) return;
        if (!(event.getDamager() instanceof Player)) return;
        if (FlyBuff.mythicHook==null) return;
        Player player = (Player) event.getDamager();
        List<String> skills = FlyBuff.getAttackSkills(player);
        for (String sk : skills){
            FlyBuff.mythicHook.execute(sk, SkillType.ATTACK,event.getEntity(),player);
        }
    }
}
