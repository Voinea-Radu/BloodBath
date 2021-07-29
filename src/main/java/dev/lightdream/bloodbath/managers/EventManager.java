package dev.lightdream.bloodbath.managers;

import dev.lightdream.bloodbath.Main;
import dev.lightdream.bloodbath.utils.ItemStackUtils;
import dev.lightdream.bloodbath.utils.Utils;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.io.IOException;
import java.util.List;

public class EventManager implements Listener {

    public final Main plugin;

    public EventManager(Main plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Block block = event.getClickedBlock();
        if (block == null) {
            return;
        }
        if (!event.getClickedBlock().getLocation().equals(Utils.getLocation(plugin.getSettings().chestLocation))) {
            return;
        }
        System.out.println("Taking action");
        event.setCancelled(true);
        plugin.getBloodBathManager().takeAction(event.getPlayer());
    }

    @EventHandler
    public void onPlayerInteract2(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack item = player.getInventory().getItemInHand();
        if(Utils.compareItemStacksNoNBT(item, ItemStackUtils.makeItem(plugin.getSettings().flareItem))){
            plugin.getBloodBathManager().start();
            player.getInventory().setItemInHand(null);
            event.setCancelled(true);
        }
    }


    @EventHandler
    public void onPlayerKill(PlayerDeathEvent event) {
        Player player = event.getEntity();
        if (player == null) {
            return;
        }
        Player killer = player.getKiller();
        if (killer == null) {
            return;
        }
        try {
            List<ItemStack> items = Utils.itemStackArrayFromBase64(plugin.getLoot().killLoot);
            System.out.println(items.size());
            if (items.size() == 0) {
                return;
            }
            int item = Utils.generateRandom(0, items.size() - 1);
            System.out.println("item " + item);
            System.out.println(items.get(item));
            killer.getInventory().addItem(items.get(item));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
