package com.project.atomic_redteam_analyzer.config;

import io.github.cdimascio.dotenv.Dotenv;

public class GitHubOAuthConfig {
    private static final Dotenv dotenv = Dotenv.configure()
            .directory("src/main/resources")
            .filename(".env.apitoken")
            .ignoreIfMissing()
            .load();

    public static final String getGitHubToken() {
        String token = dotenv.get("GITHUB_API_TOKEN");
        if (token == null || token.isEmpty()) {
            throw new IllegalArgumentException("GITHUB_TOKEN is not set in the environment variables.");
        }
        return token;
    }

    public static String getAuthorizationHeader() {
        String token = getGitHubToken();
        return "Bearer " + token;
    }
}