package dev.lightdream.bloodbath.dto;

import dev.lightdream.bloodbath.Main;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

@SuppressWarnings("unused")
@NoArgsConstructor
public class Messages {

    public String prefix = "[" + Main.PROJECT_NAME + "] ";

    public String mustBeAPlayer = "You must be a player to use this command.";
    public String mustBeConsole = "You must be console to use this command.";
    public String noPermission = "You do not have the permission to use this command.";
    public String unknownCommand = "This is not a valid command.";
    public String invalidPlayer = "This player is invalid.";

    //Leave empty for auto-generated
    public List<String> helpCommand = new ArrayList<>();

    public String wonBloodBath = "%player% has won the BloodBath";
    public String lootSet = "You have set the bloodbath loot";
    public String lootGUI = "Place your loot here";
    public String bloodBathStarted = "A new BloodBath event has started.";
    public String bloodBathRewardExpired = "The BloodBath reward has expired.";
    public String bloodBathExpired = "The BloodBath event has expired.";

    public List<String> stoppedHologram = Arrays.asList(
            "There is no running bloodbath event currently running",
            "Please come back later",
            "Next BloodBath in %hours%h %minutes%m %seconds%s"
    );

    public List<String> awaitingHologram = Arrays.asList(
            "Bloodbath can be started now",
            "Left click to start"
    );

    public List<String> runningHologram = Arrays.asList(
            "Blood bath is running. Wait %hours%h %minutes%m %seconds%s"
    );

    public List<String> endedHologram = Arrays.asList(
            "Bloodbath has ended",
            "Left click to get the loot"
    );

    public HashMap<Integer, String> announcements = new HashMap<Integer, String>(){{
        put(10, "There are 10s till the BloodBath event");
        put(5, "There are 5s till the BloodBath event");
    }};

}
