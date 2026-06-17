package com.spotify.api.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.spotify.api.exception.ResourceNotFoundException;
import com.spotify.api.model.ListaReproduccion;
import com.spotify.api.model.ListaReproduccionId;
import com.spotify.api.repository.ListaReproduccionRepository;
import com.spotify.api.repository.UsuarioRepository;

@Service
public class ListaReproduccionService {
	@Autowired
	private ListaReproduccionRepository repo;
	
	@Autowired
	private UsuarioRepository repoUsuario;
	
	private void chequearClaves(ListaReproduccionId id) {
		if(!repo.existsById(id)) {
    		throw  new ResourceNotFoundException("La lista con el id "+id.getId()+" y el id usuario "+id.getIdUsuario()+" no existe");
    	}
	}
	
	private void chequearUsuario(Integer id) {
		if(!repoUsuario.existsById(id)) {
    		throw  new ResourceNotFoundException("El usuario con el id "+id+" no existe" );
    	}
	}
	
	public List<ListaReproduccion> getAll(){
		return repo.findAll();
	}
	
	public ListaReproduccion crearLista(Integer idUsuario, ListaReproduccion nuevaLista) {
		chequearUsuario(idUsuario);
		Integer maxId = repo.findMaxIdByIdUsuario(idUsuario);
	    nuevaLista.setId(maxId == null ? 1 : maxId + 1);
	    nuevaLista.setIdUsuario(idUsuario);
	    return repo.save(nuevaLista);
	}
	
	public ListaReproduccion actualizar(Integer id,Integer idUsuario,ListaReproduccion listaActualizada) {
		ListaReproduccionId idLista=new ListaReproduccionId(id,idUsuario);
		chequearClaves(idLista);
		ListaReproduccion lista=repo.findById(idLista).orElse(null);
		lista.setAleatorio(listaActualizada.isAleatorio());
		lista.setNombre(listaActualizada.getNombre());
		lista.setPublica(listaActualizada.isPublica());
		lista.setDescripcion(listaActualizada.getDescripcion());
		return repo.save(lista);
	}
	
	public void borrarLista(Integer id,Integer idUsuario) {
		ListaReproduccionId idLista=new ListaReproduccionId(id,idUsuario);
		chequearClaves(idLista);
		repo.findById(idLista).orElse(null);
		repo.deleteById(idLista);
	}
	
	public List<ListaReproduccion>buscarPorNombreParcial(String nombre){
		return repo.findByNombreContainingIgnoreCaseOrderByIdDesc(nombre);
	}
	
	public ListaReproduccion buscarPorId(Integer id,Integer idUsuario) {
		ListaReproduccionId idLista=new ListaReproduccionId(id,idUsuario);
		chequearClaves(idLista);
		return repo.findById(idLista).orElse(null);
	}
	
	public Page<ListaReproduccion> listarConPaginacion(int pagina, int cantidad, String ordenarPor) {
        Pageable paginacion = PageRequest.of(pagina, cantidad, Sort.by(ordenarPor).ascending());
        return repo.findAll(paginacion);
    }
}
