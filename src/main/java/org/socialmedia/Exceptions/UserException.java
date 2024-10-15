package org.socialmedia.Exceptions;

import org.socialmedia.model.User;

public class UserException extends RuntimeException{
    public UserException(String msg){
        super(msg);
    }
}
