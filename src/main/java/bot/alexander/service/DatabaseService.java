package bot.alexander.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import bot.alexander.Classes.ConnectionClasses.Config;

public class DatabaseService {
    private static Config.DbConfig cfg;

    public static void init(String configPath) throws Exception {
        Config conf = ConfigLoader.load(configPath);
        cfg = conf.db;
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(cfg.url, cfg.user, cfg.password);
    }
}