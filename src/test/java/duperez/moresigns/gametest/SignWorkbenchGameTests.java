package duperez.moresigns.gametest;

import duperez.moresigns.MoreSigns;
import duperez.moresigns.common.block.entity.CustomSignBlockEntity;
import duperez.moresigns.common.inventory.SignWorkbenchLogic;
import duperez.moresigns.common.inventory.SignWorkbenchLogic.Mode;
import duperez.moresigns.common.inventory.SignWorkbenchMenu;
import duperez.moresigns.common.item.PartItem;
import duperez.moresigns.common.registry.ModBlocks;
import duperez.moresigns.common.registry.ModDataComponents;
import duperez.moresigns.common.registry.ModItems;
import duperez.moresigns.common.sign.Part;
import duperez.moresigns.common.sign.SignParts;
import net.minecraft.core.BlockPos;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.gametest.GameTestHolder;
import net.neoforged.neoforge.gametest.PrefixGameTestTemplate;

import java.util.List;
import java.util.Optional;

/** Drives the sign workbench menu the way a player's clicks would, in a real server. */
@GameTestHolder(MoreSigns.MODID)
@PrefixGameTestTemplate(false)
public class SignWorkbenchGameTests {
    private static final ResourceLocation SPRUCE = ResourceLocation.withDefaultNamespace("spruce_planks");

    private static SignWorkbenchMenu open(Player player) {
        return new SignWorkbenchMenu(1, player.getInventory(), ContainerLevelAccess.NULL);
    }

    private static Part partOf(ItemStack stack) {
        return stack.getOrDefault(ModDataComponents.PART.get(), Part.DEFAULT);
    }

    @GameTest(template = "empty")
    public static void makingAFrameUsesOneBlockAndKeepsTheMaterial(GameTestHelper helper) {
        Player player = helper.makeMockPlayer(GameType.SURVIVAL);
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
        Player player = helper.makeMockPlayer(GameType.SURVIVAL);
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
        Player player = helper.makeMockPlayer(GameType.SURVIVAL);
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
        Player player = helper.makeMockPlayer(GameType.SURVIVAL);
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
        Player player = helper.makeMockPlayer(GameType.SURVIVAL);
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
        Player player = helper.makeMockPlayer(GameType.SURVIVAL);
        SignWorkbenchMenu menu = open(player);
        menu.clickMenuButton(player, Mode.ASSEMBLE.ordinal());
        Part birch = Part.of(ResourceLocation.withDefaultNamespace("birch_planks"));
        menu.getSlot(0).set(SignWorkbenchLogic.makePart(PartItem.Kind.FRAME, Part.of(SPRUCE), 2));
        menu.getSlot(1).set(SignWorkbenchLogic.makePart(PartItem.Kind.BOARD, birch, 2));

        ItemStack result = menu.getSlot(SignWorkbenchMenu.RESULT_SLOT).getItem();
        SignParts parts = result.get(ModDataComponents.SIGN_PARTS.get());
        helper.assertTrue(result.is(ModItems.CUSTOM_SIGN.get()) && parts != null && parts.frame().equals(Part.of(SPRUCE)) && parts.board().equals(birch),
                "result was " + result);
        menu.getSlot(SignWorkbenchMenu.RESULT_SLOT).onTake(player, result);
        helper.assertTrue(menu.getSlot(0).getItem().getCount() == 1 && menu.getSlot(1).getItem().getCount() == 1, "one of each should be used");
        helper.succeed();
    }

    @GameTest(template = "empty")
    public static void aFrameCannotGoInTheBoardSlot(GameTestHelper helper) {
        Player player = helper.makeMockPlayer(GameType.SURVIVAL);
        SignWorkbenchMenu menu = open(player);
        menu.clickMenuButton(player, Mode.ASSEMBLE.ordinal());
        ItemStack frame = SignWorkbenchLogic.makePart(PartItem.Kind.FRAME, Part.DEFAULT, 1);
        helper.assertTrue(menu.getSlot(0).mayPlace(frame) && !menu.getSlot(1).mayPlace(frame), "frames go in the first slot only");
        helper.succeed();
    }

    @GameTest(template = "empty")
    public static void switchingModeGivesTheItemsBack(GameTestHelper helper) {
        Player player = helper.makeMockPlayer(GameType.SURVIVAL);
        SignWorkbenchMenu menu = open(player);
        menu.getSlot(0).set(new ItemStack(Items.OAK_PLANKS, 3));
        menu.clickMenuButton(player, Mode.PAINT.ordinal());

        helper.assertTrue(menu.getSlot(0).getItem().isEmpty(), "the slot should have been emptied");
        helper.assertTrue(player.getInventory().countItem(Items.OAK_PLANKS) == 3, "the planks should be back in the inventory");
        helper.succeed();
    }

    @GameTest(template = "empty")
    public static void aPlacedSignKeepsItsPartsAndDropsThemBack(GameTestHelper helper) {
        BlockPos pos = new BlockPos(1, 1, 1);
        SignParts parts = new SignParts(new Part(SPRUCE, Optional.of(DyeColor.GREEN)), Part.of(ResourceLocation.withDefaultNamespace("birch_planks")));
        ItemStack item = new ItemStack(ModItems.CUSTOM_SIGN.get());
        item.set(ModDataComponents.SIGN_PARTS.get(), parts);

        helper.setBlock(pos, ModBlocks.CUSTOM_SIGN.get());
        CustomSignBlockEntity sign = (CustomSignBlockEntity) helper.getBlockEntity(pos);
        sign.applyComponentsFromItemStack(item);
        helper.assertTrue(sign.parts().equals(parts), "placed parts were " + sign.parts());

        BlockState state = helper.getBlockState(pos);
        List<ItemStack> drops = Block.getDrops(state, helper.getLevel(), helper.absolutePos(pos), sign);
        helper.assertTrue(drops.size() == 1 && parts.equals(drops.get(0).get(ModDataComponents.SIGN_PARTS.get())), "dropped " + drops);

        BlockPos wall = new BlockPos(2, 1, 1);
        helper.setBlock(wall, ModBlocks.CUSTOM_WALL_SIGN.get());
        CustomSignBlockEntity wallSign = (CustomSignBlockEntity) helper.getBlockEntity(wall);
        wallSign.applyComponentsFromItemStack(item);
        List<ItemStack> wallDrops = Block.getDrops(helper.getBlockState(wall), helper.getLevel(), helper.absolutePos(wall), wallSign);
        helper.assertTrue(wallDrops.size() == 1 && parts.equals(wallDrops.get(0).get(ModDataComponents.SIGN_PARTS.get())), "wall sign dropped " + wallDrops);
        helper.succeed();
    }

    private static ItemStack signOf(Part frame, Part board, int count) {
        ItemStack sign = new ItemStack(ModItems.CUSTOM_SIGN.get(), count);
        sign.set(ModDataComponents.SIGN_PARTS.get(), new SignParts(frame, board));
        return sign;
    }

    @GameTest(template = "empty")
    public static void takingASignApartGivesBothPartsWithTheirColors(GameTestHelper helper) {
        Player player = helper.makeMockPlayer(GameType.SURVIVAL);
        SignWorkbenchMenu menu = open(player);
        Part frame = new Part(SPRUCE, Optional.of(DyeColor.RED));
        Part board = Part.of(ResourceLocation.withDefaultNamespace("glass"));
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
        Player player = helper.makeMockPlayer(GameType.SURVIVAL);
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
        Player player = helper.makeMockPlayer(GameType.SURVIVAL);
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
        SignParts parts = new SignParts(Part.of(ResourceLocation.withDefaultNamespace("iron_block")),
                new Part(ResourceLocation.withDefaultNamespace("glass"), Optional.of(DyeColor.CYAN)));
        helper.setBlock(pos, ModBlocks.CUSTOM_SIGN.get());
        ((CustomSignBlockEntity) helper.getBlockEntity(pos)).setParts(parts);
        BlockPos wall = new BlockPos(2, 1, 1);
        helper.setBlock(wall, ModBlocks.CUSTOM_WALL_SIGN.get());
        ((CustomSignBlockEntity) helper.getBlockEntity(wall)).setParts(parts);

        for (BlockPos where : List.of(pos, wall)) {
            BlockState state = helper.getBlockState(where);
            ItemStack picked = state.getBlock().getCloneItemStack(helper.getLevel(), helper.absolutePos(where), state);
            helper.assertTrue(picked.is(ModItems.CUSTOM_SIGN.get()) && parts.equals(picked.get(ModDataComponents.SIGN_PARTS.get())),
                    "picked " + picked);
        }
        helper.succeed();
    }

    @GameTest(template = "empty")
    public static void placingASignKeepsTheTextColorItAlreadyHas(GameTestHelper helper) {
        BlockPos pos = new BlockPos(1, 1, 1);
        helper.setBlock(pos, ModBlocks.CUSTOM_SIGN.get());
        CustomSignBlockEntity sign = (CustomSignBlockEntity) helper.getBlockEntity(pos);
        sign.updateText(text -> text.setColor(DyeColor.RED), true);
        helper.runAfterDelay(3, () -> {
            // The default color was chosen on the first tick. Applying the sign's own components again must not choose it anew.
            sign.updateText(text -> text.setColor(DyeColor.RED), true);
            ItemStack item = new ItemStack(ModItems.CUSTOM_SIGN.get());
            sign.applyComponentsFromItemStack(item);
            helper.runAfterDelay(3, () -> {
                helper.assertTrue(sign.getFrontText().getColor() == DyeColor.RED, "color is now " + sign.getFrontText().getColor());
                helper.succeed();
            });
        });
    }
}
