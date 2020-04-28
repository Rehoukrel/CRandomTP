package com.github.TiveCS.crandomtp.commands;

import com.github.TiveCS.crandomtp.CRandomTP;
import com.github.TiveCS.crandomtp.api.TeleportHandler;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class CmdCRandomTP implements CommandExecutor, TabCompleter {

    private static CRandomTP plugin = CRandomTP.getPlugin(CRandomTP.class);
    private static TeleportHandler h = plugin.getTeleportHandler();

    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (command.getName().equalsIgnoreCase("crandomtp")){
            if (commandSender.hasPermission("crandomtp.command")) {
                if (commandSender instanceof Player) {
                    Player p = (Player) commandSender;
                    if (strings.length == 0) {
                        h.teleportRandomCategory(p.getWorld().getName(), p);
                        return true;
                    } else if (strings.length == 1) {
                        if (strings[0].equalsIgnoreCase("setcenter")){
                            Location l = p.getLocation();
                            plugin.getConfig().set("center-location.x", l.getBlockX());
                            plugin.getConfig().set("center-location.z", l.getBlockZ());
                            plugin.saveConfig();
                            return true;
                        }else if (strings[0].equalsIgnoreCase("info")){
                            return true;
                        }
                        return true;
                    }
                }

                if (strings.length == 2){
                    if (strings[0].equalsIgnoreCase("tp")){
                        Player t = Bukkit.getPlayer(strings[1]);
                        String category = new ArrayList<>(h.getCategories().keySet()).get(0);
                        h.teleport(t.getWorld().getName(), category, t);
                        return true;
                    }
                    if (strings[0].equalsIgnoreCase("randomtp")){
                        Player t = Bukkit.getPlayer(strings[1]);
                        h.teleportRandomCategory(t.getWorld().getName(), t);
                        return true;
                    }
                }else if (strings.length == 3){
                    if (strings[0].equalsIgnoreCase("tp")){
                        Player t = Bukkit.getPlayer(strings[1]);
                        String category = new ArrayList<>(h.getCategories().keySet()).get(0);
                        h.teleport(strings[2], category, t);
                        return true;
                    }
                    if (strings[0].equalsIgnoreCase("randomtp")){
                        Player t = Bukkit.getPlayer(strings[1]);
                        h.teleportRandomCategory(strings[2], t);
                        return true;
                    }
                }else if (strings.length == 4){
                    if (strings[0].equalsIgnoreCase("tp")){
                        Player t = Bukkit.getPlayer(strings[1]);
                        h.teleport(strings[2], strings[3], t);
                        return true;
                    }
                }

            }
        }
        return false;
    }

    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        if (command.getName().equalsIgnoreCase("crandomtp")){
            if (strings.length == 1){
                return Arrays.asList("tp", "randomtp", "setcenter", "info");
            } else if (strings.length == 3){
                if (strings[0].equalsIgnoreCase("tp")){
                    return new ArrayList<>(plugin.getTeleportHandler().getAllowedWorlds());
                }
            }else if (strings.length == 4){
                if (strings[0].equalsIgnoreCase("tp")){
                    return new ArrayList<>(plugin.getTeleportHandler().getCategories().keySet());
                }
            }
        }
        return null;
    }
}
