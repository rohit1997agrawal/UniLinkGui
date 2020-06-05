package model.exceptions;



/*
Custom Exception to validate Username when user logs in
This exception is thrown if username is left blank or
if username format is invalid
 */
public class UsernameInvalidException extends Exception {


    public UsernameInvalidException(String message)
    {
        super(message);

    }

}
