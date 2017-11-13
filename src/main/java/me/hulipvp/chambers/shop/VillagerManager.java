package me.hulipvp.chambers.shop;

import me.hulipvp.chambers.Chambers;
import me.hulipvp.chambers.shop.structure.VillagerType;
import me.hulipvp.chambers.util.LocationUtil;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Villager;
import org.bukkit.entity.Villager.Profession;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Arrays;

public class VillagerManager {
	
	public VillagerManager() {
		
		spawnAllVillagers();
		
	}
	
	/**
	 * Spawns a Villager of the given VillagerType at the provided Location
	 * 
	 * @param type - the Type of the Villager you wish to Spawn
	 * @param location - the Location at which you want the Villager to be
	 * @return Villager - the Villager that you had set at the provided Location if you wish to use it
	 */
	public Villager spawnVillager(VillagerType type, Location location) {
		if (!location.getChunk().isLoaded()) {
			location.getChunk().load();
		}
		Villager villager = (Villager) location.getWorld().spawnEntity(location, EntityType.VILLAGER);
		create(villager, type, location);
		return villager;
	}
	
	/**
	 * Spawn all of the Shop Villagers for each Team
	 */
	public void spawnAllVillagers() {
		Chambers plugin = Chambers.getInstance();
		//cleared up some one-liner brackets
		plugin.getTeamManager().getAllPlayerTeams().forEach(team ->
			Arrays.stream(VillagerType.values()).forEach(villagerType -> {
				if (plugin.getDataFile().getString("TEAMS." + team.getType().name() + "." + villagerType.name()) != null)
					spawnVillager(villagerType, LocationUtil.deserializeLocation(plugin.getDataFile().getString("TEAMS." + team.getType().name() + "." + villagerType.name())));
			})
		);
	}

	/**
	 * Really? Do I have to explain this?
	 *
	 * @param villager The villager we want to set the attributes of.
	 * @param type     The type of villager we want.
	 * @param location The location where he's gonna be wowza
	 */
	public void create(Villager villager, VillagerType type, Location location) {
		villager.setAdult();
		villager.setAgeLock(true);
		villager.setProfession(Profession.FARMER);
		villager.setRemoveWhenFarAway(false);
		villager.setCustomName(type.getColor() + type.getName());
		villager.setCustomNameVisible(true);
		villager.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, -6, true), true);
		villager.teleport(location, TeleportCause.PLUGIN);
		villager.setHealth(20.0D);
	}

}
