/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package foodstore.enums;

/**
 *
 * @author usuario
 */
public enum Rol {
    ADMIN(1), USUARIO(2);
    
    private final Integer numero;

    Rol(Integer numero) {
        this.numero = numero;
    }

    public Integer getNumero() {
        return numero;
    }
    
    public static Rol rolNumero(Integer numero ) throws Exception{
        switch (numero){
            case 1: return ADMIN;
            case 2: return USUARIO;
            default: throw new Exception("Error en el rol, elegir entre 1 y 2");
        }
        
    }
}
