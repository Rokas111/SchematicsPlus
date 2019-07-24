package schematicplus.core.gui.item;

import org.bukkit.inventory.ItemStack;
import schematicplus.core.main;

public enum ItemsConfig {
    NONE_ITEM("noneitem",""),
    NEXT_PAGE_ITEM("nextpageitem","nextpage"),
    PREVIOUS_PAGE_ITEM("previouspageitem","backpage"),
    BOTTOM_ITEM("bottomitem","");
    private ItemStack item;
    private ItemsConfig(String configkey,String signature) {
        BItem item = new BItem(main.cm.getItemYaml().toItem(main.cm.getGuiYaml().getYaml().getString("schematicsgui.items."+configkey),""));
        if (!signature.isEmpty()) {item.setSignature(signature);}
        this.item = item.build();
    }
    public ItemStack getItem() {
        return this.item;
    }
}
