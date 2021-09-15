package net.nicolasdot.front_service.services;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;
import net.nicolasdot.front_service.beans.MealType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.keycloak.adapters.springsecurity.client.KeycloakRestTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
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
public class MealTypeServiceImpl implements IMealTypeService {

    private static final Logger log = LogManager.getLogger(MealTypeServiceImpl.class);

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
    public List<MealType> getAllMealTypes() throws URISyntaxException, RestClientException {

        URI uri = new URI(baseUrl + serverPort + mealService + "/api/user/types");

        ResponseEntity<MealType[]> result = keycloakRestTemplate.exchange(uri, HttpMethod.GET, null, MealType[].class);
        
        List<MealType> mealTypes = Arrays.asList(result.getBody());

        return mealTypes;
    }

}
