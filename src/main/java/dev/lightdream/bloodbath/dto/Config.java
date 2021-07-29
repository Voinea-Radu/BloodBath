package dev.lightdream.bloodbath.dto;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class Config {

    public PluginLocation pluginLocation = new PluginLocation("world", 0,100,0);

    public int bloodBathDuration = 30*20;

    public int bloodBathMinItems = 3;
    public int bloodBathMaxItems = 5;
}
