package com.legion.tests.data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

    @JsonIgnoreProperties(ignoreUnknown = true)
public class Location {

        @JsonProperty("LocationNames")
        private List<String> LocationNames;
        public List<String> getLocationNames() {
            return LocationNames;
        }

        public void setLocationNames(List<String> locationNames) {
            this.LocationNames = locationNames;
        }

    }

