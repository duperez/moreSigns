package duperez.moresigns.gametest;

import duperez.moresigns.MoreSigns;
import duperez.moresigns.common.block.entity.CanvasSignBlockEntity;
import duperez.moresigns.common.block.entity.CustomSignBlockEntity;
import duperez.moresigns.common.sign.Part;
import duperez.moresigns.common.sign.SignNbt;
import duperez.moresigns.common.sign.SignParts;
import duperez.moresigns.common.registry.ModBlockEntityTypes;
import duperez.moresigns.common.registry.ModBlocks;
import duperez.moresigns.common.registry.ModItems;
import duperez.moresigns.common.variant.CanvasVariant;
import duperez.moresigns.common.variant.CanvasVariants;
import net.minecraft.core.BlockPos;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.SignBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.gametest.GameTestHolder;
import net.minecraftforge.gametest.PrefixGameTestTemplate;

import java.util.List;

/** Runs inside a real server through {@code ./gradlew runGameTestServer}. */
@GameTestHolder(MoreSigns.MODID)
@PrefixGameTestTemplate(false)
public class CanvasSignGameTests {
    private static final BlockPos POS = new BlockPos(1, 1, 1);

    @GameTest(template = "empty")
    public static void everySignHasACanvasBlockEntity(GameTestHelper helper) {
        for (CanvasVariant variant : CanvasVariants.ALL) {
            for (Block block : List.of(ModBlocks.standingSign(variant).get(), ModBlocks.wallSign(variant).get())) {
                helper.setBlock(POS, block);
                BlockEntity entity = helper.getBlockEntity(POS);
                helper.assertTrue(entity instanceof CanvasSignBlockEntity, block + " has block entity " + entity);
            }
        }
        helper.succeed();
    }

    @GameTest(template = "empty")
    public static void signsDropTheirOwnItem(GameTestHelper helper) {
        for (CanvasVariant variant : CanvasVariants.ALL) {
            for (Block block : List.of(ModBlocks.standingSign(variant).get(), ModBlocks.wallSign(variant).get())) {
                helper.setBlock(POS, block);
                BlockState state = helper.getBlockState(POS);
                List<ItemStack> drops = Block.getDrops(state, helper.getLevel(), helper.absolutePos(POS), helper.getBlockEntity(POS));
                ItemStack expected = new ItemStack(ModItems.sign(variant).get());
                helper.assertTrue(drops.size() == 1 && ItemStack.isSameItem(drops.get(0), expected),
                        block + " dropped " + drops + " instead of " + expected);
            }
        }
        helper.succeed();
    }

    @GameTest(template = "empty")
    public static void darkSignsStartWithWhiteText(GameTestHelper helper) {
        for (CanvasVariant variant : CanvasVariants.ALL) {
            Block block = ModBlocks.standingSign(variant).get();
            helper.setBlock(POS, block);
            block.setPlacedBy(helper.getLevel(), helper.absolutePos(POS), helper.getBlockState(POS), null, ItemStack.EMPTY);
            SignBlockEntity sign = (SignBlockEntity) helper.getBlockEntity(POS);
            DyeColor expected = variant.darkBackground() ? DyeColor.WHITE : DyeColor.BLACK;
            helper.assertTrue(sign.getFrontText().getColor() == expected,
                    variant.standingName() + " text is " + sign.getFrontText().getColor() + ", expected " + expected);
        }
        helper.succeed();
    }

    @GameTest(template = "empty")
    public static void customSignKeepsItsMaterialsAcrossSaveAndLoad(GameTestHelper helper) {
        helper.setBlock(POS, ModBlocks.CUSTOM_SIGN.get());
        CustomSignBlockEntity sign = (CustomSignBlockEntity) helper.getBlockEntity(POS);
        SignParts expected = new SignParts(
                new Part(new ResourceLocation("minecraft", "spruce_planks"), java.util.Optional.of(DyeColor.RED)),
                Part.of(new ResourceLocation("minecraft", "birch_planks")));
        sign.setParts(expected);

        CompoundTag saved = sign.saveWithFullMetadata();
        BlockEntity loaded = BlockEntity.loadStatic(helper.absolutePos(POS), helper.getBlockState(POS), saved);

        helper.assertTrue(loaded instanceof CustomSignBlockEntity, "loaded " + loaded);
        CustomSignBlockEntity copy = (CustomSignBlockEntity) loaded;
        helper.assertTrue(copy.parts().equals(expected), "parts came back as " + copy.parts());
        helper.succeed();
    }

    @GameTest(template = "empty")
    public static void customSignPicksReadableTextColorFromItsBoard(GameTestHelper helper) {
        helper.setBlock(POS, ModBlocks.CUSTOM_SIGN.get());
        ((CustomSignBlockEntity) helper.getBlockEntity(POS)).setParts(new SignParts(
                Part.of(new ResourceLocation("minecraft", "oak_planks")), Part.of(new ResourceLocation("minecraft", "black_wool"))));
        BlockPos light = new BlockPos(2, 1, 1);
        helper.setBlock(light, ModBlocks.CUSTOM_SIGN.get());
        ((CustomSignBlockEntity) helper.getBlockEntity(light)).setParts(new SignParts(
                Part.of(new ResourceLocation("minecraft", "oak_planks")), Part.of(new ResourceLocation("minecraft", "birch_planks"))));

        helper.runAfterDelay(3, () -> {
            SignBlockEntity dark = (SignBlockEntity) helper.getBlockEntity(POS);
            SignBlockEntity bright = (SignBlockEntity) helper.getBlockEntity(light);
            helper.assertTrue(dark.getFrontText().getColor() == DyeColor.WHITE && dark.getBackText().getColor() == DyeColor.WHITE,
                    "black wool board got " + dark.getFrontText().getColor() + " / " + dark.getBackText().getColor());
            helper.assertTrue(bright.getFrontText().getColor() == DyeColor.BLACK,
                    "birch board got " + bright.getFrontText().getColor());
            helper.succeed();
        });
    }

    @GameTest(template = "empty")
    public static void canvasSignsTickSoTheirEditLockIsReleased(GameTestHelper helper) {
        for (CanvasVariant variant : CanvasVariants.ALL) {
            for (Block block : List.of(ModBlocks.standingSign(variant).get(), ModBlocks.wallSign(variant).get())) {
                helper.assertTrue(block.defaultBlockState().getBlock() instanceof net.minecraft.world.level.block.EntityBlock entityBlock
                                && entityBlock.getTicker(helper.getLevel(), block.defaultBlockState(), ModBlockEntityTypes.CANVAS_SIGN.get()) != null,
                        block + " has no ticker for its block entity");
            }
        }
        helper.succeed();
    }
}
