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

import com.spotify.api.model.Cancion;
import com.spotify.api.model.ListaReproduccion;
import com.spotify.api.service.ListaReproduccionService;

@RestController
@RequestMapping("/lista")
public class ListaReproduccionController {
	@Autowired
	private ListaReproduccionService service;
	
	@GetMapping
	public List<ListaReproduccion> listarTodas(){
		return service.getAll();
	}
	
	@PostMapping("/{idUsuario}")
	public ListaReproduccion crearLista(@PathVariable Integer idUsuario,@RequestBody ListaReproduccion lista) {
		return service.crearLista(idUsuario,lista);
	}
	
	@PutMapping("/{id}/{idUsuario}")
	public ListaReproduccion actualizarLista(@PathVariable Integer id,@PathVariable Integer idUsuario,@RequestBody ListaReproduccion listaActualizada) {
		return service.actualizar(id, idUsuario, listaActualizada);
	}
	
	@DeleteMapping("/{id}/{idUsuario}")
	public void borrarLista(@PathVariable Integer id,@PathVariable Integer idUsuario) {
		service.borrarLista(id, idUsuario);
	}
	
	@GetMapping("/buscar")
	public List<ListaReproduccion> buscarPorNombre(@RequestParam String nombre) {
		return service.buscarPorNombreParcial(nombre);
	}
	
	@GetMapping("/{id}/{idUsuario}")
	public ListaReproduccion buscarPorId(@PathVariable Integer id,@PathVariable Integer idUsuario) {
		return service.buscarPorId(id, idUsuario);
	}
	
	@GetMapping("/paginado")
	 public Page<ListaReproduccion> getAllPaginado(
			 @RequestParam(defaultValue = "0") int page,
	         @RequestParam(defaultValue = "10") int size,
	         @RequestParam(defaultValue = "id") String sort){
		 return service.listarConPaginacion(page, size, sort);
	 }
}
