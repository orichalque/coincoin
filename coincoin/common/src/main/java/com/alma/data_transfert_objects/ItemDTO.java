package com.alma.data_transfert_objects;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.Generated;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


/**
 * Created by Thibault on 27/09/16.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({
        "prix",
        "nom",
        "description"
})
public class ItemDTO {

    @JsonProperty("prix")
    private Double prix;
    @JsonProperty("nom")
    private String nom;
    @JsonProperty("description")
    private String description;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     *
     * @return
     * The prix
     */
    @JsonProperty("prix")
    public Double getPrix() {
        return prix;
    }

    /**
     *
     * @param prix
     * The prix
     */
    @JsonProperty("prix")
    public void setPrix(Double prix) {
        this.prix = prix;
    }

    /**
     *
     * @return
     * The nom
     */
    @JsonProperty("nom")
    public String getNom() {
        return nom;
    }

    /**
     *
     * @param nom
     * The nom
     */
    @JsonProperty("nom")
    public void setNom(String nom) {
        this.nom = nom;
    }

    /**
     *
     * @return
     * The description
     */
    @JsonProperty("description")
    public String getDescription() {
        return description;
    }

    /**
     *
     * @param description
     * The description
     */
    @JsonProperty("description")
    public void setDescription(String description) {
        this.description = description;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}

