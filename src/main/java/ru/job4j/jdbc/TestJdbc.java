package ru.job4j.jdbc;

import ru.job4j.io.Config;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Map;

public class TestJdbc {

    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        Map<String, String> config = new Config("app.properties").loadAndGetValues();

        Class.forName(config.get("hibernate.connection.driver_class"));
        String url = config.get("hibernate.connection.url");
        String login = config.get("hibernate.connection.username");
        String password = config.get("hibernate.connection.password");

        try (Connection connection = DriverManager.getConnection(url, login, password)) {
            DatabaseMetaData metaData = connection.getMetaData();
            System.out.println(metaData.getUserName());
            System.out.println(metaData.getURL());
        }
    }
}
