/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package foodstore.enums;

/**
 *
 * @author usuario
 */
public enum FormaPago {
   TARJETA(1),
   TRANSFERENCIA(2),
   EFECTIVO(3);
   
   private final Integer numero;

    private FormaPago(Integer numero) {
        this.numero = numero;
    }

    public Integer getNumero() {
        return numero;
    }

    
    
    public static FormaPago rolNumero(Integer numero ) throws Exception{
        switch (numero){
            case 1: return TARJETA;
            case 2: return TRANSFERENCIA;
            case 3: return EFECTIVO;
            default: throw new Exception("Error en el rol, elegir entre 1 y 2");
        }
        
    }
}
