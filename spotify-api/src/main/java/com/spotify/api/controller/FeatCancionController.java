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
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;


import com.spotify.api.service.FeatCancionService;
import com.spotify.api.model.FeatCancion;

@RestController
@RequestMapping("/featCancion")
@Tag(name = "Colaboraciones (Feats)", description = "Control de participaciones de artistas invitados en las canciones")
public class FeatCancionController {
	@Autowired
	private FeatCancionService service;
	
	@GetMapping
	@Operation(summary = "Listar todos los registros de colaboraciones", description = "Devuelve el mapa completo de todas las participaciones cruzadas en la base de datos.")
	public List<FeatCancion> obtenerTodos(){
		return service.getAll();
	}
	
	@PostMapping("/{idArtista}/{nombreCancion}/{idFeat}")
	@Operation(summary = "Registrar una nueva colaboración (Feat)", description = "Añade un artista invitado a un track existente. Valida que tanto la canción original como el artista invitado existan.")
	public FeatCancion crear(@PathVariable Integer idArtista,@PathVariable String nombreCancion,@PathVariable Integer idFeat) {
		return service.crearFeat(idArtista, nombreCancion, idFeat);
	}
	
	@DeleteMapping("/{idArtista}/{nombreCancion}/{idFeat}")
	@Operation(summary = "Eliminar un artista invitado de una canción", description = "Remueve la relación de colaboración sin borrar la canción ni los artistas.")
	public void borrar(@PathVariable Integer idArtista,@PathVariable String nombreCancion,@PathVariable Integer idFeat) {
		service.borrarFeat(idArtista, nombreCancion, idFeat);
	}
	
	@GetMapping("/feat/{idFeat}")
	@Operation(summary = "Buscar canciones donde un artista participó como invitado", description = "Lista todas las canciones ajenas en las cuales el ID provisto figura bajo el rol de Feat.")
	public List<FeatCancion> buscarPorFeat(@PathVariable Integer idFeat){
		return service.featEnCanciones(idFeat);
	}
	
	@GetMapping("/cancion/{idArtista}/{nombreCancion}")
	@Operation(summary = "Listar artistas invitados de una canción", description = "Devuelve todos los músicos secundarios que colaboraron en un track específico.")
	public List<FeatCancion> buscarPorFeat(@PathVariable Integer idArtista,@PathVariable String nombreCancion){
		return service.featsDeCanciones(idArtista, nombreCancion);
	}
	
	@GetMapping("/buscar/{idArtista}/{nombreCancion}/{idFeat}")
	@Operation(summary = "Buscar una colaboración por su clave primaria triple", description = "Busca el registro exacto de un feat combinando ID de artista principal, nombre del track e ID del artista invitado.")
	public FeatCancion buscarPorId(@PathVariable Integer idArtista,@PathVariable String nombreCancion,@PathVariable Integer idFeat) {
		return service.buscarPorId(idArtista, nombreCancion, idFeat);
	}
	
	@GetMapping("/paginado")
	@Operation(summary = "Listar colaboraciones con paginación", description = "Permite paginar las relaciones de artistas invitados.")
	public Page<FeatCancion> getAllPaginado(
			 @RequestParam(defaultValue = "0") int page,
	         @RequestParam(defaultValue = "10") int size,
	         @RequestParam(defaultValue = "idFeat") String sort){
		 return service.listarConPaginacion(page, size, sort);
	}
}
