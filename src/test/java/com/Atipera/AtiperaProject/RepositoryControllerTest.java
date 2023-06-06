package com.Atipera.AtiperaProject;


import com.Atipera.AtiperaProject.controller.RepositoryController;
import com.Atipera.AtiperaProject.exceptions.UserNotFoundException;
import com.Atipera.AtiperaProject.exceptions.NotAcceptableException;
import com.Atipera.AtiperaProject.model.RepositoryInfo;
import com.Atipera.AtiperaProject.service.GitHubService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RepositoryControllerTest {

    private RepositoryController repositoryController;

    @Mock
    private GitHubService gitHubService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        repositoryController = new RepositoryController(gitHubService);
    }

    @Test
    void getRepositoriesValidUserName() {

        String userName = "exampleUser";
        String acceptHeader = MediaType.APPLICATION_JSON_VALUE;
        List<RepositoryInfo> expectedRepositories = new ArrayList<>();
        when(gitHubService.getRepositories(userName)).thenReturn(expectedRepositories);

        List<RepositoryInfo> repositories = repositoryController.getRepositories(userName, acceptHeader);

        assertEquals(expectedRepositories, repositories);
        verify(gitHubService, times(1)).getRepositories(userName);
    }

    @Test
    void getRepositoriesInvalidAcceptHeader() {

        String userName = "exampleUser";
        String acceptHeader = MediaType.TEXT_HTML_VALUE;


        assertThrows(NotAcceptableException.class, () ->
                repositoryController.getRepositories(userName, acceptHeader));
        verify(gitHubService, never()).getRepositories(userName);
    }

    @Test
    void getRepositoriesUserNotExist() {
        String userName = "nonExistingUser";
        String acceptHeader = MediaType.APPLICATION_JSON_VALUE;
        when(gitHubService.getRepositories(userName)).thenThrow(new UserNotFoundException(404, "User not found"));

        assertThrows(UserNotFoundException.class, () ->
                repositoryController.getRepositories(userName, acceptHeader));
        verify(gitHubService, times(1)).getRepositories(userName);
    }
}
