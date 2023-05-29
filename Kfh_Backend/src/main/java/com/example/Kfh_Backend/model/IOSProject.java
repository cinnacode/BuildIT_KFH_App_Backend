package com.example.Kfh_Backend.model;

public class IOSProject {
    private String name;
    private String description;
    private String sitServer;
    private String ipaFile;
    private String plistFile;

    // Constructors
    public IOSProject() {
    }

    public IOSProject(String name, String description, String sitServer, String ipaFile, String plistFile) {
        this.name = name;
        this.description = description;
        this.sitServer = sitServer;
        this.ipaFile = ipaFile;
        this.plistFile = plistFile;
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

    public String getIpaFile() {
        return ipaFile;
    }

    public void setIpaFile(String ipaFile) {
        this.ipaFile = ipaFile;
    }

    public String getPlistFile() {
        return plistFile;
    }

    public void setPlistFile(String plistFile) {
        this.plistFile = plistFile;
    }
}
