package rest.database;

import rest.dao.DatabaseConnector;

import java.sql.Connection;
import java.sql.SQLException;

public class
DatabaseManager {
    static final Connection connect = DatabaseConnector.connector();

    public static void createDatabase() throws SQLException {
        connect.createStatement().executeUpdate("CREATE DATABASE IF NOT EXISTS goods");
    }

    public static void createFruitTable() throws SQLException {
        connect.createStatement().executeUpdate("CREATE TABLE IF NOT EXISTS fruit(id BIGSERIAL PRIMARY KEY, " +
                "name TEXT UNIQUE NOT NULL, price NUMERIC CHECK (price>0), sellers TEXT [])");
    }

    public static void createSellersTable() throws SQLException {
        connect.createStatement().executeUpdate("CREATE TABLE IF NOT EXISTS sellers(id BIGSERIAL PRIMARY KEY, " +
                "name TEXT UNIQUE NOT NULL, supplier TEXT, fruits TEXT [])");
    }

    public static void createSuppliersTable() throws SQLException {
        connect.createStatement().executeUpdate("CREATE TABLE IF NOT EXISTS suppliers(id BIGSERIAL PRIMARY KEY, " +
                "name TEXT UNIQUE NOT NULL, sellers TEXT [])");
    }


    public static void dropTable(String table) throws SQLException {
        connect.createStatement().executeUpdate("DROP TABLE IF EXISTS " + table);
    }

    public static void truncateTable(String table) throws SQLException {
        connect.createStatement().executeUpdate("TRUNCATE TABLE " + table + " RESTART IDENTITY");
    }

    public static void dropDatabase(String db) throws SQLException {
        connect.createStatement().executeUpdate("DROP DATABASE IF EXISTS goods");
    }
}
