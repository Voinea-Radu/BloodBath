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

    private State state = State.STOPPED;

    private BukkitTask task;

    public BloodBathManager(Main plugin) {
        this.plugin = plugin;
        initialize();
    }

    private void initialize() {
        HologramsAPI.getHolograms(plugin).forEach(Hologram::delete);
        Location location = Utils.getLocation(plugin.getSettings().hologramLocation);
        location.setX(location.getBlockX() + 0.5);
        location.setY(location.getBlockY() + 2);
        location.setZ(location.getBlockZ() + 0.5);
        hologram = HologramsAPI.createHologram(plugin, location);
        stop();

        Bukkit.getScheduler().runTaskTimer(plugin, new Runnable() {
            int seconds = plugin.getSettings().autoBloodBathDelay / 20;

            @Override
            public void run() {
                if (seconds <= 0) {
                    start();
                    seconds = plugin.getSettings().autoBloodBathDelay / 20;
                }
                if (state == State.STOPPED) {
                    setLines(parse(plugin.getMessages().stoppedHologram, String.valueOf((seconds / 60) / 60), String.valueOf((seconds / 60) % 60), String.valueOf(seconds % 60)));


                    String message = plugin.getMessages().announcements.getOrDefault(seconds, "");
                    if(!message.equals("")){
                        Bukkit.broadcastMessage(Utils.color(message));
                    }
                    seconds--;
                }

            }

        }, 0, 20);
    }

    public void setLines(List<String> lines) {
        hologram.clearLines();
        lines.forEach(line -> hologram.appendTextLine(Utils.color(line)));
    }

    public void start() {
        if (state != State.STOPPED) {
            return;
        }
        state = State.AWAITING;

        Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
            @Override
            public void run() {
                if (state == State.AWAITING) {
                    stop();
                    Bukkit.broadcastMessage(Utils.color(plugin.getMessages().bloodBathExpired));
                }
            }
        }, plugin.getSettings().bloodBathDuration);

        setLines(plugin.getMessages().awaitingHologram);
        Bukkit.broadcastMessage(Utils.color(plugin.getMessages().bloodBathStarted));
    }

    private void run() {
        if (state != State.AWAITING) {
            return;
        }
        state = State.RUNNING;

        setLines(plugin.getMessages().runningHologram);
    }

    private void end() {
        if (state != State.RUNNING) {
            return;
        }
        state = State.ENDED;

        setLines(plugin.getMessages().endedHologram);
        Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
            @Override
            public void run() {
                if (state == State.ENDED) {
                    stop();
                    Bukkit.broadcastMessage(Utils.color(plugin.getMessages().bloodBathRewardExpired));
                }
            }
        }, plugin.getSettings().bloodBathDuration);
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
                        setLines(parse(plugin.getMessages().runningHologram, String.valueOf((seconds / 60) / 60), String.valueOf((seconds / 60) % 60), String.valueOf(seconds % 60)));
                        seconds--;
                    }
                }, 0, 20);
                break;
            case ENDED:
                stop();
                String message = plugin.getMessages().wonBloodBath;
                message = message.replace("%player%", player.getName());
                Bukkit.broadcastMessage(Utils.color(message));
                try {
                    List<ItemStack> items = Utils.itemStackArrayFromBase64(plugin.getLoot().loot);
                    if (items.size() != 0) {
                        int number = Utils.generateRandom(plugin.getSettings().bloodBathMinItems, plugin.getSettings().bloodBathMaxItems);
                        for (int i = 0; i < number; i++) {
                            player.getInventory().addItem(items.get(Utils.generateRandom(0, items.size() - 1)));
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    public String parse(String raw, String hours, String minutes, String seconds) {
        String parsed = raw;

        parsed = parsed.replace("%hours%", hours);
        parsed = parsed.replace("%minutes%", minutes);
        parsed = parsed.replace("%seconds%", seconds);

        return parsed;
    }

    public List<String> parse(List<String> raw, String hours, String minutes, String seconds) {
        List<String> parsed = new ArrayList<>();

        raw.forEach(line -> parsed.add(parse(line, hours, minutes, seconds)));

        return parsed;
    }

    public enum State {
        STOPPED, AWAITING, RUNNING, ENDED
    }
}
