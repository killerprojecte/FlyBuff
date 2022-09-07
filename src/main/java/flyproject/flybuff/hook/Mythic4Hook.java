package flyproject.flybuff.hook;

import io.lumine.xikage.mythicmobs.MythicMobs;
import io.lumine.xikage.mythicmobs.adapters.bukkit.BukkitEntity;
import io.lumine.xikage.mythicmobs.mobs.GenericCaster;
import io.lumine.xikage.mythicmobs.skills.SkillMetadata;
import io.lumine.xikage.mythicmobs.skills.SkillTrigger;
import org.bukkit.entity.Entity;

public class Mythic4Hook extends MythicHook {
    @Override
    public void execute(String skillName, SkillType trigger_type, Entity caster, Entity trigger) {
        MythicMobs.inst().getSkillManager().getSkill(skillName).get().execute(new SkillMetadata(SkillTrigger.valueOf(trigger_type.toString()), new GenericCaster(new BukkitEntity(caster)), new BukkitEntity(trigger)));
    }
}
