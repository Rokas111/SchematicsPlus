package schematicplus.core.gui.config;

import org.bukkit.configuration.file.YamlConfiguration;
import schematicplus.core.config.Config;

import java.io.IOException;

public class GuiYaml extends Config {
    private YamlConfiguration yaml;
    public GuiYaml() {
        super("guis");
        this.yaml = YamlConfiguration.loadConfiguration(getFile());
        if (!setup()) {
            setupKeys();
        }
    }
    public void setupKeys() {
        yaml.set("schematicsgui.title", "Schematics");
        yaml.set("schematicsgui.items.default",true);
        yaml.set("schematicsgui.items.noneitem","none_item");
        yaml.set("schematicsgui.items.nextpageitem","nextpage_item");
        yaml.set("schematicsgui.items.previouspageitem","previouspage_item");
        yaml.set("schematicsgui.items.bottomitem","bottom_item");
        try {
            yaml.save(getFile());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public String getGuiTitle(String gui) {
        return getYaml().getString(gui + ".title");
    }
    public boolean useDefault(String gui) {
        return getYaml().getBoolean(gui+".items.default");
    }
    public String getGuiItem(String gui,String item) {
        return getYaml().getString(gui+".items."+item);
    }
    public YamlConfiguration getYaml() {
        return this.yaml;
    }
}
