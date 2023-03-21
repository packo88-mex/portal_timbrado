package com.mycompany.myapp.service.dto;

import com.mycompany.myapp.domain.enumeration.Estatus;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.Estados} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class EstadosDTO implements Serializable {

    private Long id;

    private LocalDate fecha;

    @Size(max = 100)
    private String pais;

    @Size(max = 100)
    private String estado;

    @Size(max = 150)
    private String descripcionEstado;

    private Estatus estatus;

    private LocalDate fechaModificacion;

    @Size(max = 30)
    private String usuario;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getDescripcionEstado() {
        return descripcionEstado;
    }

    public void setDescripcionEstado(String descripcionEstado) {
        this.descripcionEstado = descripcionEstado;
    }

    public Estatus getEstatus() {
        return estatus;
    }

    public void setEstatus(Estatus estatus) {
        this.estatus = estatus;
    }

    public LocalDate getFechaModificacion() {
        return fechaModificacion;
    }

    public void setFechaModificacion(LocalDate fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EstadosDTO)) {
            return false;
        }

        EstadosDTO estadosDTO = (EstadosDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, estadosDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EstadosDTO{" +
            "id=" + getId() +
            ", fecha='" + getFecha() + "'" +
            ", pais='" + getPais() + "'" +
            ", estado='" + getEstado() + "'" +
            ", descripcionEstado='" + getDescripcionEstado() + "'" +
            ", estatus='" + getEstatus() + "'" +
            ", fechaModificacion='" + getFechaModificacion() + "'" +
            ", usuario='" + getUsuario() + "'" +
            "}";
    }
}
