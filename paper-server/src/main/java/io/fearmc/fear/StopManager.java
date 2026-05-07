package io.fearmc.fear;

import org.bukkit.Bukkit;

public class StopManager {

    public static void stopServer() {

        Bukkit.getScheduler().runTask(Main.getInstance(), () -> {

            Bukkit.getOnlinePlayers().forEach(p ->
                p.kickPlayer("Server restarting...")
            );

            Bukkit.shutdown();
        });
    }
}
