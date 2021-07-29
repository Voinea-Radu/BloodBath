package dev.lightdream.bloodbath;

import dev.lightdream.bloodbath.commands.*;
import dev.lightdream.bloodbath.dto.Config;
import dev.lightdream.bloodbath.dto.Loot;
import dev.lightdream.bloodbath.dto.Messages;
import dev.lightdream.bloodbath.managers.*;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

@Getter
public final class Main extends JavaPlugin {
    //Settings
    public final static String PROJECT_NAME = "BloodBath";
    public final static String PROJECT_ID = "bb";
    private final List<Command> commands = new ArrayList<>();

    //Managers
    private CommandManager commandManager;
    private EventManager eventManager;
    private InventoryManager inventoryManager;
    private MessageManager messageManager;
    private BloodBathManager bloodBathManager;

    //Utils
    private FileManager fileManager;

    //DTO
    private Config settings;
    private Messages messages;
    private Loot loot;

    @Override
    public void onEnable() {
        //Utils
        fileManager = new FileManager(this, FileManager.PersistType.YAML);

        //Config
        loadConfigs();

        //Commands
        commands.add(new ReloadCommand(this));
        commands.add(new SetLootCommand(this));
        commands.add(new SetKillLootCommand(this));
        commands.add(new StartCommand(this));
        commands.add(new GiveFlareCommand(this));

        //Managers
        commandManager = new CommandManager(this, PROJECT_ID.toLowerCase());
        eventManager = new EventManager(this);
        inventoryManager = new InventoryManager(this);
        messageManager = new MessageManager(this);

        if (!Bukkit.getPluginManager().isPluginEnabled("HolographicDisplays")) {
            getLogger().severe("*** HolographicDisplays is not installed or not enabled. ***");
            getLogger().severe("*** This plugin will be disabled. ***");
            this.setEnabled(false);
            return;
        }

        this.bloodBathManager = new BloodBathManager(this);

    }

    @Override
    public void onDisable() {
        //Save files
        fileManager.save(loot);

        //Save to db
    }

    public void loadConfigs() {
        settings = fileManager.load(Config.class);
        messages = fileManager.load(Messages.class);
        loot = fileManager.load(Loot.class);
    }
}
