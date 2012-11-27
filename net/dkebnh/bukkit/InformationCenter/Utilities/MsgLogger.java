package net.dkebnh.bukkit.InformationCenter.Utilities;

import java.util.logging.Logger;

import net.dkebnh.bukkit.InformationCenter.InformationCenter;

import org.bukkit.plugin.PluginDescriptionFile;

public class MsgLogger {
	
	private InformationCenter plugin;
	private Logger log;
	
	public MsgLogger(InformationCenter plugin){
		this.plugin = plugin;
		this.log = Logger.getLogger("Minecraft");
	}
	
	private String FormatMessage(String message){
		PluginDescriptionFile pdFile = plugin.getDescription();
		return "[" + pdFile.getName() + "] " + message;
	}
	
	public void infoMSGDefault(String message){
		this.log.info(message);
	}
	
	public void infoMSG(String message){
		this.log.info(this.FormatMessage(message));
	}
	
	public void warningMSG(String message){
		this.log.warning(this.FormatMessage(message));
	}
	
	public void severeMSG(String message){
		this.log.severe(this.FormatMessage(message));
	}
	
}

