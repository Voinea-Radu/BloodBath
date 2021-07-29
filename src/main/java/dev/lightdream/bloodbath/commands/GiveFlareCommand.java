package dev.lightdream.bloodbath.commands;

import dev.lightdream.bloodbath.Main;
import dev.lightdream.bloodbath.utils.ItemStackUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;

public class GiveFlareCommand extends Command{
    public GiveFlareCommand(@NotNull Main plugin) {
        super(plugin, Collections.singletonList("giveflare"), "Gives flare to player", Main.PROJECT_ID+".giveflare", false, false, "giveflare [player]");
    }

    @Override
    public void execute(Object sender, List<String> args) {
        if(args.size()!=1){
            sendUsage(sender);
        }
        Player player = Bukkit.getPlayer(args.get(0));
        if(player == null){
            plugin.getMessageManager().sendMessage(sender, plugin.getMessages().invalidPlayer);
        }
        player.getInventory().addItem(ItemStackUtils.makeItem(plugin.getSettings().flareItem));
    }

    @Override
    public List<String> onTabComplete(Object commandSender, List<String> args) {
        return null;
    }
}
