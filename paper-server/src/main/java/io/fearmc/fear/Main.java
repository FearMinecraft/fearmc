package io.fearmc.fear;

import io.fearmc.fear.command.StopCommand;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandMap;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Field;

public class Main extends JavaPlugin {

    private static Main instance;

    @Override
    public void onEnable() {
        instance = this;

        // Load config (auto-create fear-config.yml)
        FearConfig.init(getDataFolder());

        // Register commands
        registerCommands();

        getLogger().info("FearMC Enabled Successfully ⚡");
    }

    @Override
    public void onDisable() {
        getLogger().info("FearMC Disabled");
    }

    public static Main getInstance() {
        return instance;
    }

    // =========================
    // COMMAND REGISTRATION
    // =========================
    private void registerCommands() {
        try {
            CommandMap commandMap = getCommandMap();

            // فقط /stop (با سیستم password اگر فعال بود)
            commandMap.register("fearmc", new StopCommand("stop"));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // گرفتن CommandMap بدون NMS دردسر
    private CommandMap getCommandMap() throws Exception {
        Field f = Bukkit.getServer().getClass().getDeclaredField("commandMap");
        f.setAccessible(true);
        return (CommandMap) f.get(Bukkit.getServer());
    }
}
