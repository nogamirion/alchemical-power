# Panakeia Extractor / Alchemical Reactor models

Implemented as Minecraft Java cuboid block JSONs. Each fits inside coordinates
0..16 on all axes. North (-Z) is the front. Existing blockstates rotate the models,
and existing item models inherit them. The extractor contains 68 named elements,
and the reactor contains 69. Both
can be imported as a Java block/item model in Blockbench.

## Resources

- `src/main/resources/assets/alchemical_power/models/block/panakeia_extractor.json`
- `src/main/resources/assets/alchemical_power/models/block/alchemical_reactor.json`
- Four shared 16x16 opaque textures named `alchemical_machine_iron`,
  `alchemical_machine_brass`, `alchemical_machine_recess`, and
  `alchemical_machine_teal` in `textures/block` under the same asset namespace.
- Windows use `minecraft:block/glass` with `minecraft:cutout` rendering.
- Vessel contents use the existing animated `fluid/liquid_panakeia_still` texture.

The extractor's fixed liquid cubes have been removed. Its block entity renderer
draws biome-tinted water in the tall vessel and liquid Panakeia in the small vessel.
Their heights independently follow amount / current upgraded capacity; empty tanks
are invisible. Pipe transfers mark the block entity dirty and trigger a tank update
on the next server tick, including when no GUI is open. Item models have empty tanks.
The reactor likewise renders its actual Panakeia amount / upgraded capacity.
Idle pipe fills, per-tick processing consumption, and capacity changes synchronize
without an open GUI. Both renderers share MachineFluidRenderer.

Both blocks use cached, rotated collision and selection shapes from
`AlchemicalMachineShapes`, following major parts with solid vessel envelopes.
Fine trim is approximated. Vessel bounds for dynamic liquids are shared with the
renderer in this class. Update these bounds if the vessel geometry changes.
No GUI, recipe, or processing balance was changed.

## Rebuilding and inspecting

From the repository root, run `python output/block_models/build_models.py` to
rebuild both JSONs (overwrites edits to these JSONs). Material textures are not
overwritten. Prefer editing this generator if maintaining reproducibility.

Run `python output/block_models/preview_models.py` with Pillow and NumPy to
render `models_preview.png` from the actual geometry and UV coordinates.
It reads the cached Minecraft 1.20.1 client jar for vanilla glass. This is a
software orthographic preview with simple shading and frame-zero fluid imagery;
it does not verify Minecraft lighting, animation, or item presentation. The static
previews now show empty tanks because it does not run block entity renderers.

Material atlas was generated using the built-in imagegen tool. The exact prompt
is in `material_atlas_prompt.txt`; the original is `material_atlas_source.png`.
The four quadrants were sampled with nearest-neighbor to 16x16 PNGs and made opaque.

## In-game checks still needed

Inspect north/east/south/west placement, inventory and held item appearance,
glass and fluid animation, neighboring blocks, and an active processing cycle.
Also inspect empty/half/full tanks, pipe transfers with the GUI closed, capacity
upgrades, chunk re-entry, and collision/selection at every facing.
The extractor GUI remains its existing resource for separate future artwork.
