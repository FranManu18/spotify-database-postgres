package com.spotify.api.model;

import java.io.Serializable;
import java.util.Objects;

public class CancionId implements Serializable {
	private Integer idArtista;
	private String nombre;
	
	public CancionId() {
		super();
	}

	public CancionId(Integer idArtista, String nombre) {
		super();
		this.idArtista = idArtista;
		this.nombre = nombre;
	}

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
	
	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CancionId cancionId = (CancionId) o;
        return Objects.equals(idArtista, cancionId.idArtista) && Objects.equals(nombre, cancionId.nombre);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idArtista, nombre);
    }
}
