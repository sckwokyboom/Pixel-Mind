package ru.nsu.fit.pixelmind.exceptions;

public class PixelMindException extends Exception {
    public PixelMindException(String errorMessage, Throwable err) {
        super(errorMessage, err);
    }

    public PixelMindException(String errorMessage) {
        super(errorMessage);
    }
}