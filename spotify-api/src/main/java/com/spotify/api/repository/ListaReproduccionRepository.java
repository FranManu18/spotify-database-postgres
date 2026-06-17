package com.spotify.api.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.spotify.api.model.ListaReproduccion;
import com.spotify.api.model.ListaReproduccionId;

public interface ListaReproduccionRepository extends JpaRepository<ListaReproduccion,ListaReproduccionId>{
	@Query("SELECT MAX(l.id) FROM ListaReproduccion l WHERE l.idUsuario = :idUsuario")
    Integer findMaxIdByIdUsuario(Integer idUsuario);
	
	List<ListaReproduccion> findByNombreContainingIgnoreCaseOrderByIdDesc(String nombre);

}
