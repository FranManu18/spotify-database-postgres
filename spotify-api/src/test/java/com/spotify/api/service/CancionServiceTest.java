package com.spotify.api.service;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.Duration;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.spotify.api.exception.ResourceNotFoundException;
import com.spotify.api.model.Cancion;
import com.spotify.api.model.CancionId;
import com.spotify.api.repository.CancionRepository;


@ExtendWith(MockitoExtension.class)
public class CancionServiceTest {
	
	@Mock
	private CancionRepository repo;
	
	@InjectMocks
	private CancionService serviceCancion;
	
	private Cancion cancionEjemplo;
	
	private CancionId idCancionEjemplo;
	
	@BeforeEach
	void setUp() {
		cancionEjemplo=new Cancion();
		cancionEjemplo.setIdArtista(1);
		cancionEjemplo.setNombre("NOKIA");
		cancionEjemplo.setPortada("Portada-url");
		cancionEjemplo.setReproducciones(1500000);
		Duration duracion=Duration.ofMinutes(3).ofSeconds(21);
		cancionEjemplo.setDuracion(duracion);
		idCancionEjemplo=new CancionId(cancionEjemplo.getIdArtista(),cancionEjemplo.getNombre());
	}
	
	@Test
	void buscarPorId_CuandoLaCancionExiste_RetornarCancion() {
		
		when(repo.existsById(idCancionEjemplo)).thenReturn(true);
		
		when(repo.findById(idCancionEjemplo)).thenReturn(Optional.of(cancionEjemplo));
		
		Cancion cancionBuscada=serviceCancion.buscarPorId(1, "NOKIA");
		
		assertNotNull(cancionBuscada);
		assertEquals("NOKIA",cancionBuscada.getNombre());
		
		verify(repo, times(1)).findById(idCancionEjemplo);
	}
		
	@Test
	void buscarPorId_CuandoLaCancionNoExiste_DebeLanzarResourceNotFoundException() {
		
		when(repo.existsById(idCancionEjemplo)).thenReturn(false);
		
		
		assertThrows(ResourceNotFoundException.class, () -> {
            serviceCancion.buscarPorId(1, "NOKIA");
        });
		
		verify(repo, never()).findById(idCancionEjemplo);
	}
	
	@Test
	void actualizar_CuandoLaCancionExiste_RetornarCancionActualizada() {
		when(repo.existsById(idCancionEjemplo)).thenReturn(true);
		
		when(repo.findById(idCancionEjemplo)).thenReturn(Optional.of(cancionEjemplo));
		
		Cancion cancionActualizada=new Cancion();
		cancionActualizada.setReproducciones(15000000);
		cancionActualizada.setPortada(cancionEjemplo.getPortada());
		cancionActualizada.setDuracion(cancionEjemplo.getDuracion());
		
		serviceCancion.actualizar(1, "NOKIA", cancionActualizada);
		
		assertEquals(15000000,cancionEjemplo.getReproducciones());
		
		verify(repo, times(1)).findById(idCancionEjemplo);
	}
	
	@Test
	void actualizar_CuandoLaCancionNoExiste_DebeLanzarResourceNotFoundException() {
		when(repo.existsById(idCancionEjemplo)).thenReturn(false);
		
		
		assertThrows(ResourceNotFoundException.class, () -> {
            serviceCancion.actualizar(1, "NOKIA", cancionEjemplo);
        });
		
		verify(repo, never()).findById(idCancionEjemplo);
	}
	
	 @Test
	 void eliminarPorId_DebeEliminarLaCancion() {
		 when(repo.existsById(idCancionEjemplo)).thenReturn(true);
			
		when(repo.findById(idCancionEjemplo)).thenReturn(Optional.of(cancionEjemplo));
	    	
	    serviceCancion.eliminarPorId(1, "NOKIA");
	        
	    verify(repo, times(1)).deleteById(idCancionEjemplo);
	 }
	    
	 @Test
	 void eliminarPorId_DebeLanzarResourceNotFoundException() {
		 when(repo.existsById(idCancionEjemplo)).thenReturn(false);
			
			
		assertThrows(ResourceNotFoundException.class, () -> {
	            serviceCancion.eliminarPorId(1, "NOKIA");
	    });
			
		verify(repo, never()).deleteById(idCancionEjemplo);
	}
	    
	 @Test
	   void guardar_DebeGuardarLaNuevaCancion() {
	    	
	    	serviceCancion.guardar(cancionEjemplo);
	    	
	    	 verify(repo, times(1)).save(cancionEjemplo);
	    }
	    
}


