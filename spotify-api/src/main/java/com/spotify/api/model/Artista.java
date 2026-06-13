package com.spotify.api.model;

import jakarta.persistence.*;

@Entity
@Table(name = "artista")

public class Artista {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String nombre;
    private Integer oyentes;
    private Boolean verificado;
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
