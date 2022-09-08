package flyproject.flybuff.listener;

import flyproject.flybuff.FlyBuff;
import flyproject.flybuff.hook.SkillType;
import flyproject.flybuff.utils.JavaScriptEngine;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.LazyMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.metadata.MetadataValueAdapter;

import java.util.List;

public class MythicListener implements Listener {
    @EventHandler
    public void onEntityDamaged(EntityDamageByEntityEvent event) {
        if (event.isCancelled()) return;
        for (MetadataValue mv : event.getDamager().getMetadata("using-flybuff-damage-skill")){
            if (mv.asBoolean()) return;
        }
        if (!(event.getDamager() instanceof Player)) return;
        if (FlyBuff.mythicHook == null) return;
        event.getDamager().setMetadata("using-flybuff-damage-skill", new FixedMetadataValue(FlyBuff.instance,true));
        Player player = (Player) event.getDamager();
        List<String> skills = FlyBuff.getAttackSkills(player);
        for (String sk : skills) {
            FlyBuff.mythicHook.execute(sk, SkillType.ATTACK, event.getEntity(), player);
        }
        for (String[] jsarg : FlyBuff.getJSBuffs(player)){
            JavaScriptEngine.runScript(jsarg[0],jsarg[1],player,"damage");
        }
        event.getDamager().setMetadata("using-flybuff-damage-skill", new FixedMetadataValue(FlyBuff.instance,false));
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
        for (String[] jsarg : FlyBuff.getJSBuffs(player)){
            JavaScriptEngine.runScript(jsarg[0],jsarg[1],player,"blockbreak");
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
        for (String[] jsarg : FlyBuff.getJSBuffs(player)){
            JavaScriptEngine.runScript(jsarg[0],jsarg[1],player,"blockplace");
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
        for (String[] jsarg : FlyBuff.getJSBuffs(player)){
            JavaScriptEngine.runScript(jsarg[0],jsarg[1],player,"rclick");
        }
    }

    @EventHandler
    public void onBowHit(EntityDamageByEntityEvent event) {
        if (event.isCancelled()) return;
        for (MetadataValue mv : event.getDamager().getMetadata("using-flybuff-bowhit-skill")){
            if (mv.asBoolean()) return;
        }
        if (!(event.getDamager() instanceof Arrow)) return;
        if (FlyBuff.mythicHook == null) return;
        Arrow arrow = (Arrow) event.getDamager();
        if (!(arrow.getShooter() instanceof Player)) return;
        event.getDamager().setMetadata("using-flybuff-bowhit-skill", new FixedMetadataValue(FlyBuff.instance,true));
        Player player = (Player) arrow.getShooter();
        List<String> skills = FlyBuff.getBowHitSkills(player);
        for (String sk : skills) {
            FlyBuff.mythicHook.execute(sk, SkillType.BOW_HIT, event.getEntity(), player);
        }
        for (String[] jsarg : FlyBuff.getJSBuffs(player)){
            JavaScriptEngine.runScript(jsarg[0],jsarg[1],player,"bowhit");
        }
        event.getDamager().setMetadata("using-flybuff-bowhit-skill", new FixedMetadataValue(FlyBuff.instance,false));
    }
}
