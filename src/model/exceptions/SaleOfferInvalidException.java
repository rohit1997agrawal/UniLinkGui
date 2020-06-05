package model.exceptions;

/*
Custom Exception to validate Sale offer  when Replying to a Sale
This exception is thrown if Sale offer is a negative decimal
Or if Sale offer is not a valid decimal
Or if Sale offer is lower than the current highest offer
Or if Sale offer is not greater than the current highest offer by the set minimum raise
 */
public class SaleOfferInvalidException extends Exception {


    public SaleOfferInvalidException(String message) {
        super(message);

    }

}
