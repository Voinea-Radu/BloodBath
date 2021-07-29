package dev.lightdream.bloodbath.commands;

import dev.lightdream.bloodbath.Main;
import dev.lightdream.bloodbath.gui.BloodBathLootGUI;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SetLootCommand extends Command{
    public SetLootCommand(@NotNull Main plugin) {
        super(plugin, Collections.singletonList("setLoot"), "Sets the loot for BloodBath event", Main.PROJECT_ID+".setloot", true, false, "setloot");
    }

    @Override
    public void execute(Object sender, List<String> args) {
        Player player = (Player) sender;

        player.openInventory(new BloodBathLootGUI(plugin).getInventory());
    }

    @Override
    public List<String> onTabComplete(Object commandSender, List<String> args) {
        return new ArrayList<>();
    }
}
