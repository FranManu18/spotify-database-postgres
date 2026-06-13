package com.spotify.api.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.spotify.api.model.Artista;
import com.spotify.api.model.Cancion;
import com.spotify.api.model.CancionId;
import com.spotify.api.repository.CancionRepository;


@Service
public class CancionService {
	@Autowired
    private CancionRepository repo;
	
	public List<Cancion> listarTodas(){
		return repo.findAll();
	}
	
	public Cancion guardar(Cancion cancion) {
		return repo.save(cancion);
	}
	
	 public Cancion actualizar(Integer idArtista,String nombre,Cancion cancionNueva) {
	     CancionId id=new CancionId(idArtista,nombre);
		 Cancion cancion=repo.findById(id).orElse(null);
		 if(cancion==null) {
			 return null;
		 }
		 cancion.setReproducciones(cancionNueva.getReproducciones());
		 cancion.setDuracion(cancionNueva.getDuracion());
		 cancion.setPortada(cancionNueva.getPortada());
		 return repo.save(cancion);
	 }
	    
	 public void eliminarPorId(Integer idArtista,String nombre) {
		 CancionId id=new CancionId(idArtista,nombre);
		 repo.deleteById(id);
	 }
	    
	 public List<Cancion> buscarPorNombreParcial(String palabra) {
		 return repo.findByNombreContainingIgnoreCaseOrderByReproduccionesDesc(palabra);
	 }
	    
	 public List<Cancion>cancionesMasReproducidas(Integer numero){
		 return repo.findByReproduccionesGreaterThan(numero);
	 }
	 
	 public Cancion buscarPorId(Integer idArtista,String nombre) {
		 CancionId id=new CancionId(idArtista,nombre);
		 return repo.findById(id).orElse(null);
	 }
	 
	 public Page<Cancion> listarConPaginacion(int pagina, int cantidad, String ordenarPor) {
	        Pageable paginacion = PageRequest.of(pagina, cantidad, Sort.by(ordenarPor).ascending());
	        return repo.findAll(paginacion);
	    }
}
