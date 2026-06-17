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
import com.spotify.api.model.ListaCancion;
import com.spotify.api.model.ListaCancionId;
import com.spotify.api.model.ListaReproduccion;
import com.spotify.api.model.ListaReproduccionId;
import com.spotify.api.repository.CancionRepository;
import com.spotify.api.repository.ListaCancionRepository;
import com.spotify.api.repository.ListaReproduccionRepository;

@Service
public class ListaCancionService {
	@Autowired
	private ListaCancionRepository repo;
	
	@Autowired
	private CancionRepository cancionRepo;
	
	@Autowired
	private ListaReproduccionRepository listaRepo;
	
	public List<ListaCancion> getAll(){
		return repo.findAll();
	}
	
	private void chequeoClaves(Integer idLista,Integer idUsuario,String nombreCancion,Integer idArtista) {
		ListaReproduccionId idListaRepro=new ListaReproduccionId(idLista,idUsuario);
		if(!listaRepo.existsById(idListaRepro)) {
			throw new ResourceNotFoundException("La lista con el id "+idLista+" y el id de usuario "+idUsuario+" no existe");
		}
		
		CancionId idCancion=new CancionId(idArtista,nombreCancion);
		if(!cancionRepo.existsById(idCancion)) {
			throw new ResourceNotFoundException("La cancion con el artista con id "+idArtista+" y nombre de la cancion "+nombreCancion+" no existe");
		}
	}
	
	public ListaCancion crear(Integer idLista,Integer idUsuario,String nombreCancion,Integer idArtista) {
		this.chequeoClaves(idLista,idUsuario,nombreCancion,idArtista);
		ListaCancion listaCancion=new ListaCancion();
		listaCancion.setIdLista(idLista);
		listaCancion.setIdUsuario(idUsuario);
		listaCancion.setNombreCancion(nombreCancion);
		listaCancion.setIdArtista(idArtista);
		return repo.save(listaCancion);
	}
	
	public void borrar(Integer idLista,Integer idUsuario,String nombreCancion,Integer idArtista) {
		this.chequeoClaves(idLista,idUsuario,nombreCancion,idArtista);
		ListaCancionId id=new ListaCancionId(idLista,idUsuario,nombreCancion,idArtista);
		repo.deleteById(id);
	}
	
	public List<ListaCancion> cancionEnListas(String nombreCancion,Integer idArtista){
		return repo.findByNombreCancionAndIdArtista(nombreCancion, idArtista);
	}
	
	public List<ListaCancion> listasEnListas(Integer idLista,Integer idUsuario){
		return repo.findByIdListaAndIdUsuario(idLista, idUsuario);
	}
	
	public ListaCancion buscarPorId(Integer idLista,Integer idUsuario,String nombreCancion,Integer idArtista) {
		this.chequeoClaves(idLista,idUsuario,nombreCancion,idArtista);
		ListaCancionId id=new ListaCancionId(idLista,idUsuario,nombreCancion,idArtista);
		return repo.findById(id).orElse(null);
	}
	
	public Page<ListaCancion> listarConPaginacion(int pagina, int cantidad, String ordenarPor) {
        Pageable paginacion = PageRequest.of(pagina, cantidad, Sort.by(ordenarPor).ascending());
        return repo.findAll(paginacion);
    }
}
