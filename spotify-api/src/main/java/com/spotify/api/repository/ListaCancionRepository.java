package com.spotify.api.repository;

import org.springframework.stereotype.Repository;

import com.spotify.api.model.ListaCancion;
import com.spotify.api.model.ListaCancionId;
import com.spotify.api.model.ListaReproduccion;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface ListaCancionRepository extends JpaRepository<ListaCancion,ListaCancionId>{
	
	List<ListaCancion> findByNombreCancionAndIdArtista(String nombreCancion, Integer idArtista);

	List<ListaCancion> findByIdListaAndIdUsuario(Integer idLista, Integer idUsuario);
}
