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

    MealType register(MealTypeDTO mealDto)throws EntityAlreadyExistsException;
    
    Optional<MealType> getMealType(Long meadiaTypeId);

    List<MealType> getAllMealTypes();


}
