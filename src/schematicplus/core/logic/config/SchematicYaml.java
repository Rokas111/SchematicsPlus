package schematicplus.core.logic.config;

import org.bukkit.configuration.file.YamlConfiguration;
import schematicplus.core.config.Config;
import schematicplus.core.logic.schematics.Schematic;
import schematicplus.core.logic.schematics.SchematicCostType;
import schematicplus.core.logic.schematics.SchematicItem;
import schematicplus.core.main;

import java.io.File;
import java.util.HashMap;

public class SchematicYaml extends Config {
    private YamlConfiguration yaml;
    public SchematicYaml() {
        super("schematics");
        this.yaml = YamlConfiguration.loadConfiguration(getFile());
        if (!setup()) {
            setupKeys();
        }
    }
    public void setupKeys() {
    }
    public YamlConfiguration getYaml() {
        return this.yaml;
    }
    public SchematicItem getSchematic(String schematic) {
        return getCosts(schematic).isEmpty()?new SchematicItem(new Schematic(new File(yaml.getString(schematic + ".schematic") + ".schematic")), main.cm.getItemYaml().toItem(yaml.getString(schematic+".item"), yaml.getString(schematic + ".schematic")),yaml.getString(schematic+".permission")):new SchematicItem(new Schematic(new File(yaml.getString(schematic + ".schematic") + ".schematic")), main.cm.getItemYaml().toItem(yaml.getString(schematic+".item"), yaml.getString(schematic + ".schematic")),yaml.getString(schematic+".permission"),getCosts(schematic));
    }
    public HashMap<SchematicCostType,Double> getCosts(String schematic) {
        HashMap<SchematicCostType,Double> costs = new HashMap<>();
        if (!(yaml.contains(schematic+".costs"))||yaml.getConfigurationSection(schematic+".costs").getKeys(false).isEmpty()) { return costs; }
        for (String key:yaml.getConfigurationSection(schematic+".costs").getKeys(false)) {
            if (SchematicCostType.valueOf(key) != null) {
                costs.put(SchematicCostType.valueOf(key),yaml.getDouble(schematic+".costs."+key));
            }
        }
        return costs;
    }
    public String getSchematicByFile(String schematic) {
        for (String key:yaml.getKeys(false)) {
            if (yaml.getString(key+".schematic").equals(schematic)) {
                return key;
            }
        }
        return null;
    }
}
