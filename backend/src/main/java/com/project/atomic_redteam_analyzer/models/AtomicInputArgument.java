package com.project.atomic_redteam_analyzer.models;

import org.springframework.boot.autoconfigure.domain.EntityScan;

@EntityScan
public class AtomicInputArgument {
    String description;
    String type;
    String defaultValue;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefault(String defaultValue) {
        this.defaultValue = defaultValue;
    }
}
