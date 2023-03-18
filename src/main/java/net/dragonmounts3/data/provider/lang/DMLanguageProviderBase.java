package net.dragonmounts3.data.provider.lang;

import net.dragonmounts3.DragonMounts;
import net.minecraft.data.DataGenerator;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.common.data.LanguageProvider;

public abstract class DMLanguageProviderBase extends LanguageProvider {

    public DMLanguageProviderBase(DataGenerator gen, String locale) {
        super(gen, DragonMounts.MOD_ID, locale);
    }

    public void addCreativeTab(ItemGroup tab, String value) {
        this.add("itemGroup." + tab.langId, value);
    }

    @Override
    public void add(String key, String value) {
        try {
            super.add(key, value);
        } catch (IllegalStateException ignored) {}
    }

}