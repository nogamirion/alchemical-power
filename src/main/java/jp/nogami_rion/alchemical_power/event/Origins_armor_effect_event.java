package jp.nogami_rion.alchemical_power.event;

import jp.nogami_rion.alchemical_power.item.*;
import jp.nogami_rion.alchemical_power.util.DeadEndRainbowUtils;
import jp.nogami_rion.alchemical_power.util.ModDamageTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;
import org.joml.Vector3f;

import java.util.List;

@Mod.EventBusSubscriber(modid = "alchemical_power")
public class Origins_armor_effect_event {

    @SubscribeEvent
    public static void onPlayerHurt(LivingHurtEvent event) {
        if (!(event.getEntity() instanceof Player player)) return;
        ItemStack chest = player.getInventory().getArmor(2); // 胸装備
        ItemStack weapon = player.getMainHandItem(); // 武器装備

        // Crimson Plate of Justiceの効果
        if (chest.getItem() instanceof Crimson_Plate_of_Justice) {
            LivingEntity attacker = (LivingEntity) event.getSource().getEntity();
            if (attacker == null || !(player.level() instanceof ServerLevel level)) return;

            float incomingDamage = event.getAmount(); // 軽減前のダメージ
            DamageSource thornsSource = level.damageSources().thorns(player);

            // ダメージに応じた反射
            float reflected = incomingDamage * 1.0F;
            attacker.hurt(thornsSource, reflected);

            // ノックバック
            double dx = attacker.getX() - player.getX();
            double dz = attacker.getZ() - player.getZ();
            attacker.knockback(0.6F, dx, dz);

            // 炎上（3秒）
            attacker.setSecondsOnFire(3);

            // エフェクト演出も必要なら追加（後述）
            ((ServerLevel) player.level()).sendParticles(ParticleTypes.FLAME,
                    player.getX(), player.getY() + 1, player.getZ(),
                    16, 0.5, 0.5, 0.5, 0.01);
        }

        // Judicial Carapaceの効果
        if (chest.getItem() instanceof JudicialCarapace) {
            LivingEntity attacker = (LivingEntity) event.getSource().getEntity();
            if (attacker == null || !(player.level() instanceof ServerLevel level)) return;

            float incomingDamage = event.getAmount(); // 軽減前のダメージ
            DamageSource reflectSource = ModDamageTypes.singularityTrue((ServerLevel)player.level(),player);// 反射用のダメージソース

            // ダメージに応じた反射
            float reflected = incomingDamage * 2.0F;
            attacker.hurt(reflectSource, reflected);
//            LOGGER.info("反射ダメージを {} に対して {} 与えました", attacker.getName().getString(), incomingDamage);

            // ノックバック
            double dx = attacker.getX() - player.getX();
            double dz = attacker.getZ() - player.getZ();
            attacker.knockback(1.0F, dx, dz);

            // 炎上（3秒）
            attacker.setSecondsOnFire(3);

            // エフェクト演出も必要なら追加（後述）
            ((ServerLevel) player.level()).sendParticles(ParticleTypes.FLAME,
                    player.getX(), player.getY() + 1, player.getZ(),
                    16, 0.5, 0.5, 0.5, 0.01);
        }

        if (weapon.getItem() instanceof BladeOfValor) {
            // 勇気の祝福：自身にバフ、
            player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 100, 2));
            player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 100, 0));
        }
    }
    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase != TickEvent.Phase.END) return;

        Player player = event.player;
        ItemStack leggings = player.getInventory().getArmor(1);
        // Leggings of Temperanceの効果（移動速度低下するブロックの速度低下緩和）
        if ((leggings.getItem() instanceof Leggings_of_Sage) || (leggings.getItem() instanceof JudicialCarapace)){
            // ジャンプ中や空中では補正しない
            if (!player.onGround()) return;

            BlockPos below = player.blockPosition().below();
            Block blockBelow = player.level().getBlockState(below).getBlock();

            if (isSlowingBlock(blockBelow)) {
                Vec3 current = player.getDeltaMovement();

                // 効果が明確にわかる補正（強め）
                player.setDeltaMovement(current.x * 1.5, current.y, current.z * 1.5);
            }
        }
    }

    @SubscribeEvent
    public static void onLivingHurt(LivingHurtEvent event) {
        if (!(event.getEntity() instanceof Player player)) return;
        if (player.level().isClientSide) return;

        String id = event.getSource().getMsgId();

        // 各部位ごとの処理（装備取得）
        ItemStack boots = player.getInventory().getArmor(0);
        ItemStack legs = player.getInventory().getArmor(1);
        ItemStack chest = player.getInventory().getArmor(2);
        ItemStack helmet = player.getInventory().getArmor(3);

        if (helmet.getItem() instanceof CrownOfTheSeraph &&
                (id.equals("fall") || id.equals("drown") || id.equals("inWall") || id.equals("outOfWorld"))) {
            event.setCanceled(true);
            return;
        }

        if (chest.getItem() instanceof JudicialCarapace &&
                (id.equals("player") || id.equals("mob") || id.equals("trident") || id.contains("explosion"))) {
            event.setCanceled(true);
            return;
        }

        if (legs.getItem() instanceof GaitOfInsight &&
                (id.contains("magic") || id.equals("projectile") || id.equals("wither"))) {
            event.setCanceled(true);
            return;
        }

        if (boots.getItem() instanceof BootsOfAetherlight &&
                (id.equals("hotFloor") || id.equals("cactus") || id.equals("lava") ||
                        id.equals("onFire") || id.equals("inFire") || id.equals("flyIntoWall"))) {
            event.setCanceled(true);
            return;
        }

        // 一式装備なら全て無効化（保険）
        if (hasFullParadoxGear(player)) {
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public static void onLivingTick(LivingEvent.LivingTickEvent event) {
        LivingEntity entity = event.getEntity();
        Level level = entity.level();

        if (!level.isClientSide && entity instanceof Mob || entity instanceof Player) {
            int mark = DeadEndRainbowUtils.getAttackMark(entity);
            if (mark <= 0) return;

            if (mark > 0 && level instanceof ServerLevel serverLevel) {
                List<Vec3> colors = List.of(
                        new Vec3(1.0, 0.0, 0.0), // 赤
                        new Vec3(1.0, 0.5, 0.0), // 橙
                        new Vec3(1.0, 1.0, 0.0), // 黄
                        new Vec3(0.0, 1.0, 0.0), // 緑
                        new Vec3(0.0, 1.0, 1.0), // 水
                        new Vec3(0.0, 0.0, 1.0), // 青
                        new Vec3(0.5, 0.0, 1.0)  // 紫
                );

                for (int i = 0; i < mark && i < colors.size(); i++) {
                    Vec3 color = colors.get(i);
                    serverLevel.sendParticles(
                            new DustParticleOptions(new Vector3f((float) color.x, (float) color.y, (float) color.z), 2.0f),
                            entity.getX(), entity.getY() + 1.0, entity.getZ(),
                            2, 0.1, 0.1, 0.1, 0.0
                    );
                }
            }
        }
    }


    private static boolean isSlowingBlock(Block block) {
        ResourceLocation id = ForgeRegistries.BLOCKS.getKey(block);
        if (id == null) return false;

        // バニラおよび代表的MOD素材（AllTheMods系やTerrablender等）
        return block == Blocks.SOUL_SAND
                || block == Blocks.SOUL_SOIL
                || block == Blocks.HONEY_BLOCK
                || block == Blocks.SLIME_BLOCK
                || block == Blocks.SWEET_BERRY_BUSH

                // 追加MODのブロックIDで判定（英名に"mud"や"quicksand"等が含まれる）
                || id.getPath().contains("mud")
                || id.getPath().contains("slime")
                || id.getPath().contains("tar")
                || id.getPath().contains("moss")
                || id.getPath().contains("quicksand")
                || id.getPath().contains("root")
                || id.getPath().contains("sticky")
                || id.getPath().contains("ash")
                || id.getPath().contains("viscous")

                // 追加MODの特定ブロックを明示的に許容（例：Alex's Caves）
                || id.getNamespace().equals("alexscaves") && id.getPath().contains("sludge");
    }

    public static boolean hasFullParadoxGear(Player player) {
        return player.getInventory().getArmor(0).getItem() instanceof BootsOfAetherlight &&
                player.getInventory().getArmor(1).getItem() instanceof GaitOfInsight &&
                player.getInventory().getArmor(2).getItem() instanceof JudicialCarapace &&
                player.getInventory().getArmor(3).getItem() instanceof CrownOfTheSeraph;
    }
}
