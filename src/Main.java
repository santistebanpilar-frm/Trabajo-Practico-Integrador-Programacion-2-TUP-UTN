
import java.sql.Connection;
import foodstore.config.ConexionDB;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Usuario
 */
public class Main {public static void main(String[] args) {
        
    try (Connection conexion = ConexionDB.getConnection()){
            System.out.println("-- CONEXION EXITOSA --");
        }catch (Exception e){
            System.out.println("ERROR AL CONECTARSE: " + e.getMessage());
        }
    
    }
    
}
