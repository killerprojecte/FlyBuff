package flyproject.flybuff.utils;

import flyproject.flybuff.FlyBuff;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import javax.script.Bindings;
import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class JavaScriptEngine {

    public static void runScript(String js, String buff, Player player, String call) {
        if (FlyBuff.config.getBoolean("jsasync")){
            Bukkit.getScheduler().runTaskAsynchronously(FlyBuff.instance,() -> {excute(js, buff, player, call);});
        } else {
            excute(js, buff, player, call);
        }
    }

    private static void excute(String js, String buff, Player player, String call) {
        ScriptEngine scriptEngine = FlyBuff.scriptEngineManager.getEngineByName("flybuffJSEngine");
        try {
            Bindings bindings = scriptEngine.createBindings();
            bindings.put("buff", buff);
            bindings.put("player", player);
            bindings.put("plugin", FlyBuff.instance);
            bindings.put("call", call);
            scriptEngine.setBindings(bindings, ScriptContext.ENGINE_SCOPE);
            scriptEngine.eval(new BufferedReader(new InputStreamReader(Files.newInputStream(Paths.get(FlyBuff.instance.getDataFolder() + "/js/" + js + ".js")), StandardCharsets.UTF_8)));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
