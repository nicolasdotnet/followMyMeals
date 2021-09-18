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
    Meal validateMeal(Long mealId) throws EntityNotFoundException, EntityAlreadyExistsException, MealNotPossibleException;

    /**
     * method to get a meal by id
     *
     * @param mealId
     * @return meal object find
     * @throws net.nicolasdot.meal_service.exceptions.EntityNotFoundException
     */
    Meal getOneMeal(Long mealId) throws EntityNotFoundException;

    /**
     * method to get all meals by criteria
     *
     * @param mealCriteria
     * @param page
     * @param size
     * @return the pages meals
     */
    Page<Meal> getAllMealsByCriteria(MealCriteria mealCriteria, int page, int size);

    /**
     * method to delete produit
     *
     * @param mealId
     * @throws net.nicolasdot.meal_service.exceptions.EntityNotFoundException
     */
    void deleteMeal(Long mealId) throws EntityNotFoundException;

    /**
     * method to get all meals between Two Dates
     *
     * @param seventhDay
     * @param today
     * @param userId
     * @return the pages meals
     */
    List<Meal> getAllMealsBetweenTwoDate(LocalDate seventhDay, LocalDate today, String userId);

}
