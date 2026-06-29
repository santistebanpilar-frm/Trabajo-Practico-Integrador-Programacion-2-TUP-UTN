/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package foodstore;

import java.util.Scanner;

/**
 *
 * @author usuario
 */
public class Consola {
    private Scanner sc;

    public Consola(Scanner sc) {
        this.sc = sc;
    }
    public static void limpiarPantalla(){
        try{
            final String os = System.getProperty("os.name");
            
            if(os.contains("Windows")){
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            }else{
                new ProcessBuilder("clear").inheritIO().start().waitFor();
            }
        }catch(Exception e){
            for(int i = 0; i<20; i++){
            System.out.println();
        }
        }
    }
    
    public int leerEntero(String mensaje){
        System.out.println(mensaje);
        while(true){
            try{
                return Integer.parseInt(sc.nextLine().trim());
            }catch (NumberFormatException e){
                System.out.println("Ingrese un numero valido");
            }
        }
    }
    
    public double leerDouble(String mensaje){
        System.out.println(mensaje);
        while(true){
            try{
                return Double.parseDouble(sc.nextLine().trim());
            }catch(NumberFormatException e){
                System.out.println("Ingrese un numero decimal valido");
            }
        }
    }
    public String leerTexto(String mensaje){
            System.out.println(mensaje);
            return sc.nextLine();
        }
}
