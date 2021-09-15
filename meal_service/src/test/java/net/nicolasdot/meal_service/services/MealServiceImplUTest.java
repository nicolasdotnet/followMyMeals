package net.nicolasdot.meal_service.services;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import net.nicolasdot.meal_service.dao.IMealRepository;
import net.nicolasdot.meal_service.dto.MealDTO;
import net.nicolasdot.meal_service.entity.Consumable;
import net.nicolasdot.meal_service.entity.Meal;
import net.nicolasdot.meal_service.entity.MealType;
import net.nicolasdot.meal_service.entity.ConsumableNutriment;
import net.nicolasdot.meal_service.exceptions.EntityNotFoundException;
import net.nicolasdot.meal_service.exceptions.MealNotPossibleException;
import net.nicolasdot.meal_service.services.interfaces.IMealTypeService;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 *
 * @author nicolasdotnet
 */
@ExtendWith(MockitoExtension.class)
public class MealServiceImplUTest {

    @InjectMocks
    private MealServiceImpl instance;

    @Mock
    private IMealRepository iMealRepository;

    @Mock
    private IMealTypeService iMealTypeService;

    /**
     * Test of createMeal method, of class MealServiceImpl.
     */
    @Test
    public void testCreateMealWhenMealTypeDoesNotExit() throws Exception {
        System.out.println("create");

        Exception exception = assertThrows(EntityNotFoundException.class,
                () -> {

                    MealDTO mealDTO = new MealDTO();
                    mealDTO.setTypeId(00);

                    Optional<MealType> op = Optional.empty();

                    when(iMealTypeService.getMealType(Long.valueOf(mealDTO.getTypeId()))).thenReturn(op);

                    instance.createMeal(mealDTO);
                });

        String expectedMessage = "le type de repas n'existe pas !";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    /**
     * Test of createMeal method, of class MealServiceImpl.
     */
    @Test
    public void testCreateWhenMealDTOIsGood() throws Exception {
        System.out.println("create");

        MealDTO mealDTO = new MealDTO();
        mealDTO.setTypeId(00);

        Optional<MealType> op = Optional.of(new MealType());

        Meal expResult = new Meal();
        expResult.setValidate(Boolean.FALSE);

        when(iMealTypeService.getMealType(Long.valueOf(mealDTO.getTypeId()))).thenReturn(op);
        when(iMealRepository.save(any())).thenReturn(expResult);

        Meal result = instance.createMeal(mealDTO);
        assertEquals(expResult.getValidate(), result.getValidate());

        verify(iMealRepository).save(any());

    }

    /**
     * Test of deleteMeal method, of class MealServiceImpl.
     */
    @Test
    public void testDeleteMealWhenMealExist() throws Exception {
        System.out.println("delete");

        Optional<Meal> op = Optional.of(new Meal());

        when(iMealRepository.findById(any())).thenReturn(op);

        instance.deleteMeal(any());
        verify(iMealRepository).findById(any());

    }

    /**
     * Test of deleteMeal method, of class MealServiceImpl.
     */
    @Test
    public void testDeleteMealDoesNotExit() throws Exception {
        System.out.println("delete");

        Exception exception = assertThrows(EntityNotFoundException.class,
                () -> {

                    Optional<Meal> op = Optional.empty();

                    when(iMealRepository.findById(any())).thenReturn(op);

                    instance.deleteMeal(any());
                });

        String expectedMessage = "Le repas n'existe pas !";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));

    }

    /**
     * Test of validateMeal method, of class MealServiceImpl.
     */
    @Test
    public void testValidate1MealWhenMealDoesNotExit() throws Exception {
        System.out.println("validateMeal");

        Exception exception = assertThrows(EntityNotFoundException.class,
                () -> {

                    Optional<Meal> op = Optional.empty();

                    when(iMealRepository.findById(any())).thenReturn(op);

                    instance.validateMeal(any());
                });

        String expectedMessage = "Le repas n'existe pas !";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    /**
     * Test of validateMeal method, of class MealServiceImpl.
     */
    @Test
    public void testValidate1MealWhenMealIsAlreadyValidate() throws Exception {
        System.out.println("validateMeal");

        Exception exception = assertThrows(MealNotPossibleException.class,
                () -> {

                    Meal meal = new Meal();
                    meal.setValidate(Boolean.TRUE);

                    Optional<Meal> op = Optional.of(meal);

                    when(iMealRepository.findById(any())).thenReturn(op);

                    instance.validateMeal(any());
                });

        String expectedMessage = "Le repas est déjà validé !";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    /**
     * Test of validate method, of class MealServiceImpl.
     *
     * @throws java.lang.Exception
     */
    @Test
    public void testValidateMealWhenMealHasNotConsumables() throws Exception {
        System.out.println("validateMeal");

        Exception exception = assertThrows(EntityNotFoundException.class,
                () -> {

                    Meal meal = new Meal();
                    Collection<Consumable> consumables = new ArrayList<>();
                    meal.setConsumables(consumables);
                    meal.setValidate(Boolean.FALSE);

                    Optional<Meal> op = Optional.of(meal);

                    when(iMealRepository.findById(any())).thenReturn(op);

                    instance.validateMeal(any());
                });

        String expectedMessage = "Le repas ne contient pas de l'éléments !";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    /**
     * Test of validateMeal method, of class MealServiceImpl.
     */
    @Test
    public void testValidateMeal() throws Exception {
        System.out.println("validateMeal");

        Meal meal = new Meal();
        
        Collection<Consumable> consumables = new ArrayList<>();
        Consumable consumable = new Consumable();
        
        ConsumableNutriment nutriment = new ConsumableNutriment();
        nutriment.setCalcium(0);
        nutriment.setCalciumUnit("x");
        nutriment.setCarbohydrates(0);
        nutriment.setCarbohydratesUnit("x");
        nutriment.setEnergy(0);
        nutriment.setEnergyKjUnit("x");
        nutriment.setFat(0);
        nutriment.setFatUnit("x");
        nutriment.setFiber(0);
        nutriment.setFiberUnit("x");
        nutriment.setGrade("A");
        nutriment.setIron(0);
        nutriment.setIronUnit("x");
        nutriment.setProteins(0);
        nutriment.setProteinsUnit("x");
        nutriment.setSalt(0);
        nutriment.setSaltUnit("x");
        nutriment.setSaturatedFat(0);
        nutriment.setSaturatedFatUnit("x");
        nutriment.setSugars(0);
        nutriment.setSugarsUnit("X");
        nutriment.setVitaminA(0);
        nutriment.setVitaminAUnit("x");
        nutriment.setVitaminC(0);
        nutriment.setVitaminCUnit("x");
        nutriment.setVitaminD(0);
        nutriment.setVitaminDUnit("x");

        consumable.setConsumableNutriment(nutriment);
        consumables.add(consumable);

        meal.setConsumables(consumables);
        meal.setValidate(Boolean.FALSE);
        Optional<Meal> op = Optional.of(meal);

        when(iMealRepository.findById(any())).thenReturn(op);

        Meal expResult = new Meal();
        expResult.setValidate(Boolean.TRUE);
        
        when(iMealRepository.saveAndFlush(any())).thenReturn(expResult);
        
        Meal result = instance.validateMeal(any());

        assertEquals(expResult.getValidate(), result.getValidate());
        
        verify(iMealRepository).findById(any());

    }

    /**
     * Test of getOneMeal method, of class MealServiceImpl.
     */
    @Test
    public void testGetOneMealWhenMealExist() throws EntityNotFoundException {
        System.out.println("getOneMeal");

        Meal meal = new Meal();
        meal.setValidate(Boolean.FALSE);

        Optional<Meal> op = Optional.of(meal);

        when(iMealRepository.findById(any())).thenReturn(op);

        Meal result = instance.getOneMeal(any());

        assertEquals(meal.getValidate(), result.getValidate());
        verify(iMealRepository).findById(any());

    }

    /**
     * Test of getOneMeal method, of class MealServiceImpl.
     */
    @Test
    public void testGetOneMealWhenMealDoesNotExit() throws EntityNotFoundException {
        System.out.println("getOneMeal");

        Exception exception = assertThrows(EntityNotFoundException.class,
                () -> {

                    Optional<Meal> op = Optional.empty();

                    when(iMealRepository.findById(any())).thenReturn(op);

                    instance.getOneMeal(any());
                });

        String expectedMessage = "Le repas n'existe pas !";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));

    }

    /**
     * Test of getAllMealsBetweenTwoDate method, of class IMealService.
     */
    @Test
    public void testGetAllMealsBetweenTwoDate() {
        System.out.println("getAllMealsBetweenTwoDate");

        LocalDate seventhDay = LocalDate.now().plusDays(6);
        LocalDate today = LocalDate.now();
        String userId = "000L";

        List<Meal> expResult = new ArrayList<>();
        expResult.add(new Meal());

        when(iMealRepository.findAllByValidateDateBetweenAndUserId(today, seventhDay, userId)).thenReturn(expResult);

        List<Meal> result = instance.getAllMealsBetweenTwoDate(today, seventhDay, userId);
        assertEquals(expResult.size(), result.size());

        verify(iMealRepository).findAllByValidateDateBetweenAndUserId(today, seventhDay, userId);

    }
}
