package duperez.moresigns.gametest;

import duperez.moresigns.MoreSigns;
import duperez.moresigns.common.block.entity.CanvasSignBlockEntity;
import duperez.moresigns.common.registry.ModBlocks;
import duperez.moresigns.common.registry.ModItems;
import duperez.moresigns.common.variant.CanvasVariant;
import duperez.moresigns.common.variant.CanvasVariants;
import net.minecraft.core.BlockPos;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.SignBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.gametest.GameTestHolder;
import net.neoforged.neoforge.gametest.PrefixGameTestTemplate;

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
}
