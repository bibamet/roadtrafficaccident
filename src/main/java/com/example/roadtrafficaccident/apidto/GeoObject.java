
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
    "metaDataProperty",
    "name",
    "description",
    "boundedBy",
    "Point"
})
@Generated("jsonschema2pojo")
public class GeoObject {

    @JsonProperty("metaDataProperty")
    private MetaDataProperty__1 metaDataProperty;
    @JsonProperty("name")
    private String name;
    @JsonProperty("description")
    private String description;
    @JsonProperty("boundedBy")
    private BoundedBy boundedBy;
    @JsonProperty("Point")
    private Point point;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("metaDataProperty")
    public MetaDataProperty__1 getMetaDataProperty() {
        return metaDataProperty;
    }

    @JsonProperty("metaDataProperty")
    public void setMetaDataProperty(MetaDataProperty__1 metaDataProperty) {
        this.metaDataProperty = metaDataProperty;
    }

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty("description")
    public String getDescription() {
        return description;
    }

    @JsonProperty("description")
    public void setDescription(String description) {
        this.description = description;
    }

    @JsonProperty("boundedBy")
    public BoundedBy getBoundedBy() {
        return boundedBy;
    }

    @JsonProperty("boundedBy")
    public void setBoundedBy(BoundedBy boundedBy) {
        this.boundedBy = boundedBy;
    }

    @JsonProperty("Point")
    public Point getPoint() {
        return point;
    }

    @JsonProperty("Point")
    public void setPoint(Point point) {
        this.point = point;
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
