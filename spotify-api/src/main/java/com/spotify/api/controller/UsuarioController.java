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

import com.spotify.api.model.Artista;
import com.spotify.api.model.Usuario;
import com.spotify.api.service.UsuarioService;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {
	@Autowired
	private UsuarioService service;
	
	@GetMapping
	public List<Usuario> obtenerTodos(){
		return service.listarTodos();
	}
	
	@PostMapping
	public Usuario crearUsuario(@RequestBody Usuario nuevoUsuario) {
		return service.guardar(nuevoUsuario);
	}
	
	@PutMapping("/{id}")
	public Usuario actualizar(@RequestBody Usuario usuarioActualizado,@PathVariable Integer id) {
		return service.actualizarUsuario(usuarioActualizado, id);
	}
	
	@DeleteMapping("/{id}")
	public void borrarUsuarioPorId(@PathVariable Integer id) {
		service.borrarPorId(id);
	}
	
	@GetMapping("/buscar")
	public Usuario buscarPorNombreParcial(@RequestParam String nombre) {
		return service.buscarPorNombre(nombre);
	}
	
	@GetMapping("/{id}")
	public Usuario buscarPorId(@PathVariable Integer id) {
		return service.buscarPorId(id);
	}
	
	@GetMapping("/paginado")
	   public Page<Usuario> getAllPaginado(
	           @RequestParam(defaultValue = "0") int page,
	           @RequestParam(defaultValue = "10") int size,
	           @RequestParam(defaultValue = "id") String sort) {
	       return service.listarConPaginacion(page, size, sort);
	   }
}
