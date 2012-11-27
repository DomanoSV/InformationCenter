package net.dkebnh.bukkit.InformationCenter.CommandExecutors;

import net.dkebnh.bukkit.InformationCenter.InformationCenter;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.PluginDescriptionFile;

public class ICCommandExecutor implements CommandExecutor {
	private InformationCenter plugin;
	
	public ICCommandExecutor(InformationCenter plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		PluginDescriptionFile pdFile = plugin.getDescription();
		
		if (!sender.hasPermission("informationcenter.admin")){
			sender.sendMessage(ChatColor.WHITE + "You do not have any of the required permission(s)");
			sender.sendMessage(ChatColor.WHITE + " - " + ChatColor.GREEN + "maintenancemode.admin");
			return true;
		}
		
		if (true) {
				if(args.length == 1 && args[0].equalsIgnoreCase("version")) {
					sender.sendMessage(ChatColor.GREEN + "The InformationCenter plugin is version " + pdFile.getVersion());
					return true;
				}else if (args.length == 1 && args[0].equalsIgnoreCase("help")){
					sender.sendMessage(ChatColor.WHITE + "Command Help - " + ChatColor.GREEN + "InformationCenter " + pdFile.getVersion());
					sender.sendMessage(ChatColor.WHITE + "----------------------------------------------------");
					sender.sendMessage(ChatColor.GREEN + "/ic version" + ChatColor.WHITE + " - Gets Plugin Version.");
					sender.sendMessage(ChatColor.GREEN + "/ic reload" + ChatColor.WHITE + " - Reloads plugin configuration.");
					return true;
				}else if(args.length == 1 && args[0].equalsIgnoreCase("reload")){
					plugin.reloadConf();
					sender.sendMessage(ChatColor.GREEN + "[InformationCenter] " + ChatColor.WHITE + "InformationCenter has reloaded successfully!");
				}else{
					sender.sendMessage(ChatColor.RED + "[InformationCenter] " + ChatColor.WHITE + "Usage: " + ChatColor.GREEN + "/ic help" + ChatColor.WHITE + " for more information.");
				}
		}
		return true;
	}
}
