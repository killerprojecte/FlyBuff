var Bukkit = Packages.org.bukkit.Bukkit;
var FlyBuff = Packages.flyproject.flybuff.FlyBuff;

function main(){
    // call -> damage, blockbreak, blockplace, rclick, bowhit, test
    if (call=="test"){
       plugin.getLogger().info("FlyBuff JavaScript Loaded!");
    } else {
       plugin.getLogger().info("玩家: " + player.getName() + " 触发了JS-BUFF: " + buff + " 触发模式: " + call);
    }
    //FlyBuff.excuteCommand("say HelloWorld!");
}

main();