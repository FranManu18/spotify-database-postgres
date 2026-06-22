package com.spotify.api.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table (name="lista_reproduccion")
@IdClass(ListaReproduccionId.class)
public class ListaReproduccion {
	@Id
	@Column (name="id_usuario")
	private Integer idUsuario;
	
	@Id
	private Integer id;
	
	@NotBlank(message = "El nombre de la lista no puede estar vacío ni contener solo espacios.")
    @Size(max = 100, message = "El nombre de la lista no puede superar los 100 caracteres.")
	private String nombre;
	
	private boolean publica;
	
	private boolean aleatorio;
	
	 @Size(max = 200, message = "La descripcion de la lista no puede superar los 200 caracteres.")
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
