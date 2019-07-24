package schematicplus.core.gui.item;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import schematicplus.core.nbt.MainNBTTag;
import schematicplus.core.nbt.NBTStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BItem {
    private int amount;
    private Material m;
    private ItemStack item;
    public BItem(Material m) {
        this.item = new ItemStack(m);
    }
    public BItem(Material m,int amount) {
        this.item = new ItemStack(m,amount);
    }
    public BItem(Material m,int amount,short durability) {
        this.item = new ItemStack(m,amount,durability);
    }
    public BItem(ItemStack item) {
        this.item = item;
    }
    public Map<Enchantment, Integer> getEnchantments() {
        return item.getEnchantments();
    }
    public void applyEnchantment(Enchantment e,Integer level) {
        item.addUnsafeEnchantment(e,level);
    }

    public void setEnchantments(Map<Enchantment,Integer> ench) {
        for (Enchantment e : ench.keySet()) {
            if (!getEnchantments().containsKey(e)) {
                applyEnchantment(e,ench.get(e));
            }
        }
    }
    public String getDisplayName() {
        ItemMeta meta = item.getItemMeta();
        return meta.getDisplayName();
    }
    public void setDisplayName(String name) {
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&',name));
        item.setItemMeta(meta);
    }
    public List<String> getLore() {
        ItemMeta meta = item.getItemMeta();
        return meta.getLore();
    }
    public void setLore(List<String> name) {
        ItemMeta meta = item.getItemMeta();
        List<String> list = new ArrayList<>();
        for (String key : name) {
            list.add(ChatColor.translateAlternateColorCodes('&',key));
        }
        meta.setLore(list);
        item.setItemMeta(meta);
    }
    public String getSignature() {
        NBTStack itemnbt = new NBTStack(item);
        MainNBTTag tag = itemnbt.getTag();
        return tag.hasTag("signature")?tag.getString("signature").replaceAll("\"",""):null;
    }
    public void setSignature(String signature) {
        NBTStack itemnbt = new NBTStack(item);
        MainNBTTag tag = itemnbt.getTag();
        tag.setString("signature",signature);
        itemnbt.setTag(tag);
        item = itemnbt.build();
    }
    public String getNBTString(String nbtstring) {
        NBTStack itemnbt = new NBTStack(item);
        MainNBTTag tag = itemnbt.getTag();
        return tag.hasTag(nbtstring)?tag.getString(nbtstring).replaceAll("\"",""):null;
    }
    public void setNBTString(String nbtstring,String nbtvalue) {
        NBTStack itemnbt = new NBTStack(item);
        MainNBTTag tag = itemnbt.getTag();
        tag.setString(nbtstring,nbtvalue);
        itemnbt.setTag(tag);
        item = itemnbt.build();
    }
    public void setDurability(short dura) {
        item.setDurability(dura);
    }
    public ItemStack build() {
        return this.item;
    }
}
