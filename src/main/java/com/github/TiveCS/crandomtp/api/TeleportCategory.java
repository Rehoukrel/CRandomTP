package com.github.TiveCS.crandomtp.api;

import com.github.TiveCS.crandomtp.CRandomTP;
import com.github.TiveCS.tivecore.lab.utils.xseries.XMaterial;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.util.Vector;

import java.util.EnumSet;
import java.util.Random;

public class TeleportCategory {

    private final static CRandomTP plugin = CRandomTP.getPlugin(CRandomTP.class);
    private final static EnumSet<Material> LIQUID = EnumSet.of(XMaterial.WATER.parseMaterial(), XMaterial.LAVA.parseMaterial());

    private int xMin, xMax;
    private int zMin, zMax;
    private Random random;
    private String category;

    private TeleportHandler handler;

    public TeleportCategory(TeleportHandler handler, String category, int xMin, int xMax, int zMin, int zMax){
        this.random = new Random();
        this.handler = handler;

        this.xMin = xMin;
        this.xMax = xMax;

        this.zMin = zMin;
        this.zMax = zMax;
    }

    public int generateX(){
        return Math.max(xMin, random.nextInt(xMax));
    }

    public int generateZ(){
        return Math.max(zMin, random.nextInt(zMax));
    }

    public Location getLocationAt(String world, int x, int z){
        Location loc = null;
        World w = plugin.getServer().getWorld(world);

        x = random.nextBoolean() ? x : -x;
        z = random.nextBoolean() ? z : -z;

        Block b = w.getBlockAt(handler.getCenterX() + x, w.getHighestBlockYAt(x, z) + 1, handler.getCenterZ() + z);

        if (!b.isLiquid() && isAboveSafe(b)){
            loc = b.getLocation().add(new Vector(0, 1, 0));
            return loc;
        }

        return loc;
    }

    public void teleport(String world, Entity... entities){
        int x = generateX(), z = generateZ();
        Location loc = null;
        do{
            loc = getLocationAt(world, x, z);
        }while(loc == null);

        if (loc != null){
            for (Entity e : entities){
                e.teleport(loc);
            }
        }
    }

    public boolean isAboveSafe(Block b){
        Block at = b.getWorld().getBlockAt(b.getX(), b.getY() + 1, b.getZ());
        return at.isEmpty() || (at.isPassable() && !at.isLiquid());
    }

    public boolean isBelowSafe(Block b){
        Block at = b.getWorld().getBlockAt(b.getX(), b.getY() - 1, b.getZ());
        return !at.isEmpty() && at.getType().isSolid() && !at.isLiquid();
    }

    public String getCategory() {
        return category;
    }

    public int getMaximumX() {
        return xMax;
    }

    public int getMinimumX() {
        return xMax;
    }

    public int getMinimumZ(){
        return zMin;
    }

    public int getMaximumZ(){
        return zMax;
    }
}
