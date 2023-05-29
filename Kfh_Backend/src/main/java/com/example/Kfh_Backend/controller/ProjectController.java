package com.example.Kfh_Backend.controller;

import com.example.Kfh_Backend.model.AndroidProject;
import com.example.Kfh_Backend.model.IOSProject;
import com.example.Kfh_Backend.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    // IOS Upload: Uploads a project to the database with Name, Description, SIT Server, ipa file, and plist file attributes
    @PostMapping("/iosUpload")
    public String iosUpload(@RequestParam String name, @RequestParam String description,
                            @RequestParam String sitServer, @RequestParam MultipartFile ipaFile,
                            @RequestParam MultipartFile plistFile) {
        IOSProject iosProject = new IOSProject(name, description, sitServer, ipaFile, plistFile);
        projectService.uploadIOSProject(iosProject);
        return "Project uploaded successfully";
    }

    // Android Upload: Uploads a project to the database with Name, Description, SIT Server, and apk file attributes
    @PostMapping("/androidUpload")
    public String androidUpload(@RequestParam String name, @RequestParam String description,
                                @RequestParam String sitServer, @RequestParam MultipartFile apkFile) {
        AndroidProject androidProject = new AndroidProject(name, description, sitServer, apkFile);
        projectService.uploadAndroidProject(androidProject);
        return "Project uploaded successfully";
    }

    // IOS Edit: Edits the name, description, SIT Server, ipa file, or plist file of an ios project from the database
    @PostMapping("/iosEdit")
    public String iosEdit(@RequestParam long projectId, @RequestParam(required = false) String name,
                          @RequestParam(required = false) String description,
                          @RequestParam(required = false) String sitServer,
                          @RequestParam(required = false) MultipartFile ipaFile,
                          @RequestParam(required = false) MultipartFile plistFile) {
        IOSProject iosProject = projectService.getIOSProject(projectId);
        if (iosProject != null) {
            iosProject.setName(name);
            iosProject.setDescription(description);
            iosProject.setSitServer(sitServer);
            if (ipaFile != null) {
                iosProject.setIpaFile(ipaFile);
            }
            if (plistFile != null) {
                iosProject.setPlistFile(plistFile);
            }
            projectService.updateIOSProject(iosProject);
            return "Project updated successfully";
        } else {
            return "Project not found";
        }
    }

    // Android Edit: Edits the name, description, SIT Server, or apk file of an android project from the database
    @PostMapping("/androidEdit")
    public String androidEdit(@RequestParam long projectId, @RequestParam(required = false) String name,
                              @RequestParam(required = false) String description,
                              @RequestParam(required = false) String sitServer,
                              @RequestParam(required = false) MultipartFile apkFile) {
        AndroidProject androidProject = projectService.getAndroidProject(projectId);
        if (androidProject != null) {
            androidProject.setName(name);
            androidProject.setDescription(description);
            androidProject.setSitServer(sitServer);
            if (apkFile != null) {
                androidProject.setApkFile(apkFile);
            }
            projectService.updateAndroidProject(androidProject);
            return "Project updated successfully";
        } else {
            return "Project not found";
        }
    }

    // IOS Download: Downloads the ipa and plist files of the IOS Project from the database
    @GetMapping("/iosDownload")
    public ResponseEntity<byte[]> iosDownload(@RequestParam long projectId) {
        IOSProject iosProject = projectService.getIOSProject(projectId);
        if (iosProject != null) {
            byte[] ipaFileBytes = iosProject.getIpaFile().getBytes();
            byte[] plistFileBytes = iosProject.getPlistFile().getBytes();

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            headers.setContentDispositionFormData("attachment", "project.ipa");

            return new ResponseEntity<>(ipaFileBytes, headers, HttpStatus.OK);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Android Download: Downloads the apk file of the Android Project from the database
    @GetMapping("/androidDownload")
    public ResponseEntity<byte[]> androidDownload(@RequestParam long projectId) {
        AndroidProject androidProject = projectService.getAndroidProject(projectId);
        if (androidProject != null) {
            byte[] apkFileBytes = androidProject.getApkFile().getBytes();

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            headers.setContentDispositionFormData("attachment", "project.apk");

            return new ResponseEntity<>(apkFileBytes, headers, HttpStatus.OK);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
