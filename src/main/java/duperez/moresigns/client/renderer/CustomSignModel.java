package duperez.moresigns.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.registries.BuiltInRegistries;
import duperez.moresigns.common.sign.Part;
import net.minecraft.resources.ResourceLocation;

import java.util.Optional;

/**
 * Geometry of a custom sign: a board (the plate you write on) inside a frame (the border and the post),
 * each textured with the sprite of the block it is made of. Shared by the world renderer and the edit screen.
 * <p>
 * Coordinates are in sixteenths of a block, with the front of the sign facing +z.
 */
public final class CustomSignModel {
    public static final RenderType RENDER_TYPE = RenderType.entityCutoutNoCull(TextureAtlas.LOCATION_BLOCKS);

    /** Height of the middle of the plate, in blocks. Text is centred on it. */
    public static final float PLATE_CENTER_Y = 13.333333F / 16F;
    /** A wall sign is the same plate, moved down and back against the wall; vanilla uses the same offsets. */
    public static final float WALL_OFFSET_Y = -5F / 16F;
    public static final float WALL_OFFSET_Z = -7F / 16F;
    /** Vanilla text is sized for a plate 8 tall; the inner plate here is 6 tall, so text is drawn at this fraction of its size. */
    public static final float TEXT_SHRINK = 0.9F;
    /** How far in front of the middle of the sign the text sits, in blocks, clear of the plate's face. */
    public static final float TEXT_FRONT_OFFSET = 0.046666667F;

    // Same overall size as a vanilla standing sign: a plate 16 wide and 8 tall on a post.
    private static final float POST_TOP = 9.33F;
    private static final float PLATE_TOP = 17.33F;
    private static final float RIM = 1.0F;
    private static final float PLATE_HALF_DEPTH = 0.67F;
    /** Kept just behind the plane the text is drawn on, so the border never covers the text when seen at an angle. */
    private static final float RIM_HALF_DEPTH = 0.72F;
    private static final float POST_HALF = 0.67F;

    /**
     * The sign rises past 16 sixteenths, but a sprite only has 16 rows. Textures are mapped by height from
     * the top of the plate so no vertex asks for a row outside the sprite, which would sample a neighbour in the atlas.
     */
    private static final float TEXTURE_Y_SHIFT = PLATE_TOP - 16;

    /** Faces of a box left out because another box of the sign touches them there; drawn, two coplanar faces flicker on glass. */
    private static final int UP = 1, DOWN = 2, NORTH = 4, SOUTH = 8, EAST = 16, WEST = 32;

    private CustomSignModel() {
    }

    /** The texture a block shows when it breaks, which for a plain block is its own texture. */
    public static TextureAtlasSprite sprite(ResourceLocation blockId) {
        return Minecraft.getInstance().getBlockRenderer().getBlockModelShaper()
                .getParticleIcon(BuiltInRegistries.BLOCK.get(blockId).defaultBlockState());
    }

    /**
     * Draws whichever of the two parts are given. A frame alone is the border and the post; a board alone is just the plate.
     *
     * @param post whether the frame includes the post; a wall sign has none.
     */
    public static void render(VertexConsumer c, PoseStack.Pose pose, Optional<Part> frame, Optional<Part> board, boolean post, int light, int overlay) {
        float plateBottom = POST_TOP;
        float innerLeft = RIM;
        float innerRight = 16 - RIM;

        frame.ifPresent(part -> {
            TextureAtlasSprite s = sprite(part.block());
            int color = 0xFF000000 | part.tintRgb();
            if (post) {
                box(c, pose, s, color, UP, 8 - POST_HALF, 0, 8 - POST_HALF, 8 + POST_HALF, POST_TOP, 8 + POST_HALF, light, overlay);
            }
            box(c, pose, s, color, 0, 0, plateBottom, 8 - RIM_HALF_DEPTH, 16, plateBottom + RIM, 8 + RIM_HALF_DEPTH, light, overlay);
            box(c, pose, s, color, 0, 0, PLATE_TOP - RIM, 8 - RIM_HALF_DEPTH, 16, PLATE_TOP, 8 + RIM_HALF_DEPTH, light, overlay);
            box(c, pose, s, color, UP | DOWN, 0, plateBottom + RIM, 8 - RIM_HALF_DEPTH, innerLeft, PLATE_TOP - RIM, 8 + RIM_HALF_DEPTH, light, overlay);
            box(c, pose, s, color, UP | DOWN, innerRight, plateBottom + RIM, 8 - RIM_HALF_DEPTH, 16, PLATE_TOP - RIM, 8 + RIM_HALF_DEPTH, light, overlay);
        });
        board.ifPresent(part -> box(c, pose, sprite(part.block()), 0xFF000000 | part.tintRgb(), UP | DOWN | EAST | WEST,
                innerLeft, plateBottom + RIM, 8 - PLATE_HALF_DEPTH, innerRight, PLATE_TOP - RIM, 8 + PLATE_HALF_DEPTH, light, overlay));
    }

    /**
     * Emits an axis-aligned box. The sprite is mapped by position on every face, so a texture continues
     * unbroken across neighbouring boxes.
     */
    private static void box(VertexConsumer c, PoseStack.Pose pose, TextureAtlasSprite s, int color, int skip,
                            float x0, float y0, float z0, float x1, float y1, float z1, int light, int overlay) {
        float v0 = y0 - TEXTURE_Y_SHIFT;
        float v1 = y1 - TEXTURE_Y_SHIFT;
        if (v0 < 0) { // the post starts below the plate's row 0; slide it down so it still maps inside the sprite
            v1 -= v0;
            v0 = 0;
        }
        // south (+z)
        if ((skip & SOUTH) == 0) quad(c, pose, s, color, light, overlay, 0, 0, 1,
                x0, y0, z1, x1, y0, z1, x1, y1, z1, x0, y1, z1, x0, v0, x1, v0, x1, v1, x0, v1);
        // north (-z)
        if ((skip & NORTH) == 0) quad(c, pose, s, color, light, overlay, 0, 0, -1,
                x1, y0, z0, x0, y0, z0, x0, y1, z0, x1, y1, z0, x1, v0, x0, v0, x0, v1, x1, v1);
        // east (+x)
        if ((skip & EAST) == 0) quad(c, pose, s, color, light, overlay, 1, 0, 0,
                x1, y0, z1, x1, y0, z0, x1, y1, z0, x1, y1, z1, z1, v0, z0, v0, z0, v1, z1, v1);
        // west (-x)
        if ((skip & WEST) == 0) quad(c, pose, s, color, light, overlay, -1, 0, 0,
                x0, y0, z0, x0, y0, z1, x0, y1, z1, x0, y1, z0, z0, v0, z1, v0, z1, v1, z0, v1);
        // up (+y)
        if ((skip & UP) == 0) quad(c, pose, s, color, light, overlay, 0, 1, 0,
                x0, y1, z0, x0, y1, z1, x1, y1, z1, x1, y1, z0, x0, z0, x0, z1, x1, z1, x1, z0);
        // down (-y)
        if ((skip & DOWN) == 0) quad(c, pose, s, color, light, overlay, 0, -1, 0,
                x0, y0, z1, x0, y0, z0, x1, y0, z0, x1, y0, z1, x0, z1, x0, z0, x1, z0, x1, z1);
    }

    /** Four corners (x, y, z each) followed by the four (u, v) pairs, both in sixteenths. */
    private static void quad(VertexConsumer c, PoseStack.Pose pose, TextureAtlasSprite s, int color, int light, int overlay,
                             float nx, float ny, float nz,
                             float ax, float ay, float az, float bx, float by, float bz,
                             float cx, float cy, float cz, float dx, float dy, float dz,
                             float au, float av, float bu, float bv, float cu, float cv, float du, float dv) {
        vertex(c, pose, s, color, light, overlay, nx, ny, nz, ax, ay, az, au, 16 - av);
        vertex(c, pose, s, color, light, overlay, nx, ny, nz, bx, by, bz, bu, 16 - bv);
        vertex(c, pose, s, color, light, overlay, nx, ny, nz, cx, cy, cz, cu, 16 - cv);
        vertex(c, pose, s, color, light, overlay, nx, ny, nz, dx, dy, dz, du, 16 - dv);
    }

    private static void vertex(VertexConsumer c, PoseStack.Pose pose, TextureAtlasSprite s, int color, int light, int overlay,
                               float nx, float ny, float nz, float x, float y, float z, float u, float v) {
        c.addVertex(pose, x / 16F, y / 16F, z / 16F)
                .setColor(color)
                .setUv(s.getU(u / 16F), s.getV(v / 16F))
                .setOverlay(overlay)
                .setLight(light)
                .setNormal(pose, nx, ny, nz);
    }
}
