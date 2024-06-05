package org.hmk;

import org.snakeyaml.engine.v2.api.Load;
import org.snakeyaml.engine.v2.api.LoadSettings;

import java.io.InputStream;
import java.util.Map;

public class ConfigLoader {
    public static Config load() {
        LoadSettings settings = LoadSettings.builder().build();
        Load load = new Load(settings);

        try (InputStream inputStream = ConfigLoader.class
                .getClassLoader()
                .getResourceAsStream("config.yaml")) {
            Map<String, Object> configMap = (Map<String, Object>) load.loadFromInputStream(inputStream);

            // Convert to Config object manually
            return new Config(configMap);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
