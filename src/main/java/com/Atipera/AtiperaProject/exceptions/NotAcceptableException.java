package com.Atipera.AtiperaProject.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public
class NotAcceptableException extends RuntimeException {

    private final int statusCode;
    private final String message;


}
