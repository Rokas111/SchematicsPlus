package schematicplus.core.hooks.plugins;

import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.lang.reflect.InvocationTargetException;

public class WorldGuard {
    private Plugin pl;
    private WorldGuardPlugin wg;
    public WorldGuard(Plugin pl) {
        this.pl = pl;
        this.wg = getWorldGuard();
    }
    private WorldGuardPlugin getWorldGuard() {
        Plugin plugin = pl.getServer().getPluginManager().getPlugin("WorldGuard");
        return (WorldGuardPlugin) plugin;
    }
    public boolean checkBlock(Location loc, Player p) {
        try {
            return (boolean) wg.getClass().getMethod("canBuild",new Class[]{Player.class,Location.class}).invoke(wg,new Object[]{p,loc});
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            return true;
        }
    }
}
