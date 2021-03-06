package it.digiulio.social71.exception;

import lombok.Getter;

@Getter
public class NotFoundException extends CustomException{
    private static final long serialVersionUID = 6010716622123533274L;

    private final String field;

    private final String entityName;


    public NotFoundException(String field, String entityName) {
        super(buildMessage(field, entityName));
        this.field = field;
        this.entityName = entityName;
    }

    private static String buildMessage(String field, String entityName) {
        return "'" + entityName + "' not found for id '" + field + "'. '";
    }
}
