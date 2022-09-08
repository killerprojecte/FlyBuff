package flyproject.flybuff.utils;

import flyproject.flybuff.FlyBuff;
import org.bukkit.entity.Player;

import javax.script.Bindings;
import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;

public class JavaScriptEngine {

    public static void runScript(String js, String buff, Player player, String call) {
        ScriptEngine scriptEngine = FlyBuff.scriptEngineManager.getEngineByName("flybuffJSEngine");
        try {
            Bindings bindings = scriptEngine.createBindings();
            bindings.put("buff", buff);
            bindings.put("player", player);
            bindings.put("plugin", FlyBuff.instance);
            bindings.put("call", call);
            scriptEngine.setBindings(bindings, ScriptContext.ENGINE_SCOPE);
            scriptEngine.eval(new BufferedReader(new InputStreamReader(Files.newInputStream(Paths.get(FlyBuff.instance.getDataFolder() + "/js/" + js + ".js")))));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}