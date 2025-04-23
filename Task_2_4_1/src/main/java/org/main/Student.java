package org.main;

public class Student {
    private String github;
    private String fullName;
    private String repo;

    Student(String github, String fullName, String repo) {
        this.github = github;
        this.fullName = fullName;
        this.repo = repo;
    }

    String getGithub() {
        return github;
    }

    String getFullName() {
        return fullName;
    }

    String getRepo() {
        return repo;
    }
}

