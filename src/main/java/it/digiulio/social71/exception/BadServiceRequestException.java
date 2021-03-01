package it.digiulio.social71.exception;

import lombok.Getter;

import java.util.List;

@Getter
public class BadServiceRequestException extends CustomException{
    private static final long serialVersionUID = 3691982677844159952L;

    private final String field;

    private final String value;

    private final List<String> domain;

    public BadServiceRequestException(String field, String value, List<String> domain) {
        super(buildMessage(field, value, domain));
        this.field = field;
        this.value = value;
        this.domain = domain;
    }

    private static String buildMessage(String field, String value, List<String> domain) {
        String ret = "";
        for (String s : domain) {
            ret += s + " ";
        }
        return "'" + value + "' it's not a valid input for field '" + field + "'. '" + field + "' : " + ret;
    }
}
