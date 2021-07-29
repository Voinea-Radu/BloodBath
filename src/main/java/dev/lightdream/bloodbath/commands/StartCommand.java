package dev.lightdream.bloodbath.commands;

import dev.lightdream.bloodbath.Main;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class StartCommand extends  Command{
    public StartCommand(@NotNull Main plugin) {
        super(plugin, Collections.singletonList("start"), "Starts the bloodbath event", Main.PROJECT_ID+".start", false, false, "start");
    }

    @Override
    public void execute(Object sender, List<String> args) {
        plugin.getBloodBathManager().start();
    }

    @Override
    public List<String> onTabComplete(Object commandSender, List<String> args) {
        return new ArrayList<>();
    }
}
