/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package foodstore;

import foodstore.entities.DetallePedido;
import foodstore.entities.Pedido;
import foodstore.entities.Producto;
import foodstore.entities.Usuario;
import foodstore.enums.Estado;
import foodstore.enums.FormaPago;
import foodstore.service.DetallePedidoService;
import foodstore.service.PedidoService;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author usuario
 */
public class MenuPedido {
    private PedidoService pedidoService;
    private DetallePedidoService detalleService;
    private Consola lector;

    public MenuPedido(PedidoService pedidoService, DetallePedidoService detalleService, Consola lector) {
        this.pedidoService = pedidoService;
        this.detalleService = detalleService;
        this.lector = lector;
    }

    public void mostrar() {
        boolean volver = false;
        while (!volver) {
            lector.limpiarPantalla();
            System.out.println("========Gestión de Pedidos========");
            System.out.println("1. Listar Pedidos");
            System.out.println("2. Crear Pedido con Detalles");
            System.out.println("3. Editar Pedido");
            System.out.println("4. Eliminar Pedido");
            System.out.println("5. Listar Pedidos por Usuario");
            System.out.println("0. Volver al menú Principal");

            int opcion = lector.leerEntero("Seleccione opción: ");

            try {
                switch (opcion) {
                    case 1 -> listar();
                    case 2 -> crearConDetalles();
                    case 3 -> editar();
                    case 4 -> eliminar();
                    case 5-> listarPorUsuario();
                    case 0 -> volver = true;
                    default -> System.out.println("Opción inválida.\n");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage() + "\n");
            }
        }
    }

    private void listar() throws Exception {
        List<Pedido> pedidos = pedidoService.listar();
        System.out.println("\n=====LISTA DE PEDIDOS=====");
        if (pedidos.isEmpty()) {
            System.out.println("No hay pedidos cargados.\n");
            return;
        }
        pedidos.forEach(p -> System.out.printf(
            "ID: %d | Usuario: %d | Estado: %s | Pago: %s | Total: %.2f | Fecha: %s%n",
            p.getId(), p.getUsuario().getId(), p.getEstado(), p.getFormaPago(), p.getTotal(), p.getFecha()
        ));
    }
    private void listarPorUsuario() throws Exception {
    long idUsuario = lector.leerEntero("ID del usuario: ");
    List<Pedido> pedidos = pedidoService.listarPorUsuario(idUsuario);
    System.out.println("\n=====PEDIDOS DEL USUARIO " + idUsuario + "=====");
    if (pedidos.isEmpty()) {
        System.out.println("Este usuario no tiene pedidos.\n");
        return;
    }
    pedidos.forEach(p -> System.out.printf(
        "ID: %d | Estado: %s | Pago: %s | Total: %.2f | Fecha: %s%n",
        p.getId(), p.getEstado(), p.getFormaPago(), p.getTotal(), p.getFecha()
    ));
}

    private void crearConDetalles() throws Exception {
    Pedido nuevo = new Pedido();
    long idUsuario = lector.leerEntero("ID Usuario: ");
    Usuario usuario = new Usuario();
    usuario.setId(idUsuario);
    nuevo.setUsuario(usuario);

    List<DetallePedido> detallesSolicitados = new ArrayList<>(); // lista aparte

    boolean agregarMas = true;
    while (agregarMas) {
        long idProducto = lector.leerEntero("ID Producto: ");
        int cantidad = lector.leerEntero("Cantidad: ");
        DetallePedido detalle = new DetallePedido();
        Producto producto = new Producto();
        producto.setId(idProducto);
        detalle.setProducto(producto);
        detalle.setCantidad(cantidad);
        detalleService.prepararDetalle(detalle);
        detallesSolicitados.add(detalle); // se acumula en la lista, no en el pedido
        String continuar = lector.leerTexto("¿Agregar otro detalle? (S/N): ");
        agregarMas = continuar.equalsIgnoreCase("S");
    }
    // Ya no se llama nuevo.calcularTotal() — el Service lo hace internamente
    pedidoService.crearConDetalles(nuevo, detallesSolicitados); // se pasa la lista separada
    System.out.println("Pedido creado exitosamente.\n");
    }

    private void editar() throws Exception {
        long id = lector.leerEntero("ID del pedido a editar: ");
        Pedido actual = pedidoService.leer(id).orElseThrow(
            () -> new Exception("Pedido no encontrado")
        );

        System.out.println("Estado actual: " + actual.getEstado());
        String nuevoEstado = lector.leerTexto("Nuevo estado (ej: PENDIENTE, PAGADO, CANCELADO): ");
        actual.setEstado(Estado.valueOf(nuevoEstado.toUpperCase()));

        System.out.println("Forma de pago actual: " + actual.getFormaPago());
        String nuevaFormaPago = lector.leerTexto("Nueva forma de pago (EFECTIVO, TARJETA, TRANSFERENCIA): ");
        actual.setFormaPago(FormaPago.valueOf(nuevaFormaPago.toUpperCase()));

        String modificarDetalles = lector.leerTexto("¿Desea modificar detalles? (S/N): ");
        if (modificarDetalles.equalsIgnoreCase("S")) {
            long idDetalle = lector.leerEntero("ID del detalle a modificar: ");
            int nuevaCantidad = lector.leerEntero("Nueva cantidad: ");
            DetallePedido detalleModificado = new DetallePedido();
            detalleModificado.setId(idDetalle);
            detalleModificado.setCantidad(nuevaCantidad);
            pedidoService.modificarDetalle(actual.getId(), detalleModificado);
            
        }

        pedidoService.actualizar(actual);
        System.out.println("Pedido actualizado.\n");
    }

    private void eliminar() throws Exception {
        long id = lector.leerEntero("ID del pedido a eliminar: ");
        String confirmacion = lector.leerTexto("¿Confirma eliminación? (S/N): ");
        if (confirmacion.equalsIgnoreCase("S")) {
            pedidoService.eliminar(id);
            System.out.println("Pedido eliminado.\n");
        } else {
            System.out.println("Operación cancelada.\n");
        }
    }
}
