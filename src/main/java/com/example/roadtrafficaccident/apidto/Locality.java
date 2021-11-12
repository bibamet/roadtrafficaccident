
package com.example.roadtrafficaccident.apidto;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.Generated;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "LocalityName",
    "DependentLocality"
})
@Generated("jsonschema2pojo")
public class Locality {

    @JsonProperty("LocalityName")
    private String localityName;
    @JsonProperty("DependentLocality")
    private DependentLocality dependentLocality;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("LocalityName")
    public String getLocalityName() {
        return localityName;
    }

    @JsonProperty("LocalityName")
    public void setLocalityName(String localityName) {
        this.localityName = localityName;
    }

    @JsonProperty("DependentLocality")
    public DependentLocality getDependentLocality() {
        return dependentLocality;
    }

    @JsonProperty("DependentLocality")
    public void setDependentLocality(DependentLocality dependentLocality) {
        this.dependentLocality = dependentLocality;
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
