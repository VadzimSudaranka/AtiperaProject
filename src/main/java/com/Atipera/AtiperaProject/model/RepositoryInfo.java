package com.Atipera.AtiperaProject.model;



import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
public class RepositoryInfo {
    private String repositoryName;
    private String ownerLogin;
    private List<BranchInfo> branches;

}
