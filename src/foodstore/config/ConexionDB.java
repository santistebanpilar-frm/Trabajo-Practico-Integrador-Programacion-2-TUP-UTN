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
    private static String url = "jdbc:mysql://localhost:3306/foodstore";
    private static String user = "root" ;
    private static String password = "" ;
    private String ruta;

    public ConexionDB(String ruta) {
        this.ruta = ruta;
    }
    
    
    public static Connection getConnection() throws SQLException{
        return DriverManager.getConnection(url, user, password);
    }
}
