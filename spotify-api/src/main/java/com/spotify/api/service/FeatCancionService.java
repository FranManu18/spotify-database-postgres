package com.spotify.api.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.spotify.api.exception.ResourceNotFoundException;
import com.spotify.api.model.CancionId;
import com.spotify.api.model.FeatCancion;
import com.spotify.api.model.FeatCancionId;
import com.spotify.api.model.ListaCancion;
import com.spotify.api.repository.ArtistaRepository;
import com.spotify.api.repository.CancionRepository;
import com.spotify.api.repository.FeatCancionRepository;

@Service
public class FeatCancionService {
	@Autowired
	private FeatCancionRepository repo;
	
	@Autowired
	private CancionRepository cancionRepo;
	
	@Autowired
	private ArtistaRepository artistaRepo;
	
	private void chequearClaves(Integer idArtista,String nombreCancion,Integer idFeat) {
		CancionId idCancion=new CancionId(idArtista,nombreCancion);
		if(!cancionRepo.existsById(idCancion)) {
			throw new ResourceNotFoundException("La cancion con el artista con id "+idArtista+" y nombre de la cancion "+nombreCancion+" no existe");
		}
		
		if(!artistaRepo.existsById(idFeat)) {
			throw new ResourceNotFoundException("La cancion con el artista con id "+idFeat+" no existe");
		}
	}
	
	public List<FeatCancion> getAll(){
		return repo.findAll();
	}
	
	public FeatCancion crearFeat(Integer idArtista,String nombreCancion,Integer idFeat) {
		chequearClaves(idArtista,nombreCancion,idFeat);
		FeatCancion feat=new FeatCancion();
		feat.setIdArtista(idArtista);
		feat.setNombre(nombreCancion);
		feat.setIdFeat(idFeat);
		return repo.save(feat);
	}
	
	public void borrarFeat(Integer idArtista,String nombreCancion,Integer idFeat) {
		chequearClaves(idArtista,nombreCancion,idFeat);
		FeatCancionId id=new FeatCancionId(idArtista,nombreCancion,idFeat);
		repo.deleteById(id);
	}
	
	public List<FeatCancion> featEnCanciones(Integer idFeat){
		return repo.findByIdFeat(idFeat);
	}
	
	public List<FeatCancion> featsDeCanciones(Integer idArtista,String nombreCancion){
		return repo.findByIdArtistaAndNombre(idArtista, nombreCancion);
	}
	
	public FeatCancion buscarPorId(Integer idArtista,String nombreCancion,Integer idFeat) {
		chequearClaves(idArtista,nombreCancion,idFeat);
		FeatCancionId id=new FeatCancionId(idArtista,nombreCancion,idFeat);
		return repo.findById(id).orElseThrow(()->new ResourceNotFoundException("No existe el feat del artista con id "+idFeat+" a la cancion con nombre "+nombreCancion+" del artista con id "+idArtista));
	}
	
	public Page<FeatCancion> listarConPaginacion(int pagina, int cantidad, String ordenarPor) {
        Pageable paginacion = PageRequest.of(pagina, cantidad, Sort.by(ordenarPor).ascending());
        return repo.findAll(paginacion);
    }
}
