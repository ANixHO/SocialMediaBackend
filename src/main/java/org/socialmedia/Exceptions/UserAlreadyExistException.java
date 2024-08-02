package org.socialmedia.Exceptions;

public class UserAlreadyExistException extends RuntimeException{
    public UserAlreadyExistException(String message, Throwable e){
        super (message, e);
    }
}
