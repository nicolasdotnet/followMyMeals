package net.nicolasdot.front_service.services;

import net.nicolasdot.front_service.beans.Reporting;
import java.net.URISyntaxException;
import java.util.List;
import net.nicolasdot.front_service.beans.Meal;
import net.nicolasdot.front_service.dto.MealDTO;
import org.springframework.data.domain.Page;
import org.springframework.web.client.RestClientException;

/**
 *
 * @author nicolasdotnet
 */
public interface IMealService {

    /**
     * method to save a Meal
     *
     * @param mealDTO
     * @return Meal object save
     * @throws java.net.URISyntaxException
     */
    Meal saveMeal(MealDTO mealDTO) throws URISyntaxException, RestClientException;

    /**
     * method to get all Meal
     *
     * @return the Meal list
     * @throws java.net.URISyntaxException
     */
    List<Meal> getAllMeals() throws URISyntaxException, RestClientException;

    /**
     * method to get a Meal
     *
     * @param id
     * @return Meal object find
     * @throws java.net.URISyntaxException
     */
    Meal getMeal(Long id) throws URISyntaxException, RestClientException;

    /**
     * method to get all Meals by criteria
     *
     * @param mealId
     * @param mealType
     * @param p number page
     * @param s size page
     * @param userId
     * @return the Meal pages
     * @throws java.net.URISyntaxException
     */
    Page<Meal> getAllMealsByCriteria(Long mealId, String mealType, int p, int s, String userId) throws URISyntaxException, RestClientException;

    /**
     * method to validate a Meal
     *
     * @param id
     * @return Meal object save
     * @throws java.net.URISyntaxException
     */
    Meal validateMeal(Long id) throws URISyntaxException, RestClientException;

    void deleteMeal(Long id) throws URISyntaxException, RestClientException;

}
