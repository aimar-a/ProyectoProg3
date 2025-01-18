package io;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

//IAG: GitHub Copilot
//ADAPTADO: Autocompeltado
public class ConfigProperties {
    private static final Properties interfaceProperties = new Properties();
    private static final Properties databaseProperties = new Properties();

    private static final String INTERFACE_PROPERTIES_FILE = "conf/interface.properties";
    private static final String DATABASE_PROPERTIES_FILE = "conf/database.properties";
    private static final Logger logger = Logger.getLogger(ConfigProperties.class.getName());

    static {
        try {
            FileHandler fileHandler = new FileHandler("log/configProperties.log", true);
            fileHandler.setFormatter(new SimpleFormatter());
            logger.addHandler(fileHandler);
            logger.setLevel(Level.ALL);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void init() {
        try {
            interfaceProperties.load(new FileInputStream(INTERFACE_PROPERTIES_FILE));
            databaseProperties.load(new FileInputStream(DATABASE_PROPERTIES_FILE));
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error loading properties files", e);
        }

        logger.log(Level.INFO, "Configuration properties loaded successfully");
    }

    public static boolean isDbCreate() {
        return Boolean.parseBoolean(databaseProperties.getProperty("db.create"));
    }

    public static boolean isDbReCreateTables() {
        return Boolean.parseBoolean(databaseProperties.getProperty("db.reCreateTables"));
    }

    public static boolean isDbLoadFromCSV() {
        return Boolean.parseBoolean(databaseProperties.getProperty("db.loadFromCSV"));
    }

    public static String getDbDirCsvBalancesCSV() {
        return databaseProperties.getProperty("db.dir.csv.balances");
    }

    public static String getDbDirCsvTransactionsCSV() {
        return databaseProperties.getProperty("db.dir.csv.transactions");
    }

    public static String getDbDirPasswordsCSV() {
        return databaseProperties.getProperty("db.dir.csv.passwords");
    }

    public static String getDbDirUsersDataCSV() {
        return databaseProperties.getProperty("db.dir.csv.userData");
    }

    public static boolean isUiDarkMode() {
        return Boolean.parseBoolean(interfaceProperties.getProperty("ui.darkMode"));
    }

    public static boolean isUiFullScreen() {
        return Boolean.parseBoolean(interfaceProperties.getProperty("ui.fullScreen"));
    }

    public static String getDbUrl() {
        return resolveVariables(databaseProperties.getProperty("db.url"), databaseProperties);
    }

    public static String getDbDir() {
        return databaseProperties.getProperty("db.dir");
    }

    public static String getDbDriver() {
        return databaseProperties.getProperty("db.driver");
    }

    public static void setUiDarkMode(boolean uiDarkMode) {
        interfaceProperties.setProperty("ui.darkMode", String.valueOf(uiDarkMode));
        saveInterfaceProperties();
    }

    private static String resolveVariables(String value, Properties properties) {
        String result = value;
        for (String key : properties.stringPropertyNames()) {
            result = result.replace("${" + key + "}", properties.getProperty(key));
        }
        return result;
    }

    private static void saveInterfaceProperties() {
        try {
            interfaceProperties.store(new FileOutputStream(INTERFACE_PROPERTIES_FILE), null);
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error saving interface properties", e);
        }
    }

    private static void saveDatabaseProperties() {
        try {
            databaseProperties.store(new FileOutputStream(DATABASE_PROPERTIES_FILE), null);
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error saving database properties", e);
        }
    }
}
