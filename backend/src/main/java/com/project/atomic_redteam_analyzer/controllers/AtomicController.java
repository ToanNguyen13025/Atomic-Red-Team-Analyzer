package com.project.atomic_redteam_analyzer.controllers;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Collections;

import org.kohsuke.github.GitHub;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.info.ProjectInfoProperties.Git;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;
import com.project.atomic_redteam_analyzer.models.Technique;
import com.project.atomic_redteam_analyzer.services.ATTACKCoverageAnalyzer;
import com.project.atomic_redteam_analyzer.services.ExcelExportService;
import com.project.atomic_redteam_analyzer.services.GithubService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/api/atomic")
public class AtomicController {
    @Value("${excel.output.path}")
    private String excelOutputPath;

    @Autowired
    private GithubService githubService;
    @Autowired
    private ExcelExportService excelExportService;
    @Autowired
    private ATTACKCoverageAnalyzer analyzer;

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

    @PostMapping("/techniques/excels")
    public void generateExcels() {
        try {
            excelExportService.exportToExcel(githubService.fetchAllTechniques());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @GetMapping("/techniques/export")
    public ResponseEntity<Resource> downloadExcels() throws Exception {
        String path = excelOutputPath;
        File file = new File(path);
        System.out.println("✅ Đang truy cập file tại: " + file.getAbsolutePath());

        if (!file.exists()) {
            System.out.println("no exist");
            excelExportService.exportToExcel(githubService.fetchAllTechniques());
        }
        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=techniques.xlsx")
                .contentType(
                        MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                .contentLength(file.length())
                .body(resource);
    }

    @GetMapping("/techniques/mitre")
    public List<Map<String, String>> getMitreTechniques() throws IOException {
        return githubService.fetchAllMitreAttack();
    }

    @GetMapping("/techniques/analyze")
    public Map<String, Object> coverageAnalyze() throws IOException {
        return analyzer.coverageAnalyze();
    }

}
