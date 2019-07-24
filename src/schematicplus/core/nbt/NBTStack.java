package schematicplus.core.nbt;

import org.bukkit.inventory.ItemStack;

import java.lang.reflect.InvocationTargetException;

public class NBTStack {
    private Object nmsstack;
    public NBTStack(ItemStack item) {

        if (item == null) {
            throw new NullPointerException();
        }
        try {
            this.nmsstack = Classes.getCBukkitClass("inventory.CraftItemStack").getMethod("asNMSCopy", new Class[]{ItemStack.class}).invoke(null, new Object[]{item});
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }
    public MainNBTTag getTag () {
        try {
            return new MainNBTTag(this.nmsstack.getClass().getMethod("getTag", new Class[0]).invoke(this.nmsstack, new Object[0]));
        } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }
    public void setTag (MainNBTTag tag) {
        try {
             this.nmsstack.getClass().getMethod("setTag", new Class[]{Classes.getNETClass("NBTTagCompound")}).invoke(this.nmsstack, new Object[]{tag.getTag()});
        } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }
    public ItemStack build () {
        try {
            return (ItemStack) Classes.getCBukkitClass("inventory.CraftItemStack").getMethod("asBukkitCopy",  new Class[]{Classes.getNETClass("ItemStack")}).invoke(null,new Object[]{nmsstack});
        } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }
}
