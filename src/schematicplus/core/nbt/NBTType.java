package schematicplus.core.nbt;

public enum NBTType {
    NBTTagEnd(0),  NBTTagByte(1),  NBTTagShort(2),  NBTTagInt(3),  NBTTagLong(4),  NBTTagFloat(5),  NBTTagDouble(6),  NBTTagByteArray(7),  NBTTagIntArray(11),  NBTTagString(8),  NBTTagList(9),  NBTTagCompound(10);

    private final int i;

    private NBTType(int i)
    {
        this.i = i;
    }
    public int getId()
    {
        return this.i;
    }

    public static NBTType valueOf(int id)
    {
        for (NBTType t : NBTType.class.getEnumConstants()) {
            if (t.getId() == id) {
                return t;
            }
        }
        return NBTTagEnd;
    }

}
