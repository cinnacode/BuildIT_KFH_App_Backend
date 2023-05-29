package com.example.Kfh_Backend.model;

public class AndroidProject {
    private String name;
    private String description;
    private String sitServer;
    private String apkFile;

    // Constructors
    public AndroidProject() {
    }

    public AndroidProject(String name, String description, String sitServer, String apkFile) {
        this.name = name;
        this.description = description;
        this.sitServer = sitServer;
        this.apkFile = apkFile;
    }

    // Getters and setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSitServer() {
        return sitServer;
    }

    public void setSitServer(String sitServer) {
        this.sitServer = sitServer;
    }

    public String getApkFile() {
        return apkFile;
    }

    public void setApkFile(String apkFile) {
        this.apkFile = apkFile;
    }
}
