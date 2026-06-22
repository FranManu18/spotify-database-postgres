package com.spotify.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.spotify.api.model.ListaCancion;
import com.spotify.api.service.ListaCancionService;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/listaCancion")
@Tag(name = "Contenido de Playlists", description = "Operaciones para añadir, quitar y listar las canciones dentro de las listas de reproducción")
public class ListaCancionController {
	@Autowired
	private ListaCancionService service;
	
	@GetMapping
	@Operation(summary = "Listar el mapeo global de canciones en listas", description = "Muestra de forma masiva qué canciones están indexadas en qué playlists.")
	public List<ListaCancion>obtenerTodas(){
		return service.getAll();
	}
	
	@PostMapping("/{idLista}/{idUsuario}/{nombreCancion}/{idArtista}")
	@Operation(summary = "Añadir una canción a una playlist", description = "Inserta un track dentro de una playlist determinada. Requiere validar la existencia de la canción y la playlist (Clave foránea de 4 campos).")
	public ListaCancion crearListaCancion(@PathVariable Integer idLista,@PathVariable Integer idUsuario,@PathVariable String nombreCancion,@PathVariable Integer idArtista) {
		return service.crear(idLista,idUsuario,nombreCancion,idArtista);
	}
	
	@DeleteMapping("/{idLista}/{idUsuario}/{nombreCancion}/{idArtista}")
	@Operation(summary = "Quitar una canción de una playlist", description = "Remueve un track específico de la lista de reproducción indicada utilizando su clave compuesta.")
	public void borrarListaCancion(@PathVariable Integer idLista,@PathVariable Integer idUsuario,@PathVariable String nombreCancion,@PathVariable Integer idArtista) {
		service.borrar(idLista, idUsuario, nombreCancion, idArtista);
	}
	
	@GetMapping("/cancion/{nombreCancion}/{idArtista}")
	@Operation(summary = "Buscar en qué playlists aparece una canción", description = "Devuelve todas las listas de reproducción que tienen agregado este track específico.")
	public List<ListaCancion> buscarPorCancion(@PathVariable String nombreCancion,@PathVariable Integer idArtista){
		return service.cancionEnListas(nombreCancion, idArtista);
	}
	
	@GetMapping("/lista/{idLista}/{idUsuario}")
	@Operation(summary = "Listar canciones de una playlist", description = "Obtiene todo el contenido (catálogo de tracks) almacenado dentro de la playlist especificada.")
	public List<ListaCancion> buscarPorLista(@PathVariable Integer idLista,@PathVariable Integer idUsuario){
		return service.listasEnListas(idLista, idUsuario);
	}
	
	@GetMapping("/{idLista}/{idUsuario}/{nombreCancion}/{idArtista}")
	@Operation(summary = "Buscar ítem exacto de playlist", description = "Recupera un registro puntual usando la PK de 4 atributos: idLista, idUsuario, nombreCancion, idArtista.")
	public ListaCancion buscarPorId(@PathVariable Integer idLista,@PathVariable Integer idUsuario,@PathVariable String nombreCancion,@PathVariable Integer idArtista) {
		return service.buscarPorId(idLista, idUsuario, nombreCancion, idArtista);
	}
		
	@GetMapping("/paginado")
	@Operation(summary = "Listar contenidos de playlists de forma paginada", description = "Navega el listado de tracks en playlists mediante paginación.")
	 public Page<ListaCancion> getAllPaginado(
			 @RequestParam(defaultValue = "0") int page,
	         @RequestParam(defaultValue = "10") int size,
	         @RequestParam(defaultValue = "idUsuario") String sort){
		 return service.listarConPaginacion(page, size, sort);
	 }
}
