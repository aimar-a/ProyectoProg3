package io;

import java.util.Properties;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

//IAG: ChatGPT y GitHub Copilot
//ADAPTADO: Se ha modificado el código original para adaptarlo a las necesidades del proyecto y añadir funcionalidades adicionales.
public class ConfigProperties {
    private static final String INTERFACE_PROPERTIES_FILE = "conf/interface.properties";
    private static final String DATABASE_PROPERTIES_FILE = "conf/database.properties";
    private static final Logger logger = Logger.getLogger(ConfigProperties.class.getName());

    private static boolean dbCreate;
    private static String dbUrl;
    private static String dbDir;
    private static String dbDriver;
    private static boolean dbReCreateTables;
    private static boolean dbLoadFromCSV;
    private static String dbDirCsvBalancesCSV;
    private static String dbDirCsvTransactionsCSV;
    private static String dbDirPasswordsCSV;
    private static String dbDirUsersDataCSV;

    private static boolean uiDarkMode;
    private static boolean uiFullScreen;

    static {
        try {
            FileHandler fileHandler = new FileHandler("log/configProperties.log", true);
            fileHandler.setFormatter(new SimpleFormatter());
            logger.addHandler(fileHandler);
            logger.setLevel(Level.ALL);
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }

    public static void init() {
        Properties interfaceProperties = new Properties();
        Properties databaseProperties = new Properties();

        try {
            interfaceProperties.load(new java.io.FileInputStream(INTERFACE_PROPERTIES_FILE));
            databaseProperties.load(new java.io.FileInputStream(DATABASE_PROPERTIES_FILE));
        } catch (java.io.IOException e) {
            logger.log(Level.SEVERE, "Error loading properties files", e);
        }

        dbCreate = Boolean.parseBoolean(databaseProperties.getProperty("db.create"));
        dbUrl = resolveVariables(databaseProperties.getProperty("db.url"), databaseProperties);
        dbDir = databaseProperties.getProperty("db.dir");
        dbDriver = databaseProperties.getProperty("db.driver");
        dbReCreateTables = Boolean.parseBoolean(databaseProperties.getProperty("db.reCreateTables"));
        dbLoadFromCSV = Boolean.parseBoolean(databaseProperties.getProperty("db.loadFromCSV"));
        dbDirCsvBalancesCSV = databaseProperties.getProperty("db.dir.csv.balances");
        dbDirCsvTransactionsCSV = databaseProperties.getProperty("db.dir.csv.transactions");
        dbDirPasswordsCSV = databaseProperties.getProperty("db.dir.csv.passwords");
        dbDirUsersDataCSV = databaseProperties.getProperty("db.dir.csv.userData");

        uiDarkMode = Boolean.parseBoolean(interfaceProperties.getProperty("ui.darkMode"));
        uiFullScreen = Boolean.parseBoolean(interfaceProperties.getProperty("ui.fullScreen"));

        logger.log(Level.INFO, "Configuration properties loaded successfully");
    }

    public static boolean isDbCreate() {
        return dbCreate;
    }

    public static boolean isDbReCreateTables() {
        return dbReCreateTables;
    }

    public static boolean isDbLoadFromCSV() {
        return dbLoadFromCSV;
    }

    public static String getDbDirCsvBalancesCSV() {
        return dbDirCsvBalancesCSV;
    }

    public static String getDbDirCsvTransactionsCSV() {
        return dbDirCsvTransactionsCSV;
    }

    public static String getDbDirPasswordsCSV() {
        return dbDirPasswordsCSV;
    }

    public static String getDbDirUsersDataCSV() {
        return dbDirUsersDataCSV;
    }

    public static boolean isUiDarkMode() {
        return uiDarkMode;
    }

    public static boolean isUiFullScreen() {
        return uiFullScreen;
    }

    public static String getDbUrl() {
        return dbUrl;
    }

    public static String getDbDir() {
        return dbDir;
    }

    public static String getDbDriver() {
        return dbDriver;
    }

    public static void setUiDarkMode(boolean uiDarkMode) {
        ConfigProperties.uiDarkMode = uiDarkMode;
    }

    private static String resolveVariables(String value, Properties properties) {
        String result = value;
        for (String key : properties.stringPropertyNames()) {
            result = result.replace("${" + key + "}", properties.getProperty(key));
        }
        return result;
    }
}
