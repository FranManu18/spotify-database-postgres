package com.spotify.api.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name="usuario")
public class Usuario {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@NotBlank(message = "El nombre del usuario no puede estar vacío ni contener solo espacios.")
    @Size(max = 100, message = "El nombre del usuario no puede superar los 100 caracteres.")
	private String nombre;
	
	@NotNull(message = "Los seguidores no pueden ser nulos.")
    @Min(value = 0, message = "Los seguidores no pueden ser menores a 0.")
	private Integer seguidores;

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

	public Integer getSeguidores() {
		return seguidores;
	}

	public void setSeguidores(Integer seguidores) {
		this.seguidores = seguidores;
	}
	
	
}
