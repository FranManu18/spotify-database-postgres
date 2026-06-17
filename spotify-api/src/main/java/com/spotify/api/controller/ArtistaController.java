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

import com.spotify.api.model.Artista;
import com.spotify.api.service.ArtistaService;

@RestController
@RequestMapping("/artista")
public class ArtistaController {
	  @Autowired
	   private ArtistaService service;

	   @GetMapping
	   public List<Artista> getAll() { return service.listarTodos(); }

	   @PostMapping
	   public Artista crearArtista(@RequestBody Artista nuevoArtista) {
		   return service.guardar(nuevoArtista);
	   }
	   
	   @PutMapping("/{id}")
	   public Artista actualizarArtista(@PathVariable Integer id,@RequestBody Artista artistaEditado) {
		   Artista artistaActualizado=service.actualizar(id, artistaEditado);
		   service.actualizarCancionMasEscuchada(artistaActualizado.getId());
		   return artistaActualizado;
	   }
	   
	   @DeleteMapping("/{id}")
	   public void borrarArtista(@PathVariable Integer id) {
		   service.eliminarPorId(id);
	   }
	   
	   @GetMapping("/buscar")
	   public List<Artista> buscarArtistas(@RequestParam String nombre) {
		    return service.buscarPorNombreParcial(nombre);
		}
	   
	   @GetMapping("/verificados")
	   public List<Artista> artistasVerificados(){
		   return service.listarVeficados();
	   }
	   
	   @GetMapping("/{id}")
	   public Artista buscarPorId(@PathVariable Integer id) {
		    return service.buscarPorId(id);
	   }
	   
	   @GetMapping("/paginado")
	   public Page<Artista> getAllPaginado(
	           @RequestParam(defaultValue = "0") int page,
	           @RequestParam(defaultValue = "10") int size,
	           @RequestParam(defaultValue = "id") String sort) {
	       return service.listarConPaginacion(page, size, sort);
	   }
	   
	   @PostMapping("/limpieza-verificados")
	   public String limpiarVerificados(@RequestParam Integer minSeguidores) {
	       int modificados = service.desverificarArtistas(minSeguidores);
	       return "Se le quitó la verificación a " + modificados + " artistas.";
	   }
}
