/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package foodstore;

import foodstore.entities.Usuario;
import foodstore.enums.Rol;
import foodstore.service.UsuarioService;
import java.util.List;

/**
 *
 * @author usuario
 */
public class MenuUsuario {
    private UsuarioService usuarioService;
    private Consola lector;

    public MenuUsuario(UsuarioService usuarioService, Consola lector) {
        this.usuarioService = usuarioService;
        this.lector = lector;
    }

    public void mostrar() {
        boolean volver = false;
        while (!volver) {
            System.out.println("========Gestión de Usuarios========");
            System.out.println("1. Listar Usuarios");
            System.out.println("2. Crear Usuario");
            System.out.println("3. Editar Usuario");
            System.out.println("4. Eliminar Usuario");
            System.out.println("0. Volver al menu Principal");

            int opcion = lector.leerEntero("Seleccione opcion: ");

            try {
                switch (opcion) {
                    case 1 -> listar();
                    case 2 -> crear();
                    case 3 -> editar();
                    case 4 -> eliminar();
                    case 0 -> volver = true;
                    default -> System.out.println("Opcion invalida.\n");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage() + "\n");
            }
        }
    }

    private void listar() throws Exception {
        List<Usuario> usuarios = usuarioService.listar();
        System.out.println("\n=====LISTA DE USUARIOS=====");
        if (usuarios.isEmpty()) {
            System.out.println("No hay usuarios cargados.\n");
            return;
        }
        usuarios.forEach(u -> System.out.printf(
            "ID: %d | %s %s | Mail: %s | Celular: %s | Rol: %s%n",
            u.getId(), u.getNombre(), u.getApellido(), u.getMail(), u.getCelular(), u.getRol()
        ));
    }

    private void crear() throws Exception {
        Usuario nuevo = new Usuario();
        nuevo.setNombre(lector.leerTexto("Nombre: "));
        nuevo.setApellido(lector.leerTexto("Apellido: "));
        nuevo.setMail(lector.leerTexto("Mail: "));
        nuevo.setCelular(lector.leerTexto("Celular: "));
        nuevo.setContrasenia(lector.leerTexto("Contrasenia: "));
        Integer rolNum = lector.leerEntero("Rol (1 = ADMIN, 2 = USUARIO)");
        nuevo.setRol(Rol.rolNumero(rolNum));
        

        usuarioService.crear(nuevo);
        System.out.println("Usuario creado exitosamente.\n");
    }

    private void editar() throws Exception {
        long id = lector.leerEntero("ID del usuario a editar: ");
        Usuario actual = usuarioService.leer(id).orElseThrow(
            () -> new Exception("Usuario no encontrado")
        );

        actual.setNombre(lector.leerTexto("Nuevo nombre (" + actual.getNombre() + "): "));
        actual.setApellido(lector.leerTexto("Nuevo apellido (" + actual.getApellido() + "): "));
        actual.setMail(lector.leerTexto("Nuevo mail (" + actual.getMail() + "): "));
        actual.setCelular(lector.leerTexto("Nuevo celular (" + actual.getCelular() + "): "));
        actual.setContrasenia(lector.leerTexto("Nueva contraseña (" + actual.getContrasenia() + "): "));
        String rolStr = lector.leerTexto("Nuevo rol (" + actual.getRol() + "): ");
        actual.setRol(Rol.valueOf(rolStr.toUpperCase()));

        usuarioService.actualizar(actual);
        System.out.println("Usuario actualizado.\n");
    }

    private void eliminar() throws Exception {
        long id = lector.leerEntero("ID del usuario a eliminar: ");
        usuarioService.eliminar(id);
        System.out.println("Usuario eliminado.\n");
    }
}
