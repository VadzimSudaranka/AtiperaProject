package com.Atipera.AtiperaProject.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GitHubBranch {
    private String name;
    private GitHubCommit commit;
}
