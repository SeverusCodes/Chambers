package me.hulipvp.chambers.team.command.admin;

import org.bukkit.ChatColor;

import me.hulipvp.chambers.team.command.TeamCommand;
import me.hulipvp.chambers.team.structure.Team;
import me.hulipvp.chambers.util.LocationUtil;
import me.hulipvp.chambers.util.commandapi.Command;
import me.hulipvp.chambers.util.commandapi.CommandArgs;

public class TeamSetHomeCommand extends TeamCommand {
	
	@Command(name = "faction.sethome", description = "Set the home for a Team", aliases = { "f.sethome", "t.sethome", "s.sethome", "team.sethome", "squad.sethome" }, usage = "/<command> <args>", requiresTeam = false, adminsOnly = true)
	public void onCommand(CommandArgs commandArgs) {
		if (commandArgs.length() != 1) {
			commandArgs.getPlayer().sendMessage(ChatColor.RED + "Usage: /" + commandArgs.getLabel() + " <team>");
			return;
		}
		Team team = plugin.getTeamManager().getTeamByString(commandArgs.getArgs(0));
		if (team == null) {
			commandArgs.getPlayer().sendMessage(ChatColor.RED + "That team is invalid.");
			return;
		}
		team.setHome(commandArgs.getPlayer().getLocation());
		plugin.getDataFile().getConfiguration().set("TEAMS." + team.getType().name() + ".HOME", LocationUtil.serializeLocation(team.getHome()));
		plugin.getDataFile().save();
		commandArgs.getPlayer().sendMessage(ChatColor.GRAY + "Set the Team home for " + team.getFormattedName() + ChatColor.GRAY + ".");
	}

}
