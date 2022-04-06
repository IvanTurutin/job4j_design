package ru.job4j.jdbc;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.*;
import java.util.Properties;
import java.util.StringJoiner;

public class TableEditor implements AutoCloseable {

    private Connection connection;

    private Properties properties;

    public TableEditor(Properties properties) throws ClassNotFoundException, SQLException {
        this.properties = properties;
        initConnection();
    }

    private void initConnection() throws ClassNotFoundException, SQLException {
        Class.forName(properties.getProperty("hibernate.connection.driver_class"));
        String url = properties.getProperty("hibernate.connection.url");
        String login = properties.getProperty("hibernate.connection.username");
        String password = properties.getProperty("hibernate.connection.password");
        connection = DriverManager.getConnection(url, login, password);
    }

    public void createTable(String tableName) throws Exception {
        String query = String.format("create table if not exists %s ();", tableName);
        execute(query);
    }

    public void dropTable(String tableName) throws Exception {
        /**
         * String query = String.format("DROP TABLE %s CASCADE;", tableName);
         */
        String query = String.format("""
                        DO
                        $$
                        BEGIN
                        DROP TABLE %s CASCADE;
                        EXCEPTION
                        WHEN undefined_table THEN
                        END;
                        $$;""",
                tableName);
        execute(query);
    }

    public void addColumn(String tableName, String columnName, String type) throws Exception {
        String query = String.format(
                "ALTER TABLE %s ADD COLUMN if not exists %s %s;",
                tableName, columnName, type);
        execute(query);
    }

    public void dropColumn(String tableName, String columnName) throws Exception {
        /**
         * String query = String.format("ALTER TABLE %s DROP COLUMN %s;", tableName, columnName);
         */
        String query = String.format("""
                        DO
                        $$
                        BEGIN
                        ALTER TABLE %s
                        DROP COLUMN %s;
                        EXCEPTION
                        WHEN undefined_column THEN
                        END;
                        $$;""",
                tableName, columnName);

        execute(query);
    }

    public void renameColumn(String tableName, String columnName, String newColumnName) throws Exception {
        /**
         * String query = String.format("ALTER TABLE %s RENAME COLUMN %s TO %s;", tableName, columnName, newColumnName);
         */
        String query = String.format("""
                        DO
                        $$
                        BEGIN
                        ALTER TABLE %s
                        RENAME COLUMN %s TO %s;
                        EXCEPTION
                        WHEN undefined_column THEN
                        END;
                        $$;""",
                tableName, columnName, newColumnName);
        execute(query);
    }

    public static String getTableScheme(Connection connection, String tableName) throws Exception {
        var rowSeparator = "-".repeat(30).concat(System.lineSeparator());
        var header = String.format("%-15s|%-15s%n", "NAME", "TYPE");
        var buffer = new StringJoiner(rowSeparator, rowSeparator, rowSeparator);
        buffer.add(header);
        try (var statement = connection.createStatement()) {
            var selection = statement.executeQuery(String.format(
                    "select * from %s limit 1", tableName
            ));
            var metaData = selection.getMetaData();
            for (int i = 1; i <= metaData.getColumnCount(); i++) {
                buffer.add(String.format("%-15s|%-15s%n",
                        metaData.getColumnName(i), metaData.getColumnTypeName(i))
                );
            }
        }
        return buffer.toString();
    }

    private void execute(String query) throws Exception {
        try (Statement statement = connection.createStatement()) {
            statement.execute(query);
        }
    }

    public Connection getConnection() {
        return connection;
    }

    @Override
    public void close() throws Exception {
        if (connection != null) {
            connection.close();
        }
    }

    public static void main(String[] args) throws Exception {
        Properties properties = new Properties();
        properties.load(new BufferedReader(new FileReader("app.properties")));

        try (TableEditor tableEditor = new TableEditor(properties)) {
            tableEditor.createTable("test_table");
            System.out.println(TableEditor.getTableScheme(tableEditor.getConnection(), "test_table"));
            tableEditor.addColumn("test_table", "id", "serial primary key");
            System.out.println(TableEditor.getTableScheme(tableEditor.getConnection(), "test_table"));
            tableEditor.addColumn("test_table", "name", "varchar(255)");
            System.out.println(TableEditor.getTableScheme(tableEditor.getConnection(), "test_table"));
            tableEditor.addColumn("test_table", "age", "integer");
            System.out.println(TableEditor.getTableScheme(tableEditor.getConnection(), "test_table"));
            tableEditor.renameColumn("test_table", "age", "age2");
            System.out.println(TableEditor.getTableScheme(tableEditor.getConnection(), "test_table"));
            tableEditor.dropColumn("test_table", "age2");
            System.out.println(TableEditor.getTableScheme(tableEditor.getConnection(), "test_table"));
            tableEditor.dropTable("test_table");
        }
    }
}