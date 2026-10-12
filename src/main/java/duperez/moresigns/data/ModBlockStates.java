package duperez.moresigns.data;

import duperez.moresigns.MoreSigns;
import duperez.moresigns.common.registry.ModBlocks;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class ModBlockStates extends BlockStateProvider {
    /** Hand-written model holding only the particle texture; the sign itself is drawn by the block entity renderer. */
    private static final String SIGN_MODEL = "block/canvas_sign";

    public ModBlockStates(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, MoreSigns.MODID, existingFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        ModelFile signModel = new ModelFile.ExistingModelFile(
                ResourceLocation.fromNamespaceAndPath(MoreSigns.MODID, SIGN_MODEL), models().existingFileHelper);
        ModBlocks.allSigns().forEach(sign -> simpleBlock(sign, signModel));
    }
}
