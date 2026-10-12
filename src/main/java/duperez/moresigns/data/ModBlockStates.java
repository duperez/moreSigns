package duperez.moresigns.data;

import duperez.moresigns.MoreSigns;
import duperez.moresigns.common.registry.ModBlocks;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ModBlockStates extends BlockStateProvider {
    /** Hand-written model holding only the particle texture; the sign itself is drawn by the block entity renderer. */
    private static final String SIGN_MODEL = "block/canvas_sign";

    private final ExistingFileHelper existingFileHelper;

    public ModBlockStates(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        super(generator, MoreSigns.MODID, existingFileHelper);
        this.existingFileHelper = existingFileHelper;
    }

    @Override
    protected void registerStatesAndModels() {
        ModelFile signModel = new ModelFile.ExistingModelFile(new ResourceLocation(MoreSigns.MODID, SIGN_MODEL), existingFileHelper);
        ModBlocks.allSigns().forEach(sign -> simpleBlock(sign, signModel));
        simpleBlock(ModBlocks.CUSTOM_SIGN.get(), signModel);
        simpleBlock(ModBlocks.CUSTOM_WALL_SIGN.get(), signModel);

        // The model faces north, which is how a block facing north is meant to look.
        horizontalBlock(ModBlocks.SIGN_WORKBENCH.get(),
                new ModelFile.ExistingModelFile(new ResourceLocation(MoreSigns.MODID, "block/sign_workbench"), existingFileHelper));
    }
}
