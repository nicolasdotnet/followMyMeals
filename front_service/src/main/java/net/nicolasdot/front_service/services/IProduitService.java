package net.nicolasdot.front_service.services;

import java.net.URISyntaxException;
import java.util.List;
import net.nicolasdot.front_service.beans.Produit;
import net.nicolasdot.front_service.dto.ProduitDTO;
import org.springframework.data.domain.Page;
import org.springframework.web.client.RestClientException;

/**
 *
 * @author nicolasdotnet
 */
public interface IProduitService {

    /**
     * method to get all Produit
     *
     * @return the Produit list
     * @throws java.net.URISyntaxException
     */
    List<Produit> getAllProduits() throws URISyntaxException, RestClientException;

    /**
     * method to get a Produit
     *
     * @param id
     * @return Produit object find
     * @throws java.net.URISyntaxException
     */
    Produit getProduit(Long id) throws URISyntaxException, RestClientException;

    /**
     * method to get a Produit by code
     *
     * @param code
     * @param userId
     * @return Produit object find
     * @throws java.net.URISyntaxException
     */
    Produit getProduitByCode(String code, String userId) throws URISyntaxException, RestClientException;

    /**
     * method to get all Produits by criteria
     *
     * @param code
     * @param produit
     * @param inStock
     * @param p number page
     * @param s size page
     * @param userId
     * @return the Produit pages
     * @throws java.net.URISyntaxException
     */
    Page<Produit> getAllProduitsByCriteria(String code, String produit, String inStock, int p, int s, String userId) throws URISyntaxException, RestClientException;

    /**
     * method to save a Produit in the stock
     *
     * @param produitDetailsDTO
     * @return Produit object save
     * @throws java.net.URISyntaxException
     */
    Produit saveProduitInStock(ProduitDTO produitDetailsDTO) throws URISyntaxException, RestClientException;

}
