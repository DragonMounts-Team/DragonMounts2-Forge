package net.dragonmounts3.api;

import net.dragonmounts3.registry.DragonType;

public interface IDragonTypified {
    DragonType getDragonType();

    interface Mutable extends IDragonTypified {
        void setDragonType(DragonType type);
    }
}