
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
    "AddressLine",
    "CountryNameCode",
    "CountryName",
    "AdministrativeArea"
})
@Generated("jsonschema2pojo")
public class Country {

    @JsonProperty("AddressLine")
    private String addressLine;
    @JsonProperty("CountryNameCode")
    private String countryNameCode;
    @JsonProperty("CountryName")
    private String countryName;
    @JsonProperty("AdministrativeArea")
    private AdministrativeArea administrativeArea;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("AddressLine")
    public String getAddressLine() {
        return addressLine;
    }

    @JsonProperty("AddressLine")
    public void setAddressLine(String addressLine) {
        this.addressLine = addressLine;
    }

    @JsonProperty("CountryNameCode")
    public String getCountryNameCode() {
        return countryNameCode;
    }

    @JsonProperty("CountryNameCode")
    public void setCountryNameCode(String countryNameCode) {
        this.countryNameCode = countryNameCode;
    }

    @JsonProperty("CountryName")
    public String getCountryName() {
        return countryName;
    }

    @JsonProperty("CountryName")
    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    @JsonProperty("AdministrativeArea")
    public AdministrativeArea getAdministrativeArea() {
        return administrativeArea;
    }

    @JsonProperty("AdministrativeArea")
    public void setAdministrativeArea(AdministrativeArea administrativeArea) {
        this.administrativeArea = administrativeArea;
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
