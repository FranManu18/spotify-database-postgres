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
import com.spotify.api.model.Artista;
import com.spotify.api.model.CancionId;
import com.spotify.api.model.ListaCancion;
import com.spotify.api.model.ListaCancionId;
import com.spotify.api.model.ListaReproduccion;
import com.spotify.api.model.ListaReproduccionId;
import com.spotify.api.repository.CancionRepository;
import com.spotify.api.repository.ListaCancionRepository;
import com.spotify.api.repository.ListaReproduccionRepository;

@ExtendWith(MockitoExtension.class)
public class ListaCancionTest {
	@Mock
	private ListaCancionRepository repo;
	
	@Mock
	private CancionRepository repoCancion;
	
	@Mock
	private ListaReproduccionRepository repoLista;
	
	@InjectMocks
	private ListaCancionService serviceListaCancion;
	
	private ListaCancion listaCancionEjemplo;
	
	private ListaCancionId idListaCancionEjemplo;
	
	private ListaReproduccionId idLista;
	
	private CancionId idCancion;
	
	private 
	
	@BeforeEach
	void setUp() {
		listaCancionEjemplo=new ListaCancion();
		listaCancionEjemplo.setIdArtista(1);
		listaCancionEjemplo.setNombreCancion("One Dance");
		listaCancionEjemplo.setIdUsuario(3);
		listaCancionEjemplo.setIdLista(3);
		
		idListaCancionEjemplo=new ListaCancionId();
		idListaCancionEjemplo.setIdArtista(1);
		idListaCancionEjemplo.setNombreCancion("One Dance");
		idListaCancionEjemplo.setIdUsuario(3);
		idListaCancionEjemplo.setIdLista(3);
		
		idLista=new ListaReproduccionId();
		idLista.setId(3);
		idLista.setIdUsuario(3);
		
		idCancion=new CancionId();
		idCancion.setIdArtista(1);
		idCancion.setNombre("One Dance");
	}
	
	@Test
	void crear_CuandoLaListaYLaCancionExisten_DebeRetornarLaListaCancion() {
		when(repoLista.existsById(idLista)).thenReturn(true);
		when(repoCancion.existsById(idCancion)).thenReturn(true);
		
		when(repo.save(any(ListaCancion.class))).thenReturn(listaCancionEjemplo);
		
		ListaCancion nuevaListaCancion=serviceListaCancion.crear(3, 3, "One Dance", 1);
		
		assertEquals("One Dance",nuevaListaCancion.getNombreCancion());
		assertEquals(1,nuevaListaCancion.getIdArtista());
		assertEquals(3,nuevaListaCancion.getIdLista());
		assertEquals(3,nuevaListaCancion.getIdUsuario());
		
		verify(repo, times(1)).save(any(ListaCancion.class));
		
	}
	
	@Test
	void crear_CuandoLaListaExisteYLaCancionNoExiste_DebeLanzarResourceNotFoundException() {
		when(repoLista.existsById(idLista)).thenReturn(true);
		when(repoCancion.existsById(idCancion)).thenReturn(false);
		
		assertThrows(ResourceNotFoundException.class, () -> {
            serviceListaCancion.crear(3, 3, "One Dance", 1);
        });
		
		verify(repo, never()).save(any(ListaCancion.class));
		
	}
	
	@Test
	void crear_CuandoLaListaNoExisteYLaCancionExiste_DebeLanzarResourceNotFoundException() {
		when(repoLista.existsById(idLista)).thenReturn(false);
		
		assertThrows(ResourceNotFoundException.class, () -> {
            serviceListaCancion.crear(3, 3, "One Dance", 1);
        });
		
		verify(repo, never()).save(any(ListaCancion.class));
		
	}

	
	@Test
	void borrar_CuandoLaListaYLaCancionExisten_DebeBorrarLaListaCancion() {
		when(repoLista.existsById(idLista)).thenReturn(true);
		when(repoCancion.existsById(idCancion)).thenReturn(true);
		
		serviceListaCancion.borrar(3, 3, "One Dance", 1);
		verify(repo, times(1)).deleteById(idListaCancionEjemplo);
		
	}
	
	@Test
	void borrar_CuandoLaListaNoExisteYLaCancionExiste_DebeLanzarResourceNotFoundException() {
		when(repoLista.existsById(idLista)).thenReturn(false);
		
		assertThrows(ResourceNotFoundException.class, () -> {
            serviceListaCancion.borrar(3, 3, "One Dance", 1);
        });
		
		
		verify(repo, never()).deleteById(idListaCancionEjemplo);
		
	}
	
	@Test
	void borrar_CuandoLaListaExisteYLaCancionNoExiste_DebeLanzarResourceNotFoundException() {
		when(repoLista.existsById(idLista)).thenReturn(true);
		when(repoCancion.existsById(idCancion)).thenReturn(false);
		
		assertThrows(ResourceNotFoundException.class, () -> {
            serviceListaCancion.borrar(3, 3, "One Dance", 1);
        });
		
		
		verify(repo, never()).deleteById(idListaCancionEjemplo);
		
	}
	
	@Test
	void buscarPorId_CuandoLaListaYLaCancionExisten_DebeRetornarLaListaCancionEncontrada() {
		when(repoLista.existsById(idLista)).thenReturn(true);
		when(repoCancion.existsById(idCancion)).thenReturn(true);
		
		when(repo.findById(idListaCancionEjemplo)).thenReturn(Optional.of(listaCancionEjemplo));
		
		ListaCancion resultado=serviceListaCancion.buscarPorId(3, 3, "One Dance", 1);
		
		assertEquals("One Dance",resultado.getNombreCancion());
		assertEquals(1,resultado.getIdArtista());
		assertEquals(3,resultado.getIdLista());
		assertEquals(3,resultado.getIdUsuario());
		
		verify(repo, times(1)).findById(idListaCancionEjemplo);
		
	}
	
	@Test
	void buscarPorId_CuandoLaListaExisteYLaCancionNoExiste_DebeLanzarResourceNotFoundException() {
		when(repoLista.existsById(idLista)).thenReturn(true);
		when(repoCancion.existsById(idCancion)).thenReturn(false);
		
		assertThrows(ResourceNotFoundException.class, () -> {
            serviceListaCancion.buscarPorId(3, 3, "One Dance", 1);
        });
		
		verify(repo, never()).findById(idListaCancionEjemplo);
		
	}
	
	@Test
	void buscarPorId_CuandoLaListaNoExisteYLaCancionExiste_DebeLanzarResourceNotFoundException() {
		when(repoLista.existsById(idLista)).thenReturn(false);
		
		assertThrows(ResourceNotFoundException.class, () -> {
            serviceListaCancion.buscarPorId(3, 3, "One Dance", 1);
        });
		
		verify(repo, never()).findById(idListaCancionEjemplo);
		
	}
	
}
