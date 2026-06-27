package config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.io.IOException;
import java.io.InputStream;

public class ConexionDB {

    private static ConexionDB instancia;

    private String url;
    private String user;
    private String password;

    private ConexionDB() {
        try {
            Properties props = new Properties();
            InputStream is = getClass().getClassLoader()
                    .getResourceAsStream("base.persistence");
            props.load(is);

            this.url = props.getProperty("db.url");
            this.user = props.getProperty("db.user");
            this.password = props.getProperty("db.password");

        } catch (IOException e) {
            System.out.println("Error al leer base.persistence: " + e.getMessage());
        }
    }

    public static ConexionDB getInstance() {
        if (instancia == null) {
            instancia = new ConexionDB();
        }
        return instancia;
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }
}
