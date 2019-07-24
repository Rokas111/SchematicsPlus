package schematicplus.core.cmd;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public abstract interface ICommand {
    public abstract String getName();
    public abstract void run(Player p ,String[] args);
    public abstract String getPermission();
}
