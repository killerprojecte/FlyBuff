var Bukkit = Packages.org.bukkit.Bukkit;

function main(){
    // call -> damage, blockbreak, blockplace, rclick, bowhit, test
    if (call=="test"){
       plugin.getLogger().info("FlyBuff JavaScript Loaded!");
    } else {
       plugin.getLogger().info("玩家: " + player.getName() + " 触发了JS-BUFF: " + buff + " 触发模式: " + call);
    }
    //Bukkit.dispatchCommand(Bukkit.getConsoleSender(),"say HelloWorld");
}

main();