package schematicplus.core.gui.item;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import schematicplus.core.logic.legacy.SinceVersion;
import schematicplus.core.logic.legacy.Version;
import schematicplus.core.main;

import java.util.Arrays;
import java.util.List;

public enum Items {
    NONE_ITEM(" ","",null,new SinceVersion(Version.MC1_7_R4,"STAINED_GLASS_PANE" +"."+0), new SinceVersion(Version.MC1_13_R1,"LEGACY_STAINED_GLASS_PANE" +"." +7)),
    NEXT_PAGE_ITEM(ChatColor.GREEN + "Next Page","nextpage", Arrays.asList(ChatColor.GREEN + "Click to view the next page"),new SinceVersion(Version.MC1_7_R4,"ARROW" +"." +0)),
    PREVIOUS_PAGE_ITEM(ChatColor.GREEN + "Previous Page","backpage",Arrays.asList(ChatColor.GREEN + "Click to view the previous page"),new SinceVersion(Version.MC1_7_R4,"ARROW" +"." +0)),
    BOTTOM_ITEM(" ","",null,new SinceVersion(Version.MC1_7_R4,"IRON_FENCE" +"." +0), new SinceVersion(Version.MC1_13_R1,"LEGACY_IRON_FENCE" +"." +0));
    private ItemStack item;
    private Items(String displayname, String signature, List<String> lore, SinceVersion... sinces) {
        BItem item =null;
        for (int i = 0; i < sinces.length;i++) {
            SinceVersion vern = sinces[i];
            if (vern.getVersion().getVersionId() <= main.vern.getVersionId() && (sinces.length == i +1  || sinces[i + 1].getVersion().getVersionId() > main.vern.getVersionId())) {
                item = Short.parseShort(vern.getOutput().split("\\.")[1]) != 0?new BItem(Material.valueOf(vern.getOutput().split("\\.")[0]),1,Short.parseShort(vern.getOutput().split("\\.")[1])):new BItem(Material.valueOf(vern.getOutput().split("\\.")[0]));
            }
        }
        item.setDisplayName(displayname);
        if (lore != null && !lore.isEmpty()) {item.setLore(lore);}
        if (!signature.isEmpty()) {item.setSignature(signature);}
        this.item = item.build();
    }
    public ItemStack getItem() {
        return this.item;
    }
}
