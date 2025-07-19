package com.project.atomic_redteam_analyzer.models;

import java.util.List;
import java.util.Map;

import org.springframework.boot.autoconfigure.domain.EntityScan;

@EntityScan
public class AtomicTest {
    String name;
    String auto_generated_guid;
    String description;
    List<String> supported_platforms;
    Map<String, AtomicInputArgument> input_arguments;
    String dependency_executor_name;
    List<AtomicDependency> dependencies;
    AtomicExecutor executor;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuto_generated_guid() {
        return auto_generated_guid;
    }

    public void setAuto_generated_guid(String auto_generated_guid) {
        this.auto_generated_guid = auto_generated_guid;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getSupported_platforms() {
        return supported_platforms;
    }

    public void setSupported_platforms(List<String> supported_platforms) {
        this.supported_platforms = supported_platforms;
    }

    public Map<String, AtomicInputArgument> getInput_arguments() {
        return input_arguments;
    }

    public void setInput_arguments(Map<String, AtomicInputArgument> input_arguments) {
        this.input_arguments = input_arguments;
    }

    public String getDependency_executor_name() {
        return dependency_executor_name;
    }

    public void setDependency_executor_name(String dependency_executor_name) {
        this.dependency_executor_name = dependency_executor_name;
    }

    public List<AtomicDependency> getDependencies() {
        return dependencies;
    }

    public void setDependencies(List<AtomicDependency> dependencies) {
        this.dependencies = dependencies;
    }

    public AtomicExecutor getExecutor() {
        return executor;
    }

    public void setExecutor(AtomicExecutor executor) {
        this.executor = executor;
    }
}
