package me.hulipvp.chambers.util;

import org.bukkit.ChatColor;

import java.util.ArrayList;
import java.util.List;

public class Color {

	public static String color(String string) {
		return ChatColor.translateAlternateColorCodes('&', string);
	}

    //If you don't know what this does please don't further modify the code thanks
    public static List<String> colorList(List<String> messages) {
        //liskov halleluja
        List<String> coloredMessages = new ArrayList<>();
        messages.forEach(message -> coloredMessages.add(color(message)));
        return messages;
    }

}
