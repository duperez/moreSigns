package duperez.moresigns.common.registry;

import duperez.moresigns.MoreSigns;
import duperez.moresigns.common.block.CustomSignBlock;
import duperez.moresigns.common.block.CustomWallSignBlock;
import duperez.moresigns.common.block.SignWorkbenchBlock;
import duperez.moresigns.common.block.StandingCanvasSignBlock;
import duperez.moresigns.common.block.WallCanvasSignBlock;
import duperez.moresigns.common.variant.CanvasVariant;
import duperez.moresigns.common.variant.CanvasVariants;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public class ModBlocks {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(MoreSigns.MODID);

    public static final DeferredBlock<CustomSignBlock> CUSTOM_SIGN = BLOCKS.register("custom_sign", CustomSignBlock::new);
    public static final DeferredBlock<CustomWallSignBlock> CUSTOM_WALL_SIGN = BLOCKS.register("custom_wall_sign",
            () -> new CustomWallSignBlock(CUSTOM_SIGN.get()));

    public static final DeferredBlock<SignWorkbenchBlock> SIGN_WORKBENCH = BLOCKS.register("sign_workbench",
            () -> new SignWorkbenchBlock(Block.Properties.ofFullCopy(Blocks.CRAFTING_TABLE).sound(SoundType.WOOD).noOcclusion()));

    private static final Map<CanvasVariant, DeferredBlock<StandingCanvasSignBlock>> STANDING_SIGNS = new LinkedHashMap<>();
    private static final Map<CanvasVariant, DeferredBlock<WallCanvasSignBlock>> WALL_SIGNS = new LinkedHashMap<>();

    static {
        for (CanvasVariant variant : CanvasVariants.ALL) {
            DeferredBlock<StandingCanvasSignBlock> standing = BLOCKS.register(variant.standingName(),
                    () -> new StandingCanvasSignBlock(variant, ModWoodTypes.get(variant)));
            DeferredBlock<WallCanvasSignBlock> wall = BLOCKS.register(variant.wallName(),
                    () -> new WallCanvasSignBlock(variant, ModWoodTypes.get(variant), standing.get()));
            STANDING_SIGNS.put(variant, standing);
            WALL_SIGNS.put(variant, wall);
        }
    }

    public static DeferredBlock<StandingCanvasSignBlock> standingSign(CanvasVariant variant) {
        return STANDING_SIGNS.get(variant);
    }

    public static DeferredBlock<WallCanvasSignBlock> wallSign(CanvasVariant variant) {
        return WALL_SIGNS.get(variant);
    }

    public static Collection<DeferredBlock<StandingCanvasSignBlock>> standingSigns() {
        return STANDING_SIGNS.values();
    }

    public static Collection<DeferredBlock<WallCanvasSignBlock>> wallSigns() {
        return WALL_SIGNS.values();
    }

    /** Every block that holds a {@link duperez.moresigns.common.block.entity.CanvasSignBlockEntity}. */
    public static List<Block> allSigns() {
        return Stream.concat(STANDING_SIGNS.values().stream(), WALL_SIGNS.values().stream())
                .<Block>map(DeferredBlock::get)
                .toList();
    }

    private ModBlocks() {
    }
}
