package net.nicolasdot.meal_service.services.interfaces;

import java.util.Optional;
import net.nicolasdot.meal_service.bean.Produit;
import org.springframework.web.client.RestClientException;

/**
 *
 * @author nicolasdotnet
 */
public interface IProxyService {

    /**
     * method to get a produit by id
     *
     * @param produitId
     * @return produit optional object find
     */
    Optional<Produit> getProduitById(Long produitId) throws RestClientException;

}
