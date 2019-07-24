package schematicplus.core.message.config;

import org.bukkit.configuration.file.YamlConfiguration;
import schematicplus.core.config.Config;

import java.io.IOException;

public class MessageYaml extends Config {
    private YamlConfiguration yaml;
    public MessageYaml() {
        super("messages");
        this.yaml = YamlConfiguration.loadConfiguration(getFile());
        if (!setup()) {
            setupKeys();
        }
    }
    public void setupKeys() {
        yaml.set("gui.nonextpage","&cThere are no more pages");
        yaml.set("gui.nopreviouspage","&cThere is no previous page");
        yaml.set("schematic.permission","&cYou don't have the permission to load this schematic");
        yaml.set("schematic.failedtopay","&cYou can't pay enough to load this schematic");
        yaml.set("cmds.schematic.noschematics", "&cThere are no schematics loaded");
        yaml.set("cmds.schematic.load.nosuchschematic", "&cThere is no such schematic");
        yaml.set("cmds.schematic.load.wrongformat", "&cPlease use this format /schematic load <Schematic>");
        yaml.set("cmds.schematic.reload.nosuchconfig", "&cThere is no such schematic");
        yaml.set("cmds.schematic.reload.wrongformat", "&cPlease use this format /schematic load <Schematic>");
        yaml.set("cmds.schematic.give.nosuchschematic", "&cThere is no such schematic");
        yaml.set("cmds.schematic.give.playernotonline", "&cThe player isn't online");
        yaml.set("cmds.schematic.give.wrongformat", "&cPlease use this format /schematic give <Player> <Schematic>");
        yaml.set("worldguard.cannotload", "&cYou can't load a schematic here");
        try {
            yaml.save(getFile());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public YamlConfiguration getYaml() {
        return this.yaml;
    }
}
