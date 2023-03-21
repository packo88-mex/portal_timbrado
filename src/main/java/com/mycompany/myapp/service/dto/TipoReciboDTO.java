package com.mycompany.myapp.service.dto;

import com.mycompany.myapp.domain.enumeration.Estatus;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.TipoRecibo} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class TipoReciboDTO implements Serializable {

    private Long id;

    private LocalDate fecha;

    @Size(max = 50)
    private String tipoRecibo;

    @Size(max = 10)
    private String clave;

    private Estatus estatus;

    private LocalDate fechaModificacion;

    @Size(max = 30)
    private String usuario;

    private TipoCfdiDTO tipocfdi;

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

    public String getTipoRecibo() {
        return tipoRecibo;
    }

    public void setTipoRecibo(String tipoRecibo) {
        this.tipoRecibo = tipoRecibo;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
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

    public TipoCfdiDTO getTipocfdi() {
        return tipocfdi;
    }

    public void setTipocfdi(TipoCfdiDTO tipocfdi) {
        this.tipocfdi = tipocfdi;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TipoReciboDTO)) {
            return false;
        }

        TipoReciboDTO tipoReciboDTO = (TipoReciboDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, tipoReciboDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TipoReciboDTO{" +
            "id=" + getId() +
            ", fecha='" + getFecha() + "'" +
            ", tipoRecibo='" + getTipoRecibo() + "'" +
            ", clave='" + getClave() + "'" +
            ", estatus='" + getEstatus() + "'" +
            ", fechaModificacion='" + getFechaModificacion() + "'" +
            ", usuario='" + getUsuario() + "'" +
            ", tipocfdi=" + getTipocfdi() +
            "}";
    }
}
