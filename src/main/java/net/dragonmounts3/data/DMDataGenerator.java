package net.dragonmounts3.data;

import net.dragonmounts3.DragonMounts;
import net.dragonmounts3.data.provider.*;
import net.dragonmounts3.data.provider.lang.DMLanguageProviderENUS;
import net.dragonmounts3.data.provider.lang.DMLanguageProviderZHCN;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent;

@Mod.EventBusSubscriber(modid = DragonMounts.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DMDataGenerator {

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        ExistingFileHelper helper = event.getExistingFileHelper();
        generator.addProvider(new DMBlockStateProvider(generator, helper));
        generator.addProvider(new DMItemModelProvider(generator, helper));
        DMBlockTagsProvider blockTagsProvider = new DMBlockTagsProvider(generator, helper);
        generator.addProvider(blockTagsProvider);
        generator.addProvider(new DMItemTagsProvider(generator, blockTagsProvider, helper));
        generator.addProvider(new DMEntityTypeTagsProvider(generator, helper));
        generator.addProvider(new DMLanguageProviderENUS(generator));
        generator.addProvider(new DMLanguageProviderZHCN(generator));
        generator.addProvider(new DMLootTableProvider(generator));
        generator.addProvider(new DMRecipeProvider(generator));
        generator.addProvider(new DMSoundDefinitionsProvider(generator, helper));
    }

}