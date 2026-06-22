package com.spotify.api.model;

import java.time.Duration;
import java.time.LocalTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import org.hibernate.annotations.JdbcTypeCode; 
import org.hibernate.type.SqlTypes;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.DeserializationContext;
import java.io.IOException;


@Entity
@Table(name = "cancion")
@IdClass(CancionId.class)
public class Cancion {
	@Id
    @Column(name="id_artista")
    private Integer idArtista;
	
	@Id
	@NotBlank(message = "El nombre del artista no puede estar vacío ni contener solo espacios.")
    @Size(max = 100, message = "El nombre del artista no puede superar los 100 caracteres.")
	private String nombre;
	
	@NotNull(message = "Las reproducciones no pueden ser nulas.")
    @Min(value = 0, message = "Las reproducciones no pueden ser menores a 0.")
	private Integer reproducciones;
	
	@Column(columnDefinition = "interval")
	@JdbcTypeCode(SqlTypes.INTERVAL_SECOND)
	@JsonSerialize(using = DuracionSerializer.class)
	@JsonDeserialize(using = DuracionDeserializer.class)
	private Duration duracion;
	
	private String portada;
	
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
	public Integer getReproducciones() {
		return reproducciones;
	}
	public void setReproducciones(Integer reproducciones) {
		this.reproducciones = reproducciones;
	}
	
	
	public Duration getDuracion() {
		return duracion;
	}
	public void setDuracion(Duration duracion) {
		this.duracion = duracion;
	}
	public String getPortada() {
		return portada;
	}
	public void setPortada(String portada) {
		this.portada = portada;
	}
	
	
}


class DuracionSerializer extends StdSerializer<Duration> {
    public DuracionSerializer() { super(Duration.class); }
    
    @Override
    public void serialize(Duration d, JsonGenerator gen, SerializerProvider p) throws IOException {
        long h = d.toHours();
        long m = d.toMinutesPart();
        long s = d.toSecondsPart();
        gen.writeString(String.format("%02d:%02d:%02d", h, m, s));
    }
}

class DuracionDeserializer extends StdDeserializer<Duration> {
    public DuracionDeserializer() { super(Duration.class); }
    
    @Override
    public Duration deserialize(JsonParser p, DeserializationContext c) throws IOException {
        String[] parts = p.getText().split(":");
        long segundos = Long.parseLong(parts[0]) * 3600 
                      + Long.parseLong(parts[1]) * 60 
                      + Long.parseLong(parts[2]);
        return Duration.ofSeconds(segundos);
    }
}