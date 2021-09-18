package net.nicolasdot.meal_service.services.interfaces;

import net.nicolasdot.meal_service.dto.ConsumableDTO;
import net.nicolasdot.meal_service.entity.Consumable;
import net.nicolasdot.meal_service.exceptions.EntityNotFoundException;
import net.nicolasdot.meal_service.exceptions.NotPossibleException;
import net.nicolasdot.meal_service.specifications.ConsumableCriteria;
import org.springframework.data.domain.Page;

/**
 *
 * @author nicolasdotnet
 */
public interface IConsumableService {

    /**
     * method to add consumable
     *
     * @param consumableDTO
     * @return consumable object saved
     * @throws net.nicolasdot.meal_service.exceptions.EntityNotFoundException
     * @throws net.nicolasdot.meal_service.exceptions.NotPossibleException
     */
    Consumable addConsumable(ConsumableDTO consumableDTO) throws EntityNotFoundException, NotPossibleException;

    /**
     * method method to delete consumable
     *
     * @param consumableId
     * @throws net.nicolasdot.meal_service.exceptions.EntityNotFoundException
     * @throws net.nicolasdot.meal_service.exceptions.NotPossibleException
     */
    void removeConsumable(Long consumableId) throws EntityNotFoundException, NotPossibleException;

    /**
     * method to get all consumables by criteria
     *
     * @param consumableCriteria
     * @param page
     * @param size
     * @return the pages consumables
     */
    Page<Consumable> getAllConsumablesByCriteria(ConsumableCriteria consumableCriteria, int page, int size);

    /**
     * method to get a consumables by id
     *
     * @param consumableId
     * @return consumable object find
     * @throws net.nicolasdot.meal_service.exceptions.EntityNotFoundException
     */
    Consumable getConsumableById(Long consumableId) throws EntityNotFoundException;

}
