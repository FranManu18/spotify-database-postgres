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
import com.spotify.api.model.ListaCancion;
import com.spotify.api.model.ListaReproduccion;
import com.spotify.api.model.ListaReproduccionId;
import com.spotify.api.repository.ListaReproduccionRepository;
import com.spotify.api.repository.UsuarioRepository;

@ExtendWith(MockitoExtension.class)
public class ListaReproduccionTest {
	@Mock
	private ListaReproduccionRepository repo;
	
	@Mock
	private UsuarioRepository repoUsuario;
	
	@InjectMocks
	private ListaReproduccionService listaService;
	
	private ListaReproduccion listaEjemplo;
	
	private ListaReproduccionId idListaEjemplo;
	
	@BeforeEach
	void setUp() {
		listaEjemplo=new ListaReproduccion();
		listaEjemplo.setAleatorio(false);
		listaEjemplo.setPublica(true);
		listaEjemplo.setNombre("Lista Ejemplo");
		listaEjemplo.setDescripcion("");
		idListaEjemplo=new ListaReproduccionId(3,3);
	}
	
	@Test
    void crearLista_CuandoLaListaExiste_DebeRetornarLaNuevaLista() {
		when(repoUsuario.existsById(2)).thenReturn(true);
		when(repo.findMaxIdByIdUsuario(2)).thenReturn(3);
		when(repo.save(any(ListaReproduccion.class))).thenReturn(listaEjemplo);
		
		ListaReproduccion nuevaLista=listaService.crearLista(2, listaEjemplo);
		assertEquals(2,nuevaLista.getIdUsuario());
		assertEquals(4,nuevaLista.getId());
		assertEquals("Lista Ejemplo",nuevaLista.getNombre());
		
		verify(repo, times(1)).save(any(ListaReproduccion.class));
	}
	
	@Test
    void crearLista_CuandoLaListaNoExiste_DebeLanzarResourceNotFoundException() {
		when(repoUsuario.existsById(2)).thenReturn(false);

		assertThrows(ResourceNotFoundException.class, () -> {
            listaService.crearLista(2, listaEjemplo);
        });
		
		verify(repo,never()).save(listaEjemplo);
	}
	
	
	@Test
    void actualizar_CuandoLaListaExiste_DebeRetornarLaListaActualizada() {
		when(repo.existsById(idListaEjemplo)).thenReturn(true);
		when(repo.findById(idListaEjemplo)).thenReturn(Optional.of(listaEjemplo));
		
		ListaReproduccion listaActualizada=listaEjemplo;
		listaActualizada.setDescripcion("Descripcion");
		listaActualizada=listaService.actualizar(3, 3, listaActualizada);
		
		assertEquals("Descripcion",listaEjemplo.getDescripcion());
		
		verify(repo,times(1)).save(listaEjemplo);
	}
	
	@Test
    void actualizar_CuandoLaListaNoExiste_DebeLanzarResourceNotFoundException() {
		when(repo.existsById(idListaEjemplo)).thenReturn(false);
		
		assertThrows(ResourceNotFoundException.class, () -> {
            listaService.actualizar(3, 3, listaEjemplo);
        });
		
		verify(repo,never()).save(listaEjemplo);
	}
	
	
	@Test
    void borrarLista_CuandoLaListaExiste_DebeBorrarLaLista() {
		when(repo.existsById(idListaEjemplo)).thenReturn(true);
		when(repo.findById(idListaEjemplo)).thenReturn(Optional.of(listaEjemplo));
		
		listaService.borrarLista(3,3);
		
		verify(repo,times(1)).deleteById(idListaEjemplo);
	}
	
	@Test
    void borrarLista_CuandoLaListaNoExiste_DebeLanzarResourceNotFoundException() {
		when(repo.existsById(idListaEjemplo)).thenReturn(false);
		
		assertThrows(ResourceNotFoundException.class, () -> {
            listaService.borrarLista(3, 3);
        });
		
		verify(repo,never()).deleteById(idListaEjemplo);
	}
	
	@Test
    void buscarPorId_CuandoLaListaExiste_DebeRetornarListaEncontrada() {
		when(repo.existsById(idListaEjemplo)).thenReturn(true);
		when(repo.findById(idListaEjemplo)).thenReturn(Optional.of(listaEjemplo));
		
		ListaReproduccion resultado=listaService.buscarPorId(3, 3);
		assertEquals("Lista Ejemplo",resultado.getNombre());
		
		verify(repo,times(1)).findById(idListaEjemplo);
	}
	
	@Test
    void buscarPorId_CuandoLaListaNoExiste_DebeLanzarResourceNotFoundException() {
		when(repo.existsById(idListaEjemplo)).thenReturn(false);
		
		assertThrows(ResourceNotFoundException.class, () -> {
            listaService.buscarPorId(3, 3);
        });
		
		verify(repo,never()).findById(idListaEjemplo);
	}
}
