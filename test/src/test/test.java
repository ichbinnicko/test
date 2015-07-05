package test;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class test extends JavaPlugin implements Listener {

	public void onEnable() {

		Bukkit.getPluginManager().registerEvents(this, this);
		System.out.println("enabled.");
	}

}
