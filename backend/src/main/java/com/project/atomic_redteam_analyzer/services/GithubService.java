package com.project.atomic_redteam_analyzer.services;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.io.IOUtils;
import org.apache.tomcat.util.json.JSONParser;
import org.kohsuke.github.GHContent;
import org.kohsuke.github.GHRepository;
import org.kohsuke.github.GitHub;
import org.kohsuke.github.GitHubBuilder;
import org.springframework.stereotype.Service;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.project.atomic_redteam_analyzer.config.GitHubOAuthConfig;
import com.project.atomic_redteam_analyzer.models.Technique;

import org.springframework.boot.jackson.JsonObjectDeserializer;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

@Service
public class GithubService {
    private GitHub github;
    private GHRepository repository;

    public GithubService() throws java.io.IOException {
        this.github = new GitHubBuilder().withOAuthToken(GitHubOAuthConfig.getGitHubToken()).build();
        this.repository = github.getRepository("redcanaryco/atomic-red-team");

    }

    public List<Technique> fetchAllTechniques() throws IOException {
        List<Technique> techniques = java.util.Collections.synchronizedList(new java.util.ArrayList<>());
        List<GHContent> atomicsDir = repository.getDirectoryContent("atomics");

        ExecutorService executor = Executors.newFixedThreadPool(100);

        for (GHContent techniqueFolder : atomicsDir) {
            executor.submit(() -> {
                if (techniqueFolder.isDirectory()) {
                    try {
                        String name = techniqueFolder.getName();
                        String yamlPath = techniqueFolder.getPath() + "/" + name + ".yaml";
                        GHContent yamlFile = repository.getFileContent(yamlPath);

                        InputStream inputStream = yamlFile.read();
                        Yaml yaml = new Yaml(new Constructor(Technique.class, null));
                        Technique technique = yaml.load(inputStream);
                        if (technique != null) {
                            techniques.add(technique);

                        }
                    } catch (IOException e) {
                        System.err.println(
                                "Error fetching technique from " + techniqueFolder.getPath() + "/"
                                        + techniqueFolder.getName() + ".yaml" + ": " + e.getMessage());
                    }
                }
            });
        }
        executor.shutdown();
        try {
            if (!executor.awaitTermination(3, java.util.concurrent.TimeUnit.MINUTES)) {
                executor.shutdownNow();
            }
        } catch (InterruptedException e) {
            executor.shutdownNow();
            Thread.currentThread().interrupt();
        }
        return techniques;
    }

    public List<Map<String, String>> fetchAllMitreAttack() throws IOException {
        InputStream is1 = getClass().getClassLoader().getResourceAsStream("static/enterprise-attack-17.1-extract.json");
        InputStream is2 = getClass().getClassLoader().getResourceAsStream("static/ics-attack-17.1.json");
        InputStream is3 = getClass().getClassLoader().getResourceAsStream("static/mobile-attack-17.1.json");

        ObjectMapper objectMapper = new ObjectMapper();
        List<Map<String, String>> output = new ArrayList<Map<String, String>>();

        try {
            List<Map<String, String>> node1 = objectMapper.readValue(is1,
                    new TypeReference<ArrayList<Map<String, String>>>() {
                    });
            List<Map<String, String>> node2 = objectMapper.readValue(is2,
                    new TypeReference<ArrayList<Map<String, String>>>() {
                    });
            List<Map<String, String>> node3 = objectMapper.readValue(is3,
                    new TypeReference<ArrayList<Map<String, String>>>() {
                    });
            System.out.println("read ok");
            output.addAll(node1);
            output.addAll(node2);
            output.addAll(node3);

        } catch (JsonMappingException e) {
        } catch (JsonProcessingException e) {
        }
        output.sort((a, b) -> {
            return 1 * a.get("technique").compareTo(b.get("technique"));
        });
        return output;
    }
}
