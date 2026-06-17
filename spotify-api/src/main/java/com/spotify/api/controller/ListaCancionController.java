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
import com.spotify.api.model.ListaReproduccion;
import com.spotify.api.service.ListaCancionService;

@RestController
@RequestMapping("/listaCancion")
public class ListaCancionController {
	@Autowired
	private ListaCancionService service;
	
	@GetMapping
	public List<ListaCancion>obtenerTodas(){
		return service.getAll();
	}
	
	@PostMapping("/{idLista}/{idUsuario}/{nombreCancion}/{idArtista}")
	public ListaCancion crearListaCancion(@PathVariable Integer idLista,@PathVariable Integer idUsuario,@PathVariable String nombreCancion,@PathVariable Integer idArtista) {
		return service.crear(idLista,idUsuario,nombreCancion,idArtista);
	}
	
	@DeleteMapping("/{idLista}/{idUsuario}/{nombreCancion}/{idArtista}")
	public void borrarListaCancion(@PathVariable Integer idLista,@PathVariable Integer idUsuario,@PathVariable String nombreCancion,@PathVariable Integer idArtista) {
		service.borrar(idLista, idUsuario, nombreCancion, idArtista);
	}
	
	@GetMapping("/cancion/{nombreCancion}/{idArtista}")
	public List<ListaCancion> buscarPorCancion(@PathVariable String nombreCancion,@PathVariable Integer idArtista){
		return service.cancionEnListas(nombreCancion, idArtista);
	}
	
	@GetMapping("/lista/{idLista}/{idUsuario}")
	public List<ListaCancion> buscarPorLista(@PathVariable Integer idLista,@PathVariable Integer idUsuario){
		return service.listasEnListas(idLista, idUsuario);
	}
	
	@GetMapping("/{idLista}/{idUsuario}/{nombreCancion}/{idArtista}")
	public ListaCancion buscarPorId(@PathVariable Integer idLista,@PathVariable Integer idUsuario,@PathVariable String nombreCancion,@PathVariable Integer idArtista) {
		return service.buscarPorId(idLista, idUsuario, nombreCancion, idArtista);
	}
		
	@GetMapping("/paginado")
	 public Page<ListaCancion> getAllPaginado(
			 @RequestParam(defaultValue = "0") int page,
	         @RequestParam(defaultValue = "10") int size,
	         @RequestParam(defaultValue = "idUsuario") String sort){
		 return service.listarConPaginacion(page, size, sort);
	 }
}
