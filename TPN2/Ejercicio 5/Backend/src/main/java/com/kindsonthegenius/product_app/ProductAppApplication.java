package com.kindsonthegenius.product_app;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ProductAppApplication {

    private static final String DATABASE_NAME = "productdb";

    public static void main(String[] args) {
        createDatabaseIfMissing();
        SpringApplication.run(ProductAppApplication.class, args);
    }

    private static void createDatabaseIfMissing() {
        Properties localConfig = new Properties();
        try (InputStream input = ProductAppApplication.class.getClassLoader()
                .getResourceAsStream("application-local.properties")) {
            if (input != null) {
                localConfig.load(input);
            }
        } catch (IOException exception) {
            throw new IllegalStateException("No se pudo leer la configuración local de MySQL.", exception);
        }

        String serverUrl = System.getenv().getOrDefault("DB_SERVER_URL",
                localConfig.getProperty("app.database.bootstrap-url", "jdbc:mysql://127.0.0.1:3306/"));
        String username = System.getenv().getOrDefault("DB_USERNAME",
                localConfig.getProperty("spring.datasource.username", "root"));
        String password = System.getenv().getOrDefault("DB_PASSWORD",
                localConfig.getProperty("spring.datasource.password", ""));

        try (Connection connection = DriverManager.getConnection(serverUrl, username, password);
                Statement statement = connection.createStatement()) {
            statement.execute("CREATE DATABASE IF NOT EXISTS " + DATABASE_NAME + " CHARACTER SET utf8mb4");
        } catch (SQLException exception) {
            throw new IllegalStateException(
                    "No se pudo crear la base productdb. Verificá que MySQL esté activo y las credenciales sean correctas.",
                    exception);
        }
    }
}