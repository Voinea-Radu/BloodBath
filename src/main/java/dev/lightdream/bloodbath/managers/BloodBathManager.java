package dev.lightdream.bloodbath.managers;

import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;
import dev.lightdream.bloodbath.Main;
import dev.lightdream.bloodbath.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitTask;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BloodBathManager {

    private final Main plugin;
    private Hologram hologram;

    private State state;

    private BukkitTask task;

    public BloodBathManager(Main plugin) {
        this.plugin = plugin;
        initialize();
    }

    private void initialize() {
        HologramsAPI.getHolograms(plugin).forEach(Hologram::delete);
        Location location = Utils.getLocation(plugin.getSettings().pluginLocation);
        location.setX(location.getBlockX() + 0.5);
        location.setY(location.getBlockY() + 2);
        location.setZ(location.getBlockZ() + 0.5);
        hologram = HologramsAPI.createHologram(plugin, location);
        stop();
    }

    private void setLines(List<String> lines) {
        hologram.clearLines();
        lines.forEach(line -> hologram.appendTextLine(line));
    }

    public void start() {
        state = State.AWAITING;

        setLines(plugin.getMessages().awaitingHologram);
    }

    private void run() {
        state = State.RUNNING;

        setLines(plugin.getMessages().runningHologram);

    }

    private void end() {
        state = State.ENDED;

        setLines(plugin.getMessages().endedHologram);
    }

    private void stop() {
        state = State.STOPPED;

        setLines(plugin.getMessages().stoppedHologram);

    }

    public void takeAction(Player player) {
        switch (state) {
            case AWAITING:
                run();
                Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
                    @Override
                    public void run() {
                        end();
                    }
                }, plugin.getSettings().bloodBathDuration);

                if (task != null) {
                    task.cancel();
                }

                task = Bukkit.getScheduler().runTaskTimer(plugin, new Runnable() {
                    int seconds = plugin.getSettings().bloodBathDuration / 20;

                    @Override
                    public void run() {
                        if (state != State.RUNNING) {
                            return;
                        }
                        setLines(parse(plugin.getMessages().runningHologram, String.valueOf(seconds / 60), String.valueOf(seconds % 60)));
                        seconds--;
                    }
                }, 0, 20);
                break;
            case ENDED:
                stop();
                plugin.getMessageManager().sendMessage(player, plugin.getMessages().wonBloodBath);
                try {
                    List<ItemStack> items = Utils.itemStackArrayFromBase64(plugin.getLoot().loot);
                    int number = Utils.generateRandom(plugin.getSettings().bloodBathMinItems, plugin.getSettings().bloodBathMaxItems);
                    for (int i = 0; i < number; i++) {
                        player.getInventory().addItem(items.get(Utils.generateRandom(0, items.size() - 1)));
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

                //TODO give items
                break;
        }
    }

    private String parse(String raw, String minutes, String seconds) {
        String parsed = raw;

        parsed = parsed.replace("%minutes%", minutes);
        parsed = parsed.replace("%seconds%", seconds);

        return parsed;
    }

    private List<String> parse(List<String> raw, String minutes, String seconds) {
        List<String> parsed = new ArrayList<>();

        raw.forEach(line -> parsed.add(parse(line, minutes, seconds)));

        return parsed;
    }

    public enum State {
        STOPPED, AWAITING, RUNNING, ENDED
    }
}
