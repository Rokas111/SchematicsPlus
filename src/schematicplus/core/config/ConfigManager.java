package schematicplus.core.config;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import schematicplus.core.config.configs.ConfigYaml;
import schematicplus.core.gui.config.GuiYaml;
import schematicplus.core.gui.config.ItemYaml;
import schematicplus.core.logic.config.SchematicYaml;
import schematicplus.core.message.config.MessageYaml;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ConfigManager {
    private List<Config> configs;
    public ConfigManager() {
        configs = new ArrayList<>();
        setupDir();
        setupConfigs();
        setupSchematics();
    }
    public void setupDir() {
        File file =new File("plugins//SchematicsPlus");
        if (!file.exists()) {
            file.mkdir();
        }
    }
    public void setupSchematics() {
        File file =new File("plugins//SchematicsPlus//Schematics");
        if (!file.exists()) {
            file.mkdir();
        }
    }
    public void setupConfigs() {
        configs.add(new ConfigYaml());
        configs.add(new ItemYaml());
        configs.add(new SchematicYaml());
        configs.add(new MessageYaml());
        configs.add(new GuiYaml());
        Bukkit.getConsoleSender().sendMessage("[SchematicsPlus] " + ChatColor.GREEN + "Configs loaded");
    }
    public File getSchematicDirectory() {
        return new File("plugins//SchematicsPlus//Schematics");
    }
    public Config getYaml(String name) {
        for (Config c : configs) {
            if (c.getConfig().equals(name)) {
                return c;
            }
        }
        return null;
    }
    public ConfigYaml getConfig() {
        return (ConfigYaml) getYaml("config");
    }
    public ItemYaml getItemYaml() {
        return (ItemYaml) getYaml("items");
    }
    public SchematicYaml getSchematicYaml() {
        return (SchematicYaml) getYaml("schematics");
    }
    public MessageYaml getMessageYaml() {
        return (MessageYaml) getYaml("messages");
    }
    public GuiYaml getGuiYaml() {
        return (GuiYaml) getYaml("guis");
    }
}
