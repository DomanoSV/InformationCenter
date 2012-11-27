package net.dkebnh.bukkit.InformationCenter.CommandExecutors;

import java.util.List;
import java.util.Set;

import net.dkebnh.bukkit.InformationCenter.InformationCenter;

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
				if (plugin.getPage(args[0].toLowerCase())){	
					if (!getPermission(sender, args[0].toLowerCase())){
						sender.sendMessage(ChatColor.RED + "[InformationCenter]" + ChatColor.WHITE + " The page you are looking for doesn't exist.");
						plugin.log.warningMSG(sender.getName() + " tried accessing the page " + args[0].toLowerCase() + ", but hasn't got the permission informationcenter.page." + args[0].toLowerCase());
					}else{
						List<String> message = plugin.getMessage(args[0].toLowerCase());
						if (message.isEmpty()) {
							sender.sendMessage("&cPage has yet to have information Entered.");
						}else{
							for (int i = 0; i < message.size(); i++){
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
