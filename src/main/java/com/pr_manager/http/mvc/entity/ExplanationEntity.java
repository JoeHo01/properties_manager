package com.pr_manager.http.mvc.entity;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "properties_explanation")
public class ExplanationEntity {
    private String scope;
    private String name;
    private String description;
    private String used;

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUsed() {
        return used;
    }

    public void setUsed(String used) {
        this.used = used;
    }
}
