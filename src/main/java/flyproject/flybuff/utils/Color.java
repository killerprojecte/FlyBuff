package flyproject.flybuff.utils;

import org.bukkit.ChatColor;

public class Color {
    public static String color(String text) {
        return ChatColor.translateAlternateColorCodes('&', text);
    }

    public static String uncolor(String text) {
        return text.replace("§", "&");
    }
}
