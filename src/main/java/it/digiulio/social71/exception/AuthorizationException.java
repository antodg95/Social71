package it.digiulio.social71.exception;

import lombok.Getter;


@Getter
public class AuthorizationException extends CustomException{
    private static final long serialVersionUID = -7637125773100126746L;

    public AuthorizationException() {
        super(buildMessage());
    }

    private static String buildMessage() {
        return "You don't have permission to access to this resource";
    }
}
