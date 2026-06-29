/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
import java.util.Scanner;
/**
 *
 * @author pilarsg
 */
public class Consola {
    private Consola(){}
    public static Long leerLong(Scanner scan, String mensaje){
        while(true){
            System.out.print(mensaje);
            String linea = scan.nextLine().trim();
            if (linea.isEmpty()){
                System.out.println("El campo no puede quedar vacio. Intente nuevamente");
                continue;
            }
            try {
                return Long.parseLong(linea);
            } catch (NumberFormatException e){
                System.out.println("Valor invalido, debe ingresar un NUMERO ENTERO");
            }
        }
    }
    public static Integer leerInt(Scanner scan, String mensaje){
        while(true){
            System.out.print(mensaje);
            String linea = scan.nextLine().trim();
            if (linea.isEmpty()){
                System.out.println("El campo no puede quedar vacio. Intente nuevamente");
                continue;
            }
            try {
                return Integer.parseInt(linea);
            } catch (NumberFormatException e){
                System.out.println("Valor invalido, debe ingresar un NUMERO ENTERO");
            }
        }
    }
    public static Double leerDouble(Scanner scan, String mensaje){
        while(true){
            System.out.print(mensaje);
            String linea = scan.nextLine().trim();
            if (linea.isEmpty()){
                System.out.println("El campo no puede quedar vacio. Intente nuevamente");
                continue;
            }
            try {
                return Double.parseDouble(linea.replace(",","."));
            } catch (NumberFormatException e){
                System.out.println("Valor invalido, debe ingresar un NUMERO DECIMAL");
            }
        }
    }
    public static String leerString(Scanner scan, String mensaje){
        while(true){
            System.out.print(mensaje);
            String linea = scan.nextLine().trim();
            if (!linea.isEmpty()){
                return linea;
            }
            System.out.println("El campo NO puede qquedar vacio, intente nuevamente");
        }
    }
    
    public static boolean confirmacion(Scanner scan, String mensaje){
        while(true){
            System.out.print(mensaje+" (S/N: )");
            String respuesta = scan.nextLine().trim().toUpperCase();
            if(respuesta.equals("S")) return true;
            if(respuesta.equals("N")) return false;
            System.out.println("Respuesta invalida. Solo puede ingresar S o N");
        }
    }
    public static int leerOpcionMenu(Scanner scan){
        System.out.print("Seleccione: ");
            String linea = scan.nextLine().trim();
            try {
                return Integer.parseInt(linea);
            } catch (NumberFormatException e){
                return -1;
            }
    }
    public static String leerVacio(Scanner scan, String mensaje){
        System.out.println(mensaje);
        String linea = scan.nextLine().trim();
        return linea.isEmpty() ? null : linea;
    }
}
