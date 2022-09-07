package flyproject.flybuff.listener;

import flyproject.flybuff.FlyBuff;
import flyproject.flybuff.hook.SkillType;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.List;

public class MythicListener implements Listener {
    @EventHandler
    public void onEntityDamaged(EntityDamageByEntityEvent event) {
        if (event.isCancelled()) return;
        if (!(event.getDamager() instanceof Player)) return;
        if (FlyBuff.mythicHook == null) return;
        Player player = (Player) event.getDamager();
        List<String> skills = FlyBuff.getAttackSkills(player);
        for (String sk : skills) {
            FlyBuff.mythicHook.execute(sk, SkillType.ATTACK, event.getEntity(), player);
        }
    }

    @EventHandler
    public void onBlockBreaked(BlockBreakEvent event) {
        if (event.isCancelled()) return;
        if (FlyBuff.mythicHook == null) return;
        Player player = event.getPlayer();
        List<String> skills = FlyBuff.getMineSkills(player);
        for (String sk : skills) {
            FlyBuff.mythicHook.execute(sk, SkillType.BLOCK_BREAK, player, player);
        }
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        if (event.isCancelled()) return;
        if (FlyBuff.mythicHook == null) return;
        Player player = event.getPlayer();
        List<String> skills = FlyBuff.getPlaceSkills(player);
        for (String sk : skills) {
            FlyBuff.mythicHook.execute(sk, SkillType.BLOCK_PLACE, player, player);
        }
    }

    @EventHandler
    public void onClick(PlayerInteractEvent event) {
        if (event.isCancelled()) return;
        if (FlyBuff.mythicHook == null) return;
        if (!event.getAction().toString().startsWith("RIGHT_CLICK")) return;
        Player player = event.getPlayer();
        List<String> skills = FlyBuff.getRClickSkills(player);
        for (String sk : skills) {
            FlyBuff.mythicHook.execute(sk, SkillType.RIGHTCLICK, player, player);
        }
    }

    @EventHandler
    public void onBowHit(EntityDamageByEntityEvent event) {
        if (event.isCancelled()) return;
        if (!(event.getDamager() instanceof Arrow)) return;
        if (FlyBuff.mythicHook == null) return;
        Arrow arrow = (Arrow) event.getDamager();
        if (!(arrow.getShooter() instanceof Player)) return;
        Player player = (Player) arrow.getShooter();
        List<String> skills = FlyBuff.getBowHitSkills(player);
        for (String sk : skills) {
            FlyBuff.mythicHook.execute(sk, SkillType.BOW_HIT, event.getEntity(), player);
        }
    }
}
