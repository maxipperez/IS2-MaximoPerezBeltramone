package com.ejercicio4.mvc.controllers;

import com.ejercicio4.mvc.exceptions.ErrorService;
import com.ejercicio4.mvc.models.Cliente;
import com.ejercicio4.mvc.repositories.ClienteRepository;
import com.ejercicio4.mvc.services.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Controlador de Cliente, encargado de intermediar entre la vista Thymeleaf y la lógica del negocio.
 * Mismas reglas y conceptos aplican aquí sobre las anotaciones @Controller, @GetMapping, @PostMapping.
 */
@Controller
@RequestMapping("/clientes")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private ClienteRepository clienteRepository;

    @GetMapping("/listar")
    public String listarClientes(ModelMap modelo) {
        List<Cliente> activos = clienteService.listarClientesActivos();
        modelo.addAttribute("clientes", activos);
        return "clientes_list"; 
    }

    @GetMapping("/crear")
    public String formularioCreacion() {
        return "cliente_form"; 
    }

    @PostMapping("/guardar")
    public String guardarCliente(@RequestParam int documento, 
                                 @RequestParam String nombre, 
                                 @RequestParam String apellido, 
                                 @RequestParam String correo, 
                                 @RequestParam String password, 
                                 ModelMap modelo) {
        try {
            // Llama al servicio, si no cumple las reglas de negocio saltará ErrorService
            clienteService.crearCliente(documento, nombre, apellido, correo, password);
            return "redirect:/clientes/listar"; 
            
        } catch (ErrorService ex) {
            // El catch atrapa el error y lo inyecta al HTML mediante ModelMap
            modelo.put("error", ex.getMessage());
            return "cliente_form"; 
        }
    }

    @GetMapping("/modificar/{id}")
    public String formularioModificacion(@PathVariable String id, ModelMap modelo) {
        Optional<Cliente> respuesta = clienteRepository.findById(id);
        
        if (respuesta.isPresent()) {
            modelo.addAttribute("cliente", respuesta.get());
            return "cliente_form_edit";
        }
        return "redirect:/clientes/listar";
    }

    @PostMapping("/actualizar")
    public String actualizarCliente(@RequestParam String id, 
                                    @RequestParam String nombre, 
                                    @RequestParam String apellido, 
                                    ModelMap modelo) {
        try {
            clienteService.modificarCliente(id, nombre, apellido);
            return "redirect:/clientes/listar";
            
        } catch (ErrorService ex) {
            modelo.put("error", ex.getMessage());
            
            // Volvemos a pasar la entidad al modelo para recargar los datos
            Optional<Cliente> rep = clienteRepository.findById(id);
            if(rep.isPresent()) {
                modelo.addAttribute("cliente", rep.get());
            }
            
            return "cliente_form_edit";
        }
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarCliente(@PathVariable String id) {
        try {
            clienteService.eliminarCliente(id);
        } catch (ErrorService ex) {
            System.err.println(ex.getMessage());
        }
        return "redirect:/clientes/listar";
    }
}
