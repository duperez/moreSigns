# MoreSigns

<a href="https://www.curseforge.com/minecraft/mc-mods/moresigns">
  <img src="https://cf.way2muchnoise.eu/moresigns.svg" alt="Curseforge Downloads">
</a>

**More Signs** adds signs you build yourself, and a set of canvas signs. This branch is the port of the NeoForge 1.21.1 version of the mod back to Forge 1.20.1. It was built as a new project: the branch starts from `origin/1.20.x` for history, but the source tree was replaced, not edited.

- Minecraft 1.20.1, Forge 47.3.0, Java 17, built with ForgeGradle 6
- Version 2.0.0 (`moresigns-1.20.1-2.0.0.jar`)

## Features

- **Custom signs.** A sign is a frame (border and post) plus a board (the plate you write on). Each part is a block plus an optional dye tint, and uses the texture of that block. Standing and wall versions. Drops and pick block keep the parts. The text color is chosen once, white or black, by contrast against the board (glass-like blocks without a map color count as light).
- **Sign Workbench.** Crafting table + any sign (shapeless). Four tabs: make parts, paint, assemble, take apart. Live rotating 3D preview.
- **Canvas signs.** 19 signs: plain, the 16 dye colors, and 2 hand-painted ones (`orange_purple`, `green_gradient`), each with standing and wall variants. Canvas item (furnace fuel, 400 ticks), and recipes for canvas, canvas signs, dye recoloring, book, painting and scaffolding. The hand-painted signs are creative-only.
- Registry names of the canvas signs and the block entity id `moresigns:canvas_sign` are unchanged from the old 1.20.1 releases, so existing worlds keep their signs.
- Languages: English, Portuguese (pt_br, pt_pt) and Spanish (es_es, es_mx, es_ar, es_cl, es_uy, es_ve).

## Making a sign at the workbench

1. **Make parts**: put a block in the slot, then pick frame (1 block gives 1 frame) or board (1 block gives 2 boards). The block is consumed.
2. **Paint** (optional): put a frame or board and a dye. A dye tints the part and is not consumed. The wash button removes paint.
3. **Assemble**: a frame and a board give a sign.
4. **Take apart**: a custom sign gives back its frame and board with their colors. Taking either result gives the other too. Shift-click does a whole stack.

Accepted blocks are in the block tag `moresigns:sign_materials`: planks, wool, terracotta, stone bricks, explicit concrete blocks, bricks, nether bricks, quartz block, smooth stone, bone block, prismarine, cut copper, and the shared Forge tags `forge:storage_blocks`, `forge:glass`, `forge:stone`, `forge:cobblestone` and `forge:obsidian` (so blocks from other mods in those tags work). The creative tab has the workbench, canvas, the canvas signs, and sample frames, boards and signs.

The custom sign is deliberately not in the `minecraft:signs` item tag. The workbench recipe takes any item from that tag, so this keeps it from consuming a customized sign.

### Allowing more materials

A data pack can add blocks by overriding `data/moresigns/tags/blocks/sign_materials.json` (or adding entries with `"replace": false`). In the mod itself, edit the tag provider in `data/ModBlockTags.java` and run `./gradlew runData`.

## Building and development

```
./gradlew build              # build the jar
./gradlew test               # JUnit tests (variant naming, text contrast, language files)
./gradlew runGameTestServer  # 19 game tests on a headless server (include the workbench menu flows)
./gradlew runData            # regenerate src/generated (recipes, tags, models, lang); runs in run-data/
./gradlew runClient          # launch a dev client
python3 tools/generate_art.py  # regenerate workbench block textures, the GUI sprite sheet and WorkbenchSprites.java (no dependencies, deterministic; run from the repo root)
```

The tests are in the test source set, so they are not in the jar. The game tests are in `src/test/java/.../gametest` and use the empty structure `data/moresigns/structures/empty.nbt`.

## Architecture

- `common/sign/Part` and `SignParts` hold what a sign is made of. This Minecraft version has no data components, so the parts are item NBT (`common/sign/SignNbt.java`): the tag `Part` on frame and board items, the tag `SignParts` on sign items. The block entity saves them under `parts`.
- `SignParts` is not stored under `BlockEntityTag`: the game ignores that tag on signs placed by non-operators (`SignBlockEntity.onlyOpCanSetNbt`). The sign blocks apply `SignParts` themselves in `setPlacedBy`, and the loot table uses `copy_nbt`.
- Rendering is custom: `client/renderer/CustomSignModel`, `CustomSignRenderer`, `CustomSignItemRenderer`. Faces hidden against another part are not drawn, which avoids z-fighting on glass. The edit screen is swapped on `ScreenEvent.Opening` and an access transformer (`META-INF/accesstransformer.cfg`), and draws the real sign. Forge reads SRG names in the access transformer (`f_244140_` is `AbstractSignEditScreen.sign`, `f_276451_` is `isFrontText`).
- The workbench logic is `SignWorkbenchLogic` (costs and yields: `FRAME_COST`, `FRAME_YIELD`, `BOARD_COST`, `BOARD_YIELD`), the menu is `SignWorkbenchMenu`, the screen is `SignWorkbenchScreen`. This version has no GUI sprite system, so the interface is drawn from one sprite sheet, `textures/gui/sign_workbench.png`. The position of each sprite is in the generated `client/gui/WorkbenchSprites.java`, and `SpriteDrawer` does the blitting and the nine-slice. Design reasoning for the screen: `docs/workbench-ui-design.md`.
- `common/variant/CanvasVariants.java` is the single list of canvas signs. It drives their blocks, items, WoodTypes, blockstates, item models, recipes, loot, tags and lang. Each has its own vanilla `WoodType` (`moresigns:canvas_<key>`).
- Translations are generated by datagen from one table, `data/Translations.java`. `LanguageFilesTest` checks that every language has the same keys and placeholders as English.

## Adding a canvas sign variant

1. Add `textures/entity/signs/canvas_<key>.png` and `textures/item/<key>_canvas_sign.png` to the resources.
2. Add a line in `CanvasVariants`.
3. Run `./gradlew runData` and commit `src/generated`.

Never rename the registry name of an existing entry; it breaks signs in existing worlds.

## Status and known limitations

- The canvas signs are separate from the custom signs and cannot be taken apart.
- Paint is a tint of the block texture, with the 16 vanilla dyes only, so it can look muddy on blocks that already have color.
- Every material costs 1 block, whatever its rarity (a block of iron costs the same as a plank).
- Taking a sign apart with a nearly full inventory can drop one of the two parts on the ground. Nothing is lost.
- No JEI integration for the workbench.
- Gradient canvas signs have no dye recipe (creative only).
- The Canvas Rug from the old 1.20.1 branch was removed (see changelog). It is not in this source tree but exists on `origin/1.20.x`.

## Contributing

Issues and PRs are welcome.

## License

MIT, see `LICENSE`. The license file is the original one from vectorwing (Farmer's Delight), the template this code originated from.
