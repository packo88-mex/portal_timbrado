package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mycompany.myapp.domain.enumeration.Estatus;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A TipoCfdi.
 */
@Entity
@Table(name = "tipo_cfdi")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class TipoCfdi implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "fecha")
    private LocalDate fecha;

    @Size(max = 50)
    @Column(name = "tipo_cfdi", length = 50)
    private String tipoCfdi;

    @Enumerated(EnumType.STRING)
    @Column(name = "estatus")
    private Estatus estatus;

    @Column(name = "fecha_modificacion")
    private LocalDate fechaModificacion;

    @Size(max = 30)
    @Column(name = "usuario", length = 30)
    private String usuario;

    @OneToMany(mappedBy = "tipocfdi")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "representaciongraficas", "tipocfdi" }, allowSetters = true)
    private Set<TipoRecibo> tiporecibos = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public TipoCfdi id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getFecha() {
        return this.fecha;
    }

    public TipoCfdi fecha(LocalDate fecha) {
        this.setFecha(fecha);
        return this;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public String getTipoCfdi() {
        return this.tipoCfdi;
    }

    public TipoCfdi tipoCfdi(String tipoCfdi) {
        this.setTipoCfdi(tipoCfdi);
        return this;
    }

    public void setTipoCfdi(String tipoCfdi) {
        this.tipoCfdi = tipoCfdi;
    }

    public Estatus getEstatus() {
        return this.estatus;
    }

    public TipoCfdi estatus(Estatus estatus) {
        this.setEstatus(estatus);
        return this;
    }

    public void setEstatus(Estatus estatus) {
        this.estatus = estatus;
    }

    public LocalDate getFechaModificacion() {
        return this.fechaModificacion;
    }

    public TipoCfdi fechaModificacion(LocalDate fechaModificacion) {
        this.setFechaModificacion(fechaModificacion);
        return this;
    }

    public void setFechaModificacion(LocalDate fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }

    public String getUsuario() {
        return this.usuario;
    }

    public TipoCfdi usuario(String usuario) {
        this.setUsuario(usuario);
        return this;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public Set<TipoRecibo> getTiporecibos() {
        return this.tiporecibos;
    }

    public void setTiporecibos(Set<TipoRecibo> tipoRecibos) {
        if (this.tiporecibos != null) {
            this.tiporecibos.forEach(i -> i.setTipocfdi(null));
        }
        if (tipoRecibos != null) {
            tipoRecibos.forEach(i -> i.setTipocfdi(this));
        }
        this.tiporecibos = tipoRecibos;
    }

    public TipoCfdi tiporecibos(Set<TipoRecibo> tipoRecibos) {
        this.setTiporecibos(tipoRecibos);
        return this;
    }

    public TipoCfdi addTiporecibo(TipoRecibo tipoRecibo) {
        this.tiporecibos.add(tipoRecibo);
        tipoRecibo.setTipocfdi(this);
        return this;
    }

    public TipoCfdi removeTiporecibo(TipoRecibo tipoRecibo) {
        this.tiporecibos.remove(tipoRecibo);
        tipoRecibo.setTipocfdi(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TipoCfdi)) {
            return false;
        }
        return id != null && id.equals(((TipoCfdi) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TipoCfdi{" +
            "id=" + getId() +
            ", fecha='" + getFecha() + "'" +
            ", tipoCfdi='" + getTipoCfdi() + "'" +
            ", estatus='" + getEstatus() + "'" +
            ", fechaModificacion='" + getFechaModificacion() + "'" +
            ", usuario='" + getUsuario() + "'" +
            "}";
    }
}
