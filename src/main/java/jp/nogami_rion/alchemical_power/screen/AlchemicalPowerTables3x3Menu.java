package jp.nogami_rion.alchemical_power.screen;

import jp.nogami_rion.alchemical_power.block.entity.AbstractAlchemicalTableBlockEntity;
import jp.nogami_rion.alchemical_power.block.entity.AlchemicalTablesTier1BlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.level.block.entity.BlockEntity;

public class AlchemicalPowerTables3x3Menu extends AbstractAlchemicalPowerTablesMenu {

    private static final AlchemicalPowerTablesLayout LAYOUT =
            new AlchemicalPowerTablesLayout(16,23,88,41,145,41,8,81);

    // =========================
    // サーバー側コンストラクタ
    // =========================
    public AlchemicalPowerTables3x3Menu(
            int id,
            Inventory playerInv,
            AbstractAlchemicalTableBlockEntity blockEntity
    ) {
        super(
                ModMenuTypes.ALCHEMICAL_POWER_TABLES_3X3_MENU.get(),
                id,
                playerInv,
                blockEntity,
                LAYOUT
        );
    }

    // =========================
    // クライアント側コンストラクタ
    // =========================
    public AlchemicalPowerTables3x3Menu(
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
    private static AlchemicalTablesTier1BlockEntity getBlockEntity(
            Inventory playerInv,
            FriendlyByteBuf buf
    ) {
        BlockPos pos = buf.readBlockPos();

        BlockEntity be = playerInv.player.level().getBlockEntity(pos);

        if (!(be instanceof AlchemicalTablesTier1BlockEntity tier1)) {
            throw new IllegalStateException(
                    "BlockEntity at " + pos + " is not Tier1 Alchemy Table"
            );
        }

        return tier1;
    }
}
