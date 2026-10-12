package duperez.moresigns.common.registry;

import duperez.moresigns.MoreSigns;
import duperez.moresigns.common.sign.Part;
import duperez.moresigns.common.sign.SignParts;
import net.minecraft.core.component.DataComponentType;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModDataComponents {
    public static final DeferredRegister.DataComponents DATA_COMPONENTS = DeferredRegister.createDataComponents(MoreSigns.MODID);

    /** Carried by sign frames and sign boards. */
    public static final Supplier<DataComponentType<Part>> PART = DATA_COMPONENTS.registerComponentType("part",
            builder -> builder.persistent(Part.CODEC).networkSynchronized(Part.STREAM_CODEC));

    /** Carried by custom signs, as items and as the block entity of a placed one. */
    public static final Supplier<DataComponentType<SignParts>> SIGN_PARTS = DATA_COMPONENTS.registerComponentType("sign_parts",
            builder -> builder.persistent(SignParts.CODEC).networkSynchronized(SignParts.STREAM_CODEC));

    private ModDataComponents() {
    }
}
