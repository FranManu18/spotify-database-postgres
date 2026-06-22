package com.spotify.api.service;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.spotify.api.exception.ResourceNotFoundException;
import com.spotify.api.model.CancionId;
import com.spotify.api.model.FeatCancion;
import com.spotify.api.model.FeatCancionId;
import com.spotify.api.model.ListaCancion;
import com.spotify.api.repository.ArtistaRepository;
import com.spotify.api.repository.CancionRepository;
import com.spotify.api.repository.FeatCancionRepository;

@ExtendWith(MockitoExtension.class)

public class FeatCancionTest {
	@Mock
	private FeatCancionRepository repo;
	
	@Mock
	private CancionRepository repoCancion;
	
	@Mock
	private ArtistaRepository repoArtista;
	
	@InjectMocks
	private FeatCancionService serviceFeatCancion;
	
	private FeatCancion featCancionEjemplo;
	
	private FeatCancionId idFeatCancion;
	
	private CancionId idCancionEjemplo;
	
	private Integer idArtistaFeat;
	
	@BeforeEach
	void setUp() {
		idArtistaFeat=3;
		idCancionEjemplo=new CancionId();
		idCancionEjemplo.setIdArtista(1);
		idCancionEjemplo.setNombre("One Dance");
		idFeatCancion=new FeatCancionId();
		idFeatCancion.setIdArtista(1);
		idFeatCancion.setNombre("One Dance");
		idFeatCancion.setIdFeat(idArtistaFeat);
		
		
		featCancionEjemplo=new FeatCancion();
		featCancionEjemplo.setIdArtista(1);
		featCancionEjemplo.setNombre("One Dance");
		featCancionEjemplo.setIdFeat(idArtistaFeat);
	}
	
	@Test
	void crearFeat_CuandoElArtistaYLaCancionExisten_DebeRetornarElFeatCancionCreado() {
		when(repoCancion.existsById(idCancionEjemplo)).thenReturn(true);
		when(repoArtista.existsById(idArtistaFeat)).thenReturn(true);
		when(repo.save(any(FeatCancion.class))).thenReturn(featCancionEjemplo);
		
		FeatCancion resultado=serviceFeatCancion.crearFeat(idFeatCancion.getIdArtista(),idFeatCancion.getNombre(), idArtistaFeat);
		
		assertEquals("One Dance",resultado.getNombre());
		assertEquals(1,resultado.getIdArtista());
		assertEquals(3,resultado.getIdFeat());
		
		verify(repo,times(1)).save(any(FeatCancion.class));
	}
	
	@Test
	void crearFeat_CuandoElArtistaNoExisteYLaCancionExiste_DebeLanzarResourceNotFoundException() {
		when(repoCancion.existsById(idCancionEjemplo)).thenReturn(true);
		when(repoArtista.existsById(idArtistaFeat)).thenReturn(false);
		
		assertThrows(ResourceNotFoundException.class, () -> {
            serviceFeatCancion.crearFeat(idFeatCancion.getIdArtista(),idFeatCancion.getNombre(), idArtistaFeat);
        });
		
		verify(repo,never()).save(any(FeatCancion.class));
	}
	
	@Test
	void crearFeat_CuandoElArtistaxisteYLaCancionNoExiste_DebeLanzarResourceNotFoundException() {
		when(repoCancion.existsById(idCancionEjemplo)).thenReturn(false);
		
		assertThrows(ResourceNotFoundException.class, () -> {
            serviceFeatCancion.crearFeat(idFeatCancion.getIdArtista(),idFeatCancion.getNombre(), idArtistaFeat);
        });
		
		verify(repo,never()).save(any(FeatCancion.class));
	}
	
	@Test
	void borrarFeat_CuandoElArtistaYLaCancionExisten_DebeBorrarElFeatCancionEncontrado() {
		when(repoCancion.existsById(idCancionEjemplo)).thenReturn(true);
		when(repoArtista.existsById(idArtistaFeat)).thenReturn(true);
		
		serviceFeatCancion.borrarFeat(idFeatCancion.getIdArtista(),idFeatCancion.getNombre(), idArtistaFeat);
		
		verify(repo,times(1)).deleteById(idFeatCancion);
	}
	
	@Test
	void borrarFeat_CuandoElArtistaNoExisteYLaCancionExiste_DebeLanzarResourceNotFoundException() {
		when(repoCancion.existsById(idCancionEjemplo)).thenReturn(true);
		when(repoArtista.existsById(idArtistaFeat)).thenReturn(false);
		
		
		assertThrows(ResourceNotFoundException.class, () -> {
			serviceFeatCancion.borrarFeat(idFeatCancion.getIdArtista(),idFeatCancion.getNombre(), idArtistaFeat);
        });
		
		verify(repo,never()).deleteById(idFeatCancion);
	}
	
	@Test
	void borrarFeat_CuandoElArtistaExisteYLaCancionNoExiste_DebeLanzarResourceNotFoundException() {
		when(repoCancion.existsById(idCancionEjemplo)).thenReturn(false);
		
		
		assertThrows(ResourceNotFoundException.class, () -> {
			serviceFeatCancion.borrarFeat(idFeatCancion.getIdArtista(),idFeatCancion.getNombre(), idArtistaFeat);
        });
		
		verify(repo,never()).deleteById(idFeatCancion);
	}
	
	@Test
	void buscarPorId_CuandoElArtistaYLaCancionExisten_DebeRetornarElFeatCancionEncontrado() {
		when(repoCancion.existsById(idCancionEjemplo)).thenReturn(true);
		when(repoArtista.existsById(idArtistaFeat)).thenReturn(true);
		when(repo.findById(idFeatCancion)).thenReturn(Optional.of(featCancionEjemplo));
		
		FeatCancion resultado=serviceFeatCancion.buscarPorId(idFeatCancion.getIdArtista(),idFeatCancion.getNombre(), idArtistaFeat);
		
		assertEquals("One Dance",resultado.getNombre());
		assertEquals(1,resultado.getIdArtista());
		assertEquals(3,resultado.getIdFeat());
		
		verify(repo,times(1)).findById(idFeatCancion);
	}
	
	@Test
	void buscarPorId_CuandoElArtistaNoExisteYLaCancionExiste_DebeLanzarResourceNotFoundException() {
		when(repoCancion.existsById(idCancionEjemplo)).thenReturn(true);
		when(repoArtista.existsById(idArtistaFeat)).thenReturn(false);
		
		
		assertThrows(ResourceNotFoundException.class, () -> {
			serviceFeatCancion.buscarPorId(idFeatCancion.getIdArtista(),idFeatCancion.getNombre(), idArtistaFeat);
        });
		
		verify(repo,never()).findById(idFeatCancion);
	}
	
	@Test
	void buscarPorId_CuandoElArtistaExisteYLaCancionNoExiste_DebeLanzarResourceNotFoundException() {
		when(repoCancion.existsById(idCancionEjemplo)).thenReturn(false);
		
		
		assertThrows(ResourceNotFoundException.class, () -> {
			serviceFeatCancion.borrarFeat(idFeatCancion.getIdArtista(),idFeatCancion.getNombre(), idArtistaFeat);
        });
		
		verify(repo,never()).findById(idFeatCancion);
	}
}
