package it.digiulio.social71.exception;

public class CustomException extends RuntimeException{

    private static final long serialVersionUID = -9157669625977438536L;

    protected CustomException() { super(null, null, false, false); }

    protected CustomException(String msg) { super(msg, null, false, false); }
}
