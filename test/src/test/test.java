package test;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.plugin.java.JavaPlugin;

public class test extends JavaPlugin implements Listener {

	public void onEnable() {

		Bukkit.getPluginManager().registerEvents(this, this);

	}
	
	public ChunkGenerator getDefaultWorldGenerator(String worldName, String uid) {
		return new TestGenerator();
	}
	
	@EventHandler
	public void on(PlayerPickupItemEvent e) {
		if(e.getItem().getItemStack().getType() == Material.IRON_INGOT) {
			e.setCancelled(true);
			e.getItem().remove();
			/*
			 * der rest
			 */
		}
	}

}
