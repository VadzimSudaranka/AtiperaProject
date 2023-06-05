package com.Atipera.AtiperaProject.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public
class ErrorInfo {
    private int status;
    private String message;

}
