package net.dkebnh.bukkit.InformationCenter;

import net.dkebnh.bukkit.InformationCenter.InformationCenter;

public class Configuration {

    private InformationCenter plugin;
    
    public Configuration(InformationCenter instance) {
        plugin = instance;
        plugin.saveDefaultConfig();
        plugin.getConfig().options().copyDefaults(true);
        plugin.saveConfig();
    }
    
    public void save() { plugin.saveConfig(); }
    public void reload() { plugin.reloadConfig(); }
}
