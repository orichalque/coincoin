package data_transfert_objects;

import com.fasterxml.jackson.annotation.*;

import javax.annotation.Generated;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by E104607D on 28/09/16.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({
        "email",
        "nom"
})
public class UtilisateurDTO implements Serializable{

    @JsonProperty("email")
    private String email;
    @JsonProperty("nom")
    private String nom;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * @return The email
     */
    @JsonProperty("email")
    public String getEmail() {
        return email;
    }

    /**
     * @param email The email
     */
    @JsonProperty("email")
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return The nom
     */
    @JsonProperty("nom")
    public String getNom() {
        return nom;
    }

    /**
     * @param nom The nom
     */
    @JsonProperty("nom")
    public void setNom(String nom) {
        this.nom = nom;
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
