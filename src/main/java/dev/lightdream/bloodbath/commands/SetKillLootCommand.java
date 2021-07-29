package dev.lightdream.bloodbath.commands;

import dev.lightdream.bloodbath.Main;
import dev.lightdream.bloodbath.gui.KillLootGUI;
import dev.lightdream.bloodbath.gui.LootGUI;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SetKillLootCommand extends Command{
    public SetKillLootCommand(@NotNull Main plugin) {
        super(plugin, Collections.singletonList("setkillloot"), "Sets the kill loot for BloodBath event", Main.PROJECT_ID+".setkillloot", true, false, "setkillloot");
    }

    @Override
    public void execute(Object sender, List<String> args) {
        Player player = (Player) sender;

        player.openInventory(new KillLootGUI(plugin).getInventory());
    }

    @Override
    public List<String> onTabComplete(Object commandSender, List<String> args) {
        return new ArrayList<>();
    }
}
