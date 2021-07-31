package net.nicolasdot.stock_service.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.ActiveProfiles;

/**
 *
 * @author nicolasdotnet
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class ProduitControllerIT {

    @LocalServerPort
    private int port;
    
    // pas de sécu voir annotation ? 

    @Autowired
    TestRestTemplate restTemplate;

    @Autowired
    HttpHeaders headers;
    
    private String createURLWithPort(String uri){
        
        
        return "http://localhost:"+port+uri;
        
    }
}
