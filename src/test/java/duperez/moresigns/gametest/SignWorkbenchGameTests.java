package duperez.moresigns.gametest;

import duperez.moresigns.MoreSigns;
import duperez.moresigns.common.block.entity.CustomSignBlockEntity;
import duperez.moresigns.common.inventory.SignWorkbenchLogic;
import duperez.moresigns.common.inventory.SignWorkbenchLogic.Mode;
import duperez.moresigns.common.inventory.SignWorkbenchMenu;
import duperez.moresigns.common.item.PartItem;
import duperez.moresigns.common.registry.ModBlocks;
import duperez.moresigns.common.registry.ModItems;
import duperez.moresigns.common.sign.Part;
import duperez.moresigns.common.sign.SignNbt;
import duperez.moresigns.common.sign.SignParts;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.gametest.GameTestHolder;
import net.minecraftforge.gametest.PrefixGameTestTemplate;

import java.util.List;
import java.util.Optional;

/** Drives the sign workbench menu the way a player's clicks would, in a real server. */
@GameTestHolder(MoreSigns.MODID)
@PrefixGameTestTemplate(false)
public class SignWorkbenchGameTests {
    private static final ResourceLocation SPRUCE = new ResourceLocation("minecraft", "spruce_planks");

    private static SignWorkbenchMenu open(Player player) {
        return new SignWorkbenchMenu(1, player.getInventory(), ContainerLevelAccess.NULL);
    }

    private static Part partOf(ItemStack stack) {
        return SignNbt.getPart(stack);
    }

    @GameTest(template = "empty")
    public static void makingAFrameUsesOneBlockAndKeepsTheMaterial(GameTestHelper helper) {
        Player player = helper.makeMockPlayer();
        SignWorkbenchMenu menu = open(player);
        menu.getSlot(0).set(new ItemStack(Items.SPRUCE_PLANKS, 5));
        menu.clickMenuButton(player, SignWorkbenchMenu.BUTTON_MAKE_FRAME);

        ItemStack result = menu.getSlot(SignWorkbenchMenu.RESULT_SLOT).getItem();
        helper.assertTrue(result.is(ModItems.SIGN_FRAME.get()) && partOf(result).equals(Part.of(SPRUCE)), "result was " + result);
        menu.getSlot(SignWorkbenchMenu.RESULT_SLOT).onTake(player, result);
        helper.assertTrue(menu.getSlot(0).getItem().getCount() == 4, "planks left: " + menu.getSlot(0).getItem().getCount());
        helper.succeed();
    }

    @GameTest(template = "empty")
    public static void aBoardCostsOneBlockAndMakesTwo(GameTestHelper helper) {
        Player player = helper.makeMockPlayer();
        SignWorkbenchMenu menu = open(player);
        menu.getSlot(0).set(new ItemStack(Items.SPRUCE_PLANKS, 1));
        menu.clickMenuButton(player, SignWorkbenchMenu.BUTTON_MAKE_BOARD);

        ItemStack result = menu.getSlot(SignWorkbenchMenu.RESULT_SLOT).getItem();
        helper.assertTrue(result.is(ModItems.SIGN_BOARD.get()) && result.getCount() == SignWorkbenchLogic.BOARD_YIELD, "result was " + result);
        menu.getSlot(SignWorkbenchMenu.RESULT_SLOT).onTake(player, result);
        helper.assertTrue(menu.getSlot(0).getItem().isEmpty(), "the plank should be used up");
        helper.succeed();
    }

    @GameTest(template = "empty")
    public static void woodMetalAndGlassAreMaterialsButOtherItemsAreNot(GameTestHelper helper) {
        Player player = helper.makeMockPlayer();
        SignWorkbenchMenu menu = open(player);
        for (var accepted : List.of(Items.OAK_PLANKS, Items.IRON_BLOCK, Items.GLASS, Items.STONE, Items.BLACK_WOOL)) {
            helper.assertTrue(menu.getSlot(0).mayPlace(new ItemStack(accepted)), accepted + " should be accepted");
        }
        for (var refused : List.of(Items.STICK, Items.DIRT, Items.IRON_INGOT, Items.CHEST)) {
            helper.assertTrue(!menu.getSlot(0).mayPlace(new ItemStack(refused)), refused + " should be refused");
        }
        helper.succeed();
    }

    @GameTest(template = "empty")
    public static void paintingKeepsTheDyeAndUsesThePart(GameTestHelper helper) {
        Player player = helper.makeMockPlayer();
        SignWorkbenchMenu menu = open(player);
        menu.clickMenuButton(player, Mode.PAINT.ordinal());
        menu.getSlot(0).set(SignWorkbenchLogic.makePart(PartItem.Kind.FRAME, Part.of(SPRUCE), 1));
        menu.getSlot(1).set(new ItemStack(Items.RED_DYE, 1));

        ItemStack result = menu.getSlot(SignWorkbenchMenu.RESULT_SLOT).getItem();
        helper.assertTrue(partOf(result).equals(new Part(SPRUCE, Optional.of(DyeColor.RED))), "result was " + partOf(result));
        menu.getSlot(SignWorkbenchMenu.RESULT_SLOT).onTake(player, result);
        helper.assertTrue(menu.getSlot(0).getItem().isEmpty(), "the unpainted frame should be used");
        helper.assertTrue(menu.getSlot(1).getItem().is(Items.RED_DYE) && menu.getSlot(1).getItem().getCount() == 1, "the dye must be kept");
        helper.succeed();
    }

    @GameTest(template = "empty")
    public static void washingTakesThePaintOff(GameTestHelper helper) {
        Player player = helper.makeMockPlayer();
        SignWorkbenchMenu menu = open(player);
        menu.clickMenuButton(player, Mode.PAINT.ordinal());
        menu.getSlot(0).set(SignWorkbenchLogic.makePart(PartItem.Kind.BOARD, new Part(SPRUCE, Optional.of(DyeColor.BLUE)), 1));
        menu.clickMenuButton(player, SignWorkbenchMenu.BUTTON_TOGGLE_WASH);

        ItemStack result = menu.getSlot(SignWorkbenchMenu.RESULT_SLOT).getItem();
        helper.assertTrue(partOf(result).equals(Part.of(SPRUCE)), "result was " + partOf(result));
        helper.succeed();
    }

    @GameTest(template = "empty")
    public static void assemblingJoinsAFrameAndABoard(GameTestHelper helper) {
        Player player = helper.makeMockPlayer();
        SignWorkbenchMenu menu = open(player);
        menu.clickMenuButton(player, Mode.ASSEMBLE.ordinal());
        Part birch = Part.of(new ResourceLocation("minecraft", "birch_planks"));
        menu.getSlot(0).set(SignWorkbenchLogic.makePart(PartItem.Kind.FRAME, Part.of(SPRUCE), 2));
        menu.getSlot(1).set(SignWorkbenchLogic.makePart(PartItem.Kind.BOARD, birch, 2));

        ItemStack result = menu.getSlot(SignWorkbenchMenu.RESULT_SLOT).getItem();
        SignParts parts = SignNbt.getSignParts(result);
        helper.assertTrue(result.is(ModItems.CUSTOM_SIGN.get()) && parts != null && parts.frame().equals(Part.of(SPRUCE)) && parts.board().equals(birch),
                "result was " + result);
        menu.getSlot(SignWorkbenchMenu.RESULT_SLOT).onTake(player, result);
        helper.assertTrue(menu.getSlot(0).getItem().getCount() == 1 && menu.getSlot(1).getItem().getCount() == 1, "one of each should be used");
        helper.succeed();
    }

    @GameTest(template = "empty")
    public static void aFrameCannotGoInTheBoardSlot(GameTestHelper helper) {
        Player player = helper.makeMockPlayer();
        SignWorkbenchMenu menu = open(player);
        menu.clickMenuButton(player, Mode.ASSEMBLE.ordinal());
        ItemStack frame = SignWorkbenchLogic.makePart(PartItem.Kind.FRAME, Part.DEFAULT, 1);
        helper.assertTrue(menu.getSlot(0).mayPlace(frame) && !menu.getSlot(1).mayPlace(frame), "frames go in the first slot only");
        helper.succeed();
    }

    @GameTest(template = "empty")
    public static void switchingModeGivesTheItemsBack(GameTestHelper helper) {
        Player player = helper.makeMockPlayer();
        SignWorkbenchMenu menu = open(player);
        menu.getSlot(0).set(new ItemStack(Items.OAK_PLANKS, 3));
        menu.clickMenuButton(player, Mode.PAINT.ordinal());

        helper.assertTrue(menu.getSlot(0).getItem().isEmpty(), "the slot should have been emptied");
        helper.assertTrue(player.getInventory().countItem(Items.OAK_PLANKS) == 3, "the planks should be back in the inventory");
        helper.succeed();
    }

    /** Places a sign the way a player does, clicking a face of a block with the item in hand. */
    private static BlockPos place(GameTestHelper helper, Player player, ItemStack item, BlockPos support, Direction face) {
        helper.setBlock(support, Blocks.STONE);
        player.setItemInHand(InteractionHand.MAIN_HAND, item);
        BlockPos clicked = helper.absolutePos(support);
        item.useOn(new UseOnContext(player, InteractionHand.MAIN_HAND,
                new BlockHitResult(Vec3.atCenterOf(clicked), face, clicked, false)));
        return support.relative(face);
    }

    @GameTest(template = "empty")
    public static void aSignPlacedByAPlayerKeepsItsPartsAndDropsThemBack(GameTestHelper helper) {
        Player player = helper.makeMockPlayer();
        helper.assertTrue(!player.canUseGameMasterBlocks(), "the point of this test is a player who is not an operator");
        SignParts parts = new SignParts(new Part(SPRUCE, Optional.of(DyeColor.GREEN)), Part.of(new ResourceLocation("minecraft", "birch_planks")));
        ItemStack item = signOf(parts.frame(), parts.board(), 1);

        BlockPos standing = place(helper, player, item.copy(), new BlockPos(1, 1, 1), Direction.UP);
        helper.assertTrue(helper.getBlockEntity(standing) instanceof CustomSignBlockEntity sign && sign.parts().equals(parts),
                "standing sign parts were " + helper.getBlockEntity(standing));
        List<ItemStack> drops = Block.getDrops(helper.getBlockState(standing), helper.getLevel(), helper.absolutePos(standing), helper.getBlockEntity(standing));
        helper.assertTrue(drops.size() == 1 && parts.equals(SignNbt.getSignParts(drops.get(0))), "dropped " + drops);

        BlockPos wall = place(helper, player, item.copy(), new BlockPos(0, 1, 0), Direction.EAST);
        helper.assertTrue(helper.getBlockState(wall).is(ModBlocks.CUSTOM_WALL_SIGN.get()), "expected a wall sign, found " + helper.getBlockState(wall));
        helper.assertTrue(helper.getBlockEntity(wall) instanceof CustomSignBlockEntity sign && sign.parts().equals(parts),
                "wall sign parts were " + helper.getBlockEntity(wall));
        List<ItemStack> wallDrops = Block.getDrops(helper.getBlockState(wall), helper.getLevel(), helper.absolutePos(wall), helper.getBlockEntity(wall));
        helper.assertTrue(wallDrops.size() == 1 && parts.equals(SignNbt.getSignParts(wallDrops.get(0))), "wall sign dropped " + wallDrops);
        helper.succeed();
    }

    private static ItemStack signOf(Part frame, Part board, int count) {
        ItemStack sign = new ItemStack(ModItems.CUSTOM_SIGN.get(), count);
        SignNbt.setSignParts(sign, new SignParts(frame, board));
        return sign;
    }

    @GameTest(template = "empty")
    public static void takingASignApartGivesBothPartsWithTheirColors(GameTestHelper helper) {
        Player player = helper.makeMockPlayer();
        SignWorkbenchMenu menu = open(player);
        Part frame = new Part(SPRUCE, Optional.of(DyeColor.RED));
        Part board = Part.of(new ResourceLocation("minecraft", "glass"));
        menu.clickMenuButton(player, Mode.DISASSEMBLE.ordinal());
        menu.getSlot(0).set(signOf(frame, board, 1));

        ItemStack frameResult = menu.getSlot(SignWorkbenchMenu.FRAME_RESULT_SLOT).getItem();
        ItemStack boardResult = menu.getSlot(SignWorkbenchMenu.BOARD_RESULT_SLOT).getItem();
        helper.assertTrue(frameResult.is(ModItems.SIGN_FRAME.get()) && partOf(frameResult).equals(frame), "frame was " + frameResult);
        helper.assertTrue(boardResult.is(ModItems.SIGN_BOARD.get()) && partOf(boardResult).equals(board), "board was " + boardResult);
        helper.assertTrue(menu.getSlot(SignWorkbenchMenu.RESULT_SLOT).getItem().isEmpty(), "the ordinary result stays empty");

        menu.getSlot(SignWorkbenchMenu.FRAME_RESULT_SLOT).onTake(player, frameResult);
        helper.assertTrue(menu.getSlot(0).getItem().isEmpty(), "the sign should be used up");
        helper.assertTrue(player.getInventory().countItem(ModItems.SIGN_BOARD.get()) == 1, "taking the frame must hand over the board too");
        helper.succeed();
    }

    @GameTest(template = "empty")
    public static void shiftClickingATakenApartSignMovesEveryPart(GameTestHelper helper) {
        Player player = helper.makeMockPlayer();
        SignWorkbenchMenu menu = open(player);
        menu.clickMenuButton(player, Mode.DISASSEMBLE.ordinal());
        menu.getSlot(0).set(signOf(Part.of(SPRUCE), Part.DEFAULT, 3));
        menu.quickMoveStack(player, SignWorkbenchMenu.BOARD_RESULT_SLOT);

        helper.assertTrue(menu.getSlot(0).getItem().isEmpty(), "all three signs should have been taken apart");
        helper.assertTrue(player.getInventory().countItem(ModItems.SIGN_FRAME.get()) == 3, "frames: " + player.getInventory().countItem(ModItems.SIGN_FRAME.get()));
        helper.assertTrue(player.getInventory().countItem(ModItems.SIGN_BOARD.get()) == 3, "boards: " + player.getInventory().countItem(ModItems.SIGN_BOARD.get()));
        helper.succeed();
    }

    @GameTest(template = "empty")
    public static void onlyCustomSignsGoInTheTakeApartSlot(GameTestHelper helper) {
        Player player = helper.makeMockPlayer();
        SignWorkbenchMenu menu = open(player);
        menu.clickMenuButton(player, Mode.DISASSEMBLE.ordinal());
        helper.assertTrue(menu.getSlot(0).mayPlace(new ItemStack(ModItems.CUSTOM_SIGN.get())), "a custom sign should be accepted");
        helper.assertTrue(!menu.getSlot(0).mayPlace(new ItemStack(Items.OAK_SIGN)), "a vanilla sign cannot be taken apart");
        helper.assertTrue(!menu.getSlot(0).mayPlace(new ItemStack(ModItems.SIGN_FRAME.get())), "a frame cannot be taken apart");
        helper.succeed();
    }

    @GameTest(template = "empty")
    public static void pickingASignGivesBackItsOwnParts(GameTestHelper helper) {
        BlockPos pos = new BlockPos(1, 1, 1);
        SignParts parts = new SignParts(Part.of(new ResourceLocation("minecraft", "iron_block")),
                new Part(new ResourceLocation("minecraft", "glass"), Optional.of(DyeColor.CYAN)));
        helper.setBlock(pos, ModBlocks.CUSTOM_SIGN.get());
        ((CustomSignBlockEntity) helper.getBlockEntity(pos)).setParts(parts);
        BlockPos wall = new BlockPos(2, 1, 1);
        helper.setBlock(wall, ModBlocks.CUSTOM_WALL_SIGN.get());
        ((CustomSignBlockEntity) helper.getBlockEntity(wall)).setParts(parts);

        for (BlockPos where : List.of(pos, wall)) {
            BlockState state = helper.getBlockState(where);
            ItemStack picked = state.getBlock().getCloneItemStack(helper.getLevel(), helper.absolutePos(where), state);
            helper.assertTrue(picked.is(ModItems.CUSTOM_SIGN.get()) && parts.equals(SignNbt.getSignParts(picked)),
                    "picked " + picked);
        }
        helper.succeed();
    }
}
