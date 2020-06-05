package model.exceptions;

/*
Custom Exception to validate Proposed price  when New Job is created
This exception is thrown if Proposed price is a negative decimal
Or if proposed price value is not a valid decimal
 */
public class ProposedPriceInvalidException extends Exception {


    public ProposedPriceInvalidException(String message) {
        super(message);

    }

}
