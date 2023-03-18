package net.dragonmounts3.data.provider;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import net.dragonmounts3.data.loot.DMBlockLoot;
import net.dragonmounts3.data.loot.DMEntityLoot;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.LootTableProvider;
import net.minecraft.loot.*;
import net.minecraft.util.ResourceLocation;

import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class DMLootTableProvider extends LootTableProvider {

    public DMLootTableProvider(DataGenerator generator) {
        super(generator);
    }

    @Override
    protected List<Pair<Supplier<Consumer<BiConsumer<ResourceLocation, LootTable.Builder>>>, LootParameterSet>> getTables() {
        return ImmutableList.of(Pair.of(DMBlockLoot::new, LootParameterSets.BLOCK), Pair.of(DMEntityLoot::new, LootParameterSets.ENTITY));
    }

    @Override
    protected void validate(Map<ResourceLocation, LootTable> map, ValidationTracker validationtracker) {
        map.forEach(((id, lootTable) -> LootTableManager.validate(validationtracker, id, lootTable)));
    }

}