package schematicplus.core.nbt;


import org.bukkit.Bukkit;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

public class MainNBTTag {
    private Object tag;
    public MainNBTTag(Object tag) {
        try {
            this.tag = tag == null ?Classes.getNETClass("NBTTagCompound").newInstance():tag;
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }
    public Object getTag() {
        return this.tag;
    }
    public boolean hasTag(String key) {
        try {
            return ((Boolean)this.tag.getClass().getMethod("hasKey", new Class[]{String.class}).invoke(this.tag,new Object[]{key})).booleanValue();
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            return false;
        }
    }
    public String getString(String key) {
        try {
            return tag.getClass().getMethod("get", new Class[]{String.class}).invoke(this.tag, new Object[]{key}).toString();
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            return null;
        }
    }
    public Integer getInteger(String key) {
        try {
            return ((Integer)tag.getClass().getMethod("get", new Class[]{String.class}).invoke(this.tag, new Object[]{key})).intValue();
        } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            return null;
        }
    }

    public void setInteger(String key, Integer value) {
        try {
            tag.getClass().getMethod("setInteger", new Class[]{String.class,Integer.TYPE}).invoke(this.tag, new Object[]{key,value});
        } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }
    public void setString(String key, String value) {
        try {
            tag.getClass().getMethod("setString",new Class[]{String.class,String.class}).invoke(tag,new Object[]{key,value});
        } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

}
