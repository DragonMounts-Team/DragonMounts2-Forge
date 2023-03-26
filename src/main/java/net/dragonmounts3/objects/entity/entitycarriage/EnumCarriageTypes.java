package net.dragonmounts3.objects.entity.entitycarriage;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;

public enum EnumCarriageTypes {
    OAK(Blocks.OAK_PLANKS, "oak"),
    SPRUCE(Blocks.SPRUCE_PLANKS,"spruce"),
    BIRCH(Blocks.BIRCH_PLANKS, "birch"),
    JUNGLE(Blocks.JUNGLE_PLANKS, "jungle"),
    ACACIA(Blocks.ACACIA_PLANKS, "acacia"),
    DARK_OAK(Blocks.DARK_OAK_PLANKS, "dark_oak"),

    //mangrove
    //cherry
    //bamboo

    CRIMSON(Blocks.CRIMSON_PLANKS, "crimson"),
    WARPED(Blocks.WARPED_PLANKS, "warped");
    private final String name;
    private final Block planks;
    EnumCarriageTypes(Block planks, String name) {
        this.name = name;
        this.planks = planks;
    }
    public String getName() {
        return this.name;
    }
    public Block getPlanks() {
        return this.planks;
    }
    public String toString() {
        return this.name;
    }

    public static EnumCarriageTypes byId(int pId) {
        EnumCarriageTypes[] types = values();
        if (pId < 0 || pId >= types.length) {
            pId = 0;
        }
        return types[pId];
    }

    public static EnumCarriageTypes byName(String pName) {
        EnumCarriageTypes[] types = values();
        for (EnumCarriageTypes type : types) {
            if (type.getName().equals(pName)) {
                return type;
            }
        }
        return types[0];
    }
}
