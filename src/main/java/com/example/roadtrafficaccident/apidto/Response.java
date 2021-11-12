
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
    "GeoObjectCollection"
})
@Generated("jsonschema2pojo")
public class Response {

    @JsonProperty("GeoObjectCollection")
    private GeoObjectCollection geoObjectCollection;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("GeoObjectCollection")
    public GeoObjectCollection getGeoObjectCollection() {
        return geoObjectCollection;
    }

    @JsonProperty("GeoObjectCollection")
    public void setGeoObjectCollection(GeoObjectCollection geoObjectCollection) {
        this.geoObjectCollection = geoObjectCollection;
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
