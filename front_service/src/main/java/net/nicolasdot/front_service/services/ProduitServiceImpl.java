package net.nicolasdot.front_service.services;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import net.nicolasdot.front_service.beans.Produit;
import net.nicolasdot.front_service.dto.ProduitDTO;
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
import org.springframework.web.client.RestTemplate;

/**
 *
 * @author nicolasdotnet
 */
@Service
@Transactional
public class ProduitServiceImpl implements IProduitService {

    private static final Logger log = LogManager.getLogger(ProduitServiceImpl.class);

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private KeycloakRestTemplate keycloakRestTemplate;

    @Value("${api.server.port}")
    private String serverPort;

    @Value("${baseUrl}")
    private String baseUrl;
    
    @Value("${stockServiceName}")
    private String stockService;

    private HttpHeaders headers = new HttpHeaders();

    @Override
    public List<Produit> getAllProduits() throws URISyntaxException, RestClientException {

        URI uri = new URI(baseUrl + serverPort + stockService +"/api/user/produits/all");

        HttpEntity requestEntity = new HttpEntity(headers);

        ResponseEntity<List<Produit>> result = null;

        result = restTemplate.exchange(uri, HttpMethod.GET, requestEntity, new ParameterizedTypeReference<List<Produit>>() {
        });

        return result.getBody();

    }

    @Override
    public Produit getProduit(Long id) throws URISyntaxException, RestClientException {

        URI uri = new URI(baseUrl + serverPort + stockService +"/api/user/produits/" + id);

        ResponseEntity<Produit> result = keycloakRestTemplate.exchange(uri, HttpMethod.GET, null, Produit.class);

//        HttpEntity requestEntity = new HttpEntity(headers);
//
//        ResponseEntity<Produit> result = restTemplate.exchange(uri, HttpMethod.GET, requestEntity, Produit.class);
        return result.getBody();

    }

    @Override
    public Produit getProduitByCode(String code, String userId) throws URISyntaxException, RestClientException {

        URI uri = new URI(baseUrl + serverPort + stockService +"/api/user/product?code=" + code + "&user=" + userId);

        ResponseEntity<Produit> produitFind = null;

        produitFind = keycloakRestTemplate.exchange(uri, HttpMethod.GET, null, Produit.class);

        System.out.println("net.nicolasdot.front_service.services.ProduitServiceImpl.getProduitByCode()" + produitFind.getStatusCode());

        return produitFind.getBody();

    }

    @Override
    public Page<Produit> getAllProduitsByCriteria(String code, String produit, String InStock, int p, int s, String userId)
            throws URISyntaxException, RestClientException {

        URI uri = new URI(baseUrl + serverPort + stockService +"/api/user/produits?code=" + code + "&produit=" + produit + "&stock=" + InStock + "&page=" + p + "&size=" + s + "&user=" + userId);

        ResponseEntity<RestResponsePage<Produit>> result = null;

        ParameterizedTypeReference<RestResponsePage<Produit>> responseType = new ParameterizedTypeReference<RestResponsePage<Produit>>() {
        };

        result = keycloakRestTemplate.exchange(uri, HttpMethod.GET, null, responseType);

        System.out.println("888888888888888888888888888888888 : " + userId);
        System.out.println("999999999999999999999999999999999 : " + result.getBody().getNumberOfElements());

        return result.getBody();

    }

    @Override
    public Produit saveProduitInStock(ProduitDTO produitDTO) throws URISyntaxException, RestClientException {
        URI uri = new URI(baseUrl + serverPort + stockService +"/api/user/produits");

        headers.add("Accept", MediaType.APPLICATION_JSON_VALUE);
        
        HttpEntity<ProduitDTO> requestBody = new HttpEntity<>(produitDTO, headers);

        ResponseEntity<Produit> produitUpdate = keycloakRestTemplate.exchange(uri, HttpMethod.PUT, requestBody, Produit.class);
        
        return produitUpdate.getBody();
    }

}
