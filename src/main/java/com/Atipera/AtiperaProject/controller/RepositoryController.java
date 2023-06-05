package com.Atipera.AtiperaProject.controller;

import com.Atipera.AtiperaProject.exceptions.GitHubApiException;
import com.Atipera.AtiperaProject.model.ErrorInfo;
import com.Atipera.AtiperaProject.model.RepositoryInfo;
import com.Atipera.AtiperaProject.service.GitHubService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/repositories")
class RepositoryController {

    private final GitHubService gitHubService;

    @Autowired
    public RepositoryController(GitHubService gitHubService) {
        this.gitHubService = gitHubService;
    }

    @GetMapping(value = "/{userName}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getRepositories(@PathVariable String userName, @RequestHeader("Accept") String acceptHeader) {
        if (!acceptHeader.equalsIgnoreCase(MediaType.APPLICATION_JSON_VALUE)) {
            ErrorInfo errorInfo = new ErrorInfo(406, "Wrong header provided");
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(errorInfo);
        } else {
            try {
                List<RepositoryInfo> repositoryInfos = gitHubService.getRepositories(userName);
                return ResponseEntity.ok(repositoryInfos);
            } catch (GitHubApiException e) {
                ErrorInfo errorInfo = new ErrorInfo(e.getStatusCode(), "User not found");
                return ResponseEntity.status(e.getStatusCode()).body(errorInfo);
            }
        }
    }
}
