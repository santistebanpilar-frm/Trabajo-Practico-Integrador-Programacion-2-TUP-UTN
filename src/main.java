
import config.ConexionDB;
import dao.CategoriaDAO;
import entities.Categoria;
import exception.PersistenciaException;
import java.sql.Connection;
import java.sql.SQLException;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author pilarsg
 */
public class main {
    public static void main(String[] args) {
    try {
        Connection conn = ConexionDB.getInstance().getConnection();
        if (conn != null) {
            System.out.println("Conexión exitosa!");
        }
    } catch (SQLException e) {
        System.out.println("Error al conectar: " + e.getMessage());
    }
    CategoriaDAO categoriaDAO = new CategoriaDAO();

        // Armás el objeto a mano
        Categoria nueva = new Categoria("Pizzas", "Pizzas y empanadas");

        // Llamás directo al DAO
        try {
            Categoria guardada = categoriaDAO.crear(nueva);
            System.out.println("Categoría creada con id: " + guardada.getId());
            System.out.println(guardada);
        } catch (PersistenciaException e) {
            System.out.println("Error: " + e.getMessage());
        }
}
}
