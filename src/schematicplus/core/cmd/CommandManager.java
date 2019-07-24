package schematicplus.core.cmd;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.plugin.Plugin;
import schematicplus.core.cmd.cmds.schematic;

import java.util.HashMap;
import java.util.List;

public class CommandManager implements Listener, TabCompleter {
    private HashMap<String,ICommand> commands;
    private Plugin pl;
    public CommandManager(Plugin plugin) {
        commands = new HashMap<>();
        this.pl = plugin;
        pl.getServer().getPluginManager().registerEvents(this,this.pl);
        setupCommands();
    }
    @EventHandler
    public void command(PlayerCommandPreprocessEvent e) {
            String commandname = e.getMessage().contains(" ") ? e.getMessage().split(" ")[0].substring(1):e.getMessage().substring(1);
            String[] args = e.getMessage().contains(" ")?e.getMessage().substring(e.getMessage().indexOf(' ')+1).split(" "):null;
            ICommand cmd = commands.get(commandname.toLowerCase());
            if (cmd != null) {
                e.setCancelled(true);
                if (e.getPlayer().hasPermission("SchematicsPlus."+ cmd.getPermission())) {
                    cmd.run(e.getPlayer(), args);
                } else {
                    e.getPlayer().sendMessage(ChatColor.RED +"You don't have the permission to execute this command");
                }
            }
    }
        private void setupCommands() {
        commands.put("schematic", new schematic());
            Bukkit.getConsoleSender().sendMessage("[SchematicsPlus] "+ChatColor.GREEN +"Commands loaded");
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        return null;
    }
}
