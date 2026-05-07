package com.destroystokyo.paper;

import com.destroystokyo.paper.util.VersionFetcher;
import io.papermc.paper.ServerBuildInfo;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.event.ClickEvent;

import java.util.Optional;
import java.util.OptionalInt;

import static net.kyori.adventure.text.Component.text;
import static io.papermc.paper.ServerBuildInfo.StringRepresentation.VERSION_SIMPLE;

public class PaperVersionFetcher implements VersionFetcher {

    private static final ServerBuildInfo BUILD_INFO = ServerBuildInfo.buildInfo();

    private static final String DOWNLOAD_PAGE = "https://fearmc.local/download";

    // =========================
    // NO CACHE NEEDED
    // =========================
    @Override
    public long getCacheTime() {
        return 0; // no cache, local only
    }

    // =========================
    // /ver OUTPUT
    // =========================
    @Override
    public Component getVersionMessage() {

        Component base = text(
            "This server is running FearMC version "
                + BUILD_INFO.asString(VERSION_SIMPLE),
            NamedTextColor.GRAY
        );

        Component status = text(
            "Thanks For Running my software",
            NamedTextColor.GREEN
        );

        return Component.textOfChildren(
            base,
            Component.newline(),
            status
        );
    }

    // =========================
    // STARTUP LOG
    // =========================
    public static void getUpdateStatusStartupMessage() {

        OptionalInt build = BUILD_INFO.buildNumber();

        if (build.isEmpty()) {
            System.out.println("[FearMC] Development build started");
        } else {
            System.out.println("[FearMC] Running build #" + build.getAsInt());
        }

        System.out.println("[FearMC] No external version checking enabled");
    }

    // =========================
    // UPDATE MESSAGE (DISABLED)
    // =========================
    private static Component getUpdateStatusMessage() {
        return text(
            "FearMC local mode - version check disabled",
            NamedTextColor.GREEN
        );
    }

    // =========================
    // DISABLED FUNCTIONS (NO API)
    // =========================
    private static int fetchDistanceFromSiteApi(int jenkinsBuild) {
        return 0;
    }

    private static int fetchDistanceFromGitHub(String branch, String hash) {
        return 0;
    }

    private static Optional<Object> fetchMinecraftVersionList() {
        return Optional.empty();
    }

    private Component getHistory() {
        return null;
    }
}
