package me.hulipvp.chambers.config;

import lombok.SneakyThrows;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * I'm too lazy to ask alex for permission to use this class, so I typed
 * everything line by line so basically this class wasn't written by him. But
 * I'll still give that kid some credit. So here it goes - this class was
 * created by bizarrealex<br>
 * (but I still typed it out line by line)
 *
 * @author bizarrealex
 *
 * That's great and all, Huli, but this class needs some improvement.
 */

//First, let's extend YamlConfiguration, so we don't have to add boilerplate code such as set, get, etc.
public class DataFile extends YamlConfiguration {

	private File file;

	public DataFile(JavaPlugin plugin, String name) {
		file = new File(plugin.getDataFolder(), name + ".yml");

        //It's a one-liner, no need for brackets.
        if (!file.getParentFile().exists())
            file.getParentFile().mkdir();

		plugin.saveResource(name + ".yml", false);

        loadConfiguration(file);
    }

	/**
	 * Loads the file<br>
	 * You can also use this method to reload the file
	 */
	public void load() {
        loadConfiguration(file);
    }

	/**
	 * If you don't understand this, why are you even reading this
	 */

    //Make this code look pretty.
    @SneakyThrows
    public void save() {
        save(file);
    }

	/**
	 * Get the configuration so you can access it
	 * 
	 * @return configuration - the YamlConfiguration object
	 */
	public YamlConfiguration getConfiguration() {
        return this;
    }

    //Deleted a lot of unnecessary code such as getters and setters.


	public List<String> getStringList(String path) {
        if (contains(path)) {
            List<String> strings = new ArrayList<>();
            getStringList(path).stream().forEach(string -> strings.add(ChatColor.translateAlternateColorCodes('&', string)));
            return strings;
		}
		return Arrays.asList("Invalid path.");
	}

	/**
	 * Simply reverses a String list<br>
	 * I'm not really sure why this is needed, but okay
	 * 
	 * @param path - the path you wish to find a String list at
	 * @return list - a List of Strings that are in reversed order from which it
	 *         was found
	 */
	public List<String> getReversedStringList(String path) {
		List<String> list = getStringList(path);
		if (list != null) {
			Collections.reverse(list);
			return list;
		}
		return Arrays.asList("Invalid path.");
	}

}