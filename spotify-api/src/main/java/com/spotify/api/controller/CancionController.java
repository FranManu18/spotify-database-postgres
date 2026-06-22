package com.spotify.api.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.beans.factory.annotation.Autowired;
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

import com.spotify.api.model.Cancion;
import com.spotify.api.service.ArtistaService;
import com.spotify.api.service.CancionService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/cancion")
@Tag(name = "Canciones", description = "Gestión del catálogo de tracks, duraciones y estadísticas de reproducción")
public class CancionController {
	 @Autowired
	 private CancionService service;
	 
	 @Autowired
	 private ArtistaService serviceArtista;
	 
	 @GetMapping
	 @Operation(summary = "Listar todas las canciones", description = "Devuelve el catálogo global de tracks cargados en la base de datos.")
	 public List<Cancion>getAll(){
		 return service.listarTodas();
	 }
	 
	 @PostMapping
	 @Operation(summary = "Cargar una nueva canción", description = "Guarda la canción asociada a su artista y actualiza de inmediato el top-track del perfil del músico.")
	 public Cancion crearCancion(@Valid @RequestBody Cancion cancion) {
		 Cancion nuevaCancion=service.guardar(cancion);
		 serviceArtista.actualizarCancionMasEscuchada(cancion.getIdArtista());
		 return nuevaCancion;
	 }
	 
	 @PutMapping("/{idArtista}/{nombre}")
	 @Operation(summary = "Actualizar datos de una canción", description = "Modifica la información de un track basándose en su clave compuesta (idArtista y nombre original).")
	 public Cancion actualizar(@PathVariable Integer idArtista, @PathVariable String nombre,@Valid @RequestBody Cancion cancionEditada) {
		 return service.actualizar(idArtista, nombre, cancionEditada);
	 }
	 
	 @DeleteMapping("/{idArtista}/{nombre}")
	 @Operation(summary = "Eliminar una canción", description = "Borra un track del catálogo utilizando su identificador compuesto.")
	 public void eliminarCancion(@PathVariable Integer idArtista,@PathVariable String nombre) {
		 service.eliminarPorId(idArtista, nombre);
	 }
	 
	 @GetMapping("/{nombre}")
	 @Operation(summary = "Buscar canciones por título", description = "Realiza una búsqueda parcial de tracks que contengan la palabra ingresada en el nombre.")
	 public List<Cancion> buscarCanciones(@PathVariable String nombre){
		 return service.buscarPorNombreParcial(nombre);
	 }
	 
	 @GetMapping("/masReproducidas")
	 @Operation(summary = "Obtener ranking de canciones más escuchadas", description = "Devuelve un Top N de las canciones con mayor cantidad de reproducciones en toda la plataforma.")
	 public List<Cancion>getMasReproducidas(@RequestParam Integer numero){
		 return service.cancionesMasReproducidas(numero);
	 }
	 
	 @GetMapping("/{idArtista}/{nombre}")
	 @Operation(summary = "Buscar canción por clave compuesta", description = "Recupera una canción específica utilizando el ID del artista dueño y el nombre exacto del track.")
	 public Cancion buscarPorId(@PathVariable Integer idArtista, @PathVariable String nombre ) {
		 return service.buscarPorId(idArtista, nombre);
	 }
	 
	 @GetMapping("/paginado")
	 @Operation(summary = "Listar canciones con paginación", description = "Retorna una página específica del catálogo global de canciones.")
	 public Page<Cancion> getAllPaginado(
			 @RequestParam(defaultValue = "0") int page,
	         @RequestParam(defaultValue = "10") int size,
	         @RequestParam(defaultValue = "reproducciones") String sort){
		 return service.listarConPaginacion(page, size, sort);
	 }
}
