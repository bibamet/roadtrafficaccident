
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
    "AdministrativeAreaName",
    "SubAdministrativeArea"
})
@Generated("jsonschema2pojo")
public class AdministrativeArea {

    @JsonProperty("AdministrativeAreaName")
    private String administrativeAreaName;
    @JsonProperty("SubAdministrativeArea")
    private SubAdministrativeArea subAdministrativeArea;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("AdministrativeAreaName")
    public String getAdministrativeAreaName() {
        return administrativeAreaName;
    }

    @JsonProperty("AdministrativeAreaName")
    public void setAdministrativeAreaName(String administrativeAreaName) {
        this.administrativeAreaName = administrativeAreaName;
    }

    @JsonProperty("SubAdministrativeArea")
    public SubAdministrativeArea getSubAdministrativeArea() {
        return subAdministrativeArea;
    }

    @JsonProperty("SubAdministrativeArea")
    public void setSubAdministrativeArea(SubAdministrativeArea subAdministrativeArea) {
        this.subAdministrativeArea = subAdministrativeArea;
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
