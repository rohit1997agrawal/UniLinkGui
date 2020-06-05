package model.exceptions;

/*
Custom Exception to validate Minimum Raise  when New Sale is created
This exception is thrown if Minimum Raise is a negative decimal
Or if Minimum Raise value is not a valid decimal
 */
public class MinimumRaiseInvalidException extends Exception {


    public MinimumRaiseInvalidException(String message) {
        super(message);

    }

}