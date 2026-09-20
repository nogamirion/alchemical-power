package jp.nogami_rion.alchemical_power.init;

import jp.nogami_rion.alchemical_power.Alchemical_power;
import jp.nogami_rion.alchemical_power.entity.AlchetreeMysteriousScarecrowEntity;
import jp.nogami_rion.alchemical_power.entity.ThrownSwordEntity;
import jp.nogami_rion.alchemical_power.entity.SummonedArsenalWeapon;
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

    public static final RegistryObject<EntityType<ThrownSwordEntity>> THROWN_SWORD =
            ENTITIES.register("thrown_sword", () ->
                    EntityType.Builder.<ThrownSwordEntity>of(ThrownSwordEntity::new, MobCategory.MISC)
                            .sized(0.5F, 0.5F)
                            .clientTrackingRange(4)
                            .updateInterval(20)
                            .build(new ResourceLocation(Alchemical_power.MODID, "thrown_sword").toString()));

    public static final RegistryObject<EntityType<SummonedArsenalWeapon>> SUMMONED_ARSENAL_WEAPON =
            ENTITIES.register("summoned_arsenal_weapon", () ->
                    EntityType.Builder.<SummonedArsenalWeapon>of(SummonedArsenalWeapon::new, MobCategory.MISC)
                            .sized(0.25F, 0.25F).clientTrackingRange(8).updateInterval(1).noSave().noSummon()
                            .build(new ResourceLocation(Alchemical_power.MODID, "summoned_arsenal_weapon").toString()));

    //エンティティリスト登録用
    public static void register(IEventBus eventBus){ENTITIES.register(eventBus);}

}
