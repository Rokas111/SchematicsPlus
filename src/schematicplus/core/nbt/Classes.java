package schematicplus.core.nbt;


import org.bukkit.Bukkit;

public class Classes {
    public static Class<?> getNETClass(String classname) {
        String version = Bukkit.getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3];
        try {
            return Class.forName("net.minecraft.server." + version + "." + classname);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static Class<?> getCBukkitClass(String classname) {
        String version = Bukkit.getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3];
        try {
            return Class.forName("org.bukkit.craftbukkit." + version + "." + classname);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
