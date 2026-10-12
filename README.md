# MoreSigns

<a href="https://www.curseforge.com/minecraft/mc-mods/moresigns">
  <img src="https://cf.way2muchnoise.eu/moresigns.svg" alt="Curseforge Downloads">
</a>

**More Signs** adds canvas signs to Minecraft. This branch is the port of the mod from Forge 1.20.1 to NeoForge 1.21.1.

- Minecraft 1.21.1, NeoForge 21.1.259, Java 21, built with ModDevGradle
- Version 2.0.0 (`moresigns-1.21.1-2.0.0.jar`)

## Features

- 19 canvas signs: plain, the 16 dye colors, and 2 hand-painted ones (`orange_purple`, `green_gradient`). Each has a standing and a wall variant.
- Canvas item, usable as furnace fuel (400 ticks).
- Recipes: canvas from string, canvas sign from canvas, recoloring with dye from any canvas sign, plus book, painting and scaffolding from canvas. The two hand-painted signs have no dye recipe and are creative-only.
- Registry names of all existing signs and the block entity id `moresigns:canvas_sign` are unchanged from 1.20.1, so existing worlds keep their signs.

## Building and development

```
./gradlew build              # build the jar
./gradlew test               # JUnit tests (variant naming)
./gradlew runGameTestServer  # game tests (src/test) on a headless server
./gradlew runData            # regenerate src/generated
./gradlew runClient          # launch a dev client
```

## Architecture

`src/main/java/duperez/moresigns/common/variant/CanvasVariants.java` is the single list of signs. It drives blocks, items, WoodTypes, blockstates, item models, recipes, loot, tags and lang (the data generators are in `data/`).

Each variant has its own vanilla `WoodType` (`moresigns:canvas_<key>`), so vanilla's sign renderer and edit screen draw `textures/entity/signs/canvas_<key>.png`. There is no custom renderer, edit screen or mixin.

## Adding a sign

1. Add `textures/entity/signs/canvas_<key>.png` and `textures/item/<key>_canvas_sign.png` to the resources.
2. Add a line in `CanvasVariants`.
3. Run `./gradlew runData` and commit `src/generated`.

Never rename the registry name of an existing entry; it breaks signs in existing worlds.

## Status and known gaps

- Nothing has been checked visually in a real client yet (sprites, edit screen, item models).
- Sign text may sit about 1px differently than in 1.20.1, since the old custom renderer's -1 vertical offset is gone.
- Gradient signs have no dye recipe (creative only).
- The Canvas Rug from 1.20.1 was removed (see changelog); it can be restored from branch `1.20.x`.

## Contributing

Issues and PRs are welcome.

## License

MIT, see `LICENSE`. The license file is the original one from vectorwing (Farmer's Delight), the template this code originated from.
