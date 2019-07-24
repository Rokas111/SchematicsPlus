package schematicplus.core.cmd;

import org.bukkit.entity.Player;

public abstract class Command implements ICommand {
    private String name;
    private String permission;
    public Command(String name,  String permission) {
        this.name = name;
        this.permission = permission;
    }
    public String getName() {
        return this.name;
    }
    public String getPermission() {
        return this.permission;
    }

}
