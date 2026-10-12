package duperez.moresigns.common.block;

import duperez.moresigns.common.inventory.SignWorkbenchMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nullable;
import java.util.EnumMap;
import java.util.Map;

/** The table where frames, boards and finished custom signs are made. */
public class SignWorkbenchBlock extends HorizontalDirectionalBlock {
    private static final Component TITLE = new TranslatableComponent("container.moresigns.sign_workbench");

    /** The table top plus the board that rises behind it. Written for a block facing north, so its back is on the south side. */
    private static final VoxelShape NORTH_SHAPE = Shapes.or(
            Block.box(0, 0, 0, 16, 16, 16),
            Block.box(0, 16, 12, 16, 23, 14));
    private static final Map<Direction, VoxelShape> SHAPES = new EnumMap<>(Direction.class);

    static {
        for (Direction direction : Direction.Plane.HORIZONTAL) {
            SHAPES.put(direction, rotate(NORTH_SHAPE, direction));
        }
    }

    public SignWorkbenchBlock(Properties properties) {
        super(properties);
        registerDefaultState(stateDefinition.any().setValue(FACING, Direction.NORTH));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPES.get(state.getValue(FACING));
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        if (!level.isClientSide) {
            player.openMenu(new SimpleMenuProvider(
                    (id, inventory, user) -> new SignWorkbenchMenu(id, inventory, ContainerLevelAccess.create(level, pos)), TITLE));
        }
        return InteractionResult.sidedSuccess(level.isClientSide);
    }

    @Override
    public BlockState rotate(BlockState state, Rotation rotation) {
        return state.setValue(FACING, rotation.rotate(state.getValue(FACING)));
    }

    @Override
    public BlockState mirror(BlockState state, Mirror mirror) {
        return state.rotate(mirror.getRotation(state.getValue(FACING)));
    }

    /** Turns a shape written for a block facing north so it matches {@code facing}. */
    private static VoxelShape rotate(VoxelShape shape, Direction facing) {
        int quarterTurns = (facing.get2DDataValue() - Direction.NORTH.get2DDataValue() + 4) % 4;
        VoxelShape[] result = {Shapes.empty()};
        shape.forAllBoxes((x0, y0, z0, x1, y1, z1) -> {
            double ax = x0, az = z0, bx = x1, bz = z1;
            for (int i = 0; i < quarterTurns; i++) { // a quarter turn clockwise around the block's center: (x, z) -> (1 - z, x)
                double nax = 1 - bz, naz = ax, nbx = 1 - az, nbz = bx;
                ax = nax; az = naz; bx = nbx; bz = nbz;
            }
            result[0] = Shapes.or(result[0], Shapes.box(ax, y0, az, bx, y1, bz));
        });
        return result[0];
    }
}
