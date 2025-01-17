package io;

import java.util.Properties;

public class ConfigProperties {
    private static final String INTERFACE_PROPERTIES_FILE = "conf/interface.properties";
    private static final String DATABASE_PROPERTIES_FILE = "conf/database.properties";

    private static boolean dbCreate;
    private static String dbUrl;
    private static boolean dbReCreateTables;
    private static boolean dbLoadFromCSV;
    private static String dbDirCsvBalancesCSV;
    private static String dbDirCsvTransactionsCSV;
    private static String dbDirPasswordsCSV;
    private static String dbDirUsersDataCSV;

    private static boolean uiDarkMode;
    private static boolean uiFullScreen;

    public static void init() {
        Properties interfaceProperties = new Properties();
        Properties databaseProperties = new Properties();

        try {
            interfaceProperties.load(new java.io.FileInputStream(INTERFACE_PROPERTIES_FILE));
            databaseProperties.load(new java.io.FileInputStream(DATABASE_PROPERTIES_FILE));
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }

        dbCreate = Boolean.parseBoolean(databaseProperties.getProperty("db.create"));
        dbUrl = databaseProperties.getProperty("db.url");
        dbReCreateTables = Boolean.parseBoolean(databaseProperties.getProperty("db.reCreateTables"));
        dbLoadFromCSV = Boolean.parseBoolean(databaseProperties.getProperty("db.loadFromCSV"));
        dbDirCsvBalancesCSV = databaseProperties.getProperty("db.dir.csv.balances");
        dbDirCsvTransactionsCSV = databaseProperties.getProperty("db.dir.csv.transactions");
        dbDirPasswordsCSV = databaseProperties.getProperty("db.dir.csv.passwords");
        dbDirUsersDataCSV = databaseProperties.getProperty("db.dir.csv.usersData");

        uiDarkMode = Boolean.parseBoolean(interfaceProperties.getProperty("ui.darkMode"));
        uiFullScreen = Boolean.parseBoolean(interfaceProperties.getProperty("ui.fullScreen"));
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

    public static void setUiDarkMode(boolean uiDarkMode) {
        ConfigProperties.uiDarkMode = uiDarkMode;
    }
}
