package model.exceptions;


/*
Custom Exception to validate Asking price  when New Sale is created
This exception is thrown if Asking price is a negative decimal
Or if Asking price value is not a valid decimal
 */
public class AskingPriceInvalidException extends Exception {


    public AskingPriceInvalidException(String message) {
        super(message);

    }

}
