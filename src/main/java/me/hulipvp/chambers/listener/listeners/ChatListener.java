package me.hulipvp.chambers.listener.listeners;

import me.hulipvp.chambers.Chambers;
import me.hulipvp.chambers.game.structure.Game;
import me.hulipvp.chambers.profile.structure.ChatStatus;
import me.hulipvp.chambers.profile.structure.Profile;
import me.hulipvp.chambers.profile.structure.ProfileStatus;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatListener implements Listener {
	
	@EventHandler
	public void onChat(AsyncPlayerChatEvent event) {
		Game game = Chambers.getInstance().getGameManager().getGame();
		Profile profile = Chambers.getInstance().getProfileManager().getProfileByUuid(event.getPlayer().getUniqueId());
		String message = event.getMessage();

		if (!game.hasStarted()) {
			if (profile.getTeam() == null)
				event.setFormat(ChatColor.WHITE + event.getPlayer().getName() + ": " + event.getMessage());
			else
				event.setFormat(getGlobalFormat(event.getPlayer(), profile, message.substring(1)));
			return;
		}
		
		if (profile.getTeam() == null) {
			event.setFormat(getSpectatorFormat(event.getPlayer(), message.substring(1)));
			return;
		}
		
		if (message.startsWith("@")) {
			if (profile.getTeam() != null) {
				profile.getTeam().sendMessage(getTeamMessageFormat(event.getPlayer(), message.substring(1)));
				return;
			}
		}
		if (event.getMessage().startsWith("!")) {
			event.setFormat(getGlobalFormat(event.getPlayer(), profile, message.substring(1)));
			return;
		}
		
		if (profile.getChatStatus() == ChatStatus.FACTION) {
			if (profile.getTeam() != null) {
				profile.getTeam().sendMessage(getTeamMessageFormat(event.getPlayer(), message));
			} else {
				profile.setChatStatus(ChatStatus.PUBLIC);
				profile.setTeam(null);
				event.setFormat(getSpectatorFormat(event.getPlayer(), message));
			}
			return;
		} else {
			event.setFormat(getGlobalFormat(event.getPlayer(), profile, message));
		}
	}
	
	private String getGlobalFormat(Player player, Profile profile, String message) {
		return profile.getProfileStatus() == ProfileStatus.RESPAWNING ? ChatColor.GRAY + "[Dead]" + player.getName() + ": " + ChatColor.WHITE + message : profile.getTeam().getColor() + player.getName() + ChatColor.WHITE + ": " + message; 
	}
	
	private String getTeamMessageFormat(Player player, String message) {
		return ChatColor.DARK_AQUA + "(Team) " + player.getName() + ": " + ChatColor.YELLOW + message;
	}
	
	private String getSpectatorFormat(Player player, String message) {
		return ChatColor.GRAY + "[Spectator]" + player.getName() + ": " + ChatColor.WHITE + message;
	}

}
