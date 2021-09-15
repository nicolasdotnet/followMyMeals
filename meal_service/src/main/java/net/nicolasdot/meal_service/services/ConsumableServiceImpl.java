package net.nicolasdot.meal_service.services;

import java.util.Optional;
import javax.transaction.Transactional;
import net.nicolasdot.meal_service.bean.Nutriment;
import net.nicolasdot.meal_service.bean.Produit;

import net.nicolasdot.meal_service.dao.IConsumableRepository;
import net.nicolasdot.meal_service.dto.ConsumableDTO;
import net.nicolasdot.meal_service.entity.Consumable;
import net.nicolasdot.meal_service.entity.Meal;
import net.nicolasdot.meal_service.entity.ConsumableNutriment;
import net.nicolasdot.meal_service.exceptions.EntityNotFoundException;
import net.nicolasdot.meal_service.exceptions.NotPossibleException;
import net.nicolasdot.meal_service.services.interfaces.IConsumableService;
import net.nicolasdot.meal_service.services.interfaces.IMealService;
import net.nicolasdot.meal_service.services.interfaces.IProxyService;
import net.nicolasdot.meal_service.specifications.ConsumableCriteria;
import net.nicolasdot.meal_service.specifications.ConsumableSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

/**
 *
 * @author nicolasdotnet
 */
@Service
@Transactional
public class ConsumableServiceImpl implements IConsumableService {

    @Autowired
    IMealService iMealService;

    @Autowired
    IConsumableRepository iConsumableRepository;

    @Autowired
    IProxyService iProxy;

    @Override
    public Consumable addConsumable(ConsumableDTO consumableDTO) throws EntityNotFoundException, NotPossibleException {

        Meal mealFind = iMealService.getOneMeal(Long.valueOf(consumableDTO.getMealId()));

        if (mealFind == null) {

            //log.error("Le repas n'existe pas !");
            throw new EntityNotFoundException("Le repas n'existe pas !");

        }

        if (mealFind.getValidate()) {

            //log.error("Le repas n'existe pas !");
            throw new NotPossibleException("Le repas est validate !");

        }

        if (consumableDTO.getQuantity() <= 0) {

            //log.error("Le repas n'existe pas !");
            throw new NotPossibleException("Il n'y a pas de quantité !");

        }

        Optional<Produit> produitFind = iProxy.getProduitById(Long.valueOf(consumableDTO.getProduitId()));

        Optional<Consumable> consumablefind = iConsumableRepository.findByProduitIdAndMealId(Long.valueOf(consumableDTO.getProduitId()), Long.valueOf(consumableDTO.getMealId()));

        // séparer en 2 les testes voir remove 
        if (produitFind.isPresent() && consumablefind.isEmpty()) {

            Consumable consumable = new Consumable();
            consumable.setProduitId(produitFind.get().getId());
            consumable.setMeal(mealFind);
            consumable.setQuantity(consumableDTO.getQuantity());
            consumable.setUserId(consumableDTO.getUserId());
            consumable.setConsumableName(produitFind.get().getProduitName());

            ConsumableNutriment consumableNutriment = new ConsumableNutriment();
            consumableNutriment.setGrade(produitFind.get().getNutritionGrades());

            Nutriment nt = produitFind.get().getNutriment();
            consumableNutriment.setEnergy((int) ((nt.getEnergy100G() * consumableDTO.getQuantity()) / 100));
            consumableNutriment.setEnergyKjUnit(nt.getEnergyKjUnit());
            consumableNutriment.setFat((nt.getFat100G() * consumableDTO.getQuantity()) / 100);
            consumableNutriment.setFatUnit(nt.getFatUnit());
            consumableNutriment.setSaturatedFat((nt.getSaturatedFat100G() * consumableDTO.getQuantity()) / 100);
            consumableNutriment.setSaturatedFatUnit(nt.getSaturatedFatUnit());
            consumableNutriment.setCarbohydrates((nt.getCarbohydrates100G() * consumableDTO.getQuantity()) / 100);
            consumableNutriment.setCarbohydratesUnit(nt.getCarbohydratesUnit());
            consumableNutriment.setSugars((nt.getSugars100G() * consumableDTO.getQuantity()) / 100);
            consumableNutriment.setSugarsUnit(nt.getSugarsUnit());
            consumableNutriment.setProteins((nt.getProteins100G() * consumableDTO.getQuantity()) / 100);
            consumableNutriment.setProteinsUnit(nt.getProteinsUnit());
            consumableNutriment.setSalt((nt.getSalt100G() * consumableDTO.getQuantity()) / 100);
            consumableNutriment.setSaltUnit(nt.getSaltUnit());

            consumable.setConsumableNutriment(consumableNutriment);

            iConsumableRepository.save(consumable);

            return consumable;

        } else if (produitFind.isPresent() && !consumablefind.isEmpty()) {

            throw new NotPossibleException("Le produit est déjà dans le menue !");

        } else {

            throw new NotPossibleException("Le produit n'existe pas !");

        }

    }

    @Override
    public void removeConsumable(Long consumableId) throws EntityNotFoundException, NotPossibleException {

        Optional<Consumable> ce = iConsumableRepository.findById(consumableId);

        if (ce.isPresent() && !ce.get().getMeal().getValidate()) {

            iConsumableRepository.delete(ce.get());

        } else if (ce.isPresent() && ce.get().getMeal().getValidate()) {

            throw new NotPossibleException("Le repas est validé");

        } else {

            //log.error("Le repas n'existe pas !");
            throw new EntityNotFoundException("La consommation n'existe pas !");
        }

    }

    // update (quantity)
    // si -12 erreur quantity négative
    @Override
    public Page<Consumable> getAllConsumablesByCriteria(ConsumableCriteria consumableCriteria, int page, int size) {

        ConsumableSpecification consumableSpecification = new ConsumableSpecification(consumableCriteria);

        return iConsumableRepository.findAll(consumableSpecification, PageRequest.of(page, size));
    }

    @Override
    public Consumable getConsumableById(Long consumableId) throws EntityNotFoundException {

        Optional<Consumable> consumableFind = iConsumableRepository.findById(consumableId);

        if (!consumableFind.isPresent()) {

            throw new EntityNotFoundException("La consommation n'existe pas !");
        }

        return consumableFind.get();
    }
}
