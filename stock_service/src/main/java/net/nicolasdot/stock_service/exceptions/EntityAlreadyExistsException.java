package net.nicolasdot.stock_service.exceptions;

/**
 *
 * @author nicolasdotnet
 */

public class EntityAlreadyExistsException extends StockServiceException {

    public EntityAlreadyExistsException() {
    }

    public EntityAlreadyExistsException(String message) {

        super(message);
    }

}
