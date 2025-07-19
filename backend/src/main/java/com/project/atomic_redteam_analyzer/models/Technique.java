package com.project.atomic_redteam_analyzer.models;

import java.util.List;

import org.springframework.boot.autoconfigure.domain.EntityScan;

@EntityScan
public class Technique
        implements Comparable<Technique> {
    String attack_technique;
    String display_name;
    List<AtomicTest> atomic_tests;

    public String getAttack_technique() {
        return attack_technique;
    }

    public String getDisplay_name() {
        return display_name;
    }

    public List<AtomicTest> getAtomic_tests() {
        return atomic_tests;
    }

    public void setAttack_technique(String attack_technique) {
        this.attack_technique = attack_technique;
    }

    public void setDisplay_name(String display_name) {
        this.display_name = display_name;
    }

    public void setAtomic_tests(List<AtomicTest> atomic_tests) {
        this.atomic_tests = atomic_tests;
    }

    @Override
    public int compareTo(Technique tech) {
        if (this.getAttack_technique() == null || tech.getAttack_technique() == null) {
            return 0;
        }
        return getAttack_technique().compareTo(tech.getAttack_technique());
    }
}
