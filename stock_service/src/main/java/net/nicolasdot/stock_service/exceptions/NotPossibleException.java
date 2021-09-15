package net.nicolasdot.stock_service.exceptions;

/**
 *
 * @author nicolasdotnet
 */
public class NotPossibleException extends StockServiceException {

    public NotPossibleException() {
    }

    public NotPossibleException(String message) {
        super(message);
    }

}
