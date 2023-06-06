package com.Atipera.AtiperaProject.service;

import com.Atipera.AtiperaProject.model.BranchInfo;
import com.Atipera.AtiperaProject.model.GitHubBranch;
import com.Atipera.AtiperaProject.model.GitHubRepository;
import com.Atipera.AtiperaProject.model.RepositoryInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;


import java.util.ArrayList;
import java.util.List;

@Service
public class GitHubService {
    private final WebClient webClient;

    @Autowired
    public GitHubService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.build();
    }

    public List<RepositoryInfo> getRepositories(String userName) {
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
    }

    private GitHubRepository[] fetchRepositoriesFromGitHub(String userName) {
        return webClient.get()
                .uri("https://api.github.com/users/" + userName + "/repos?forks=false")
                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .retrieve()
                .bodyToMono(GitHubRepository[].class)
                .block();
    }

    public GitHubBranch[] fetchBranchesFromGitHub(String userName, String repositoryName) {
        return webClient.get()
                .uri("https://api.github.com/repos/" + userName + "/" + repositoryName + "/branches")
                .retrieve()
                .bodyToMono(GitHubBranch[].class)
                .block();
    }
}
