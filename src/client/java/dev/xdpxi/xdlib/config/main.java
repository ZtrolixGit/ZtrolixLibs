package dev.xdpxi.xdlib.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.cloth.clothconfig.shadowed.blue.endless.jankson.Comment;

@Config(name = "main")
public class main implements ConfigData {
    @Comment("Add The Mod To The World (Not Working)")
    public boolean injectToWorld = false;
    @Comment("Add Custom Badges to Modmenu")
    public boolean customBadges = true;
    @Comment("Show Changelog On Every Startup")
    public boolean changelogEveryStartup = false;
}