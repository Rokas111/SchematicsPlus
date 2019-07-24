package schematicplus.core.hooks.plugins;

import com.wasteofplastic.askyblock.ASkyBlockAPI;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class ASkyblock {
    private Plugin pl;
    public ASkyblock(Plugin pl) {
        this.pl = pl;
    }
    public boolean canPlace(Location l, Player p) {
        return ASkyBlockAPI.getInstance().getIslandAt(l) != null && (ASkyBlockAPI.getInstance().getIslandAt(l).getMembers().contains(p.getUniqueId()));
    }
}
