/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package foodstore.exception;

import java.sql.SQLException;

/**
 *
 * @author Usuario
 */
public class BaseDeDatosException extends SQLException{

    public BaseDeDatosException(String mensaje) {
        super(mensaje);
    }
    
}
