package schematicplus.core.logic.schematics;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import schematicplus.core.gui.item.BItem;
import schematicplus.core.main;

import java.util.HashMap;

public class SchematicItem {
    private BItem item;
    public SchematicItem(Schematic s) {
        this.item = new BItem(Material.PAPER);
        item.setDisplayName(main.cm.getConfig().getName(s.getName()));
        item.setLore(main.cm.getConfig().getLore(s.getName()));
        item.setNBTString("schematic", s.getName());
    }
    public SchematicItem(Schematic s, ItemStack item) {
        this.item = new BItem(item);
        this.item.setNBTString("schematic", s.getName());
    }
    public SchematicItem(Schematic s, ItemStack item, String perm) {
        this.item = new BItem(item);
        this.item.setNBTString("schematic", s.getName());
        this.item.setNBTString("perm",perm);
    }
    public SchematicItem(Schematic s, ItemStack item, String perm, HashMap<SchematicCostType,Double> costs) {
        this.item = new BItem(item);
        this.item.setNBTString("schematic", s.getName());
        this.item.setNBTString("perm",perm);
        for (SchematicCostType sc:costs.keySet()) {
          this.item.setNBTString("cost" + sc.name(),costs.get(sc).toString());
        }
    }
    public String getSchematic() {
        return item.getNBTString("schematic");
    }
    public ItemStack build() {
        return item.build();
    }
}
