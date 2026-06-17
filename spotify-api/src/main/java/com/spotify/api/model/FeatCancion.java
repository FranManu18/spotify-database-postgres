package com.spotify.api.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;

@Entity
@Table (name="feat_cancion")
@IdClass(FeatCancionId.class)
public class FeatCancion {
	@Id
	@Column(name="id_artista")
	private Integer idArtista;
	
	@Id
	@Column(name="nombre_cancion")
	private String nombre;
	
	@Id
	@Column(name="id_artista_feat")
	private Integer idFeat;

	public Integer getIdArtista() {
		return idArtista;
	}

	public void setIdArtista(Integer idArtista) {
		this.idArtista = idArtista;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Integer getIdFeat() {
		return idFeat;
	}

	public void setIdFeat(Integer idFeat) {
		this.idFeat = idFeat;
	}
	
	
}
