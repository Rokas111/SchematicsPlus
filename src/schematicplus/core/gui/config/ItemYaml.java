package schematicplus.core.gui.config;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import schematicplus.core.config.Config;
import schematicplus.core.gui.item.BItem;

import java.util.Collections;
import java.util.List;

public class ItemYaml extends Config {
    private YamlConfiguration yaml;

    public ItemYaml() {
        super("items");
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

    public ItemStack toItem(String key, String schematicname) {
        if (getYaml().contains(key + ".material")) {
            BItem item = !(getYaml().contains(key + ".amount")) ? new BItem(Material.valueOf(getYaml().getString(key + ".material")), getYaml().getInt(key + ".amount")) : new BItem(Material.valueOf(getYaml().getString(key + ".material")));
            if (getYaml().contains(key + ".enchanted") && getYaml().getBoolean(key + ".enchanted")) {
                item.applyEnchantment(Enchantment.DAMAGE_ALL, 1);
            }
            if (getYaml().contains(key + ".name")) {
                item.setDisplayName(getYaml().getString((key + ".name").replaceAll("%schematic%", schematicname)));
            }
            if (getYaml().contains(key + ".lore")) {
                List<String> lore = getYaml().getStringList(key + ".lore");
                int i = 0;
                for (String ke : lore) {
                    lore.set(i, ChatColor.translateAlternateColorCodes('&', ke));
                    i++;
                }
                Collections.replaceAll(lore, "%schematic%", schematicname);
                item.setLore(lore);
            }
            if (getYaml().contains(key + ".durability")) {
                item.setDurability((short) getYaml().getInt(key + ".durability"));
            }
            return item.build();
        }
        return null;
    }
}

