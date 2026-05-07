package org.spigotmc;

import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.util.CraftChatMessage;

import java.util.Locale;

public class RestartCommand extends Command {

    public RestartCommand(String name) {
        super(name);
        this.description = "Restarts the server";
        this.usageMessage = "/restart";
        this.setPermission("bukkit.command.restart");
    }

    @Override
    public boolean execute(CommandSender sender, String label, String[] args) {
        if (!this.testPermission(sender)) return true;

        MinecraftServer.getServer().execute(RestartCommand::restart);
        return true;
    }

    public static void restart() {
        restart(SpigotConfig.restartScript);
    }

    // ==============================
    // MAIN RESTART LOGIC
    // ==============================
    private static void restart(String script) {
        MinecraftServer server = MinecraftServer.getServer();

        try {
            boolean canRestart = addShutdownHook(script);

            System.out.println("[FearMC] Server shutting down...");

            if (canRestart) {
                System.out.println("[FearMC] Restart enabled: " + script);
            } else {
                System.out.println("[FearMC] No restart script found, stopping only.");
            }

            // Kick all players
            for (ServerPlayer player : server.getPlayerList().getPlayers()) {
                player.connection.disconnect(
                    CraftChatMessage.fromStringOrEmpty(SpigotConfig.restartMessage, true),
                    org.bukkit.event.player.PlayerKickEvent.Cause.RESTART_COMMAND
                );
            }

            // Stop network
            server.getConnection().stop();

            // Safe shutdown
            server.safeShutdown(false, canRestart);

            // Fallback hard stop
            new Thread(() -> {
                try {
                    Thread.sleep(8000);
                    if (!server.isStopped()) {
                        System.out.println("[FearMC] Force shutdown triggered.");
                        System.exit(0);
                    }
                } catch (InterruptedException ignored) {}
            }).start();

        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    // ==============================
    // IMPORTANT: PAPER COMPAT METHOD
    // (THIS FIXES YOUR BUILD ERROR)
    // ==============================
    public static boolean addShutdownHook(String restartScript) {
        if (restartScript == null || restartScript.trim().isEmpty()) {
            return false;
        }

        try {
            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                try {
                    String os = System.getProperty("os.name").toLowerCase(Locale.ENGLISH);

                    ProcessBuilder builder;

                    if (os.contains("win")) {
                        builder = new ProcessBuilder("cmd", "/c", restartScript);
                    } else {
                        builder = new ProcessBuilder("bash", "-c", restartScript);
                    }

                    builder.inheritIO();
                    builder.start();

                } catch (Exception e) {
                    System.err.println("[FearMC] Restart failed:");
                    e.printStackTrace();
                }
            }));

            return true;

        } catch (Exception e) {
            return false;
        }
    }
}
