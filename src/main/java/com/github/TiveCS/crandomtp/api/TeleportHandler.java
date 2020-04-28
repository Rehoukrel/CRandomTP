package com.github.TiveCS.crandomtp.api;

import com.github.TiveCS.crandomtp.CRandomTP;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class TeleportHandler {

    private static CRandomTP plugin = CRandomTP.getPlugin(CRandomTP.class);
    private static FileConfiguration config = plugin.getConfig();

    private List<String> allowedWorlds = new ArrayList<>();
    private HashMap<String, TeleportCategory> categories = new HashMap<>();
    private int centerX = 0, centerZ = 0;

    public TeleportHandler(){
        this.centerX = config.getInt("center-location.x");
        this.centerZ = config.getInt("center-location.z");

        loadAllowedWorlds();
        loadCategories();

        System.out.println(categories);
    }

    public void teleport(String world, String category, Entity... entities){
        TeleportCategory c = categories.get(category);
        if (c != null){
            if (allowedWorlds.contains(world)) {
                c.teleport(world, entities);
            }
        }
    }

    public void teleportRandomCategory(String world, Entity... entities){
        if (allowedWorlds.contains(world)) {
            int r = Math.max(0, new Random().nextInt(categories.size()) - 1);
            if (categories.size() > r) {
                TeleportCategory c = new ArrayList<>(categories.values()).get(r);
                if (c != null) {
                    c.teleport(world, entities);
                }
            }
        }
    }

    public void loadAllowedWorlds(){
        allowedWorlds.clear();
        allowedWorlds.addAll(config.getStringList("allowed-worlds"));
    }

    public void loadCategories(){
        categories.clear();
        for (String s : config.getConfigurationSection("teleport-category").getKeys(false)){
            int xMin, zMin;
            int xMax, zMax;
            String path = "teleport-category." + s;

            String[] x = config.getString(path + ".x").split("-");
            String[] z = config.getString(path + ".z").split("-");

            xMin = Integer.parseInt(x[0]); xMax = Integer.parseInt(x[1]);
            zMin = Integer.parseInt(x[0]); zMax = Integer.parseInt(z[1]);

            TeleportCategory c = new TeleportCategory(this, s, xMin,xMax, zMin,zMax);
            categories.put(s, c);
        }
    }

    public HashMap<String, TeleportCategory> getCategories() {
        return categories;
    }

    public List<String> getAllowedWorlds() {
        return allowedWorlds;
    }

    public int getCenterX() {
        return centerX;
    }

    public int getCenterZ() {
        return centerZ;
    }
}
