package com.maac.api.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

import com.maac.api.domain.enumeration.TipoConteudo;

/**
 * A Beacon.
 */
@Document(collection = "beacon")
public class Beacon implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @NotNull
    @Field("local")
    private String local;

    @NotNull
    @Field("id_beacon")
    private String idBeacon;

    @Field("tipo_conteudo")
    private TipoConteudo tipoConteudo;

    @Size(max = 500)
    @Field("conteudo")
    private String conteudo;

    @Size(max = 250)
    @Field("legenda")
    private String legenda;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLocal() {
        return local;
    }

    public Beacon local(String local) {
        this.local = local;
        return this;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public String getIdBeacon() {
        return idBeacon;
    }

    public Beacon idBeacon(String idBeacon) {
        this.idBeacon = idBeacon;
        return this;
    }

    public void setIdBeacon(String idBeacon) {
        this.idBeacon = idBeacon;
    }

    public TipoConteudo getTipoConteudo() {
        return tipoConteudo;
    }

    public Beacon tipoConteudo(TipoConteudo tipoConteudo) {
        this.tipoConteudo = tipoConteudo;
        return this;
    }

    public void setTipoConteudo(TipoConteudo tipoConteudo) {
        this.tipoConteudo = tipoConteudo;
    }

    public String getConteudo() {
        return conteudo;
    }

    public Beacon conteudo(String conteudo) {
        this.conteudo = conteudo;
        return this;
    }

    public void setConteudo(String conteudo) {
        this.conteudo = conteudo;
    }

    public String getLegenda() {
        return legenda;
    }

    public Beacon legenda(String legenda) {
        this.legenda = legenda;
        return this;
    }

    public void setLegenda(String legenda) {
        this.legenda = legenda;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Beacon)) {
            return false;
        }
        return id != null && id.equals(((Beacon) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Beacon{" +
            "id=" + getId() +
            ", local='" + getLocal() + "'" +
            ", idBeacon='" + getIdBeacon() + "'" +
            ", tipoConteudo='" + getTipoConteudo() + "'" +
            ", conteudo='" + getConteudo() + "'" +
            ", legenda='" + getLegenda() + "'" +
            "}";
    }
}
