package com.spotify.api.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.spotify.api.exception.ResourceNotFoundException;
import com.spotify.api.model.Artista;
import com.spotify.api.model.Cancion;
import com.spotify.api.repository.ArtistaRepository;
import com.spotify.api.repository.CancionRepository;

import jakarta.transaction.Transactional;

@Service
public class ArtistaService {
	 	@Autowired
	    private ArtistaRepository repo;
	 	
	    @Autowired
	    private CancionRepository cancionRepository;
	    
	    private void chequearClave(Integer id) {
	    	if(!repo.existsById(id)) {
	    		throw  new ResourceNotFoundException("El artista con ID "+ id +" no existe." );
	    	}
	    }

	    public List<Artista> listarTodos() { 
	    	return repo.findAll(); 
	    }
	    
	    public Artista guardar(Artista artista) {
	    	if(artista.getId()!=null && repo.existsById(artista.getId())) {
	    		throw new ResourceNotFoundException("El artista con ID "+ artista.getId() +" ya existe.");
	    	}
	    	return repo.save(artista);
	    }
	    
	    public Artista actualizar(Integer id,Artista artista) {
	    	this.chequearClave(id);
	    	Artista artistaExistente=repo.findById(id).orElse(null);
	        artistaExistente.setNombre(artista.getNombre());
	        artistaExistente.setOyentes(artista.getOyentes());
	        artistaExistente.setVerificado(artista.getVerificado());
	        artistaExistente.setSeguidores(artista.getSeguidores());
	        return repo.save(artistaExistente);
	    }
	    
	    @Transactional
	    public void eliminarPorId(Integer id) {
	    	chequearClave(id);
	    	Artista artista=repo.findById(id).orElse(null);
  	
	    	artista.setCancionMasEscuchada(null);
	        repo.saveAndFlush(artista);
	    	
	    	cancionRepository.deleteByIdArtista(id);
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
	    	        .orElseThrow(() -> new ResourceNotFoundException("El artista con ID "+ id +" no existe." ));
	    }
	    
	    public Page<Artista> listarConPaginacion(int pagina, int cantidad, String ordenarPor) {
	        Pageable paginacion = PageRequest.of(pagina, cantidad, Sort.by(ordenarPor).ascending());
	        return repo.findAll(paginacion);
	    }
	    
	    public int desverificarArtistas(Integer seguidoresMinimos) {
	        return repo.desverificarArtistasPopulares(seguidoresMinimos);
	    }
	    
	    
	    @Transactional
	    public void actualizarCancionMasEscuchada(Integer idArtista) {
	    	chequearClave(idArtista);
	    	Artista artista=repo.findById(idArtista).orElse(null);
	    	Optional<Cancion>topCancion=cancionRepository.findFirstByIdArtistaOrderByReproduccionesDesc(idArtista);
	    	if(topCancion.isPresent()) {
	    		artista.setCancionMasEscuchada(topCancion.get().getNombre());
	    		repo.save(artista);
	    	}
	    }
}
