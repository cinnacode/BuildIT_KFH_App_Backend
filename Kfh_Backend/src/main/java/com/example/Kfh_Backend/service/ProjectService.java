package com.example.Kfh_Backend.service;

import com.example.Kfh_Backend.model.AndroidProject;
import com.example.Kfh_Backend.model.IOSProject;
import com.example.Kfh_Backend.repository.AndroidProjectRepository;
import com.example.Kfh_Backend.repository.IOSProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProjectService {

    @Autowired
    private IOSProjectRepository iosProjectRepository;

    @Autowired
    private AndroidProjectRepository androidProjectRepository;

    public void uploadIOSProject(IOSProject iosProject) {
        iosProjectRepository.save(iosProject);
    }

    public void uploadAndroidProject(AndroidProject androidProject) {
        androidProjectRepository.save(androidProject);
    }

    public IOSProject getIOSProject(long projectId) {
        Optional<IOSProject> optionalProject = iosProjectRepository.findById(projectId);
        return optionalProject.orElse(null);
    }

    public AndroidProject getAndroidProject(long projectId) {
        Optional<AndroidProject> optionalProject = androidProjectRepository.findById(projectId);
        return optionalProject.orElse(null);
    }

    public void updateIOSProject(IOSProject iosProject) {
        iosProjectRepository.save(iosProject);
    }

    public void updateAndroidProject(AndroidProject androidProject) {
        androidProjectRepository.save(androidProject);
    }
}
