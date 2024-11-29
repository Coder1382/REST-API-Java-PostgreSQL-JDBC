package rest.dao;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseConnector {
    public static Connection connector() {
        Connection connect = null;
        String db = null, driver = null, url = null, login = null, pwd = null;
        Properties props = new Properties();
        try(FileInputStream read = new FileInputStream(Paths.get("config.properties").toAbsolutePath().toString());) {
            props.load(read);
            db = props.getProperty("db");
            driver = props.getProperty("driver");
            url = props.getProperty("url");
            login = props.getProperty("login");
            pwd = props.getProperty("pwd");
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            Class.forName(driver);
            connect = DriverManager.getConnection(url + db, login, pwd);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return connect;
    }
}