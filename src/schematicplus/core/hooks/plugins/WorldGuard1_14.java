package schematicplus.core.hooks.plugins;

import com.sk89q.worldguard.LocalPlayer;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class WorldGuard1_14 {
    private Plugin pl;
    private WorldGuardPlugin wg;
    public WorldGuard1_14(Plugin pl) {
        this.pl = pl;
        this.wg = getWorldGuard();
    }
    private WorldGuardPlugin getWorldGuard() {
        Plugin plugin = pl.getServer().getPluginManager().getPlugin("WorldGuard");
        return (WorldGuardPlugin) plugin;
    }
    public boolean checkBlock(Location loc, Player p) {
        try {
        if (WorldGuard.getInstance().getPlatform().getRegionContainer().createQuery().getApplicableRegions((com.sk89q.worldedit.util.Location) Class.forName("com.sk89q.worldedit.bukkit.BukkitAdapter").getMethod("adapt",new Class[]{Location.class}).invoke(Class.forName("com.sk89q.worldedit.bukkit.BukkitAdapter"),loc))== null) {
            return true;
        }
        return WorldGuard.getInstance().getPlatform().getRegionContainer().createQuery().getApplicableRegions((com.sk89q.worldedit.util.Location) Class.forName("com.sk89q.worldedit.bukkit.BukkitAdapter").getMethod("adapt",new Class[]{Location.class}).invoke(Class.forName("com.sk89q.worldedit.bukkit.BukkitAdapter"),loc)).isMemberOfAll(wg.wrapPlayer(p));
        } catch(Exception e) {
            e.printStackTrace();
            return true;
        }
        }
}
