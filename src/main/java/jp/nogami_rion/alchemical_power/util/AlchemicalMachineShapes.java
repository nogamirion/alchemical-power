package jp.nogami_rion.alchemical_power.util;

import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.EnumMap;
import java.util.Map;

/** Major model envelopes; vessels are solid for collision, including their glass. */
public final class AlchemicalMachineShapes {
    private AlchemicalMachineShapes() {}

    // Inner glass clearance, shared by the renderer and fill-height calculations.
    public static final AABB EXTRACTOR_WATER = units(2.1, 3.1, 4.6, 6.9, 11.35, 10.9);
    public static final AABB EXTRACTOR_OUTPUT = units(9.6, 3.1, 5.1, 13.9, 8.05, 10.4);
    public static final AABB REACTOR_FLUID = units(3.1, 3.1, 3.1, 12.9, 12.75, 12.9);

    private static final Map<Direction, VoxelShape> EXTRACTOR = rotations(Shapes.or(
            Block.box(0.4, 0, 0.4, 15.6, 2.55, 15.6),
            Block.box(2, 0.9, 0.1, 6.5, 2, 0.5),
            Block.box(10, 0.9, 0.3, 13.5, 1.8, 0.5),
            Block.box(3.2, 0.9, 15.5, 5.8, 2, 16),
            Block.box(1.4, 2.5, 3.9, 7.6, 12, 11.6),
            Block.box(3, 12, 5.5, 6, 12.7, 10),
            Block.box(2.3, 12.7, 4.8, 6.7, 13.4, 10.7),
            Block.box(0.9, 13.4, 3.4, 8.1, 15, 12.1),
            Block.box(2, 15, 4.5, 7, 15.5, 11),
            Block.box(4, 14, 3.2, 5, 14.8, 3.5),
            Block.box(8.9, 2.5, 4.4, 14.6, 8.7, 11.1),
            Block.box(7.5, 10, 7, 12.5, 11, 8),
            Block.box(9.3, 9.8, 6.8, 10, 11.2, 8.2),
            Block.box(11.5, 8.7, 7, 12.5, 10, 8),
            Block.box(11.2, 8.7, 6.7, 12.8, 9.3, 8.3)));

    private static final Map<Direction, VoxelShape> REACTOR = rotations(Shapes.or(
            Block.box(0.4, 0, 0.4, 15.6, 2.5, 15.6),
            Block.box(2.4, 2.5, 2.4, 13.6, 13.4, 13.6),
            Block.box(0.4, 2, 0.4, 2.6, 13.5, 2.6),
            Block.box(13.4, 2, 0.4, 15.6, 13.5, 2.6),
            Block.box(0.4, 2, 13.4, 2.6, 13.5, 15.6),
            Block.box(13.4, 2, 13.4, 15.6, 13.5, 15.6),
            frame(3.1, 3.65), frame(11.7, 12.25),
            Block.box(0.5, 13.4, 0.5, 15.5, 14.9, 15.5),
            Block.box(5.5, 14.9, 5.5, 10.5, 15.3, 10.5),
            Block.box(5.6, 0.4, 0, 10.4, 2.7, 0.5),
            Block.box(13.5, 6.5, 6, 16, 9.5, 10)));

    private static VoxelShape frame(double bottom, double top) {
        return Shapes.or(Block.box(0.6,bottom,0.6,15.4,top,1.1),
                Block.box(0.6,bottom,14.9,15.4,top,15.4),
                Block.box(0.6,bottom,1.1,1.1,top,14.9),
                Block.box(14.9,bottom,1.1,15.4,top,14.9));
    }

    private static AABB units(double x0, double y0, double z0, double x1, double y1, double z1) {
        return new AABB(x0/16, y0/16, z0/16, x1/16, y1/16, z1/16);
    }

    private static Map<Direction, VoxelShape> rotations(VoxelShape north) {
        Map<Direction, VoxelShape> result = new EnumMap<>(Direction.class);
        VoxelShape current = north.optimize();
        for (Direction direction : new Direction[]{Direction.NORTH, Direction.EAST, Direction.SOUTH, Direction.WEST}) {
            result.put(direction, current);
            VoxelShape[] rotated = {Shapes.empty()};
            current.forAllBoxes((x0,y0,z0,x1,y1,z1) -> rotated[0] = Shapes.or(rotated[0],
                    Shapes.box(1-z1,y0,x0,1-z0,y1,x1)));
            current = rotated[0].optimize();
        }
        return result;
    }

    public static VoxelShape extractor(Direction direction) { return EXTRACTOR.get(direction); }
    public static VoxelShape reactor(Direction direction) { return REACTOR.get(direction); }

    public static double fluidTop(AABB tank, int amount, int capacity) {
        double fraction = capacity <= 0 ? 0 : Math.max(0, Math.min(1, amount / (double) capacity));
        return tank.minY + tank.getYsize() * fraction;
    }
}
