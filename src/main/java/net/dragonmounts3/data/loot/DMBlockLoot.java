package net.dragonmounts3.data.loot;

import net.dragonmounts3.block.HatchableDragonEggBlock;
import net.dragonmounts3.init.DMBlocks;
import net.dragonmounts3.registry.DragonType;
import net.minecraft.block.Block;
import net.minecraft.data.loot.BlockLootTables;
import net.minecraft.loot.*;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;
import java.util.HashSet;
import java.util.Set;
import java.util.function.BiConsumer;

import static net.minecraft.loot.LootTable.lootTable;

public class DMBlockLoot extends BlockLootTables {

    @Override
    protected void addTables() {
        this.add(DMBlocks.DRAGON_CORE, lootTable());
        this.dropSelf(DMBlocks.DRAGON_NEST);
        for (DragonType type : DragonType.REGISTRY) {//Do NOT load other mods at the same time!
            HatchableDragonEggBlock block = type.getInstance(HatchableDragonEggBlock.class, null);
            if (block != null) {
                this.dropDragonEgg(block);
            }
        }
    }

    public void accept(@Nonnull BiConsumer<ResourceLocation, LootTable.Builder> biConsumer) {
        this.addTables();
        Set<ResourceLocation> set = new HashSet<>();
        for (Block block : getKnownBlocks()) {
            ResourceLocation resource = block.getLootTable();
            if (resource != LootTables.EMPTY && set.add(resource)) {
                LootTable.Builder lootTable$builder = this.map.remove(resource);
                if (lootTable$builder != null) {
                    biConsumer.accept(resource, lootTable$builder);
                }
            }
        }

        if (!this.map.isEmpty()) {
            throw new IllegalStateException("Created block loot tables for non-blocks: " + this.map.keySet());
        }
    }

    protected void dropDragonEgg(Block block) {
        this.add(block, lootTable().withPool(LootPool.lootPool().setRolls(ConstantRange.exactly(1)).add(ItemLootEntry.lootTableItem(block))));
    }
}