package com.bridgelabz;

public class CSVCensusException extends Exception {

    ExceptionType type;

    enum ExceptionType {NULL_DATA_FOUND, NO_SUCH_HEADER, NO_SUCH_FILE, NO_SUCH_FIELD}

    public CSVCensusException(String message, ExceptionType cause) {
        super(message);
    }

    public CSVCensusException(ExceptionType type, String message, Throwable cause) {
        super(message, cause);
        this.type = type;
    }
}

