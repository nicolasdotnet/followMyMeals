package net.nicolasdot.meal_service.services.interfaces;

import java.util.List;
import java.util.Optional;
import net.nicolasdot.meal_service.dto.MealTypeDTO;
import net.nicolasdot.meal_service.entity.MealType;
import net.nicolasdot.meal_service.exceptions.EntityAlreadyExistsException;

/**
 *
 * @author nicolasdotnet
 */
public interface IMealTypeService {

    /**
     * method to register mealType
     *
     * @param mealDto
     * @return consumable object saved
     * @throws
     * net.nicolasdot.meal_service.exceptions.EntityAlreadyExistsException
     */
    MealType register(MealTypeDTO mealDto) throws EntityAlreadyExistsException;

    /**
     * method to get a mealType by id
     *
     * @param meadiaTypeId
     * @return mealType object find
     */
    Optional<MealType> getMealType(Long meadiaTypeId);

    /**
     * method to get all mealTypes
     *
     * @return the pages meals
     */
    List<MealType> getAllMealTypes();

}
