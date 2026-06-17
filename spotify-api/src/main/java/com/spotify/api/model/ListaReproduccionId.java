package com.spotify.api.model;

import java.io.Serializable;
import java.util.Objects;

public class ListaReproduccionId implements Serializable{
	private Integer id;
	private Integer idUsuario;
	
	public ListaReproduccionId() {
		super();
	}

	
	
	public ListaReproduccionId(Integer id, Integer idUsuario) {
		super();
		this.id = id;
		this.idUsuario = idUsuario;
	}



	public Integer getId() {
		return id;
	}



	public void setId(Integer id) {
		this.id = id;
	}



	public Integer getIdUsuario() {
		return idUsuario;
	}



	public void setIdUsuario(Integer idUsuario) {
		this.idUsuario = idUsuario;
	}



	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ListaReproduccionId listaId = (ListaReproduccionId) o;
        return Objects.equals(id, listaId.id) && Objects.equals(idUsuario, listaId.idUsuario);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id,idUsuario);
    }
	
}
