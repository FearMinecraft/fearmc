package io.papermc.paper;

import java.util.List;
import joptsimple.OptionSet;
import net.minecraft.server.Main;
import net.minecraft.SharedConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class PaperBootstrap {

    private static final Logger LOGGER = LoggerFactory.getLogger("bootstrap");

    private PaperBootstrap() {}

    public static void boot(final OptionSet options) {
        SharedConstants.tryDetectVersion();

        printFearMCLogo();

        getStartupVersionMessages().forEach(LOGGER::info);

        // Start vanilla server
        Main.main(options);

        // Start FearMC core (NO init dependency issues anymore)
        Main.main(options);
    }

    private static void printFearMCLogo() {
        System.out.println("""
███████╗███████╗ █████╗ ██████╗ ███╗   ███╗ ██████╗ 
██╔════╝██╔════╝██╔══██╗██╔══██╗████╗ ████║██╔════╝ 
█████╗  █████╗  ███████║██████╔╝██╔████╔██║██║     
██╔══╝  ██╔══╝  ██╔══██║██╔══██╗██║╚██╔╝██║██║     
██║     ███████╗██║  ██║██║  ██║██║ ╚═╝ ██║╚██████╗
╚═╝     ╚══════╝╚═╝  ╚═╝╚═╝  ╚═╝╚═╝     ╚═╝ ╚═════╝

        FearMC - Next Generation Paper Fork
        """);
    }

    private static List<String> getStartupVersionMessages() {
        return List.of(
            "Java: " + System.getProperty("java.version"),
            "OS: " + System.getProperty("os.name"),
            "FearMC Booting..."
        );
    }
}
