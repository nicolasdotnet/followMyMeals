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
    
    // Ajouter : userid, un mealid, une liste de  consumableDTO 
    
    // algo + test
    
    // tester les id & quantity
    
    // ajouter un par un les consu : calculer nutriment et persistance
    
    Consumable addConsumable(ConsumableDTO consumableDTO) throws EntityNotFoundException, NotPossibleException;    
    // Retirer : userid, un mealid, une liste de  produitid

    void removeConsumable(Long consumableId)throws EntityNotFoundException, NotPossibleException;
    
    // méthode update (consu id , quantity)

   Page<Consumable> getAllConsumablesByCriteria(ConsumableCriteria consumableCriteria, int page, int size);

   Consumable getConsumableById(Long consumableId) throws EntityNotFoundException;
    
}
