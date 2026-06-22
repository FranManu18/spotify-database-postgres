package com.spotify.api.service;

import static org.junit.jupiter.api.Assertions.*;
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
import com.spotify.api.model.Cancion;
import com.spotify.api.repository.ArtistaRepository;
import com.spotify.api.repository.CancionRepository;

@ExtendWith(MockitoExtension.class)
class ArtistaServiceTest {

    @Mock
    private ArtistaRepository repo; 
    
    @Mock
    private CancionRepository repoCancion;

    @InjectMocks
    private ArtistaService artistaService; 

    private Artista artistaEjemplo;
    

    @BeforeEach
    void setUp() {
        artistaEjemplo = new Artista();
        artistaEjemplo.setId(10);
        artistaEjemplo.setNombre("Duki");
        artistaEjemplo.setSeguidores(15000000);
        artistaEjemplo.setVerificado(true);
    }

    @Test
    void buscarPorId_CuandoElArtistaExiste_DebeRetornarElArtista() {
       
        when(repo.findById(10)).thenReturn(Optional.of(artistaEjemplo));

        
        Artista resultado = artistaService.buscarPorId(10);

        
        assertNotNull(resultado);
        assertEquals(10, resultado.getId());
        assertEquals("Duki", resultado.getNombre());
        
        
        verify(repo, times(1)).findById(10);
    }

    @Test
    void buscarPorId_CuandoElArtistaNoExiste_DebeLanzarResourceNotFoundException() {
        
        when(repo.findById(99)).thenReturn(Optional.empty());

        
        assertThrows(ResourceNotFoundException.class, () -> {
            artistaService.buscarPorId(99);
        });

        
        verify(repo, times(1)).findById(99);
    }
    
    @Test
    void actualizarCancionMasEscuchada_DebeAsignarLaCancionConMasReproducciones() {

        Cancion cancionGanadora = new Cancion();
        cancionGanadora.setNombre("She Don't Give a FO");
        cancionGanadora.setReproducciones(999999);
        cancionGanadora.setIdArtista(10);

        when(repo.existsById(10)).thenReturn(true);
        
        when(repo.findById(10)).thenReturn(Optional.of(artistaEjemplo));

        when(repoCancion.findFirstByIdArtistaOrderByReproduccionesDesc(10))
            .thenReturn(Optional.of(cancionGanadora));


        artistaService.actualizarCancionMasEscuchada(10);


        assertNotNull(artistaEjemplo.getCancionMasEscuchada());
        assertEquals("She Don't Give a FO", artistaEjemplo.getCancionMasEscuchada());


        verify(repo, times(1)).save(artistaEjemplo);
    }
    
    @Test
    void actualizarCancionMasEscuchada_NoDebeTieneCanciones() {


        when(repo.existsById(10)).thenReturn(true);
        
        when(repo.findById(10)).thenReturn(Optional.of(artistaEjemplo));

        when(repoCancion.findFirstByIdArtistaOrderByReproduccionesDesc(10))
            .thenReturn(Optional.empty());


        artistaService.actualizarCancionMasEscuchada(10);


        assertNull(artistaEjemplo.getCancionMasEscuchada());


        verify(repo, never()).save(artistaEjemplo);
    }
    
    @Test
    void eliminarPorId_DebeEliminarElArtista() {
    	when(repo.existsById(10)).thenReturn(true);
    	
    	when(repo.findById(10)).thenReturn(Optional.of(artistaEjemplo));
    	
    	artistaService.eliminarPorId(10);

        assertNull(artistaEjemplo.getCancionMasEscuchada());
        
        verify(repo, times(1)).saveAndFlush(artistaEjemplo);
        
        verify(repoCancion, times(1)).deleteByIdArtista(10);
        
        verify(repo, times(1)).deleteById(10);
    }
    
    @Test
    void eliminarPorId_DebeLanzarResourceNotFoundException() {
    	when(repo.existsById(10)).thenReturn(false);

    	assertThrows(ResourceNotFoundException.class, () -> {
            artistaService.eliminarPorId(10);
        });
        
    	verify(repo, never()).deleteById(10);
    }
    
    @Test
    void guardar_DebeGuardarElNuevoArtista() {
    	when(repo.existsById(artistaEjemplo.getId())).thenReturn(false);
    	
    	artistaService.guardar(artistaEjemplo);
    	
    	 verify(repo, times(1)).save(artistaEjemplo);
    }
    
    @Test
    void guardar_DebeLanzarResourceNotFoundException() {
    	when(repo.existsById(artistaEjemplo.getId())).thenReturn(true);
    	
    	assertThrows(ResourceNotFoundException.class, () -> {
            artistaService.guardar(artistaEjemplo);
        });
    	
    	 verify(repo, never()).save(artistaEjemplo);
    }
    
}