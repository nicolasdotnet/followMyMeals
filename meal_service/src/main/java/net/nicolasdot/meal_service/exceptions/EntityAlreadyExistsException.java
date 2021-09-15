package net.nicolasdot.meal_service.exceptions;

/**
 *
 * @author nicolasdotnet
 */

public class EntityAlreadyExistsException extends MealNotPossibleException {

    public EntityAlreadyExistsException() {
    }

    public EntityAlreadyExistsException(String message) {

        super(message);
    }

}
