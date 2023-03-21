package com.mycompany.myapp.domain;

import com.mycompany.myapp.domain.enumeration.Estatus;
import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A EstatusCfdi.
 */
@Entity
@Table(name = "estatus_cfdi")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class EstatusCfdi implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "fecha")
    private LocalDate fecha;

    @Size(max = 50)
    @Column(name = "descripcion_estatus_cfdi", length = 50)
    private String descripcionEstatusCfdi;

    @Enumerated(EnumType.STRING)
    @Column(name = "estatus")
    private Estatus estatus;

    @Column(name = "fecha_modificacion")
    private LocalDate fechaModificacion;

    @Size(max = 30)
    @Column(name = "usuario", length = 30)
    private String usuario;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public EstatusCfdi id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getFecha() {
        return this.fecha;
    }

    public EstatusCfdi fecha(LocalDate fecha) {
        this.setFecha(fecha);
        return this;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public String getDescripcionEstatusCfdi() {
        return this.descripcionEstatusCfdi;
    }

    public EstatusCfdi descripcionEstatusCfdi(String descripcionEstatusCfdi) {
        this.setDescripcionEstatusCfdi(descripcionEstatusCfdi);
        return this;
    }

    public void setDescripcionEstatusCfdi(String descripcionEstatusCfdi) {
        this.descripcionEstatusCfdi = descripcionEstatusCfdi;
    }

    public Estatus getEstatus() {
        return this.estatus;
    }

    public EstatusCfdi estatus(Estatus estatus) {
        this.setEstatus(estatus);
        return this;
    }

    public void setEstatus(Estatus estatus) {
        this.estatus = estatus;
    }

    public LocalDate getFechaModificacion() {
        return this.fechaModificacion;
    }

    public EstatusCfdi fechaModificacion(LocalDate fechaModificacion) {
        this.setFechaModificacion(fechaModificacion);
        return this;
    }

    public void setFechaModificacion(LocalDate fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }

    public String getUsuario() {
        return this.usuario;
    }

    public EstatusCfdi usuario(String usuario) {
        this.setUsuario(usuario);
        return this;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EstatusCfdi)) {
            return false;
        }
        return id != null && id.equals(((EstatusCfdi) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EstatusCfdi{" +
            "id=" + getId() +
            ", fecha='" + getFecha() + "'" +
            ", descripcionEstatusCfdi='" + getDescripcionEstatusCfdi() + "'" +
            ", estatus='" + getEstatus() + "'" +
            ", fechaModificacion='" + getFechaModificacion() + "'" +
            ", usuario='" + getUsuario() + "'" +
            "}";
    }
}
