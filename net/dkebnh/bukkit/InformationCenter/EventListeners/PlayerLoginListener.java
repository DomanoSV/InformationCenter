package net.dkebnh.bukkit.InformationCenter.EventListeners;

import net.dkebnh.bukkit.InformationCenter.InformationCenter;

import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerLoginListener implements Listener {

	private InformationCenter plugin;
	
	public PlayerLoginListener(InformationCenter plugin){
		this.plugin = plugin;
	}

	@EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
	public void onPlayerJoin(PlayerJoinEvent event){
		Player player = event.getPlayer();
		String playerName = player.getName();
		
		// Will tell InformationCenter Admins or Server OPs if there is an update available upon logon.
		if(player.isOp() || player.hasPermission("informationcenter.admin")){
			if(plugin.updateChecker.updateNeeded()){
				player.sendMessage(ChatColor.GREEN + "[DKE UpdateChecker]" + ChatColor.WHITE + " A new version of InformationCenter is avaliable, Version " + plugin.updateChecker.getVersion() + ", you can get it from:");
				player.sendMessage(ChatColor.BLUE + plugin.updateChecker.getLink());
			}
		}
		
		// Will later implement MOTD or Welcome Page here.
		if(plugin.getConfig().getBoolean("motd.enabled")){
			String message = StringUtils.join(plugin.getMotd().toArray(), '\n');
			
			if (message.contains("%%pN%")){
				message = message.replace("%%pN%", playerName);
			}
			
			if (message.contains("%%mP%")){
				message = message.replace("%%mP%", Integer.toString(Bukkit.getServer().getMaxPlayers()));
			}
			
			if (message.contains("%%oP%")){
				message = message.replace("%%oP%", Integer.toString(Bukkit.getServer().getOnlinePlayers().length));
			}
			
			if (message.contains("%%srvN%")){
				message = message.replace("%%srvN%", Bukkit.getServer().getServerName());
			}
			
			if (message.contains("%%motd%")){
				message = message.replace("%%motd%", Bukkit.getServer().getMotd());
			}
			
			if (message.contains("%%ver%")){
				message = message.replace("%%ver%", Bukkit.getServer().getVersion());
			}

			if (message.contains("%%time%")){
				message = message.replace("%%time%", plugin.getTimeString());
			}
			
			player.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
		}
	}

}
