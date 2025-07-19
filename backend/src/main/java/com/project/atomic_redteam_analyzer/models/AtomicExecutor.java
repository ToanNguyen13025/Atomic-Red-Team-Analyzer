package com.project.atomic_redteam_analyzer.models;

import java.util.List;

import org.springframework.boot.autoconfigure.domain.EntityScan;

@EntityScan
public class AtomicExecutor {
    String name;
    String elevation_required;
    String command;
    String cleanup_command;
    String steps;
    List<String> step_list;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getElevation_required() {
        return elevation_required;
    }

    public void setElevation_required(String elevation_required) {
        this.elevation_required = elevation_required;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public String getCleanup_command() {
        return cleanup_command;
    }

    public void setCleanup_command(String cleanup_command) {
        this.cleanup_command = cleanup_command;
    }

    public String getSteps() {
        return steps;
    }

    public void setSteps(String steps) {
        this.steps = steps;
    }

    public List<String> getStep_list() {
        return step_list;
    }

    public void setStep_list(List<String> step_list) {
        this.step_list = step_list;
    }
}
