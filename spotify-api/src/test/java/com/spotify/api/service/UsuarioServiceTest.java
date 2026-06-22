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
import com.spotify.api.model.Usuario;
import com.spotify.api.repository.UsuarioRepository;

@ExtendWith(MockitoExtension.class)
public class UsuarioServiceTest {
	@Mock
	private UsuarioRepository repo;
	
	@InjectMocks
	private UsuarioService usuarioService;
	
	private Usuario usuarioEjemplo;
	
	@BeforeEach
	void setUp() {
		usuarioEjemplo=new Usuario();
		usuarioEjemplo.setId(10);
		usuarioEjemplo.setNombre("Francisco");
		usuarioEjemplo.setSeguidores(100);
	}
	
	@Test
	void guardar_NoExisteUsuarioConEseId_GuardarUsuario() {
		when(repo.existsById(10)).thenReturn(false);
		
		usuarioService.guardar(usuarioEjemplo);
		
		verify(repo,times(1)).save(usuarioEjemplo);
	}
	
	@Test
	void guardar_ExisteUsuarioConEseId_DebeLanzarResourceNotFoundException() {
		when(repo.existsById(10)).thenReturn(true);
		
		assertThrows(ResourceNotFoundException.class, () -> {
            usuarioService.guardar(usuarioEjemplo);
        });
		
		verify(repo,never()).save(usuarioEjemplo);
	}
	
	@Test
	void actualizar_ExisteUsuarioConEseId_ActualizarUsuario() {
		assertEquals(100,usuarioEjemplo.getSeguidores());
		
		when(repo.existsById(10)).thenReturn(true);
		
		when(repo.findById(10)).thenReturn(Optional.of(usuarioEjemplo));
		
		Usuario actualizacion=new Usuario();
		actualizacion.setNombre("Francisco");
		actualizacion.setSeguidores(1000);
		usuarioService.actualizarUsuario(actualizacion, 10);
		assertEquals(1000,usuarioEjemplo.getSeguidores());
		
		verify(repo,times(1)).findById(10);
	}
	
	@Test
	void actualizar_NoExisteUsuarioConEseId_DebeLanzarResourceNotFoundException() {
		when(repo.existsById(10)).thenReturn(false);
		
		assertThrows(ResourceNotFoundException.class, () -> {
            usuarioService.actualizarUsuario(usuarioEjemplo, 10);
        });
		
		verify(repo,never()).findById(10);
	}
	
	@Test
	void buscarPorId_ExisteUsuarioConEseId_RetornarUsuario() {
		when(repo.existsById(10)).thenReturn(true);
		when(repo.findById(10)).thenReturn(Optional.of(usuarioEjemplo));
		
		assertEquals("Francisco",usuarioService.buscarPorId(10).getNombre());
		
		verify(repo,times(1)).findById(10);
	}
	
	@Test
	void buscarPorId_NoExisteUsuarioConEseId_DebeLanzarResourceNotFoundException() {
		when(repo.existsById(10)).thenReturn(false);
		
		assertThrows(ResourceNotFoundException.class, () -> {
            usuarioService.buscarPorId(10);
        });
		
		verify(repo,never()).findById(10);
	}
	
	@Test
	void eliminarPorId_ExisteUsuarioConEseId_EliminaUsuario() {
		when(repo.existsById(10)).thenReturn(true);
		
		usuarioService.borrarPorId(10);
		
		verify(repo,times(1)).deleteById(10);
	}
	
	@Test
	void eliminarPorId_NoExisteUsuarioConEseId_DebeLanzarResourceNotFoundException() {
		when(repo.existsById(10)).thenReturn(false);
		
		assertThrows(ResourceNotFoundException.class, () -> {
            usuarioService.borrarPorId(10);
        });
		
		verify(repo,never()).deleteById(10);
	}
}
