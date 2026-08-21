package com.ejercicio4.mvc.controllers;

import com.ejercicio4.mvc.exceptions.ErrorService;
import com.ejercicio4.mvc.models.Producto;
import com.ejercicio4.mvc.repositories.ProductoRepository;
import com.ejercicio4.mvc.services.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * ¿Cuál es el rol del Controlador (C) en la arquitectura MVC?
 * El Controlador actúa como el "director de orquesta" o intermediario.
 * Su responsabilidad EXCLUSIVA es:
 * 1. Recibir la petición HTTP del usuario (del navegador).
 * 2. Extraer los parámetros de la URL o del formulario.
 * 3. Llamar a la Capa de Servicio (donde realmente vive la lógica de negocio).
 * 4. Decidir qué Vista (HTML) devolver al usuario y pasarle los datos necesarios.
 * ¡NUNCA debe tener lógica de validación o cálculos de negocio aquí!
 * 
 * @Controller: Estereotipo de Spring que indica que esta clase es un controlador web.
 *              Registra los endpoints para que el DispatcherServlet (el enrutador principal de Spring) 
 *              sepa adónde enviar las peticiones de los usuarios.
 * 
 * @RequestMapping("/productos"): Configura una ruta base para todos los métodos. 
 *                                Ejemplo: localhost:8080/productos/listar
 */
@Controller
@RequestMapping("/productos")
public class ProductoController {

    /**
     * INYECCIÓN DE DEPENDENCIAS (Inversion of Control)
     * @Autowired: Le pide a Spring que busque en memoria un objeto ya instanciado de tipo ProductoService
     *             (que se creó automáticamente por tener @Service) y lo asigne a esta variable.
     *             Esto fomenta el bajo acoplamiento (no usamos "new ProductoService()").
     */
    @Autowired
    private ProductoService productoService;

    // Inyectamos el repositorio solo para consultas de lectura simple (como traer un producto por ID para la vista de edición).
    @Autowired
    private ProductoRepository productoRepository;

    /**
     * @GetMapping: Mapea una petición HTTP GET.
     *              GET se usa para SOLICITAR o LEER información, por lo que es ideal para cargar páginas.
     * 
     * ¿Cómo nos comunicamos con la Vista?
     * A través del objeto ModelMap (o Model). Es como un "bolso" de transporte donde el Controlador 
     * guarda variables (ej. la lista de productos) y Thymeleaf las "saca" para dibujarlas en el HTML.
     */
    @GetMapping("/listar")
    public String listarProductos(ModelMap modelo) {
        // Obtenemos solo los productos activos gracias a la lógica del Servicio
        List<Producto> activos = productoService.listarProductosActivos();
        
        // Colocamos la lista en el ModelMap con la llave "productos"
        modelo.addAttribute("productos", activos);
        
        // Retornamos el nombre de la plantilla HTML de Thymeleaf (sin el .html)
        return "productos_list"; 
    }

    /**
     * Muestra el formulario vacío para crear un nuevo producto.
     */
    @GetMapping("/crear")
    public String mostrarFormularioCreacion() {
        return "producto_form"; 
    }

    /**
     * @PostMapping: Mapea una petición HTTP POST.
     *               POST se usa para ENVIAR datos al servidor. A diferencia de GET, 
     *               los datos viajan de forma más segura (ocultos en el cuerpo de la petición).
     * 
     * @RequestParam: Extrae los valores tipeados por el usuario en los <input> del formulario HTML.
     *                El nombre en String debe coincidir EXACTAMENTE con el atributo 'name' del HTML 
     *                (ej. <input type="text" name="nombre" />).
     */
    @PostMapping("/guardar")
    public String guardarProducto(@RequestParam String nombre, 
                                  @RequestParam double precio, 
                                  @RequestParam int stock, 
                                  ModelMap modelo) {
        try {
            // Intentamos ejecutar el ABM en el Servicio
            productoService.crearProducto(nombre, precio, stock);
            
            // Si sale bien, redirigimos a la ruta listar para ver la tabla actualizada.
            // Usar 'redirect:' evita que se reenvíe el formulario si el usuario refresca la página (F5).
            return "redirect:/productos/listar"; 
            
        } catch (ErrorService ex) {
            // CAPTURAMOS LA EXCEPCIÓN: Si el Servicio arroja ErrorService (ej. nombre nulo)
            // Agregamos el mensaje de error al ModelMap para que Thymeleaf lo muestre en una alerta.
            modelo.put("error", ex.getMessage());
            
            // Recargamos el formulario (esta vez sin 'redirect:' para mantener los datos del modelo).
            return "producto_form"; 
        }
    }

    /**
     * Muestra el formulario PRE-CARGADO con los datos del producto a EDITAR.
     * @PathVariable: Extrae una variable dinámica incrustada directamente en la URL (ej. /modificar/abc-123).
     */
    @GetMapping("/modificar/{id}")
    public String mostrarFormularioModificacion(@PathVariable String id, ModelMap modelo) {
        Optional<Producto> respuesta = productoRepository.findById(id);
        
        if (respuesta.isPresent()) {
            // Pasamos el producto entero al modelo para rellenar los valores predeterminados (value="") de los <input>
            modelo.addAttribute("producto", respuesta.get());
            return "producto_form_edit";
        } else {
            return "redirect:/productos/listar";
        }
    }

    /**
     * Recibe los datos del formulario de edición y actualiza la Base de Datos.
     */
    @PostMapping("/actualizar")
    public String actualizarProducto(@RequestParam String id, 
                                     @RequestParam String nombre, 
                                     @RequestParam double precio, 
                                     @RequestParam int stock, 
                                     ModelMap modelo) {
        try {
            productoService.modificarProducto(id, nombre, precio, stock);
            return "redirect:/productos/listar";
            
        } catch (ErrorService ex) {
            // Manejo de excepciones en la edición
            modelo.put("error", ex.getMessage());
            
            // Reinyectamos el producto en el modelo para no perder los datos del formulario al recargar
            Optional<Producto> rep = productoRepository.findById(id);
            if(rep.isPresent()) {
                modelo.addAttribute("producto", rep.get());
            }
            return "producto_form_edit";
        }
    }

    /**
     * Endpoint para dar de BAJA LÓGICA a un producto usando el enlace de "Eliminar" en la tabla.
     * Normalmente debería ser POST/DELETE por REST, pero en aplicaciones web MVC clásicas 
     * se suele hacer con GET desde un enlace <a>.
     */
    @GetMapping("/eliminar/{id}")
    public String eliminarProducto(@PathVariable String id) {
        try {
            // Delega la responsabilidad del borrado lógico al servicio
            productoService.eliminarProducto(id);
        } catch (ErrorService ex) {
            // En caso de error, podríamos usar Flash Attributes para mandar mensajes a redirect
            System.err.println("Ocurrió un error al intentar eliminar: " + ex.getMessage());
        }
        return "redirect:/productos/listar";
    }
}
