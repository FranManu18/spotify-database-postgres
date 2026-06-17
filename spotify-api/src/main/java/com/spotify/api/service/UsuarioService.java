package com.spotify.api.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.spotify.api.exception.ResourceNotFoundException;
import com.spotify.api.model.Usuario;

import com.spotify.api.repository.UsuarioRepository;

@Service
public class UsuarioService {
	@Autowired
	private UsuarioRepository repo;
	
	private void chequearClave(Integer id) {
		if(!repo.existsById(id)) {
    		throw  new ResourceNotFoundException("El usuario con el id "+id+" no existe" );
    	}
	}
	
	public List<Usuario>listarTodos(){
		return repo.findAll();
	}
	
	public Usuario guardar(Usuario usuario) {
		if(usuario.getId()!=null && repo.existsById(usuario.getId())) {
    		throw new ResourceNotFoundException("El usuario con ID "+ usuario.getId() +" ya existe.");
    	}
		return repo.save(usuario);
	}
	
	public Usuario actualizarUsuario(Usuario usuarioActualizado,Integer id) {
		chequearClave(id);
		Usuario usuario=repo.findById(id).orElse(null);
		usuario.setNombre(usuarioActualizado.getNombre());
		usuario.setSeguidores(usuarioActualizado.getSeguidores());
		return usuario;
	}
	
	public void borrarPorId(Integer id) {
		chequearClave(id);
		repo.deleteById(id);
	}
	
	public Usuario buscarPorNombre(String nombre) {
		return repo.findByNombreContainingIgnoreCaseOrderBySeguidoresDesc(nombre);
	}
	
	public Usuario buscarPorId(Integer id) {
		chequearClave(id);
		Usuario usuario=repo.findById(id).orElse(null);
		return usuario;
	}
	
	public Page<Usuario> listarConPaginacion(int pagina, int cantidad, String ordenarPor) {
		 Pageable paginacion = PageRequest.of(pagina, cantidad, Sort.by(ordenarPor).ascending());
	     return repo.findAll(paginacion);
	}
}
