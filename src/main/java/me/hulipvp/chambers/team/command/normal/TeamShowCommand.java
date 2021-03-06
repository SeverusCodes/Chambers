package me.hulipvp.chambers.team.command.normal;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;

import me.hulipvp.chambers.koth.structure.Koth;
import me.hulipvp.chambers.profile.structure.Profile;
import me.hulipvp.chambers.team.command.TeamCommand;
import me.hulipvp.chambers.team.structure.Team;
import me.hulipvp.chambers.util.commandapi.Command;
import me.hulipvp.chambers.util.commandapi.CommandArgs;

public class TeamShowCommand extends TeamCommand {
	
	@Command(name = "faction.show", description = "See the information of a Faction", aliases = { "f.show", "t.show", "s.show", "team.show", "squad.show", "faction.who", "f.who", "t.who", "s.who", "team.who", "squad.who" }, usage = "/<command> <args>", requiresTeam = false)
	public void onCommand(CommandArgs commandArgs) {
		Profile profile = plugin.getProfileManager().getProfileByUuid(commandArgs.getPlayer().getUniqueId());
		if (commandArgs.length() == 0) {
			if (profile.getTeam() == null) {
				profile.sendMessage(ChatColor.RED + "Usage: /" + commandArgs.getLabel() + "<team>");
				return;
			}
			getShowLines(profile.getTeam()).forEach(line -> profile.sendMessage(line));
		} else {
			Team team = plugin.getTeamManager().getTeamByString(commandArgs.getArgs(0));
			if (team == null) {
				profile.sendMessage(ChatColor.RED + "No team by that name or with that player was found.");
				return;
			}
			getShowLines(team).forEach(line -> profile.sendMessage(line));
		}
	}
	
	public List<String> getShowLines(Team team) {
		List<String> lines = new ArrayList<>();
		
		if (!team.isPlayerTeam()) {
			lines.add(team.getFormattedName());
			if (team.getType().name().contains("KOTH")) {
				Koth koth = plugin.getKothManager().getKoth();
				lines.add("&eLocation:&7 0, 0");
				lines.add("&eStatus:&7 " + (koth != null ? "Contestable" : "Not contestable"));
				if (koth != null) {
					lines.add("&eTime:&7 " + koth.getTime());
					lines.add("&eMax time:&7 " + koth.getMaxCapTime());
				}
				lines.add(" ");
				lines.add("&eCome to the center of the map to cap the KoTH.");
			} else {
				lines.add("&eThis is just a placeholder faction.");
				lines.add("&eSo that players cannot build outside of their Team claims.");
			}
		} else {
			lines.add(team.getFormattedName() + " &7[" + team.getSize() + "/5]");
			lines.add("&eHome:&7 " + team.getHome().getBlockX() + ", " + team.getHome().getBlockZ());
			StringBuilder memberBuilder = new StringBuilder();
			for (int i = 0; i < team.getSize(); i++) {
				if (i == team.getSize() - 1) {
					memberBuilder.append(team.getOnlinePlayers().get(i).getName());
				} else {
					memberBuilder.append(team.getOnlinePlayers().get(i).getName() + ", ");
				}
			}
			lines.add("&eMembers:&7 " + memberBuilder.toString());
			lines.add("&eDTR:&7 " + (team.isRaidable() ? ChatColor.RED + String.valueOf(team.getDtr()) + " [Raidable]" : ChatColor.GREEN + String.valueOf(team.getDtr()) + " [Not Raidable]"));
		}
		
		lines.add(0, "&7&m-------------------------------------------------------------");
		lines.add("&7&m-------------------------------------------------------------");
		
		return lines;
	}

}
