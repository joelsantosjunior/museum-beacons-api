package com.maac.api.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;
import java.time.ZonedDateTime;

/**
 * A Visitante.
 */
@Document(collection = "visitante")
public class Visitante implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @NotNull
    @Field("nome")
    private String nome;

    @Field("email")
    private String email;

    @Field("telefone")
    private String telefone;

    @NotNull
    @Field("id_celular")
    private String idCelular;

    @NotNull
    @Field("data")
    private ZonedDateTime data;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public Visitante nome(String nome) {
        this.nome = nome;
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public Visitante email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }

    public Visitante telefone(String telefone) {
        this.telefone = telefone;
        return this;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getIdCelular() {
        return idCelular;
    }

    public Visitante idCelular(String idCelular) {
        this.idCelular = idCelular;
        return this;
    }

    public void setIdCelular(String idCelular) {
        this.idCelular = idCelular;
    }

    public ZonedDateTime getData() {
        return data;
    }

    public Visitante data(ZonedDateTime data) {
        this.data = data;
        return this;
    }

    public void setData(ZonedDateTime data) {
        this.data = data;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Visitante)) {
            return false;
        }
        return id != null && id.equals(((Visitante) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Visitante{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", email='" + getEmail() + "'" +
            ", telefone='" + getTelefone() + "'" +
            ", idCelular='" + getIdCelular() + "'" +
            ", data='" + getData() + "'" +
            "}";
    }
}
