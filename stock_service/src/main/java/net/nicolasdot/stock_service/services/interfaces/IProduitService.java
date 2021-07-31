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
     * @return produit object saved
     * @throws net.nicolasdot.stock_service.exceptions.EntityNotFoundException
     */
    Produit consultProduitByCode(String code) throws EntityNotFoundException;

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
     * method to save a produit quatity
     *
     * @param id
     * @param quantity
     * @return produit object update
     * @throws net.nicolasdot.stock_service.exceptions.EntityNotFoundException
     * @throws net.nicolasdot.stock_service.exceptions.NotPossibleException
     */
    Produit saveProduit(Long id, int quantity) throws EntityNotFoundException, NotPossibleException;

    /**
     * method to update a produit quatity
     *
     * @param id
     * @param quantity
     * @return produit object update
     * @throws net.nicolasdot.stock_service.exceptions.EntityNotFoundException
     * @throws net.nicolasdot.stock_service.exceptions.NotPossibleException
     */
    Produit updateQuantityProduit(Long id, int quantity) throws EntityNotFoundException, NotPossibleException;

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
