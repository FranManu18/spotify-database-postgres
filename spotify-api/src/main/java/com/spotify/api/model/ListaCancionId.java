package com.spotify.api.model;
import java.io.Serializable;
import java.util.Objects;

public class ListaCancionId implements Serializable{
	private Integer idLista;
	private Integer idUsuario;
	private String nombreCancion;
	private Integer idArtista;
	
	public ListaCancionId() {
		super();
	}

	
	
	public ListaCancionId(Integer idLista, Integer idUsuario, String nombreCancion, Integer idArtista) {
		super();
		this.idLista = idLista;
		this.idUsuario = idUsuario;
		this.nombreCancion = nombreCancion;
		this.idArtista = idArtista;
	}
	
	


	public Integer getIdLista() {
		return idLista;
	}



	public void setIdLista(Integer idLista) {
		this.idLista = idLista;
	}



	public Integer getIdUsuario() {
		return idUsuario;
	}



	public void setIdUsuario(Integer idUsuario) {
		this.idUsuario = idUsuario;
	}



	public String getNombreCancion() {
		return nombreCancion;
	}



	public void setNombreCancion(String nombreCancion) {
		this.nombreCancion = nombreCancion;
	}



	public Integer getIdArtista() {
		return idArtista;
	}



	public void setIdArtista(Integer idArtista) {
		this.idArtista = idArtista;
	}



	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ListaCancionId listaCancionId = (ListaCancionId) o;
        return Objects.equals(idLista, listaCancionId.idLista) && Objects.equals(idUsuario,listaCancionId.idUsuario) && 
        		Objects.equals(nombreCancion,listaCancionId.nombreCancion) && Objects.equals(idArtista,listaCancionId.idArtista)  ;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idLista,idUsuario,nombreCancion,idArtista);
    }
}
