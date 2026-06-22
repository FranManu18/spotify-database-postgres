package com.spotify.api.controller;

import java.util.List;
import java.util.Optional;

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

import com.spotify.api.model.Artista;
import com.spotify.api.service.ArtistaService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/artista")
@Tag(name = "Artistas", description = "Gestión de músicos, estado de verificación y métricas de reproducción")
public class ArtistaController {
	  @Autowired
	   private ArtistaService service;

	   @GetMapping
	   @Operation(summary = "Listar todos los artistas", description = "Devuelve un listado completo de todos los artistas registrados en la plataforma.")
	   public List<Artista> getAll() { return service.listarTodos(); }

	   @PostMapping
	   @Operation(summary = "Crear un nuevo artista", description = "Registra un artista en el sistema con sus datos iniciales.")
	   public Artista crearArtista(@Valid @RequestBody Artista nuevoArtista) {
		   return service.guardar(nuevoArtista);
	   }
	   
	   @PutMapping("/{id}")
	   @Operation(summary = "Actualizar datos de un artista", description = "Modifica la información de un artista y dispara automáticamente el recalculo de su canción más escuchada.")
	   public Artista actualizarArtista(@PathVariable Integer id,@Valid @RequestBody Artista artistaEditado) {
		   Artista artistaActualizado=service.actualizar(id, artistaEditado);
		   service.actualizarCancionMasEscuchada(artistaActualizado.getId());
		   return artistaActualizado;
	   }
	   
	   @DeleteMapping("/{id}")
	   @Operation(summary = "Eliminar un artista por ID", description = "Borra de forma física al artista y remueve en cascada sus canciones para mantener la integridad de los datos.")
	   public void borrarArtista(@PathVariable Integer id) {
		   service.eliminarPorId(id);
	   }
	   
	   @GetMapping("/buscar")
	   @Operation(summary = "Buscar artistas por nombre", description = "Filtra de manera parcial e insensible a mayúsculas/minúsculas. Ordena los resultados por cantidad de seguidores de mayor a menor.")
	   public List<Artista> buscarArtistas(@RequestParam String nombre) {
		    return service.buscarPorNombreParcial(nombre);
		}
	   
	   @GetMapping("/verificados")
	   @Operation(summary = "Listar artistas verificados", description = "Devuelve únicamente los músicos que poseen el tilde de verificación en la plataforma.")
	   public List<Artista> artistasVerificados(){
		   return service.listarVeficados();
	   }
	   
	   @GetMapping("/{id}")
	   @Operation(summary = "Buscar artista por ID", description = "Obtiene los detalles de un artista específico. Lanza un error 404 (ResourceNotFoundException) si el ID no existe.")
	   public Artista buscarPorId(@PathVariable Integer id) {
		    return service.buscarPorId(id);
	   }
	   
	   @GetMapping("/paginado")
	   @Operation(summary = "Listar artistas con paginación", description = "Permite navegar el catálogo de artistas de forma paginada, definiendo página, tamaño y criterio de ordenamiento.")
	   public Page<Artista> getAllPaginado(
	           @RequestParam(defaultValue = "0") int page,
	           @RequestParam(defaultValue = "10") int size,
	           @RequestParam(defaultValue = "id") String sort) {
	       return service.listarConPaginacion(page, size, sort);
	   }
	   
	   @PostMapping("/limpieza-verificados")
	   @Operation(summary = "Limpiar artistas verificados", description = "Modifica el campo 'verificado' a false de todos los artistas que tengan una cantidad de seguidores mayor o igual a una cantidad minima")
	   public String limpiarVerificados(@RequestParam Integer minSeguidores) {
	       int modificados = service.desverificarArtistas(minSeguidores);
	       return "Se le quitó la verificación a " + modificados + " artistas.";
	   }
}
