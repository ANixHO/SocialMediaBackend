package org.socialmedia.Exceptions;

public class LikeNotFoundException extends RuntimeException {
    public LikeNotFoundException(String message){
        super(message);
    }
}
