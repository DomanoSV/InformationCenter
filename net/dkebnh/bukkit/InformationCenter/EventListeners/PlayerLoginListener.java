package net.dkebnh.bukkit.InformationCenter.EventListeners;

import net.dkebnh.bukkit.InformationCenter.InformationCenter;

import org.apache.commons.lang.StringUtils;
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
		
		// Will later implement MOTD or Welcome Page here.
		if(plugin.getConfig().getBoolean("motd.enabled")){
			if(player.hasPermission("informationcenter.motd")){
				player.sendMessage(ChatColor.translateAlternateColorCodes('&', StringUtils.join(plugin.getMotd().toArray(), '\n')));
			}	
		}
	}

}
