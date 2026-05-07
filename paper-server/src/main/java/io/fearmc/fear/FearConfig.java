package io.fearmc.fear;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import org.yaml.snakeyaml.Yaml;
import java.util.Map;

public class FearConfig {

    private static File file;
    private static Map<String, Object> data;

    public static void init(File dataFolder) {
        try {
            if (!dataFolder.exists()) {
                dataFolder.mkdirs();
            }

            file = new File(dataFolder, "fear-config.yml");

            if (!file.exists()) {
                InputStream in = FearConfig.class.getResourceAsStream("/fear-config.yml");
                if (in != null) {
                    Files.copy(in, file.toPath());
                } else {
                    String defaultYaml =
                        "stop-password: false\n" +
                            "stop-password-value: imthecreatoroffear\n";

                    Files.write(file.toPath(), defaultYaml.getBytes());
                }
            }

            Yaml yaml = new Yaml();
            data = yaml.load(Files.newInputStream(file.toPath()));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean stopPasswordEnabled() {
        return (boolean) data.getOrDefault("stop-password", false);
    }

    public static String getStopPassword() {
        return (String) data.getOrDefault("stop-password-value", "imthecreatoroffear");
    }
}
