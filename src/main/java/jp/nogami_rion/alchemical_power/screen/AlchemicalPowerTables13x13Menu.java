package jp.nogami_rion.alchemical_power.screen;

import jp.nogami_rion.alchemical_power.block.entity.AbstractAlchemicalTableBlockEntity;
import jp.nogami_rion.alchemical_power.block.entity.AlchemicalTablesTier3BlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.level.block.entity.BlockEntity;

public class AlchemicalPowerTables13x13Menu extends AbstractAlchemicalPowerTablesMenu {

    private static final AlchemicalPowerTablesLayout LAYOUT =
            new AlchemicalPowerTablesLayout(7,4,257,64,348,64,250,148);

    // =========================
    // サーバー側コンストラクタ
    // =========================
    public AlchemicalPowerTables13x13Menu(
            int id,
            Inventory playerInv,
            AbstractAlchemicalTableBlockEntity blockEntity
    ) {
        super(
                ModMenuTypes.ALCHEMICAL_POWER_TABLES_13X13_MENU.get(),
                id,
                playerInv,
                blockEntity,
                LAYOUT
        );
    }

    // =========================
    // クライアント側コンストラクタ
    // =========================
    public AlchemicalPowerTables13x13Menu(
            int id,
            Inventory playerInv,
            FriendlyByteBuf buf
    ) {
        this(
                id,
                playerInv,
                getBlockEntity(playerInv, buf)
        );
    }

    // =========================
    // BE取得ヘルパー
    // =========================
    private static AlchemicalTablesTier3BlockEntity getBlockEntity(
            Inventory playerInv,
            FriendlyByteBuf buf
    ) {
        BlockPos pos = buf.readBlockPos();

        BlockEntity be = playerInv.player.level().getBlockEntity(pos);

        if (!(be instanceof AlchemicalTablesTier3BlockEntity tier3)) {
            throw new IllegalStateException(
                    "BlockEntity at " + pos + " is not Tier3 Alchemy Table"
            );
        }

        return tier3;
    }
}
