package io.fearmc.fear.command;

import io.fearmc.fear.FearConfig;
import io.fearmc.fear.StopManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class StopCommand extends Command {

    private boolean waitingPassword = false;

    public StopCommand(String name) {
        super(name);
        setPermission("fearmc.command.stop");
    }

    @Override
    public boolean execute(CommandSender sender, String label, String[] args) {

        if (!testPermission(sender)) return true;

        if (FearConfig.stopPasswordEnabled()) {

            if (args.length == 0) {
                sender.sendMessage("§eType The Password in Chat (It Wont Send to global its local)");
                waitingPassword = true;
                return true;
            }

            if (!args[0].equals(FearConfig.getStopPassword())) {
                sender.sendMessage("§cWrong password!");
                return true;
            }
        }

        StopManager.stopServer();
        return true;
    }
}
