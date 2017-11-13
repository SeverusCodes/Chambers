package me.hulipvp.chambers.util;

import org.apache.commons.lang.SerializationException;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

public class LocationUtil {

	public static String serializeLocation(Location l) {
		String s = "";
		s = s + "@w;" + l.getWorld().getName();
		s = s + ":@x;" + l.getX();
		s = s + ":@y;" + l.getY();
		s = s + ":@z;" + l.getZ();
		s = s + ":@p;" + l.getPitch();
		s = s + ":@ya;" + l.getYaw();
		return s;
	}

	public static Location deserializeLocation(String s) {
		String[] attributes = s.split(":");

		World world = null;
		Double x = null;
		Double y = null;
		Double z = null;
		Float pitch = null;
		Float yaw = null;

		for (String attribute : attributes) {
			String[] split = attribute.split(";");

			/*if (split[0].equalsIgnoreCase("@w")) {
				world = Bukkit.getWorld(split[1]);
				continue;
			}

			if (split[0].equalsIgnoreCase("@x")) {
				x = Double.parseDouble(split[1]);
				continue;
			}

			if (split[0].equalsIgnoreCase("@y")) {
				y = Double.parseDouble(split[1]);
				continue;
			}

			if (split[0].equalsIgnoreCase("@z")) {
				z = Double.parseDouble(split[1]);
				continue;
			}

			if (split[0].equalsIgnoreCase("@p")) {
				pitch = Float.parseFloat(split[1]);
				continue;
			}

			if (split[0].equalsIgnoreCase("@ya")) {
				yaw = Float.parseFloat(split[1]);
			}*/

			/**

			 Changed the thousands of if statements to a clean looking switch statement halleluja

			 */
			switch (split[0].toLowerCase()) {
				case "@w":
					world = Bukkit.getWorld(split[1]);
					continue;
				case "@x":
					x = Double.parseDouble(split[1]);
					continue;
				case "@y":
					y = Double.parseDouble(split[1]);
					continue;
				case "@z":
					z = Double.parseDouble(split[1]);
					continue;
				case "@p":
					pitch = Float.parseFloat(split[1]);
					continue;
				case "@ya":
					pitch = Float.parseFloat(split[1]);
					break;
				default:
					throw new SerializationException("Something went wrong with deserialization, don't touch the config serialized locations.");
			}
		}


		//ohmalord ternary operators hallellula
		return ((world == null) || (x == null) || (y == null) || (z == null) || (pitch == null) || (yaw == null)) ? null : new Location(world, x, y, z, yaw, pitch);
	}

}
