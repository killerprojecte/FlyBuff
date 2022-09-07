package flyproject.flybuff.utils;

import flyproject.flybuff.FlyBuff;
import org.bukkit.entity.Player;

import javax.script.*;
import java.io.FileReader;

public class JavaScriptEngine {

    public static void runScript(String js,String buff, Player player){
        ScriptEngine scriptEngine = new ScriptEngineManager().getEngineByName("javascript");
        try {
            scriptEngine.eval(new FileReader(FlyBuff.instance.getDataFolder() + "/js/" + js + ".js"));
            Bindings bindings = scriptEngine.createBindings();
            bindings.put("buff",buff);
            bindings.put("player",player);
            scriptEngine.setBindings(bindings,ScriptContext.ENGINE_SCOPE);
            if (scriptEngine instanceof Invocable){
                Invocable invocable = (Invocable) scriptEngine;
                invocable.invokeFunction("main",FlyBuff.instance);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
