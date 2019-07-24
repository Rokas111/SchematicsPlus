package schematicplus.core.logic.schematics;

import org.bukkit.Bukkit;
import schematicplus.core.main;

public enum SchematicCostType {
    MONEY("VAULT"),
    XP("");
    private String dependency;
    private SchematicCostType(String dependency) {
        this.dependency = dependency;
    }
    public boolean canSupport() {
        return Bukkit.getServer().getPluginManager().isPluginEnabled(this.dependency);
    }
    public boolean hasDependency() {
        return !(this.dependency.isEmpty());
    }
}
