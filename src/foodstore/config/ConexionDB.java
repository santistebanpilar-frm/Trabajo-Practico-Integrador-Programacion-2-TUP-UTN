/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package foodstore.config;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
/**
 *
 * @author usuario
 */
public class ConexionDB {
    private static String url;
    private static String user;
    private static String password;
    
    public static void init(lecturaConfiguracion  configuracion){
        url = configuracion.get("db.url");
        user = configuracion.get("db.user");
        password = configuracion.get("db.password");
    }
    public static Connection getConnection() throws SQLException{
        return DriverManager.getConnection(url, user, password);
    }
}
