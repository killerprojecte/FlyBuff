package flyproject.flybuff.hook;

import org.bukkit.entity.Entity;

public abstract class MythicHook {
    public abstract void execute(String skillName, String trigger_type, Entity caster,Entity trigger);
}
