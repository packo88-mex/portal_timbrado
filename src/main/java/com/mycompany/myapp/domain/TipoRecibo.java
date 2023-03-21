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
 * A TipoRecibo.
 */
@Entity
@Table(name = "tipo_recibo")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class TipoRecibo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "fecha")
    private LocalDate fecha;

    @Size(max = 50)
    @Column(name = "tipo_recibo", length = 50)
    private String tipoRecibo;

    @Size(max = 10)
    @Column(name = "clave", length = 10)
    private String clave;

    @Enumerated(EnumType.STRING)
    @Column(name = "estatus")
    private Estatus estatus;

    @Column(name = "fecha_modificacion")
    private LocalDate fechaModificacion;

    @Size(max = 30)
    @Column(name = "usuario", length = 30)
    private String usuario;

    @OneToMany(mappedBy = "tiporecibo")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "tiporecibo" }, allowSetters = true)
    private Set<RepresentacionGrafica> representaciongraficas = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = { "tiporecibos" }, allowSetters = true)
    private TipoCfdi tipocfdi;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public TipoRecibo id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getFecha() {
        return this.fecha;
    }

    public TipoRecibo fecha(LocalDate fecha) {
        this.setFecha(fecha);
        return this;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public String getTipoRecibo() {
        return this.tipoRecibo;
    }

    public TipoRecibo tipoRecibo(String tipoRecibo) {
        this.setTipoRecibo(tipoRecibo);
        return this;
    }

    public void setTipoRecibo(String tipoRecibo) {
        this.tipoRecibo = tipoRecibo;
    }

    public String getClave() {
        return this.clave;
    }

    public TipoRecibo clave(String clave) {
        this.setClave(clave);
        return this;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public Estatus getEstatus() {
        return this.estatus;
    }

    public TipoRecibo estatus(Estatus estatus) {
        this.setEstatus(estatus);
        return this;
    }

    public void setEstatus(Estatus estatus) {
        this.estatus = estatus;
    }

    public LocalDate getFechaModificacion() {
        return this.fechaModificacion;
    }

    public TipoRecibo fechaModificacion(LocalDate fechaModificacion) {
        this.setFechaModificacion(fechaModificacion);
        return this;
    }

    public void setFechaModificacion(LocalDate fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }

    public String getUsuario() {
        return this.usuario;
    }

    public TipoRecibo usuario(String usuario) {
        this.setUsuario(usuario);
        return this;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public Set<RepresentacionGrafica> getRepresentaciongraficas() {
        return this.representaciongraficas;
    }

    public void setRepresentaciongraficas(Set<RepresentacionGrafica> representacionGraficas) {
        if (this.representaciongraficas != null) {
            this.representaciongraficas.forEach(i -> i.setTiporecibo(null));
        }
        if (representacionGraficas != null) {
            representacionGraficas.forEach(i -> i.setTiporecibo(this));
        }
        this.representaciongraficas = representacionGraficas;
    }

    public TipoRecibo representaciongraficas(Set<RepresentacionGrafica> representacionGraficas) {
        this.setRepresentaciongraficas(representacionGraficas);
        return this;
    }

    public TipoRecibo addRepresentaciongrafica(RepresentacionGrafica representacionGrafica) {
        this.representaciongraficas.add(representacionGrafica);
        representacionGrafica.setTiporecibo(this);
        return this;
    }

    public TipoRecibo removeRepresentaciongrafica(RepresentacionGrafica representacionGrafica) {
        this.representaciongraficas.remove(representacionGrafica);
        representacionGrafica.setTiporecibo(null);
        return this;
    }

    public TipoCfdi getTipocfdi() {
        return this.tipocfdi;
    }

    public void setTipocfdi(TipoCfdi tipoCfdi) {
        this.tipocfdi = tipoCfdi;
    }

    public TipoRecibo tipocfdi(TipoCfdi tipoCfdi) {
        this.setTipocfdi(tipoCfdi);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TipoRecibo)) {
            return false;
        }
        return id != null && id.equals(((TipoRecibo) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TipoRecibo{" +
            "id=" + getId() +
            ", fecha='" + getFecha() + "'" +
            ", tipoRecibo='" + getTipoRecibo() + "'" +
            ", clave='" + getClave() + "'" +
            ", estatus='" + getEstatus() + "'" +
            ", fechaModificacion='" + getFechaModificacion() + "'" +
            ", usuario='" + getUsuario() + "'" +
            "}";
    }
}
