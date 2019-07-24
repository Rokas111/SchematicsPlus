package schematicplus.core.message;

import org.bukkit.ChatColor;
import org.bukkit.plugin.Plugin;
import schematicplus.core.config.ConfigManager;
import schematicplus.core.main;

public class MessageManager {
    private Plugin pl;
    public MessageManager(Plugin pl) {
        this.pl = pl;
    }
    private String decrypt(String name) {
        return ChatColor.translateAlternateColorCodes('&',name);
    }
    public String getGuiKey(String key) {
        return decrypt(main.cm.getMessageYaml().getYaml().getString("gui."+key));
    }
    public String getSchematicLoadKey(String key) {
        return decrypt(main.cm.getMessageYaml().getYaml().getString("schematic."+key));
    }
    public String getSchematicCmdKey(String cmd,String key) {
        return decrypt(main.cm.getMessageYaml().getYaml().getString("cmds."+cmd+"."+key));
    }
    public String getSchematicCmdArgKey(String cmd,String subcmd,String key) {
        return decrypt(main.cm.getMessageYaml().getYaml().getString("cmds."+cmd+"."+subcmd+"."+key));
    }
    public String getWorldGuardKey(String key) {
        return decrypt(main.cm.getMessageYaml().getYaml().getString("worldguard." + key));
    }
}
