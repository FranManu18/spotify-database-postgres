package com.spotify.api.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.spotify.api.model.FeatCancion;
import com.spotify.api.model.FeatCancionId;

@Repository
public interface FeatCancionRepository extends JpaRepository<FeatCancion,FeatCancionId>{
	List<FeatCancion>findByIdFeat(Integer idFeat);
	
	List<FeatCancion> findByIdArtistaAndNombre(Integer idArtista, String nombre);
}
