package net.nicolasdot.meal_service.services;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import net.nicolasdot.meal_service.dao.IMealRepository;
import net.nicolasdot.meal_service.dto.MealDTO;
import net.nicolasdot.meal_service.entity.Composition;
import net.nicolasdot.meal_service.entity.Consumable;
import net.nicolasdot.meal_service.entity.Meal;
import net.nicolasdot.meal_service.entity.MealType;
import net.nicolasdot.meal_service.entity.ConsumableNutriment;
import net.nicolasdot.meal_service.exceptions.EntityAlreadyExistsException;
import net.nicolasdot.meal_service.exceptions.EntityNotFoundException;
import net.nicolasdot.meal_service.exceptions.MealNotPossibleException;
import net.nicolasdot.meal_service.services.interfaces.IMealService;
import net.nicolasdot.meal_service.services.interfaces.IMealTypeService;
import net.nicolasdot.meal_service.specifications.MealCriteria;
import net.nicolasdot.meal_service.specifications.MealSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

/**
 *
 * @author nicolasdotnet
 */
@Service
@Transactional
public class MealServiceImpl implements IMealService {

    @Autowired
    IMealRepository iMealRepository;

    @Autowired
    IMealTypeService iMealTypeService;

    @Override
    public Meal createMeal(MealDTO mealDTO) throws EntityNotFoundException {

        Meal meal = new Meal();

        Optional<MealType> mealTypeFind = iMealTypeService.getMealType(Long.valueOf(mealDTO.getTypeId()));

        if (mealTypeFind.isEmpty()) {

            //log.error("le type de repas n'existe pas dans la base.");
            throw new EntityNotFoundException("le type de repas n'existe pas !");
        }

        meal.setMealType(mealTypeFind.get());
        meal.setDate(new Date());
        meal.setValidate(Boolean.FALSE);
        meal.setUserId(mealDTO.getUserId());

        return iMealRepository.save(meal);

    }

    @Override
    public Meal validateMeal(Long mealId) throws EntityNotFoundException, EntityAlreadyExistsException, MealNotPossibleException {

        Optional<Meal> mealFind = iMealRepository.findById(mealId);

        if (mealFind.isEmpty()) {

            //log.error("Le repas n'existe pas !");
            throw new EntityNotFoundException("Le repas n'existe pas !");

        }

        if (mealFind.get().getValidate()) {

            //log.error("Le repas n'existe pas !");
            throw new MealNotPossibleException("Le repas est déjà validé !");

        }

        Collection<Consumable> consumables = mealFind.get().getConsumables();

        if (consumables.isEmpty()) {

            throw new EntityNotFoundException("Le repas ne contient pas de l'éléments !");
        }

        Composition composition = new Composition();

        for (Consumable consumable : consumables) {

            ConsumableNutriment nt = consumable.getConsumableNutriment();
            composition.setEnergy(composition.getEnergy() + nt.getEnergy());
            composition.setEnergyKjUnit(nt.getEnergyKjUnit());
            composition.setCarbohydrates(composition.getCarbohydrates() + nt.getCarbohydrates());
            composition.setCarbohydratesUnit(nt.getCarbohydratesUnit());
            composition.setFat(composition.getFat() + nt.getFat());
            composition.setFatUnit(nt.getFatUnit());
            composition.setProteins(composition.getProteins() + nt.getProteins());
            composition.setProteinsUnit(nt.getProteinsUnit());
            composition.setSalt(composition.getSalt() + nt.getSalt());
            composition.setSaltUnit(nt.getSaltUnit());
            composition.setSaturatedFat(composition.getSaturatedFat() + nt.getSaturatedFat());
            composition.setSaturatedFatUnit(nt.getSaturatedFatUnit());
            composition.setSugars(composition.getSugars() + nt.getSugars());
            composition.setSugarsUnit(nt.getSugarsUnit());

        }

        composition.setMeal(mealFind.get());
        mealFind.get().setValidate(Boolean.TRUE);
        mealFind.get().setValidateDate(LocalDate.now());
        mealFind.get().setComposition(composition);

        return iMealRepository.saveAndFlush(mealFind.get());
    }

    @Override
    public Meal getOneMeal(Long mealId) throws EntityNotFoundException {

        Optional<Meal> mealFind = iMealRepository.findById(mealId);

        if (!mealFind.isPresent()) {

            throw new EntityNotFoundException("Le repas n'existe pas !");
        }

        return mealFind.get();
    }

    @Override
    public Page<Meal> getAllMealsByCriteria(MealCriteria mealCriteria, int page, int size) {

        mealCriteria.setMealId("".equals(mealCriteria.getMealId()) ? null : mealCriteria.getMealId());
        mealCriteria.setMealtype("".equals(mealCriteria.getMealtype()) ? null : mealCriteria.getMealtype());
        mealCriteria.setUserId("".equals(mealCriteria.getUserId()) ? null : mealCriteria.getUserId());

        MealSpecification mealSpecification = new MealSpecification(mealCriteria);

        return iMealRepository.findAll(mealSpecification, PageRequest.of(page, size, Sort.Direction.DESC, "id"));
    }

    @Override
    public void deleteMeal(Long mealId) throws EntityNotFoundException {

        Optional<Meal> mealFind = iMealRepository.findById(mealId);

        if (mealFind.isEmpty()) {

            throw new EntityNotFoundException("Le repas n'existe pas !");
        }

        iMealRepository.delete(mealFind.get());

    }

    @Override
    public List<Meal> getAllMealsBetweenTwoDate(LocalDate startDate, LocalDate endDate, String userId) {
        return iMealRepository.findAllByValidateDateBetweenAndUserId(startDate, endDate,userId);
    }


}
