package schematicplus.core.config.configs;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;
import schematicplus.core.config.Config;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ConfigYaml extends Config {
    private YamlConfiguration yaml;
    public ConfigYaml() {
        super("config");
        this.yaml = YamlConfiguration.loadConfiguration(getFile());
        if (!setup()) {
            setupKeys();
        }
    }
    public void setupKeys() {
        yaml.set("pluginSupport.CheckToPlaceInASkyblock",false);
        yaml.set("pluginSupport.ChecktoPlaceInWorldGuard",false);
        try {
            yaml.save(getFile());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public YamlConfiguration getYaml() {
        return this.yaml;
    }
    public String getName(String schematicname) {
        String name =getYaml().getString("default.schematicName");
        if (name.contains("%schematic%")) {
            name =name.replaceAll("%schematic%",schematicname);
        }
        return ChatColor.translateAlternateColorCodes('&',name);
    }
    public List<String> getLore(String schematicname) {
         List<String> list =getYaml().getStringList("default.schematicLore");
        List<String> newlist = new ArrayList<>();
         for (String key:list) {
             if (key.contains("%schematic%")) {
                 key =key.replaceAll("%schematic%",schematicname);
             }
                newlist.add(ChatColor.translateAlternateColorCodes('&',key));
         }
         return newlist;
    }
}
