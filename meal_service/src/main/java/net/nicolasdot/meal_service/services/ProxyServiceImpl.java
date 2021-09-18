package net.nicolasdot.meal_service.services;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;
import net.nicolasdot.meal_service.bean.Produit;
import net.nicolasdot.meal_service.services.interfaces.IProxyService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

/**
 *
 * @author nicolasdotnet
 */
@Service
public class ProxyServiceImpl implements IProxyService{

    @Autowired
    private RestTemplate restTemplate;

    @Value("${api.server.port}")
    private String serverPort;

    @Value("${baseUrl}")
    private String baseUrl;

    @Value("${apiLogin}")
    private String login;

    @Value("${apiPassword}")
    private String password;

    private HttpHeaders headers = new HttpHeaders();
    
    private static final Logger log = LogManager.getLogger(ProxyServiceImpl.class);
    
    
    public Optional<Produit> getProduitById(Long produitId) throws RestClientException{
        
        URI uri = null;

        try {
            uri = new URI(baseUrl + serverPort + "/api/user/produits/" + produitId);
        } catch (URISyntaxException ex) {
            
            log.error("erreur de endpoint !");
        }

        headers.setBasicAuth(login, password);

        HttpEntity requestEntity = new HttpEntity(headers);

        ResponseEntity<Produit> result = null;

        result = restTemplate.exchange(uri, HttpMethod.GET, requestEntity, Produit.class);

        if (result.hasBody()) {

            log.info("il y a un produit");

        } else {

            log.info("il n'y a pas de produit");
        }
        
        

        return Optional.of(result.getBody());
    }

}
