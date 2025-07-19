package com.project.atomic_redteam_analyzer.controllers;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Collections;

import org.kohsuke.github.GitHub;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.info.ProjectInfoProperties.Git;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;
import com.project.atomic_redteam_analyzer.models.Technique;
import com.project.atomic_redteam_analyzer.services.ExcelExportService;
import com.project.atomic_redteam_analyzer.services.GithubService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/api/atomic")
public class AtomicController {
    @Autowired
    private GithubService githubService;
    @Autowired
    private ExcelExportService excelExportService;

    @GetMapping("/techniques")
    public List<Technique> getTechniques() {
        System.out.println("Start fetching techniques from GitHub...");

        try {
            List<Technique> techniques = githubService.fetchAllTechniques();

            Collections.sort(techniques);
            return techniques;
        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch techniques: " + e.getMessage(), e);
        }
    }

    @GetMapping("/techniques/excels")
    public String getExportExcels() {
        try {
            excelExportService.exportToExcel(githubService.fetchAllTechniques());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "Export to Excel completed successfully.";
    }

    @GetMapping("/techniques/mitre")
    public List<Map<String, String>> getMitreTechniques() throws IOException {
        return githubService.fetchAllMitreAttack();
    }

}
