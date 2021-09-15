package net.nicolasdot.front_service.services;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import net.nicolasdot.front_service.beans.Consumable;
import net.nicolasdot.front_service.dto.ConsumableDTO;
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
public class ConsumableServiceImpl implements IConsumableService {

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
    public Consumable saveConsumable(ConsumableDTO consumableDTO) throws URISyntaxException, RestClientException {

        URI uri = new URI(baseUrl + serverPort + mealService + "/api/user/consumables");

        headers.add("Accept", MediaType.APPLICATION_JSON_VALUE);

        System.out.println("WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW DTO / " + consumableDTO.getUserId());
        System.out.println("WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW DTO / " + consumableDTO.getProduitId());
        System.out.println("WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW DTO / " + consumableDTO.getMealId());

        HttpEntity<ConsumableDTO> requestBody = new HttpEntity<>(consumableDTO, headers);

        ResponseEntity<Consumable> consumableSave = keycloakRestTemplate.exchange(uri, HttpMethod.POST, requestBody, Consumable.class);

        return consumableSave.getBody();
    }

    @Override
    public List<Consumable> getAllConsumables() throws URISyntaxException, RestClientException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Consumable getConsumable(Long id) throws URISyntaxException, RestClientException {

        URI uri = new URI(baseUrl + serverPort + mealService + "/api/user/consumables/" + id);

        ResponseEntity<Consumable> result = keycloakRestTemplate.exchange(uri, HttpMethod.GET, null, Consumable.class);

        return result.getBody();
    }

    @Override
    public Page<Consumable> getAllConsumablesByCriteria(String consumableId, String mealId, String userId, int p, int s) throws URISyntaxException, RestClientException {

        URI uri = new URI(baseUrl + serverPort + mealService + "/api/user/consumables?id=" + consumableId + "&meal=" + mealId + "&user=" + userId + "&page=" + p + "&size=" + s);

        ResponseEntity<RestResponsePage<Consumable>> result = null;

        ParameterizedTypeReference<RestResponsePage<Consumable>> responseType = new ParameterizedTypeReference<RestResponsePage<Consumable>>() {
        };

        result = keycloakRestTemplate.exchange(uri, HttpMethod.GET, null, responseType);

        System.out.println("888888888888888888888888888888888 : " + userId);
        System.out.println("999999999999999999999999999999999 : " + result.getBody().getNumberOfElements());

        return result.getBody();
    }

    @Override
    public Consumable validateConsumable(Long id) throws URISyntaxException, RestClientException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void removeConsumable(Long consumableId) throws URISyntaxException, RestClientException {
               
        URI uri = new URI(baseUrl + serverPort + mealService + "/api/user/consumables/" + consumableId);

        keycloakRestTemplate.exchange(uri, HttpMethod.DELETE, null, Consumable.class);
    }

}
