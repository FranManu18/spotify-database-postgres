package com.spotify.api.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;

@Entity
@Table (name="lista_reproduccion")
@IdClass(ListaReproduccionId.class)
public class ListaReproduccion {
	@Id
	@Column (name="id_usuario")
	private Integer idUsuario;
	
	@Id
	private Integer id;
	
	private String nombre;
	
	private boolean publica;
	
	private boolean aleatorio;
	
	private String descripcion;

	public Integer getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(Integer idUsuario) {
		this.idUsuario = idUsuario;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public boolean isPublica() {
		return publica;
	}

	public void setPublica(boolean publica) {
		this.publica = publica;
	}

	public boolean isAleatorio() {
		return aleatorio;
	}

	public void setAleatorio(boolean aleatorio) {
		this.aleatorio = aleatorio;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	
}
