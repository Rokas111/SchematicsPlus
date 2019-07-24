package schematicplus.core;


import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import schematicplus.core.cmd.CommandManager;
import schematicplus.core.config.ConfigManager;
import schematicplus.core.gui.GuiManager;
import schematicplus.core.hooks.HookManager;
import schematicplus.core.logic.schematics.SchematicsManager;
import schematicplus.core.logic.legacy.Version;
import schematicplus.core.message.MessageManager;
import schematicplus.core.metrics.Metrics;

public class main extends JavaPlugin
{
    public static WorldEditPlugin worldedit;
    public static SchematicsManager sm;
    public static ConfigManager cm;
    public static Version vern;
    public static GuiManager gm;
    public static HookManager hm;
    public static CommandManager cmdm;
    public static MessageManager mm;
    public static Economy econ;
    public void onEnable()
    {
        vern = Version.getVersion();
        cmdm = new CommandManager(this);
        cm = new ConfigManager();
        sm = new SchematicsManager(this);
        gm = new GuiManager(this);
        hm = new HookManager(this);
        mm = new MessageManager(this);
        Metrics metrics = new Metrics(this);
        worldedit = (WorldEditPlugin)Bukkit.getServer().getPluginManager().getPlugin("WorldEdit");
        Bukkit.getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
            @Override
            public void run() {
                if (setupEconomy()) {
                    Bukkit.getConsoleSender().sendMessage("[SchematicsPlus] " + ChatColor.RED +"Vault not found");
                }
            }
        },15);
    }

    public void onDisable() {
        Bukkit.getScheduler().cancelTasks(this);
    }
    private boolean setupEconomy() {
        if (Bukkit.getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }

        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        econ = rsp.getProvider();
        return econ != null;
    }
}
