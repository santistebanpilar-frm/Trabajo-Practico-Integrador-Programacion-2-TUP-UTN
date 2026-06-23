/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package foodstore.config;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 *
 * @author usuario
 */
public class lecturaConfiguracion {
    private Properties datos;
    
    public lecturaConfiguracion(String archivo)throws IOException{
        datos = new Properties();
        try(FileInputStream fis = new FileInputStream(archivo)){
            datos.load(fis);
        }
    }
    
    public String get(String clave){
        return datos.getProperty(clave);
    }
}
