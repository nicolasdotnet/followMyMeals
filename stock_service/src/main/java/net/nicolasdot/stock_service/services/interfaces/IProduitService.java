package net.nicolasdot.stock_service.services.interfaces;

import java.util.List;
import net.nicolasdot.stock_service.entity.Produit;
import net.nicolasdot.stock_service.exceptions.EntityNotFoundException;
import net.nicolasdot.stock_service.exceptions.NotPossibleException;
import net.nicolasdot.stock_service.specifications.ProduitCriteria;
import org.springframework.data.domain.Page;

/**
 *
 * @author nicolasdotnet
 */
public interface IProduitService {

    /**
     * method to fetch a produit by code
     *
     * @param code
     * @param userId
     * @return produit object saved
     * @throws net.nicolasdot.stock_service.exceptions.EntityNotFoundException
     */
    Produit consultProduitByCode(String code, String userId) throws EntityNotFoundException;

    /**
     * method to get a produit by id
     *
     * @param id
     * @return produit object find
     * @throws net.nicolasdot.stock_service.exceptions.EntityNotFoundException
     */
    Produit getProduitById(Long id) throws EntityNotFoundException;

    /**
     * method to delete produit
     *
     * @param id
     * @throws net.nicolasdot.stock_service.exceptions.EntityNotFoundException
     */
    void deleteProduit(Long id) throws EntityNotFoundException;

    /**
     * method to save a produit in the stock
     *
     * @param produitId
     * @param userId
     * @return produit object save in the stock
     * @throws net.nicolasdot.stock_service.exceptions.EntityNotFoundException
     * @throws net.nicolasdot.stock_service.exceptions.NotPossibleException
     */
    Produit saveProduit(Long produitId, String userId) throws EntityNotFoundException, NotPossibleException;

    /**
     * method to get all produits by criteria
     *
     * @param produitCriteria
     * @param page
     * @param size
     * @return the pages produits
     */
    Page<Produit> getAllProduitsByCriteria(ProduitCriteria produitCriteria, int page, int size);
    
    /**
     * method to call update (from OpenFoodsFact) the produits
     *
     * @return list produit update
     * @throws net.nicolasdot.stock_service.exceptions.EntityNotFoundException
     */
    List<Produit> ManagementUpdateProduits() throws EntityNotFoundException;
}
