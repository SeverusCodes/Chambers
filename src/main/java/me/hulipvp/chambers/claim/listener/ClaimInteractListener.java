package me.hulipvp.chambers.claim.listener;

import me.hulipvp.chambers.Chambers;
import me.hulipvp.chambers.claim.structure.Claim;
import me.hulipvp.chambers.claim.structure.ClaimProfile;
import me.hulipvp.chambers.claim.structure.Pillar;
import me.hulipvp.chambers.profile.structure.Profile;
import me.hulipvp.chambers.util.LocationUtil;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class ClaimInteractListener implements Listener {

    private Chambers plugin;

    public ClaimInteractListener(Chambers plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

	@EventHandler
	public void onInteract(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		if (player.getItemInHand() == null || player.getItemInHand().getType() != Material.GOLD_HOE) {
			return;
		}
        Profile profile = plugin.getProfileManager().getProfileByUuid(player.getUniqueId());
        if (!plugin.getClaimManager().isClaiming(profile)) {
            return;
		}
		if (profile.getTeam() == null || profile.getTeam().getType().getClaimMaterial() == null) {
			player.sendMessage(ChatColor.RED + "Please join a Team to claim for.");
			return;
		}
        ClaimProfile claimProfile = plugin.getClaimManager().getClaimProfile(profile);
        Action action = event.getAction();
		event.setCancelled(true);
		switch (action) {
		case RIGHT_CLICK_BLOCK: {
			if (claimProfile.getCornerOne() != null) {
				claimProfile.getPillarOne().remove();
				claimProfile.setPillarOne(null);
			}
			claimProfile.setCornerOne(event.getClickedBlock().getLocation());
			Pillar pillar = new Pillar(event.getPlayer(), event.getClickedBlock().getLocation(), profile.getTeam().getType().getClaimMaterial());
			claimProfile.setPillarOne(pillar);
			pillar.display();
			profile.sendMessage(ChatColor.GRAY + "Selected corner 1 for " + profile.getTeam().getFormattedName() + ChatColor.GRAY + ".");
			break;
		}
		case LEFT_CLICK_BLOCK: {
			if (claimProfile.getCornerTwo() != null) {
				claimProfile.getPillarTwo().remove();
				claimProfile.setPillarTwo(null);
			}
			claimProfile.setCornerTwo(event.getClickedBlock().getLocation());
			Pillar pillar = new Pillar(event.getPlayer(), event.getClickedBlock().getLocation(), profile.getTeam().getType().getClaimMaterial());
			claimProfile.setPillarTwo(pillar);
			pillar.display();
			profile.sendMessage(ChatColor.GRAY + "Selected corner 2 for " + profile.getTeam().getFormattedName() + ChatColor.GRAY + ".");
			break;
		}
		case LEFT_CLICK_AIR: {
			if (player.isSneaking()) {
				claimProfile.clearPillars();
				claimProfile.setCornerOne(null);
				claimProfile.setCornerTwo(null);
				player.sendMessage(ChatColor.GREEN + "Selection cleared.");
			}
			break;
		}
		case RIGHT_CLICK_AIR: {
			if (player.isSneaking()) {
				claimProfile.clearPillars();
				Claim claim = new Claim(claimProfile.getCornerOne(), claimProfile.getCornerTwo(), profile.getTeam());
				profile.getTeam().setClaim(claim);
                plugin.getClaimManager().addClaim(claim);
                plugin.getDataFile().getConfiguration().set("TEAMS." + profile.getTeam().getType().name() + ".CORNERONE", LocationUtil.serializeLocation(claimProfile.getCornerOne()));
                plugin.getDataFile().getConfiguration().set("TEAMS." + profile.getTeam().getType().name() + ".CORNERTWO", LocationUtil.serializeLocation(claimProfile.getCornerTwo()));
                plugin.getDataFile().save();
                plugin.getDataFile().load();
                plugin.getClaimManager().removeClaimProfile(profile);
                player.getInventory().removeItem(plugin.getClaimManager().getClaimingWand());
                player.sendMessage(ChatColor.GRAY + "Selection claimed for " + profile.getTeam().getFormattedName() + ChatColor.GRAY + ".");
			}
			break;
		}
		}
	}

}
