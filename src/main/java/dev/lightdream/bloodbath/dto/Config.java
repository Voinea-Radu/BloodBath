package dev.lightdream.bloodbath.dto;

import dev.lightdream.bloodbath.utils.XMaterial;
import lombok.NoArgsConstructor;

import java.util.Arrays;

@NoArgsConstructor
public class Config {

    public PluginLocation chestLocation = new PluginLocation("world", 0,100,0);

    public int bloodBathDuration = 30*20;
    public int autoBloodBathDelay = 30*20;

    public int bloodBathMinItems = 3;
    public int bloodBathMaxItems = 5;

    public PluginLocation hologramLocation = new PluginLocation("world", 0.5f,102,0.5f);

    public Item flareItem = new Item(XMaterial.PAPER, 1,"BloodBath Flare", Arrays.asList("Right click on a block to use"));
}
