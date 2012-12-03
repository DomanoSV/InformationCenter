package net.dkebnh.bukkit.InformationCenter.CommandExecutors;

import java.util.List;
import java.util.Set;

import net.dkebnh.bukkit.InformationCenter.InformationCenter;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class HelpCommandExecutor implements CommandExecutor {
	private InformationCenter plugin;
	
	public HelpCommandExecutor(InformationCenter plugin) {
		this.plugin = plugin;
	}
	
	public boolean getPermission(CommandSender sender, String pageName){
		Set<String> books = plugin.getConfig().getConfigurationSection("books").getKeys(false);
		Object[] parseList = books.toArray();
		
		for (int i = 0; i < books.size(); i++){
			if (plugin.getConfig().getConfigurationSection("books").getStringList(parseList[i].toString()).contains(pageName)){
				if (sender.hasPermission("informationcenter.book." + parseList[i].toString())){
					return true;
				}
			}
		}
		
		if (sender.hasPermission("informationcenter.page." + pageName.toLowerCase())){
			return true;
		}
		return false;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (!plugin.getEnabled()){
			sender.sendMessage(ChatColor.RED + "[InformationCenter]" + ChatColor.WHITE + " Please try again later InformationCenter is currently being Edited.");
			return true;
		}
		
		if (args.length > 0){
			
			String pageSearchString = args[0];
			for (int i = 1; i < args.length; ++i){
				pageSearchString = pageSearchString + "-" + args[i];
			}
			
			plugin.log.infoMSG("The page '" + pageSearchString +"' has been searched for by " + sender.getName());
			
			if (plugin.getPage(pageSearchString)){	
				if (!getPermission(sender, pageSearchString)){
					sender.sendMessage(ChatColor.RED + "[InformationCenter]" + ChatColor.WHITE + " The page you are looking for doesn't exist.");
					plugin.log.warningMSG(sender.getName() + " tried accessing the page " + args[0].toLowerCase() + ", but hasn't got the permission informationcenter.page." + args[0].toLowerCase());
				}else{
					List<String> message = plugin.getMessage(pageSearchString);
					if (message.isEmpty()) {
						sender.sendMessage("&cPage has yet to have information Entered.");
					}else{
						for (int i = 0; i < message.size(); i++){
							if (message.get(i).contains("%%pN%")){
								String newString = message.get(i).replace("%%pN%", sender.getName());
								message.set(i, newString);
							}
							
							if (message.get(i).contains("%%mP%")){
								String newString = message.get(i).replace("%%mP%", Integer.toString(Bukkit.getServer().getMaxPlayers()));
								message.set(i, newString);
							}
								
							if (message.get(i).contains("%%oP%")){
								String newString = message.get(i).replace("%%oP%", Integer.toString(Bukkit.getServer().getOnlinePlayers().length));
								message.set(i, newString);
							}
							
							if (message.get(i).contains("%%srvN%")){
								String newString = message.get(i).replace("%%srvN%", Bukkit.getServerName());
								message.set(i, newString);
							}
								
							if (message.get(i).contains("%%motd%")){
								String newString = message.get(i).replace("%%motd%", Bukkit.getMotd());
								message.set(i, newString);
							}
								
							if (message.get(i).contains("%%ver%")){
								String newString = message.get(i).replace("%%ver%", Bukkit.getVersion());
								message.set(i, newString);
							}
								
							if (message.get(i).contains("%%time%")){
								String newString = message.get(i).replace("%%time%", plugin.getTimeString());
								message.set(i, newString);
							}
								
							sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message.get(i)));
							if (i == 7){
								break;
							}
						}
					}
				}
			}else{
				sender.sendMessage(ChatColor.RED + "[InformationCenter]" + ChatColor.WHITE + " The page you are looking for doesn't exist.");
			}
		}else{
			sender.sendMessage(ChatColor.RED + "[InformationCenter]" + ChatColor.WHITE + " Please choose the help page you wish to view.");
		}
		return true;
	}
}
