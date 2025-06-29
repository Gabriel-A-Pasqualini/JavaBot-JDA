package bot.alexander.service;

import com.google.gson.Gson;
import bot.alexander.Classes.ConnectionClasses.Config;
import java.io.FileReader;

public class ConfigLoader {
    private static Config config;

    public static Config load(String path) throws Exception {
        if (config == null) {
            try (FileReader reader = new FileReader(path)) {
                config = new Gson().fromJson(reader, Config.class);
            }
        }
        return config;
    }
}