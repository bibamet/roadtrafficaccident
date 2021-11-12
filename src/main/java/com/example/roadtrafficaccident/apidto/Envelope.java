
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
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "lowerCorner",
    "upperCorner"
})
@Component
@Generated("jsonschema2pojo")
public class Envelope {

    @JsonProperty("lowerCorner")
    private String lowerCorner;
    @JsonProperty("upperCorner")
    private String upperCorner;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("lowerCorner")
    public String getLowerCorner() {
        return lowerCorner;
    }

    @JsonProperty("lowerCorner")
    public void setLowerCorner(String lowerCorner) {
        this.lowerCorner = lowerCorner;
    }

    @JsonProperty("upperCorner")
    public String getUpperCorner() {
        return upperCorner;
    }

    @JsonProperty("upperCorner")
    public void setUpperCorner(String upperCorner) {
        this.upperCorner = upperCorner;
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
