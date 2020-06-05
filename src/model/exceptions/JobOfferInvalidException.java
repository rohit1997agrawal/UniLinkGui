package model.exceptions;

/*
Custom Exception to validate Job offer  when Replying to a Job
This exception is thrown if Job offer is a negative decimal
Or if Job offer is not a valid decimal
Or if Job offer is higher than the current lowest offer
 */
public class JobOfferInvalidException extends Exception {


    public JobOfferInvalidException(String message) {
        super(message);

    }

}
