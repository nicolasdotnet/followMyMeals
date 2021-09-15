package net.nicolasdot.stock_service.exceptions;

/**
 *
 * @author nicolasdotnet
 */

public class EntityNotFoundException extends StockServiceException {

    public EntityNotFoundException() {
    }

    public EntityNotFoundException(String message) {

        super(message);
    }
    
}
