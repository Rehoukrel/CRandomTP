package com.github.TiveCS.crandomtp;

import com.github.TiveCS.crandomtp.api.TeleportHandler;
import com.github.TiveCS.crandomtp.commands.CmdCRandomTP;
import org.bukkit.plugin.java.JavaPlugin;

public class CRandomTP extends JavaPlugin {

    private TeleportHandler teleportHandler = null;

    @Override
    public void onEnable() {
        if (!getServer().getPluginManager().getPlugin("TiveCore").isEnabled()){
            getServer().getPluginManager().disablePlugin(this);
        }

        loadConfig();

        this.teleportHandler = new TeleportHandler();
        loadCommands();
    }

    public TeleportHandler getTeleportHandler() {
        return this.teleportHandler;
    }

    public void loadConfig(){
        getConfig().options().copyDefaults(true);
        saveConfig();
    }

    public void loadCommands(){
        getCommand("crandomtp").setExecutor(new CmdCRandomTP());
        getCommand("crandomtp").setTabCompleter(new CmdCRandomTP());
    }

}
