package test;

import java.util.Random;

import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.generator.BlockPopulator;

public class ChestPopulator extends BlockPopulator {

	@SuppressWarnings("deprecation")
	@Override
	public void populate(World w, Random r, Chunk c) {
		int x, y, z;
		for (x = 0; x < 16; x++) {
			for (z = 0; z < 16; z++) {
				if (r.nextInt(100) < 20) {
					for (y = 256; c.getBlock(x, y, z).getType() == Material.AIR; y--) {
						Block b = c.getBlock(x, y, z);
						b.setType(Material.LONG_GRASS);
						b.setData((byte) 0x1);
					}
				}
			}
		}
	}

}
