package com.spotify.api.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.spotify.api.model.Artista;
import com.spotify.api.repository.ArtistaRepository;

@Service
public class ArtistaService {
	 	@Autowired
	    private ArtistaRepository repo;

	    public List<Artista> listarTodos() { 
	    	return repo.findAll(); 
	    }
	    
	    public Artista guardar(Artista artista) {
	    	return repo.save(artista);
	    }
	    
	    public Artista actualizar(Integer id,Artista artista) {
	    	return repo.findById(id).map(artistaExistente -> {
	            artistaExistente.setNombre(artista.getNombre());
	            artistaExistente.setOyentes(artista.getOyentes());
	            artistaExistente.setVerificado(artista.getVerificado());
	            artistaExistente.setSeguidores(artista.getSeguidores());
	            artistaExistente.setCancionMasEscuchada(artista.getCancionMasEscuchada());
	            return repo.save(artistaExistente);
	    	}).orElseThrow(() -> new RuntimeException("Artista no encontrado con el id: " + id));
	    }
	    
	    public void eliminarPorId(Integer id) {
	    	repo.deleteById(id);
	    }
	    
	    public List<Artista> buscarPorNombreParcial(String palabra) {
	        return repo.findByNombreContainingIgnoreCaseOrderBySeguidoresDesc(palabra);
	    }
	    
	    public List<Artista> listarVeficados(){
	    	return repo.findByVerificadoTrue();
	    }
	    
	    public Artista buscarPorId(Integer id) {
	    	return repo.findById(id)
	    	        .orElseThrow(() -> new RuntimeException("No se encontró el artista con ID: " + id));
	    }
	    
	    public Page<Artista> listarConPaginacion(int pagina, int cantidad, String ordenarPor) {
	        Pageable paginacion = PageRequest.of(pagina, cantidad, Sort.by(ordenarPor).ascending());
	        return repo.findAll(paginacion);
	    }
	    
	    public int desverificarArtistas(Integer seguidoresMinimos) {
	        return repo.desverificarArtistasPopulares(seguidoresMinimos);
	    }
}
