package net.nicolasdot.front_service.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.nicolasdot.front_service.beans.ExceptionMessage;
import org.apache.logging.log4j.LogManager;
import org.springframework.web.client.HttpClientErrorException;

/**
 *
 * @author nicolasdotnet
 */
public class Tools {
    
    private static final org.apache.logging.log4j.Logger log = LogManager.getLogger(Tools.class);

    public static ExceptionMessage messageExtraction(HttpClientErrorException e){

        ObjectMapper mapper = new ObjectMapper();
        
        ExceptionMessage message = null ;

        try {
           message = mapper.readValue(e.getResponseBodyAsString(), ExceptionMessage.class);
        } catch (JsonProcessingException ex) {
            log.error("erreur de parse json !");
        }
        return message;

    }

}
