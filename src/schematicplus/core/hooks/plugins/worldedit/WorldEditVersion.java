package schematicplus.core.hooks.plugins.worldedit;

import org.bukkit.Bukkit;

public enum WorldEditVersion {
    V7(7),
    V6(6),
    Unknown(Integer.MAX_VALUE);
    private int versionid;
    private WorldEditVersion(int versionid) {
        this.versionid = versionid;
    }
    public int getVersionId() {
        return this.versionid;
    }
    public static WorldEditVersion getVersion() {
        String version = "V"+Bukkit.getServer().getPluginManager().getPlugin("WorldEdit").getDescription().getVersion().split(";")[0].substring(0,1);
        try {
            return valueOf(version);
        } catch(IllegalArgumentException e) {
            e.printStackTrace();
            return null;
        }
    }
}
