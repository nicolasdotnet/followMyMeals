package net.nicolasdot.front_service.services;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import net.nicolasdot.front_service.beans.Meal;
import net.nicolasdot.front_service.dto.MealDTO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.keycloak.adapters.springsecurity.client.KeycloakRestTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestClientException;

/**
 *
 * @author nicolasdotnet
 */
@Service
@Transactional
public class MealServiceImpl implements IMealService {

    private static final Logger log = LogManager.getLogger(MealServiceImpl.class);

    @Autowired
    private KeycloakRestTemplate keycloakRestTemplate;

    @Value("${api.server.port}")
    private String serverPort;

    @Value("${baseUrl}")
    private String baseUrl;

    @Value("${mealServiceName}")
    private String mealService;

    private HttpHeaders headers = new HttpHeaders();

    @Override
    public Meal saveMeal(MealDTO mealDTO) throws URISyntaxException, RestClientException {

        URI uri = new URI(baseUrl + serverPort + mealService + "/api/user/meals");

        headers.add("Accept", MediaType.APPLICATION_JSON_VALUE);

        HttpEntity<MealDTO> requestBody = new HttpEntity<>(mealDTO, headers);

        ResponseEntity<Meal> mealSave = keycloakRestTemplate.exchange(uri, HttpMethod.POST, requestBody, Meal.class);

        return mealSave.getBody();
    }

    @Override
    public List<Meal> getAllMeals() throws URISyntaxException, RestClientException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Meal getMeal(Long id) throws URISyntaxException, RestClientException {

        URI uri = new URI(baseUrl + serverPort + mealService + "/api/user/meals/" + id);

        ResponseEntity<Meal> result = keycloakRestTemplate.exchange(uri, HttpMethod.GET, null, Meal.class);

        return result.getBody();
    }

    @Override
    public Page<Meal> getAllMealsByCriteria(Long mealId, String mealType, int p, int s, String userId) throws URISyntaxException, RestClientException {

        URI uri = new URI(baseUrl + serverPort + mealService + "/api/user/meals?id=" + mealId + "&type=" + mealType + "&user=" + userId + "&page=" + p + "&size=" + s);

        ResponseEntity<RestResponsePage<Meal>> result = null;

        ParameterizedTypeReference<RestResponsePage<Meal>> responseType = new ParameterizedTypeReference<RestResponsePage<Meal>>() {
        };

        result = keycloakRestTemplate.exchange(uri, HttpMethod.GET, null, responseType);

        System.out.println("888888888888888888888888888888888 : " + userId);
        System.out.println("999999999999999999999999999999999 : " + result.getBody().getNumberOfElements());

        return result.getBody();
    }

    @Override
    public Meal validateMeal(Long id) throws URISyntaxException, RestClientException {

        URI uri = new URI(baseUrl + serverPort + mealService + "/api/user/meals/" + id + "/validate");

        ResponseEntity<Meal> result = keycloakRestTemplate.exchange(uri, HttpMethod.PUT, null, Meal.class);

        return result.getBody();
    }

    @Override
    public void deleteMeal(Long id) throws URISyntaxException, RestClientException {

        URI uri = new URI(baseUrl + serverPort + mealService + "/api/user/meals/" + id);

        keycloakRestTemplate.exchange(uri, HttpMethod.DELETE, null, Meal.class);
    }

}
