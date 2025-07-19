package com.project.atomic_redteam_analyzer.models;

import org.springframework.boot.autoconfigure.domain.EntityScan;

@EntityScan
public class AtomicDependency {
    String description;
    String prereq_command;
    String get_prereq_command;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPrereq_command() {
        return prereq_command;
    }

    public void setPrereq_command(String prereq_command) {
        this.prereq_command = prereq_command;
    }

    public String getGet_prereq_command() {
        return get_prereq_command;
    }

    public void setGet_prereq_command(String get_prereq_command) {
        this.get_prereq_command = get_prereq_command;
    }
}
