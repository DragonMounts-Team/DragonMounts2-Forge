package net.dragonmounts3.data.loot;

import com.google.common.collect.Sets;
import net.minecraft.block.Block;
import net.minecraft.data.loot.BlockLootTables;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.LootTables;
import net.minecraft.util.ResourceLocation;

import java.util.Set;
import java.util.function.BiConsumer;

public class DMBlockLoot extends BlockLootTables {

    @Override
    protected void addTables() {

    }

    public void accept(BiConsumer<ResourceLocation, LootTable.Builder> biConsumer) {
        this.addTables();
        Set<ResourceLocation> set = Sets.newHashSet();
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

}