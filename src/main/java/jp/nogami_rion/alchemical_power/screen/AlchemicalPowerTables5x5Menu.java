package jp.nogami_rion.alchemical_power.screen;

import jp.nogami_rion.alchemical_power.block.entity.AbstractAlchemicalTableBlockEntity;
import jp.nogami_rion.alchemical_power.block.entity.AlchemicalTablesTier1BlockEntity;
import jp.nogami_rion.alchemical_power.block.entity.AlchemicalTablesTier2BlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.level.block.entity.BlockEntity;

public class AlchemicalPowerTables5x5Menu extends AbstractAlchemicalPowerTablesMenu {

    private static final AlchemicalPowerTablesLayout LAYOUT =
            new AlchemicalPowerTablesLayout(10,20,109,56,163,56,20,114);

    // =========================
    // サーバー側コンストラクタ
    // =========================
    public AlchemicalPowerTables5x5Menu(
            int id,
            Inventory playerInv,
            AbstractAlchemicalTableBlockEntity blockEntity
    ) {
        super(
                ModMenuTypes.ALCHEMICAL_POWER_TABLES_5X5_MENU.get(),
                id,
                playerInv,
                blockEntity,
                LAYOUT
        );
    }

    // =========================
    // クライアント側コンストラクタ
    // =========================
    public AlchemicalPowerTables5x5Menu(
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
    private static AlchemicalTablesTier2BlockEntity getBlockEntity(
            Inventory playerInv,
            FriendlyByteBuf buf
    ) {
        BlockPos pos = buf.readBlockPos();

        BlockEntity be = playerInv.player.level().getBlockEntity(pos);

        if (!(be instanceof AlchemicalTablesTier2BlockEntity tier2)) {
            throw new IllegalStateException(
                    "BlockEntity at " + pos + " is not Tier2 Alchemy Table"
            );
        }

        return tier2;
    }
}
