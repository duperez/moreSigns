# Changelog

## 2.0.0 (NeoForge 1.21.1)

Port from Forge 1.20.1 to NeoForge 1.21.1 (21.1.259), Java 21, ModDevGradle.

### Content
- Canvas signs: plain, 16 dye colors and 2 hand-painted (`orange_purple`, `green_gradient`), each with standing and wall variants.
- Canvas item, usable as fuel (400 ticks).
- Recipes: canvas from string, canvas sign, dye recoloring from any canvas sign, and book, painting and scaffolding from canvas.
- Registry names of existing signs and the block entity id `moresigns:canvas_sign` are unchanged from 1.20.1.

### Changed
- All variants are defined in one list, `CanvasVariants`, which drives blocks, items, WoodTypes, blockstates, item models, recipes, loot, tags and lang. Data is generated with `./gradlew runData` and committed in `src/generated`.
- Each variant has its own vanilla `WoodType` (`moresigns:canvas_<key>`); the vanilla sign renderer and edit screen are used. The custom renderer, edit screen and mixins were removed.
- Added JUnit tests (variant naming) and game tests (`./gradlew runGameTestServer`).

### Fixed
- The canvas sign edit lock is now released (ticker override).
- Removed the odd recipe that turned a canvas sign plus white dye into plain Canvas.

### Removed
- Canvas Rug. The block existed without an item or textures, and its recipes referenced a non-existent item. It can be restored from branch `1.20.x`.
- Forge-only dependencies (CraftTweaker, AppleSkin, JEI stubs).
- Unused template code: loot modifiers, custom recipe types, placement modifiers, util classes.

### Known issues
- Not yet verified in a real client (sprites, edit screen, item models). Text may sit about 1px differently than in 1.20.1.
- Gradient signs have no dye recipe (creative only).
