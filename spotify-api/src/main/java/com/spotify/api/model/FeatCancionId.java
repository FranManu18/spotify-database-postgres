package com.spotify.api.model;
import java.io.Serializable;
import java.util.Objects;


public class FeatCancionId implements Serializable {
	private Integer idArtista;
	
	private String nombre;
	
	private Integer idFeat;

	public FeatCancionId() {
		super();
	}

	public FeatCancionId(Integer idArtista, String nombre, Integer idFeat) {
		super();
		this.idArtista = idArtista;
		this.nombre = nombre;
		this.idFeat = idFeat;
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

	public Integer getIdFeat() {
		return idFeat;
	}

	public void setIdFeat(Integer idFeat) {
		this.idFeat = idFeat;
	}
	
	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FeatCancionId featId = (FeatCancionId) o;
        return Objects.equals(idArtista, featId.idArtista) && Objects.equals(nombre, featId.nombre)&& Objects.equals(idFeat, featId.idFeat);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idArtista, nombre,idFeat);
    }
}
