package com.project.atomic_redteam_analyzer.services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.atomic_redteam_analyzer.models.Technique;

@Service
public class ATTACKCoverageAnalyzer {
    private Map<String, Object> analyzer = new java.util.HashMap<>();
    @Autowired
    private GithubService ghService;

    public Map<String, Object> coverageAnalyze() throws IOException {
        List<Technique> techniques = ghService.fetchAllTechniques();
        List<Map<String, String>> mitreTechniques = ghService.fetchAllMitreAttack();

        this.analyzer.put("atomic_techniques_count", techniques.size());
        this.analyzer.put("mitre_techniques_count", mitreTechniques.size());

        this.analyzer.put("Coverage", (double) techniques.size() / mitreTechniques.size() * 100);
        return analyzer;
    }
}
