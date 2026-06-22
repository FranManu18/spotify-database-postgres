package com.spotify.api.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "artista")

public class Artista {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
	
	@NotBlank(message = "El nombre del artista no puede estar vacío ni contener solo espacios.")
    @Size(max = 100, message = "El nombre del artista no puede superar los 100 caracteres.")
    private String nombre;
	
	@NotNull(message = "Los oyentes mensuales no pueden ser nulos.")
    @Min(value = 0, message = "Los oyentes mensuales no pueden ser menores a 0.")
    private Integer oyentes;
	
    private Boolean verificado;
    
    @NotNull(message = "La cantidad de seguidores no puede ser nula.")
    @Min(value = 0, message = "La cantidad de seguidores no puede ser menor a 0.")
    private Integer seguidores;

    @Column(name = "cancion_mas_escuchada")
    private String cancionMasEscuchada;

	

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

	public Integer getOyentes() {
		return oyentes;
	}

	public void setOyentes(Integer oyentes) {
		this.oyentes = oyentes;
	}

	public Boolean getVerificado() {
		return verificado;
	}

	public void setVerificado(Boolean verificado) {
		this.verificado = verificado;
	}

	public Integer getSeguidores() {
		return seguidores;
	}

	public void setSeguidores(Integer seguidores) {
		this.seguidores = seguidores;
	}

	public String getCancionMasEscuchada() {
		return cancionMasEscuchada;
	}

	public void setCancionMasEscuchada(String cancionMasEscuchada) {
		this.cancionMasEscuchada = cancionMasEscuchada;
	}
    
    
}
