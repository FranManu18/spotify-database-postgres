package com.spotify.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;

import com.spotify.api.model.Usuario;
import com.spotify.api.service.UsuarioService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/usuario")
@Tag(name = "Usuarios", description = "Gestión de los perfiles de usuarios oyentes y sus cuentas")
public class UsuarioController {
	@Autowired
	private UsuarioService service;
	
	@GetMapping
	@Operation(summary = "Listar todos los usuarios", description = "Devuelve el listado completo de los usuarios registrados.")
	public List<Usuario> obtenerTodos(){
		return service.listarTodos();
	}
	
	@PostMapping
	@Operation(summary = "Registrar un usuario", description = "Crea una nueva cuenta de oyente en el sistema.")
	public Usuario crearUsuario(@Valid @RequestBody Usuario nuevoUsuario) {
		return service.guardar(nuevoUsuario);
	}
	
	@PutMapping("/{id}")
	@Operation(summary = "Actualizar datos del usuario", description = "Modifica los datos personales del usuario basándose en su ID único.")
	public Usuario actualizar(@Valid @RequestBody Usuario usuarioActualizado,@PathVariable Integer id) {
		return service.actualizarUsuario(usuarioActualizado, id);
	}
	
	@DeleteMapping("/{id}")
	@Operation(summary = "Eliminar un usuario por ID", description = "Da de baja la cuenta del usuario del sistema.")
	public void borrarUsuarioPorId(@PathVariable Integer id) {
		service.borrarPorId(id);
	}
	
	@GetMapping("/buscar")
	@Operation(summary = "Buscar usuario por nombre", description = "Busca perfiles de usuarios cuyo nombre coincida con el criterio de búsqueda.")
	public Usuario buscarPorNombreParcial(@RequestParam String nombre) {
		return service.buscarPorNombre(nombre);
	}
	
	@GetMapping("/{id}")
	@Operation(summary = "Buscar usuario por ID", description = "Obtiene los detalles del perfil de un usuario por su identificador numérico.")
	public Usuario buscarPorId(@PathVariable Integer id) {
		return service.buscarPorId(id);
	}
	
	@GetMapping("/paginado")
	@Operation(summary = "Listar usuarios con paginación", description = "Retorna un segmento ordenado y paginado de los usuarios de la base de datos.")
	   public Page<Usuario> getAllPaginado(
	           @RequestParam(defaultValue = "0") int page,
	           @RequestParam(defaultValue = "10") int size,
	           @RequestParam(defaultValue = "id") String sort) {
	       return service.listarConPaginacion(page, size, sort);
	   }
}
