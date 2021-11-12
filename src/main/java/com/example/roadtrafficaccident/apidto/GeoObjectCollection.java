
package com.example.roadtrafficaccident.apidto;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Generated;

import com.example.roadtrafficaccident.apidto.MetaDataProperty;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "metaDataProperty",
    "featureMember"
})
@Generated("jsonschema2pojo")
public class GeoObjectCollection {

    @JsonProperty("metaDataProperty")
    private MetaDataProperty metaDataProperty;
    @JsonProperty("featureMember")
    private List<FeatureMember> featureMember = null;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("metaDataProperty")
    public MetaDataProperty getMetaDataProperty() {
        return metaDataProperty;
    }

    @JsonProperty("metaDataProperty")
    public void setMetaDataProperty(MetaDataProperty metaDataProperty) {
        this.metaDataProperty = metaDataProperty;
    }

    @JsonProperty("featureMember")
    public List<FeatureMember> getFeatureMember() {
        return featureMember;
    }

    @JsonProperty("featureMember")
    public void setFeatureMember(List<FeatureMember> featureMember) {
        this.featureMember = featureMember;
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
