package net.dragonmounts3.objects.entity.carriage;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.util.IStringSerializable;

import javax.annotation.Nonnull;

public enum CarriageType {
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
    CarriageType(Block planks, String name) {
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

    public static CarriageType byId(int pId) {
        CarriageType[] types = values();
        if (pId < 0 || pId >= types.length) {
            pId = 0;
        }
        return types[pId];
    }

    public static CarriageType byName(String pName) {
        CarriageType[] types = values();
        for (CarriageType type : types) {
            if (type.getName().equals(pName)) {
                return type;
            }
        }
        return types[0];
    }
}
