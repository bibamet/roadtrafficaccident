
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
    "precision",
    "text",
    "kind",
    "Address",
    "AddressDetails"
})
@Generated("jsonschema2pojo")
public class GeocoderMetaData {

    @JsonProperty("precision")
    private String precision;
    @JsonProperty("text")
    private String text;
    @JsonProperty("kind")
    private String kind;
    @JsonProperty("Address")
    private Address address;
    @JsonProperty("AddressDetails")
    private AddressDetails addressDetails;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("precision")
    public String getPrecision() {
        return precision;
    }

    @JsonProperty("precision")
    public void setPrecision(String precision) {
        this.precision = precision;
    }

    @JsonProperty("text")
    public String getText() {
        return text;
    }

    @JsonProperty("text")
    public void setText(String text) {
        this.text = text;
    }

    @JsonProperty("kind")
    public String getKind() {
        return kind;
    }

    @JsonProperty("kind")
    public void setKind(String kind) {
        this.kind = kind;
    }

    @JsonProperty("Address")
    public Address getAddress() {
        return address;
    }

    @JsonProperty("Address")
    public void setAddress(Address address) {
        this.address = address;
    }

    @JsonProperty("AddressDetails")
    public AddressDetails getAddressDetails() {
        return addressDetails;
    }

    @JsonProperty("AddressDetails")
    public void setAddressDetails(AddressDetails addressDetails) {
        this.addressDetails = addressDetails;
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
