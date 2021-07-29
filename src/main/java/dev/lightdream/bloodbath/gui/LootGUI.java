package dev.lightdream.bloodbath.gui;

import dev.lightdream.bloodbath.Main;
import dev.lightdream.bloodbath.utils.Utils;
import lombok.AllArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public class LootGUI implements GUI {

    private final Main plugin;

    @Override
    public void onInventoryClick(InventoryClickEvent event) {

    }

    @Override
    public void onInventoryClose(InventoryCloseEvent event) {
        List<ItemStack> loot = new ArrayList<>();

        for (ItemStack item : event.getInventory().getContents()) {
            if (item != null) {
                loot.add(item);
            }
        }
        plugin.getLoot().loot = Utils.itemStackArrayToBase64(loot);
    }

    @Override
    public Inventory getInventory() {
        Inventory inventory = Bukkit.createInventory(this, 54, Utils.color(plugin.getMessages().lootGUI));

        try {
            List<ItemStack> loot = Utils.itemStackArrayFromBase64(plugin.getLoot().loot);
            loot.forEach(inventory::addItem);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return inventory;

    }
}