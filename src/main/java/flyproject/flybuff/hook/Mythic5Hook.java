package flyproject.flybuff.hook;

import io.lumine.mythic.api.mobs.GenericCaster;
import io.lumine.mythic.api.skills.SkillTrigger;
import io.lumine.mythic.bukkit.MythicBukkit;
import io.lumine.mythic.bukkit.adapters.BukkitEntity;
import io.lumine.mythic.core.skills.SkillMetadataImpl;
import org.bukkit.entity.Entity;

public class Mythic5Hook extends MythicHook {
    @Override
    public void execute(String skillName, SkillType trigger_type, Entity caster, Entity trigger) {
        MythicBukkit.inst().getSkillManager().getSkill(skillName).get().execute(new SkillMetadataImpl(SkillTrigger.get(trigger_type.toString()), new GenericCaster(new BukkitEntity(caster)), new BukkitEntity(trigger)));
    }
}
