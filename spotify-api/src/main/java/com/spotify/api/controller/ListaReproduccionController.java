package com.spotify.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

import com.spotify.api.model.ListaReproduccion;
import com.spotify.api.service.ListaReproduccionService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/lista")
@Tag(name = "Playlists", description = "Gestión de las listas de reproducción creadas por los usuarios oyentes")
public class ListaReproduccionController {
	@Autowired
	private ListaReproduccionService service;
	
	@GetMapping
	@Operation(summary = "Listar todas las playlists de la plataforma", description = "Devuelve todas las listas de reproducción existentes sin distinción de privacidad.")
	public List<ListaReproduccion> listarTodas(){
		return service.getAll();
	}
	
	@PostMapping("/{idUsuario}")
	@Operation(summary = "Crear una nueva playlist", description = "Crea una lista de reproducción vacía asignada al ID del usuario creador.")
	public ListaReproduccion crearLista(@PathVariable Integer idUsuario,@Valid @RequestBody ListaReproduccion lista) {
		return service.crearLista(idUsuario,lista);
	}
	
	@PutMapping("/{id}/{idUsuario}")
	@Operation(summary = "Modificar datos de una playlist", description = "Permite cambiar el nombre o el estado de visibilidad (pública/privada) de una lista mediante su clave compuesta.")
	public ListaReproduccion actualizarLista(@PathVariable Integer id,@PathVariable Integer idUsuario,@Valid@RequestBody ListaReproduccion listaActualizada) {
		return service.actualizar(id, idUsuario, listaActualizada);
	}
	
	@DeleteMapping("/{id}/{idUsuario}")
	@Operation(summary = "Eliminar una playlist", description = "Borra la playlist por completo, limpiando previamente los tracks que contenía.")
	public void borrarLista(@PathVariable Integer id,@PathVariable Integer idUsuario) {
		service.borrarLista(id, idUsuario);
	}
	
	@GetMapping("/buscar")
	@Operation(summary = "Buscar playlists por coincidencia de nombre", description = "Filtra listas de reproducción cuyo título contenga la palabra clave enviada.")
	public List<ListaReproduccion> buscarPorNombre(@RequestParam String nombre) {
		return service.buscarPorNombreParcial(nombre);
	}
	
	@GetMapping("/{id}/{idUsuario}")
	@Operation(summary = "Buscar playlist por ID compuesto", description = "Obtiene los detalles de una lista usando el ID de la lista y el ID del dueño.")
	public ListaReproduccion buscarPorId(@PathVariable Integer id,@PathVariable Integer idUsuario) {
		return service.buscarPorId(id, idUsuario);
	}
	
	@GetMapping("/paginado")
	@Operation(summary = "Listar playlists con paginación", description = "Entrega un fragmento paginado de todas las listas cargadas.")
	 public Page<ListaReproduccion> getAllPaginado(
			 @RequestParam(defaultValue = "0") int page,
	         @RequestParam(defaultValue = "10") int size,
	         @RequestParam(defaultValue = "id") String sort){
		 return service.listarConPaginacion(page, size, sort);
	 }
}
