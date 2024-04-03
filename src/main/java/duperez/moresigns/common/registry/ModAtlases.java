package duperez.moresigns.common.registry;

import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.resources.model.Material;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.DyeColor;
import duperez.moresigns.MoreSigns;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ModAtlases
{
	public static final Material BLANK_CANVAS_SIGN_MATERIAL = new Material(Sheets.SIGN_SHEET, new ResourceLocation(MoreSigns.MODID, "entity/signs/canvas"));

	public static final Map<String, Material> DYED_CANVAS_SIGN_MATERIALS =
			Stream.of("white", "orange", "magenta", "light_blue", "yellow", "lime", "pink", "gray", "light_gray", "cyan", "purple", "blue", "brown", "green", "red", "black", "orange_purple")
					.collect(Collectors.toMap(Function.identity(), ModAtlases::getSignMaterial));


	public static Material getSignMaterial(String dyeType) {
		return new Material(Sheets.SIGN_SHEET, new ResourceLocation(MoreSigns.MODID, "entity/signs/canvas_" + dyeType));
	}
}
