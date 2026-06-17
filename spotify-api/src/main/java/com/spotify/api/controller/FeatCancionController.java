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

import com.spotify.api.service.FeatCancionService;
import com.spotify.api.model.FeatCancion;
import com.spotify.api.model.ListaCancion;

@RestController
@RequestMapping("/featCancion")
public class FeatCancionController {
	@Autowired
	private FeatCancionService service;
	
	@GetMapping
	public List<FeatCancion> obtenerTodos(){
		return service.getAll();
	}
	
	@PostMapping("/{idArtista}/{nombreCancion}/{idFeat}")
	public FeatCancion crear(@PathVariable Integer idArtista,@PathVariable String nombreCancion,@PathVariable Integer idFeat) {
		return service.crearFeat(idArtista, nombreCancion, idFeat);
	}
	
	@DeleteMapping("/{idArtista}/{nombreCancion}/{idFeat}")
	public void borrar(@PathVariable Integer idArtista,@PathVariable String nombreCancion,@PathVariable Integer idFeat) {
		service.borrarFeat(idArtista, nombreCancion, idFeat);
	}
	
	@GetMapping("/feat/{idFeat}")
	public List<FeatCancion> buscarPorFeat(@PathVariable Integer idFeat){
		return service.featEnCanciones(idFeat);
	}
	
	@GetMapping("/cancion/{idArtista}/{nombreCancion}")
	public List<FeatCancion> buscarPorFeat(@PathVariable Integer idArtista,@PathVariable String nombreCancion){
		return service.featsDeCanciones(idArtista, nombreCancion);
	}
	
	@GetMapping("/buscar/{idArtista}/{nombreCancion}/{idFeat}")
	public FeatCancion buscarPorId(@PathVariable Integer idArtista,@PathVariable String nombreCancion,@PathVariable Integer idFeat) {
		return service.buscarPorId(idArtista, nombreCancion, idFeat);
	}
	
	@GetMapping("/paginado")
	public Page<FeatCancion> getAllPaginado(
			 @RequestParam(defaultValue = "0") int page,
	         @RequestParam(defaultValue = "10") int size,
	         @RequestParam(defaultValue = "idFeat") String sort){
		 return service.listarConPaginacion(page, size, sort);
	}
}
