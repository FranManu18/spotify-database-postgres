package com.spotify.api.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Repository;
import com.spotify.api.model.Artista;

@Repository
public interface ArtistaRepository  extends JpaRepository<Artista, Integer> {
	List<Artista> findByNombreContainingIgnoreCaseOrderBySeguidoresDesc(String nombre);
	
	List<Artista> findByVerificadoTrue();
	
	@Transactional
	@Modifying
	@Query("UPDATE Artista a SET a.verificado = false WHERE a.seguidores < :minSeguidores")
	int desverificarArtistasPopulares(Integer minSeguidores);
}
