package com.spotify.api.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Repository;

import com.spotify.api.model.Artista;
import com.spotify.api.model.Cancion;
import com.spotify.api.model.CancionId;

@Repository
public interface CancionRepository extends JpaRepository<Cancion, CancionId>{
	List<Cancion> findByNombreContainingIgnoreCaseOrderByReproduccionesDesc(String nombre);

	List<Cancion> findByReproduccionesGreaterThan(Integer reproducciones);
}
