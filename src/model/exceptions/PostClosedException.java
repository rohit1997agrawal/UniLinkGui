package model.exceptions;


/*
Custom Exception to handle when user tries to Reply to a Sale/ Event / Job,
which is already closed

 */
public class PostClosedException extends Exception {


    public PostClosedException(String message) {
        super(message);

    }

}