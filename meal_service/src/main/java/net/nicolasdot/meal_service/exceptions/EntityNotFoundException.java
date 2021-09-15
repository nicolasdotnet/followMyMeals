package net.nicolasdot.meal_service.exceptions;

/**
 *
 * @author nicolasdotnet
 */

public class EntityNotFoundException extends MealNotPossibleException {

    public EntityNotFoundException() {
    }

    public EntityNotFoundException(String message) {

        super(message);
    }
    
}
