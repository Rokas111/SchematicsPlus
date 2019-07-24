package schematicplus.core.gui;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.plugin.Plugin;
import schematicplus.core.gui.item.BItem;
import schematicplus.core.gui.pages.MultiPageSchematics;
import schematicplus.core.logic.schematics.Schematic;
import schematicplus.core.logic.schematics.SchematicItem;
import schematicplus.core.main;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GuiManager implements Listener {
    private Plugin pl;
    private Map<Player, MultiPageSchematics> schematicGUIs;
    public GuiManager(Plugin pl) {
        this.pl = pl;
        pl.getServer().getPluginManager().registerEvents(this,this.pl);
        schematicGUIs = new HashMap<>();
    }
    public void addSchematicGUI(Player p, List<SchematicItem> schematics) {
        schematicGUIs.put(p, new MultiPageSchematics(schematics,p));
    }
    public MultiPageSchematics getSchematicGUI(Player p) {
        return schematicGUIs.get(p);
    }
    public void removeSchematicGUI(Player p) {
        schematicGUIs.remove(p);
    }
    @EventHandler
    public void click(InventoryClickEvent e) {
        if (!e.getView().getTitle().equals(main.cm.getGuiYaml().getGuiTitle("schematicsgui"))) {return;}
        e.setCancelled(true);
        BItem item = new BItem(e.getCurrentItem());
        Player p = (Player)e.getWhoClicked();
        if (item.getSignature() != null) {
            if (item.getSignature().equals("nextpage")) {getSchematicGUI(p).nextPage();} else {getSchematicGUI(p).previousPage();}
        }
        if (item.getNBTString("schematic") != null) {
                if (e.getClick()  == ClickType.RIGHT) {
                    new Schematic(new File("plugins//SchematicsPlus//Schematics//" +item.getNBTString("schematic")+".schematic")).load(p.getLocation());
                    p.closeInventory();
                } else {
                    p.getInventory().addItem(item.build());
                    p.updateInventory();
                    p.closeInventory();
                }
        }
    }
    @EventHandler
    public void close(InventoryCloseEvent e) {
        if (!e.getView().getTitle().equals("Schematics")) {return;}
        schematicGUIs.remove((Player)e.getPlayer());
    }

}
