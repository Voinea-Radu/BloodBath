package dev.lightdream.bloodbath.managers;

import dev.lightdream.bloodbath.Main;
import dev.lightdream.bloodbath.utils.Utils;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;

public class EventManager implements Listener {

    public final Main plugin;

    public EventManager(Main plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        plugin.getDatabaseManager().getUser(event.getPlayer().getUniqueId());
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Block block = event.getClickedBlock();
        if (block == null) {
            return;
        }
        if (!event.getClickedBlock().getLocation().equals(Utils.getLocation(plugin.getSettings().pluginLocation))) {
            return;
        }
        System.out.println("Taking action");
        event.setCancelled(true);
        plugin.getBloodBathManager().takeAction(event.getPlayer());

    }

}
