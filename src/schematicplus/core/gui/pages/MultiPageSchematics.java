package schematicplus.core.gui.pages;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import schematicplus.core.gui.item.Items;
import schematicplus.core.gui.item.ItemsConfig;
import schematicplus.core.logic.schematics.SchematicItem;
import schematicplus.core.main;

import java.util.List;

public class MultiPageSchematics {
    private List<SchematicItem> schematics;
    private Player p;
    private int page;
    private int currentpage;
    private Inventory inv;
    public MultiPageSchematics(List<SchematicItem> schematics, Player p) {
        if (schematics.isEmpty()) {throw new NullPointerException();}
        this.p = p;
        this.schematics =schematics;
        this.inv = Bukkit.createInventory(null, 54, main.cm.getGuiYaml().getGuiTitle("schematicsgui"));
        this.page = schematics.size()>45?(Integer.parseInt(Double.toString(schematics.size() / 45).split("\\.")[0])  +1):1;
        this.currentpage = 1;
        SchematicsGUI(currentpage);
    }
    public void SchematicsGUI(int page) {
        int start = 45* (page-1);
        for (int ie = 0; ie< 45;ie++) {
            if (start < schematics.size()) {
                inv.setItem(ie, schematics.get(start).build());
                start++;
            } else {
                inv.setItem(ie, main.cm.getGuiYaml().useDefault("schematicsgui")?Items.NONE_ITEM.getItem():ItemsConfig.NONE_ITEM.getItem());
            }
        }

        for (int ie = 45; ie < 54;ie++) {
            inv.setItem(ie, main.cm.getGuiYaml().useDefault("schematicsgui")?Items.BOTTOM_ITEM.getItem(): ItemsConfig.BOTTOM_ITEM.getItem());
        }
        inv.setItem(45, main.cm.getGuiYaml().useDefault("schematicsgui")?Items.PREVIOUS_PAGE_ITEM.getItem():ItemsConfig.PREVIOUS_PAGE_ITEM.getItem());
        inv.setItem(53, main.cm.getGuiYaml().useDefault("schematicsgui")?Items.NEXT_PAGE_ITEM.getItem():ItemsConfig.NEXT_PAGE_ITEM.getItem());
        p.openInventory(inv);

    }
    public void nextPage() {
        if (!(this.currentpage < this.page)) {
            p.sendMessage(main.mm.getGuiKey("nonextpage"));
            return;
        }
        currentpage++;
        SchematicsGUI(currentpage);
    }
    public void previousPage() {
        if (this.currentpage <= 1) {
            p.sendMessage(main.mm.getGuiKey("nopreviouspage"));
            return;
        }
        currentpage--;
        SchematicsGUI(currentpage);
    }
    public Player getPlayer() {
        return this.p;
    }
}
