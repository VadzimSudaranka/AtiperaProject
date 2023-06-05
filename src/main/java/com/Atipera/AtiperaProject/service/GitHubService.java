package com.Atipera.AtiperaProject.service;

import com.Atipera.AtiperaProject.exceptions.GitHubApiException;
import com.Atipera.AtiperaProject.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@Service
public
class GitHubService {
    private final RestTemplate restTemplate;

    @Autowired
    public GitHubService(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    public List<RepositoryInfo> getRepositories(String userName) {
        try {
            GitHubRepository[] repositories = fetchRepositoriesFromGitHub(userName);
            List<RepositoryInfo> repositoryInfos = new ArrayList<>();

            for (GitHubRepository repository : repositories) {
                RepositoryInfo repositoryInfo = new RepositoryInfo();
                repositoryInfo.setRepositoryName(repository.getName());
                repositoryInfo.setOwnerLogin(repository.getOwner().getLogin());

                List<BranchInfo> branches = new ArrayList<>();
                for (GitHubBranch branch : fetchBranchesFromGitHub(userName, repository.getName())) {
                    BranchInfo branchInfo = new BranchInfo();
                    branchInfo.setBranchName(branch.getName());
                    branchInfo.setLastCommit(branch.getCommit().getSha());
                    branches.add(branchInfo);
                }

                repositoryInfo.setBranches(branches);
                repositoryInfos.add(repositoryInfo);
            }

            return repositoryInfos;
        } catch (HttpClientErrorException.NotFound e) {
            throw new GitHubApiException(404, "User not found");
        }
    }

    private GitHubRepository[] fetchRepositoriesFromGitHub(String userName) {
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);

        URI url = UriComponentsBuilder.fromUriString("https://api.github.com/users/" + userName + "/repos?fork=false")
                .build().toUri();

        RequestEntity<Void> requestEntity = RequestEntity.get(url)
                .headers(headers)
                .build();

        ResponseEntity<GitHubRepository[]> responseEntity = restTemplate.exchange(requestEntity, GitHubRepository[].class);
        return responseEntity.getBody();
    }

    private GitHubBranch[] fetchBranchesFromGitHub(String userName, String repositoryName) {
        URI url = UriComponentsBuilder.fromUriString("https://api.github.com/repos/" + userName + "/" + repositoryName + "/branches")
                .build().toUri();

        RequestEntity<Void> requestEntity = RequestEntity.get(url).build();

        ResponseEntity<GitHubBranch[]> responseEntity = restTemplate.exchange(requestEntity, GitHubBranch[].class);
        return responseEntity.getBody();
    }
}
