package com.example.wishlist.exception;

public class ErrorMessage extends Throwable {
    public ErrorMessage() {
        super();
    }

    public ErrorMessage(String message) {
        super(message);
    }
}
