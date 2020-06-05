package model.exceptions;


/*
Custom Exception to validate Capacity when New Event is created
This exception is thrown if Capacity is a negative integer
Or if capacity value is not a valid integer
 */
public class CapacityInvalidException extends Exception {


    public CapacityInvalidException(String message) {
        super(message);

    }

}

