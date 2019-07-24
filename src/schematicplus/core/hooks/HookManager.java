package schematicplus.core.hooks;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import schematicplus.core.hooks.plugins.ASkyblock;
import schematicplus.core.hooks.plugins.WorldGuard;
import schematicplus.core.hooks.plugins.WorldGuard1_14;
import schematicplus.core.hooks.plugins.worldedit.WorldEditVersion;
import schematicplus.core.main;

public class HookManager {
    private Plugin pl;
    private WorldEditVersion wv;
    private ASkyblock s;
    private WorldGuard wg;
    private WorldGuard1_14 wgn;
    public HookManager(Plugin pl) {
        this.pl = pl;
        Bukkit.getScheduler().scheduleSyncDelayedTask(pl, new Runnable() {
            @Override
            public void run() {
                setup();
            }
        },20);
    }
    private void setup() {
        this.wv = WorldEditVersion.getVersion();
        this.s = Bukkit.getServer().getPluginManager().isPluginEnabled("ASkyBlock")?new ASkyblock(pl):null;
        if (Bukkit.getServer().getPluginManager().isPluginEnabled("WorldGuard")) {setupWorldGuard();}
    }
    private void setupWorldGuard() {
        if (Bukkit.getServer().getPluginManager().getPlugin("WorldGuard").getDescription().getVersion().split(";")[0].substring(0,1).equals("7")) {
            this.wgn = new WorldGuard1_14(pl);
        } else {
            this.wg = new WorldGuard(pl);
        }
    }
    public WorldEditVersion getWorldEditVersion() {
        return this.wv;
    }
    public boolean canPlace(Location l, Player p) {
        if (main.cm.getConfig().getYaml().getBoolean("pluginSupport.CheckToPlaceInASkyblock")&&s != null) {
            return s.canPlace(l,p);
        }
        return true;
    }
    public boolean checkBlock(Location loc, Player p ) {
        if (wg == null && wgn == null) {return true;}
        if (wgn!= null) {
            return wgn.checkBlock(loc,p);
        }
        return wg.checkBlock(loc,p);
    }
}
