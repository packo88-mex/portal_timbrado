package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mycompany.myapp.domain.enumeration.Estatus;
import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A RepresentacionGrafica.
 */
@Entity
@Table(name = "representacion_grafica")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class RepresentacionGrafica implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "fecha")
    private LocalDate fecha;

    @Size(max = 100)
    @Column(name = "representacion_grafica", length = 100)
    private String representacionGrafica;

    @Enumerated(EnumType.STRING)
    @Column(name = "estatus")
    private Estatus estatus;

    @Column(name = "fecha_modificacion")
    private LocalDate fechaModificacion;

    @Size(max = 30)
    @Column(name = "usuario", length = 30)
    private String usuario;

    @ManyToOne
    @JsonIgnoreProperties(value = { "representaciongraficas", "tipocfdi" }, allowSetters = true)
    private TipoRecibo tiporecibo;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public RepresentacionGrafica id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getFecha() {
        return this.fecha;
    }

    public RepresentacionGrafica fecha(LocalDate fecha) {
        this.setFecha(fecha);
        return this;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public String getRepresentacionGrafica() {
        return this.representacionGrafica;
    }

    public RepresentacionGrafica representacionGrafica(String representacionGrafica) {
        this.setRepresentacionGrafica(representacionGrafica);
        return this;
    }

    public void setRepresentacionGrafica(String representacionGrafica) {
        this.representacionGrafica = representacionGrafica;
    }

    public Estatus getEstatus() {
        return this.estatus;
    }

    public RepresentacionGrafica estatus(Estatus estatus) {
        this.setEstatus(estatus);
        return this;
    }

    public void setEstatus(Estatus estatus) {
        this.estatus = estatus;
    }

    public LocalDate getFechaModificacion() {
        return this.fechaModificacion;
    }

    public RepresentacionGrafica fechaModificacion(LocalDate fechaModificacion) {
        this.setFechaModificacion(fechaModificacion);
        return this;
    }

    public void setFechaModificacion(LocalDate fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }

    public String getUsuario() {
        return this.usuario;
    }

    public RepresentacionGrafica usuario(String usuario) {
        this.setUsuario(usuario);
        return this;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public TipoRecibo getTiporecibo() {
        return this.tiporecibo;
    }

    public void setTiporecibo(TipoRecibo tipoRecibo) {
        this.tiporecibo = tipoRecibo;
    }

    public RepresentacionGrafica tiporecibo(TipoRecibo tipoRecibo) {
        this.setTiporecibo(tipoRecibo);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RepresentacionGrafica)) {
            return false;
        }
        return id != null && id.equals(((RepresentacionGrafica) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RepresentacionGrafica{" +
            "id=" + getId() +
            ", fecha='" + getFecha() + "'" +
            ", representacionGrafica='" + getRepresentacionGrafica() + "'" +
            ", estatus='" + getEstatus() + "'" +
            ", fechaModificacion='" + getFechaModificacion() + "'" +
            ", usuario='" + getUsuario() + "'" +
            "}";
    }
}
