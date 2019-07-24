package schematicplus.core.logic.schematics;



import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import schematicplus.core.gui.item.BItem;
import schematicplus.core.logic.legacy.Version;
import schematicplus.core.main;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SchematicsManager implements Listener {
    private List<SchematicItem> schematics;
    private Plugin pl;

    public SchematicsManager(Plugin p) {
        schematics = new ArrayList<>();
        this.pl = p;
        pl.getServer().getPluginManager().registerEvents(this,this.pl);
        registerSchematics();
    }

    public List<SchematicItem> getSchematics() {
        return this.schematics;
    }

    public void registerSchematics() {
        if (main.cm.getSchematicYaml().getYaml() == null) {
            return;
        }
        if (!main.cm.getSchematicYaml().getYaml().getKeys(false).isEmpty()) {
            for (String key : main.cm.getSchematicYaml().getYaml().getKeys(false)) {
                if (main.cm.getSchematicYaml().getYaml().contains(key + ".schematic") && exists(new File("plugins//SchematicsPlus//Schematics"), main.cm.getSchematicYaml().getYaml().getString(key + ".schematic"))) {
                    registerSchematic(key);
                }
            }
            Bukkit.getConsoleSender().sendMessage("[SchematicsPlus] " + ChatColor.GREEN + "Schematics loaded");
        }
    }

    public boolean exists(File directory, String schematic) {
        if (directory == null || !directory.isDirectory() || directory.listFiles() == null) {
            return false;
        }
        for (File sch : directory.listFiles()) {
            if (sch.getName().split("\\.")[0].equals(schematic)) {
                return true;
            }
        }
        return false;
    }

    public void registerSchematic(String schematic) {
        schematics.add(main.cm.getSchematicYaml().getSchematic(schematic));
    }

    public boolean isRegistered(String schematic) {
        return main.cm.getSchematicYaml().getYaml().getKeys(false).contains(schematic) && !schematics.isEmpty() && scheContains(schematic);
    }

    public boolean isSchematicInFile(File sch) {
        return new File("plugins//SchematicsPlus//Schematics//" + sch.getName()).exists();
    }

    public boolean scheContains(String schematic) {
        for (SchematicItem key : schematics) {
            if (key.getSchematic().equals(main.cm.getSchematicYaml().getSchematic(schematic).getSchematic())) {
                return true;
            }
        }
        return false;
    }

    public boolean canPay(HashMap<SchematicCostType, Double> costs, Player p) {
        for (SchematicCostType sc : costs.keySet()) {
            if (!canPayType(costs.get(sc), sc, p)) {
                return false;
            }
        }
        return true;
    }

    public void pay(HashMap<SchematicCostType, Double> costs, Player p) {
        for (SchematicCostType sc : costs.keySet()) {
            payType(costs.get(sc), sc, p);
        }
    }

    public void payType(Double cost, SchematicCostType sc, Player p) {
        if (sc == SchematicCostType.MONEY && canPayType(cost, sc, p)) {
            main.econ.withdrawPlayer(p, cost);

        }
        if (sc == SchematicCostType.XP && canPayType(cost, sc, p)) {
            p.setLevel(Integer.parseInt(Double.toString(p.getLevel() - cost).split("\\.")[0]));
            p.updateInventory();
        }
    }

    public boolean canPayType(Double cost, SchematicCostType sc, Player p) {
        if (sc == SchematicCostType.MONEY && main.econ.has(p, cost)) {
            return true;
        }
        if (sc == SchematicCostType.XP && cost <= p.getLevel()) {
            return true;
        }
        return false;
    }

    @EventHandler
    public void oninteraction(PlayerInteractEvent e) {
        if (main.vern.getVersionId() >= Version.MC1_9_R1.getVersionId() && !e.getHand().equals(EquipmentSlot.HAND)) {
            return;
        }
        Player p = e.getPlayer();
        ItemStack plitem = main.vern.getVersionId() >= Version.MC1_9_R1.getVersionId() ? p.getInventory().getItemInMainHand() : p.getItemInHand();
        Location plloc = new Location(p.getWorld(), p.getLocation().getX(), p.getLocation().getY() + 1, p.getLocation().getZ());
        if (plitem == null || !(e.getAction() == Action.RIGHT_CLICK_BLOCK) || !plloc.getBlock().isEmpty()) {
            return;
        }
        BItem item = new BItem(plitem);
        if (item.getNBTString("schematic") != null && main.hm.canPlace(plloc, p)) {
            String schematic = main.cm.getSchematicYaml().getSchematicByFile(item.getNBTString("schematic"));
            Schematic sch = new Schematic(new File("plugins//SchematicsPlus//Schematics//" + item.getNBTString("schematic") + ".schematic"));
            String permission = item.getNBTString("perm");
            if (p.hasPermission(permission)) {
                if (!(main.cm.getSchematicYaml().getYaml().contains(schematic + ".costs")) || main.sm.canPay(main.cm.getSchematicYaml().getCosts(schematic), p)) {
                    main.sm.pay(main.cm.getSchematicYaml().getCosts(schematic), p);
                    if (main.cm.getConfig().getYaml().getBoolean("pluginSupport.ChecktoPlaceInWorldGuard")) {sch.load(plloc,p);} else {sch.load(plloc);}
                    if (plitem.getAmount() > 1) {
                        plitem.setAmount(plitem.getAmount() - 1);
                    } else {
                        plitem.setType(Material.AIR);
                    }
                    if (main.vern.getVersionId() >= Version.MC1_9_R1.getVersionId()) {
                        p.getInventory().setItemInMainHand(plitem);
                    } else {
                        p.setItemInHand(plitem);
                    }
                    p.updateInventory();
                } else {
                    p.sendMessage(main.mm.getSchematicLoadKey("failedtopay"));
                }
            } else {
                p.sendMessage(main.mm.getSchematicLoadKey("permission"));
            }
        }

    }
    public List<SchematicItem> getAligableSchematics(Player p) {
        List<SchematicItem> schematics = new ArrayList<>();
        for (SchematicItem sc : getSchematics()) {
            if (p.hasPermission(new BItem(sc.build()).getNBTString("perm"))) {
                schematics.add(sc);
            }
        }
        return schematics;
    }
    public boolean canLoad(Location startloc,Player p,Object c) {
        try {
            Object vector = c.getClass().getMethod("getDimensions", new Class[0]).invoke(c, new Object[0]);
            Location minpoint = new Location(startloc.getWorld(), (startloc.getX() - ((Integer)vector.getClass().getMethod("getX", new Class[0]).invoke(vector, new Object[0]) / 2)), startloc.getY(), startloc.getZ());
            Location maxpoint = new Location(startloc.getWorld(), (startloc.getX() + ((Integer)vector.getClass().getMethod("getX", new Class[0]).invoke(vector, new Object[0]) / 2)), startloc.getY() + (Integer)vector.getClass().getMethod("getY", new Class[0]).invoke(vector, new Object[0]), startloc.getZ() + (Integer)vector.getClass().getMethod("getZ", new Class[0]).invoke(vector, new Object[0]));
            for (int i = (int) minpoint.getX(); i < maxpoint.getX(); i++) {
                for (int y = (int) minpoint.getY(); y < maxpoint.getY(); y++) {
                    for (int z = (int) minpoint.getZ(); z < maxpoint.getZ(); z++) {
                        if (main.hm.checkBlock(new Location(startloc.getWorld(), i, y, z), p)) {
                            return true;
                        }
                    }
                }
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return true;
        }
    }
    public boolean canLoadCuboid(Location startloc,Player p,Object c) {
        try {
            Location minpoint = new Location(startloc.getWorld(), (startloc.getX() - ((Integer)c.getClass().getMethod("getWidth", new Class[0]).invoke(c, new Object[0]) / 2)), startloc.getY(), startloc.getZ());
            Location maxpoint = new Location(startloc.getWorld(), (startloc.getX() + ((Integer)c.getClass().getMethod("getWidth", new Class[0]).invoke(c, new Object[0])/ 2)), startloc.getY() + (Integer)c.getClass().getMethod("getHeight", new Class[0]).invoke(c, new Object[0]), startloc.getZ() + (Integer)c.getClass().getMethod("getLength", new Class[0]).invoke(c, new Object[0]));
            for (int i = (int) minpoint.getX(); i < maxpoint.getX(); i++) {
                for (int y = (int) minpoint.getY(); y < maxpoint.getY(); y++) {
                    for (int z = (int) minpoint.getZ(); z < maxpoint.getZ(); z++) {
                        if (main.hm.checkBlock(new Location(startloc.getWorld(), i, y, z), p)) {
                            return true;
                        }
                    }
                }
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return true;
        }
    }
}
