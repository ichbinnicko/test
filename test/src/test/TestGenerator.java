package test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.generator.BlockPopulator;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.util.noise.PerlinOctaveGenerator;
import org.bukkit.util.noise.SimplexOctaveGenerator;

public class TestGenerator extends ChunkGenerator {

	double scale = 32.0; // how far apart the tops of the hills are
	double threshold = 0.0; // the cutoff point for terrain
	int middle = 70; // the "middle" of the road

	public List<BlockPopulator> getDefaultPopulators(World world) {
		return new ArrayList();

	}

	/*
	 * Sets a block in the chunk. If the Block section doesn't exist, it
	 * allocates it. [y>>4] the section id (y/16) the math for the second offset
	 * confuses me
	 */
	void setBlock(int x, int y, int z, byte[][] chunk, Material material) {
		if (chunk[y >> 4] == null)
			chunk[y >> 4] = new byte[16 * 16 * 16];
		if (!(y <= 256 && y >= 0 && x <= 16 && x >= 0 && z <= 16 && z >= 0))
			return; // Out of bounds
		try {
			chunk[y >> 4][((y & 0xF) << 8) | (z << 4) | x] = (byte) material
					.getId();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	// Generates block sections. Each block section is 16*16*16 blocks, stacked
	// above each other. //There are world height / 16 sections. section number
	// is world height / 16 (>>4)
	// returns a byte[world height / 16][], formatted [section id][Blocks]. If
	// there are no blocks in a section, it need not be allocated.
	public byte[][] generateBlockSections(World world, Random rand, int ChunkX,
			int ChunkZ, BiomeGrid biomes) {
		Random random = new Random(world.getSeed());
		SimplexOctaveGenerator gen = new SimplexOctaveGenerator(random, 8);
		PerlinOctaveGenerator gen2 = new PerlinOctaveGenerator(world.getSeed(),
				8);

		byte[][] chunk = new byte[world.getMaxHeight() / 16][];

		gen2.setScale(1 / scale);
		gen.setScale(1 / scale); // The distance between peaks of the terrain.
									// Scroll down more to see what happens when
									// you play with this
		double threshold = this.threshold; // scroll down to see what happens
											// when you play with this.

		for (int x = 0; x < 16; x++) {
			for (int z = 0; z < 16; z++) {
				int real_x = x + ChunkX * 16;
				int real_z = z + ChunkZ * 16;

				double height = middle + gen2.noise(real_x, real_z, 0.5, 0.5)
						* middle / 3; // generate some smoother terrain

				for (int y = 1; y < height && y < 256; y++) {
					if (y > middle - middle / 3) {
						double noise = gen.noise(real_x, y, real_z, 0.5, 0.5);
						if (noise > threshold) // explained above
							setBlock(x, y, z, chunk, Material.STONE); // set the
																		// block
																		// solid
					} else {
						setBlock(x, y, z, chunk, Material.STONE);
					}
				}
			}
		}
		return chunk;
	}
}
