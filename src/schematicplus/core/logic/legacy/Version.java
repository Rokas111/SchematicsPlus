package schematicplus.core.logic.legacy;

import org.bukkit.Bukkit;

public enum Version {
    Unknown(Integer.MAX_VALUE), MC1_7_R4(174), MC1_8_R3(183), MC1_9_R1(191), MC1_9_R2(192), MC1_10_R1(1101), MC1_11_R1(1111), MC1_12_R1(1121), MC1_13_R1(1131), MC1_13_R2(1132),MC1_14_R1(1141),MC1_14_R2(1142);
    private static Version version;
    private final int versionid;

    private Version(int versionid) {
        this.versionid = versionid;
    }

    public static Version getVersion() {
        if (version != null) {
            return version;
        }
        String ver = org.bukkit.Bukkit.getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3];
        try {
            version = valueOf(ver.replace("v", "MC"));
        } catch (IllegalArgumentException ex) {
            version = Unknown;
        }
        return version;
    }

    public int getVersionId() {
        return this.versionid;
    }

    public static boolean compareVersions(Version v , Version vv) {
        return v.getVersionId()<=vv.getVersionId();
    }
}

