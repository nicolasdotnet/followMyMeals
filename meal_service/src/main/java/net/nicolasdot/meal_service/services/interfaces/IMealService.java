package net.nicolasdot.meal_service.services.interfaces;

import java.time.LocalDate;
import java.util.List;
import net.nicolasdot.meal_service.dto.MealDTO;
import net.nicolasdot.meal_service.entity.Meal;
import net.nicolasdot.meal_service.exceptions.EntityAlreadyExistsException;
import net.nicolasdot.meal_service.exceptions.EntityNotFoundException;
import net.nicolasdot.meal_service.exceptions.MealNotPossibleException;
import net.nicolasdot.meal_service.specifications.MealCriteria;
import org.springframework.data.domain.Page;

/**
 *
 * @author nicolasdotnet
 */
public interface IMealService {

    Meal createMeal(MealDTO mealDTO) throws EntityNotFoundException;

    /**
     * method to validateMeal meal after adding consumable
     *
     * @param mealId
     * @return one reservation
     * @throws EntityNotFoundException
     * @throws EntityAlreadyExistsException
     */
    Meal validateMeal(Long mealId) throws EntityNotFoundException,
            EntityAlreadyExistsException, MealNotPossibleException;

    Meal getOneMeal(Long mealId) throws EntityNotFoundException;

    Page<Meal> getAllMealsByCriteria(MealCriteria mealCriteria, int page, int size);

    void deleteMeal(Long mealId) throws EntityNotFoundException;

    List<Meal> getAllMealsBetweenTwoDate(LocalDate seventhDay, LocalDate today, String userId);

}
