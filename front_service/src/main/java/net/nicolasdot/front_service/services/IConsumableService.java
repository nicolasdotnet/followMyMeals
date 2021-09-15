package net.nicolasdot.front_service.services;

import java.net.URISyntaxException;
import java.util.List;
import net.nicolasdot.front_service.beans.Consumable;
import net.nicolasdot.front_service.dto.ConsumableDTO;
import org.springframework.data.domain.Page;
import org.springframework.web.client.RestClientException;

/**
 *
 * @author nicolasdotnet
 */
public interface IConsumableService {

    /**
     * method to save a Consumable
     *
     * @param consumableDTO
     * @return Consumable object save
     * @throws java.net.URISyntaxException
     */
    Consumable saveConsumable(ConsumableDTO consumableDTO) throws URISyntaxException, RestClientException;

    /**
     * method to get all Consumable
     *
     * @return the Consumable list
     * @throws java.net.URISyntaxException
     */
    List<Consumable> getAllConsumables() throws URISyntaxException, RestClientException;

    /**
     * method to get a Consumable
     *
     * @param id
     * @return Consumable object find
     * @throws java.net.URISyntaxException
     */
    Consumable getConsumable(Long id) throws URISyntaxException, RestClientException;

    /**
     * method to get all Consumables by criteria
     *
     * @param consumableId
     * @param mealId
     * @param p number page
     * @param s size page
     * @param userId
     * @return the Consumable pages
     * @throws java.net.URISyntaxException
     */
    Page<Consumable> getAllConsumablesByCriteria(String consumableId, String mealId, String userId, int p, int s) throws URISyntaxException, RestClientException;

    /**
     * method to validate a Consumable
     *
     * @param id
     * @return Consumable object save
     * @throws java.net.URISyntaxException
     */
    Consumable validateConsumable(Long id) throws URISyntaxException, RestClientException;
    
    void removeConsumable(Long consumableId)throws URISyntaxException, RestClientException;

}
