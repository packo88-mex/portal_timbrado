package com.mycompany.myapp.service.dto;

import com.mycompany.myapp.domain.enumeration.Estatus;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.RepresentacionGrafica} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class RepresentacionGraficaDTO implements Serializable {

    private Long id;

    private LocalDate fecha;

    @Size(max = 100)
    private String representacionGrafica;

    private Estatus estatus;

    private LocalDate fechaModificacion;

    @Size(max = 30)
    private String usuario;

    private TipoReciboDTO tiporecibo;

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

    public String getRepresentacionGrafica() {
        return representacionGrafica;
    }

    public void setRepresentacionGrafica(String representacionGrafica) {
        this.representacionGrafica = representacionGrafica;
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

    public TipoReciboDTO getTiporecibo() {
        return tiporecibo;
    }

    public void setTiporecibo(TipoReciboDTO tiporecibo) {
        this.tiporecibo = tiporecibo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RepresentacionGraficaDTO)) {
            return false;
        }

        RepresentacionGraficaDTO representacionGraficaDTO = (RepresentacionGraficaDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, representacionGraficaDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RepresentacionGraficaDTO{" +
            "id=" + getId() +
            ", fecha='" + getFecha() + "'" +
            ", representacionGrafica='" + getRepresentacionGrafica() + "'" +
            ", estatus='" + getEstatus() + "'" +
            ", fechaModificacion='" + getFechaModificacion() + "'" +
            ", usuario='" + getUsuario() + "'" +
            ", tiporecibo=" + getTiporecibo() +
            "}";
    }
}
