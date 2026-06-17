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

import com.spotify.api.model.Cancion;
import com.spotify.api.service.ArtistaService;
import com.spotify.api.service.CancionService;

@RestController
@RequestMapping("/cancion")
public class CancionController {
	 @Autowired
	 private CancionService service;
	 
	 @Autowired
	 private ArtistaService serviceArtista;
	 
	 @GetMapping
	 public List<Cancion>getAll(){
		 return service.listarTodas();
	 }
	 
	 @PostMapping
	 public Cancion crearCancion(@RequestBody Cancion cancion) {
		 Cancion nuevaCancion=service.guardar(cancion);
		 serviceArtista.actualizarCancionMasEscuchada(cancion.getIdArtista());
		 return nuevaCancion;
	 }
	 
	 @PutMapping("/{idArtista}/{nombre}")
	 public Cancion actualizar(@PathVariable Integer idArtista, @PathVariable String nombre,@RequestBody Cancion cancionEditada) {
		 return service.actualizar(idArtista, nombre, cancionEditada);
	 }
	 
	 @DeleteMapping("/{idArtista}/{nombre}")
	 public void eliminarCancion(@PathVariable Integer idArtista,@PathVariable String nombre) {
		 service.eliminarPorId(idArtista, nombre);
	 }
	 
	 @GetMapping("/{nombre}")
	 public List<Cancion> buscarCanciones(@PathVariable String nombre){
		 return service.buscarPorNombreParcial(nombre);
	 }
	 
	 @GetMapping("/masReproducidas")
	 public List<Cancion>getMasReproducidas(@RequestParam Integer numero){
		 return service.cancionesMasReproducidas(numero);
	 }
	 
	 @GetMapping("/{idArtista}/{nombre}")
	 public Cancion buscarPorId(@PathVariable Integer idArtista, @PathVariable String nombre ) {
		 return service.buscarPorId(idArtista, nombre);
	 }
	 
	 @GetMapping("/paginado")
	 public Page<Cancion> getAllPaginado(
			 @RequestParam(defaultValue = "0") int page,
	         @RequestParam(defaultValue = "10") int size,
	         @RequestParam(defaultValue = "reproducciones") String sort){
		 return service.listarConPaginacion(page, size, sort);
	 }
}
