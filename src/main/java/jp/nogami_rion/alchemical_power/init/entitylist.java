package jp.nogami_rion.alchemical_power.init;

import jp.nogami_rion.alchemical_power.Alchemical_power;
import jp.nogami_rion.alchemical_power.entity.AlchetreeMysteriousScarecrowEntity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class entitylist {
    public static final DeferredRegister<EntityType<?>> ENTITIES =
            DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, Alchemical_power.MODID);

    public static final RegistryObject<EntityType<AlchetreeMysteriousScarecrowEntity>> ALCHETREE_MYSTERIOUS_SCARECROW =
            ENTITIES.register("alchetree_mysterious_scarecrow", () ->
                    EntityType.Builder.of(AlchetreeMysteriousScarecrowEntity::new, MobCategory.MISC)
                            .sized(0.6f, 1.8f) // サイズを設定
                            .build(new ResourceLocation(Alchemical_power.MODID, "alchetree_mysterious_scarecrow").toString()));

    //エンティティリスト登録用
    public static void register(IEventBus eventBus){ENTITIES.register(eventBus);}

}
