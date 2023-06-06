package com.Atipera.AtiperaProject.controller;

import com.Atipera.AtiperaProject.exceptions.UserNotFoundException;

import com.Atipera.AtiperaProject.exceptions.NotAcceptableException;
import com.Atipera.AtiperaProject.model.RepositoryInfo;
import com.Atipera.AtiperaProject.service.GitHubService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/repositories")
public
class RepositoryController {

    private final GitHubService gitHubService;

    @Autowired
    public RepositoryController(GitHubService gitHubService) {
        this.gitHubService = gitHubService;
    }

    @GetMapping(value = "/{userName}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<RepositoryInfo> getRepositories(@PathVariable String userName, @RequestHeader("Accept") String acceptHeader) {
        if (!acceptHeader.equalsIgnoreCase(MediaType.APPLICATION_JSON_VALUE)) {
            throw new NotAcceptableException(406, "Wrong header provided");
        } else {
            try {
                return gitHubService.getRepositories(userName);
            } catch (UserNotFoundException e) {
                throw new UserNotFoundException(404, "User not found");
            }
        }
    }
}
