package schematicplus.core.logic.schematics;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;

import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.bukkit.BukkitWorld;
import com.sk89q.worldedit.extent.Extent;
import com.sk89q.worldedit.extent.clipboard.Clipboard;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardReader;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import schematicplus.core.hooks.plugins.worldedit.WorldEditVersion;
import schematicplus.core.main;

public class Schematic
{
    private File sch;
    public Schematic(File schematic){
            this.sch = schematic;
    }
    public String getName() {
        return sch.getName().substring(0, getSchematic().getName().length()-10);
    }
    public File getSchematic() {return sch;}
    public void load(Location loc)
    {
        WorldEditVersion wv = main.hm.getWorldEditVersion();
        if (wv == WorldEditVersion.V7 ) {
                try {
                    Object format = Class.forName("com.sk89q.worldedit.extent.clipboard.io.ClipboardFormats").getMethod("findByFile", new Class[]{File.class}).invoke(Class.forName("com.sk89q.worldedit.extent.clipboard.io.ClipboardFormats"), getSchematic());
                    Method m = format.getClass().getMethod("getReader", InputStream.class);
                    m.setAccessible(true);
                    Object reader = m.invoke(format, new FileInputStream(getSchematic()));
                    Object clipboard = reader.getClass().getMethod("read",new Class[0]).invoke(reader);
                    EditSession editSession = WorldEdit.getInstance().getEditSessionFactory().getEditSession((com.sk89q.worldedit.world.World) Class.forName("com.sk89q.worldedit.bukkit.BukkitAdapter").getMethod("adapt",new Class[]{World.class}).invoke(Class.forName("com.sk89q.worldedit.bukkit.BukkitAdapter"),loc.getWorld()), -1);
                        Object operation = Class.forName("com.sk89q.worldedit.session.ClipboardHolder").getConstructor(new Class[]{Clipboard.class}).newInstance(new Object[]{clipboard});
                        operation =operation.getClass().getMethod("createPaste", new Class[]{Extent.class}).invoke(operation, editSession);
                        Object vector = Class.forName("com.sk89q.worldedit.math.BlockVector3").getMethod("at",new Class[]{Integer.TYPE,Integer.TYPE,Integer.TYPE}).invoke(Class.forName("com.sk89q.worldedit.math.BlockVector3"), new Object[]{(int)loc.getX(), (int)loc.getY(), (int)loc.getZ()});
                        operation =operation.getClass().getMethod("to", new Class[]{Class.forName("com.sk89q.worldedit.math.BlockVector3")}).invoke(operation, new Object[]{vector});
                        operation = operation.getClass().getMethod("ignoreAirBlocks", new Class[]{Boolean.TYPE}).invoke(operation, false);
                        operation = operation.getClass().getMethod("build", new Class[0]).invoke(operation, new Object[0]);
                    Class.forName("com.sk89q.worldedit.function.operation.Operations").getMethod("complete",new Class[]{Class.forName("com.sk89q.worldedit.function.operation.Operation")}).invoke(Class.forName("com.sk89q.worldedit.function.operation.Operations"), new Object[]{operation});
                        editSession.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
        } else if (wv == WorldEditVersion.V6 ) {
            try {
                Object es = Class.forName("com.sk89q.worldedit.EditSession").getConstructor(new Class[]{Class.forName("com.sk89q.worldedit.LocalWorld"),Integer.TYPE}).newInstance(new BukkitWorld(loc.getWorld()), -1);
                Object cc = Class.forName("com.sk89q.worldedit.CuboidClipboard").getMethod("loadSchematic",new Class[]{File.class}).invoke(Class.forName("com.sk89q.worldedit.CuboidClipboard"), getSchematic());
                cc.getClass().getMethod("paste",new Class[]{EditSession.class, Class.forName("com.sk89q.worldedit.Vector"),Boolean.TYPE}).invoke(cc, es,Class.forName("com.sk89q.worldedit.Vector").getConstructor(new Class[]{Integer.TYPE,Integer.TYPE,Integer.TYPE}).newInstance((int)loc.getX(),(int)loc.getY(),(int)loc.getZ()),false);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else {
            Bukkit.getConsoleSender().sendMessage("[SchematicsPlus] "+ChatColor.RED + "Your worldedit version is not supported");
        }
    }
    public void load(Location loc, Player p)
    {

        WorldEditVersion wv = main.hm.getWorldEditVersion();
        if (wv == WorldEditVersion.V7) {
            try {
                Object format = Class.forName("com.sk89q.worldedit.extent.clipboard.io.ClipboardFormats").getMethod("findByFile", new Class[]{File.class}).invoke(Class.forName("com.sk89q.worldedit.extent.clipboard.io.ClipboardFormats"), getSchematic());
                Method m = format.getClass().getMethod("getReader", InputStream.class);
                m.setAccessible(true);
                Object reader = m.invoke(format, new FileInputStream(getSchematic()));
                Object clipboard = reader.getClass().getMethod("read",new Class[0]).invoke(reader);
                if (!main.sm.canLoad(loc, p, clipboard)) {
                    p.sendMessage(main.mm.getWorldGuardKey("worldguard.cannotload"));
                    return;
                }
                load(loc);
            } catch(Exception e) {
                e.printStackTrace();
            }
        } else if (wv == WorldEditVersion.V6 ) {
            try {
                Object es = Class.forName("com.sk89q.worldedit.EditSession").getConstructor(new Class[]{Class.forName("com.sk89q.worldedit.LocalWorld"),Integer.TYPE}).newInstance(new BukkitWorld(loc.getWorld()), -1);
                Object cc = Class.forName("com.sk89q.worldedit.CuboidClipboard").getMethod("loadSchematic",new Class[]{File.class}).invoke(Class.forName("com.sk89q.worldedit.CuboidClipboard"), getSchematic());
                if (!main.sm.canLoadCuboid(loc, p, cc)) {
                    p.sendMessage(main.mm.getWorldGuardKey("worldguard.cannotload"));
                    return;
                }
                load(loc);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Bukkit.getConsoleSender().sendMessage("[SchematicsPlus] "+ChatColor.RED + "Your worldedit version is not supported");
        }
    }
}
