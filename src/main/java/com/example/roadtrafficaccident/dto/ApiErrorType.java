package com.example.roadtrafficaccident.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum ApiErrorType {
    @JsonProperty("validation")
    VALIDATION,
    @JsonProperty("business")
    BUSINESS,
    @JsonProperty("system")
    SYSTEM;
}
