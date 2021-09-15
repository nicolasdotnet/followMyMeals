package net.nicolasdot.front_service.services;

import java.net.URISyntaxException;
import net.nicolasdot.front_service.beans.Reporting;
import org.springframework.web.client.RestClientException;

/**
 *
 * @author nicolasdotnet
 */
public interface IReportingService {

    public Reporting reporting(String userId) throws URISyntaxException, RestClientException ;
    
}
