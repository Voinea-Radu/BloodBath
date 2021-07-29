package dev.lightdream.bloodbath.dto;

import dev.lightdream.bloodbath.Main;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SuppressWarnings("unused")
@NoArgsConstructor
public class Messages {

    public String prefix = "[" + Main.PROJECT_NAME + "] ";

    public String mustBeAPlayer = "You must be a player to use this command.";
    public String mustBeConsole = "You must be console to use this command.";
    public String noPermission = "You do not have the permission to use this command.";
    public String unknownCommand = "This is not a valid command.";

    //Leave empty for auto-generated
    public List<String> helpCommand = new ArrayList<>();

    public String wonBloodBath = "You have won the bloodbath";
    public String bloodBathLootSet = "You have set the bloodbath loot";
    public String bloodBathLootGUI = "Place your loot here";

    public List<String> stoppedHologram = Arrays.asList(
            "There is no running bloodbath event currently running",
            "Please come back later"
    );

    public List<String> awaitingHologram = Arrays.asList(
            "Bloodbath can be started now",
            "Left click to start"
    );

    public List<String> runningHologram = Arrays.asList(
            "Blood bath is running. Wait %minutes%m %seconds%s"
    );

    public List<String> endedHologram = Arrays.asList(
            "Bloodbath has ended",
            "Left click to get the loot"
    );

}
