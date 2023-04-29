package net.dragonmounts3.data.loot;

import com.google.common.collect.Sets;
import net.minecraft.data.loot.EntityLootTables;
import net.minecraft.entity.EntityType;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.LootTables;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Set;
import java.util.function.BiConsumer;

public class DMEntityLoot extends EntityLootTables {

    @Override
    protected void addTables() {

    }

    public void accept(BiConsumer<ResourceLocation, LootTable.Builder> biConsumer) {
        this.addTables();
        Set<ResourceLocation> set = Sets.newHashSet();
        for (EntityType<?> entityType : ForgeRegistries.ENTITIES) {
            ResourceLocation resource = entityType.getDefaultLootTable();
            if (!isNonLiving(entityType) && resource != LootTables.EMPTY && set.add(resource)) {
                LootTable.Builder lootTable$Builder = this.map.remove(resource);
                if (lootTable$Builder != null) {
                    biConsumer.accept(resource, lootTable$Builder);
                }
            }
        }

        this.map.forEach(biConsumer::accept);
    }

}