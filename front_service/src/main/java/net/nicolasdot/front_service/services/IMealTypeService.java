package net.nicolasdot.front_service.services;

import java.net.URISyntaxException;
import java.util.List;
import net.nicolasdot.front_service.beans.MealType;
import org.springframework.web.client.RestClientException;

/**
 *
 * @author nicolasdotnet
 */
public interface IMealTypeService {
    
    
    /**
     * method to get all MealType
     *
     * @return the MealType list
     * @throws java.net.URISyntaxException
     */
    List<MealType> getAllMealTypes() throws URISyntaxException, RestClientException;
    
}
