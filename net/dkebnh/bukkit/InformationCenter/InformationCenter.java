package net.dkebnh.bukkit.InformationCenter;

import java.util.List;

import net.dkebnh.bukkit.InformationCenter.CommandExecutors.HelpCommandExecutor;
import net.dkebnh.bukkit.InformationCenter.CommandExecutors.ICCommandExecutor;
import net.dkebnh.bukkit.InformationCenter.EventListeners.PlayerLoginListener;
import net.dkebnh.bukkit.InformationCenter.Utilities.MsgLogger;
import net.dkebnh.bukkit.InformationCenter.Configuration;

import org.bukkit.ChatColor;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class InformationCenter extends JavaPlugin {
	public MsgLogger log;
	
	private Configuration conf;
	
    @Override
    public void onEnable(){
    	PluginManager pManager = this.getServer().getPluginManager();
    	
    	conf = new Configuration(this);
    	
		// Link all plugin services here.
		this.log = new MsgLogger(this);
		this.getCommand("ic").setExecutor(new ICCommandExecutor(this));
		this.getCommand("help").setExecutor(new HelpCommandExecutor(this));
		pManager.registerEvents(new PlayerLoginListener(this), this);
    }
 
    @Override
    public void onDisable() {
       this.saveConf();
       log.infoMSG(ChatColor.GREEN + "[InformationCenter]" + ChatColor.WHITE + "Successfully disabled and saved config.");
    }
	
	public Configuration getConfiguration() {
	    return conf;
	}
    
	public void saveConf(){
		conf.save();
	}
	
	public void reloadConf(){
		conf.reload();
	}
	
	public boolean getEnabled() {
        return getConfig().getBoolean("plugin-enabled");
    }
    
	public List<String> getMotd() {
		if (!getConfig().contains("motd")){
			return null;
		}else{
			if (!getConfig().getBoolean("motd.enabled")){
				return null;
			}else{
				return getConfig().getStringList("motd.message");
			}
		}
    }
   
	public boolean getPage(String pageName) {
		if (!getConfig().contains("pages." + pageName)) {
			return false;
		}else{
			return true;
		}
	}
	
	public List<String> getMessage(String pageName) {
        if (!getPage(pageName)){
    		return null;
        }else{
        	return getConfig().getStringList("pages." + pageName);
    	}
    }
}
