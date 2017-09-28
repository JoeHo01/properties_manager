package com.pr_manager.http.mvc.entity;

import com.google.gson.Gson;
import net.sf.json.JSONObject;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Map;

@Document(collection = "properties_bundle")
public class PropertiesEntity {
    private String scope;
    private String region;
    private String brand;
    private JSONObject properties;

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public JSONObject getProperties() {
        return properties;
    }

    public void setProperties(JSONObject properties) {
        this.properties = properties;
    }
}
