package net.nicolasdot.meal_service.services;

import java.util.Optional;
import net.nicolasdot.meal_service.bean.Nutriment;
import net.nicolasdot.meal_service.bean.Produit;
import net.nicolasdot.meal_service.dao.IConsumableRepository;
import net.nicolasdot.meal_service.dto.ConsumableDTO;
import net.nicolasdot.meal_service.entity.Consumable;
import net.nicolasdot.meal_service.entity.Meal;
import net.nicolasdot.meal_service.exceptions.EntityNotFoundException;
import net.nicolasdot.meal_service.exceptions.NotPossibleException;
import net.nicolasdot.meal_service.services.interfaces.IMealService;
import net.nicolasdot.meal_service.services.interfaces.IProxyService;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
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
public class ConsumableServiceImplUTest {

    @InjectMocks
    private ConsumableServiceImpl instance;

    @Mock
    private IConsumableRepository iConsumableRepository;

    @Mock
    private IMealService iMealService;

    @Mock
    private IProxyService iProxyService;

    /**
     * Test of addConsumables method, of class ConsumableServiceImpl.
     */
    @Test
    public void testAddConsumableWhenMealDoesNotExist() throws Exception {
        System.out.println("addConsumable");

        Exception exception = assertThrows(EntityNotFoundException.class,
                () -> {

                    ConsumableDTO consumableDTO = new ConsumableDTO();
                    consumableDTO.setMealId(001);

                    Meal meal = null;

                    when(iMealService.getOneMeal(Long.valueOf(consumableDTO.getMealId()))).thenReturn(meal);

                    instance.addConsumable(consumableDTO);
                });

        String expectedMessage = "Le repas n'existe pas !";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));

    }

    /**
     * Test of addConsumables method, of class ConsumableServiceImpl.
     */
    @Test
    public void testAddConsumableWhenMealIsValidate() throws Exception {
        System.out.println("addConsumable");

        Exception exception = assertThrows(NotPossibleException.class,
                () -> {

                    ConsumableDTO consumableDTO = new ConsumableDTO();
                    consumableDTO.setMealId(001);

                    Meal meal = new Meal();
                    meal.setValidate(Boolean.TRUE);

                    when(iMealService.getOneMeal(Long.valueOf(consumableDTO.getMealId()))).thenReturn(meal);

                    instance.addConsumable(consumableDTO);
                });

        String expectedMessage = "Le repas est validate !";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));

    }

    /**
     * Test of addConsumables method, of class ConsumableServiceImpl.
     */
    @Test
    public void testAddConsumableWhenQuantityDoesNotExist() throws Exception {
        System.out.println("addConsumable");

        Exception exception = assertThrows(NotPossibleException.class,
                () -> {

                    ConsumableDTO consumableDTO = new ConsumableDTO();
                    consumableDTO.setMealId(001);
                    consumableDTO.setQuantity(0);

                    Meal meal = new Meal();
                    meal.setValidate(Boolean.FALSE);

                    when(iMealService.getOneMeal(Long.valueOf(consumableDTO.getMealId()))).thenReturn(meal);

                    instance.addConsumable(consumableDTO);
                });

        String expectedMessage = "Il n'y a pas de quantité !";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));

    }

    /**
     * Test of addConsumables method, of class ConsumableServiceImpl.
     */
    @Test
    public void testAddConsumableWhenProduitIsAlreadyExistInTheMeal() throws Exception {
        System.out.println("addConsumable");

        Exception exception = assertThrows(NotPossibleException.class,
                () -> {

                    ConsumableDTO consumableDTO = new ConsumableDTO();
                    consumableDTO.setMealId(001);
                    consumableDTO.setQuantity(5);
                    consumableDTO.setProduitId(0);

                    Meal meal = new Meal();
                    meal.setValidate(Boolean.FALSE);

                    Produit produit = new Produit();
                    produit.setCode("0000");

                    Optional<Produit> opProduit = Optional.of(produit);

                    when(iProxyService.getProduitById(any())).thenReturn(opProduit);

                    Consumable consumable = new Consumable();
                    consumable.setConsumableName("xxx");

                    Optional<Consumable> opConsumable = Optional.of(consumable);

                    when(iConsumableRepository.findByProduitIdAndMealId(any(), any())).thenReturn(opConsumable);

                    when(iMealService.getOneMeal(Long.valueOf(consumableDTO.getMealId()))).thenReturn(meal);

                    instance.addConsumable(consumableDTO);
                });

        String expectedMessage = "Le produit est déjà dans le menue !";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));

    }

    /**
     * Test of addConsumables method, of class ConsumableServiceImpl.
     */
    @Test
    public void testAddConsumableWhenProduitDoesNotExist() throws Exception {
        System.out.println("addConsumable");

        Exception exception = assertThrows(NotPossibleException.class,
                () -> {

                    ConsumableDTO consumableDTO = new ConsumableDTO();
                    consumableDTO.setMealId(001);
                    consumableDTO.setQuantity(5);
                    consumableDTO.setProduitId(0);

                    Meal meal = new Meal();
                    meal.setValidate(Boolean.FALSE);

                    Optional<Produit> opProduit = Optional.empty();

                    when(iProxyService.getProduitById(any())).thenReturn(opProduit);

                    Optional<Consumable> opConsumable = Optional.empty();

                    when(iConsumableRepository.findByProduitIdAndMealId(any(), any())).thenReturn(opConsumable);

                    when(iMealService.getOneMeal(Long.valueOf(consumableDTO.getMealId()))).thenReturn(meal);

                    instance.addConsumable(consumableDTO);
                });

        String expectedMessage = "Le produit n'existe pas !";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));

    }

    /**
     * Test of addConsumable method, of class IConsumableService.
     */
    @Test
    public void testAddConsumable() throws Exception {
        System.out.println("addConsumable");

        ConsumableDTO consumableDTO = new ConsumableDTO();
        consumableDTO.setMealId(001);
        consumableDTO.setQuantity(5);
        consumableDTO.setProduitId(0);

        Meal meal = new Meal();
        meal.setValidate(Boolean.FALSE);

        when(iMealService.getOneMeal(Long.valueOf(consumableDTO.getMealId()))).thenReturn(meal);

        Nutriment nutriment = new Nutriment();
        nutriment.setCarbohydrates100G(0);
        nutriment.setCarbohydratesUnit("x");
        nutriment.setEnergy100G(0);
        nutriment.setEnergyKjUnit("x");
        nutriment.setFat100G(0);
        nutriment.setFatUnit("x");
        nutriment.setFiber100G(0);
        nutriment.setFiberUnit("x");
        nutriment.setProteins100G(0);
        nutriment.setProteinsUnit("x");
        nutriment.setSalt100G(0);
        nutriment.setSaltUnit("x");
        nutriment.setSaturatedFat100G(0);
        nutriment.setSaturatedFatUnit("x");
        nutriment.setSugars100G(0);
        nutriment.setSugarsUnit("X");

        Produit produit = new Produit();
        produit.setCode("0000");
        produit.setId(1L);
        produit.setNutritionGrades("A");
        produit.setProduitName("xxx");
        produit.setNutriment(nutriment);

        Optional<Produit> opProduit = Optional.of(produit);
        when(iProxyService.getProduitById(any())).thenReturn(opProduit);

        Optional<Consumable> opConsumable = Optional.empty();
        when(iConsumableRepository.findByProduitIdAndMealId(any(), any())).thenReturn(opConsumable);

        Consumable expResult = new Consumable();
        expResult.setConsumableName("xxx");

        Consumable result = instance.addConsumable(consumableDTO);
        assertEquals(expResult.getConsumableName(), result.getConsumableName());

    }

    /**
     * Test of removeConsumable method, of class IConsumableService.
     */
    @Test
    public void testRemoveConsumableWhenMealIsValidate() throws Exception {
        System.out.println("removeConsumable");

        Exception exception = assertThrows(NotPossibleException.class,
                () -> {

                    Long consumableId = 0L;

                    Consumable consumable = new Consumable();
                    consumable.setConsumableName("xxx");
                    Meal meal = new Meal();
                    meal.setValidate(Boolean.TRUE);
                    consumable.setMeal(meal);

                    Optional<Consumable> opConsumable = Optional.of(consumable);

                    when(iConsumableRepository.findById(any())).thenReturn(opConsumable);

                    instance.removeConsumable(consumableId);

                });

        String expectedMessage = "Le repas est validé";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));

    }

    /**
     * Test of removeConsumable method, of class IConsumableService.
     */
    @Test
    public void testRemoveConsumableWhenConsumableDoesNotExist() throws Exception {
        System.out.println("removeConsumable");

        Exception exception = assertThrows(EntityNotFoundException.class,
                () -> {

                    Long consumableId = 0L;

                    Optional<Consumable> opConsumable = Optional.empty();

                    when(iConsumableRepository.findById(any())).thenReturn(opConsumable);

                    instance.removeConsumable(consumableId);

                });

        String expectedMessage = "La consommation n'existe pas !";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));

    }

    /**
     * Test of removeConsumable method, of class IConsumableService.
     */
    @Test
    public void testRemoveConsumableWhenConsumableExist() throws Exception {
        System.out.println("removeConsumable");

        Long consumableId = 0L;

        Consumable consumable = new Consumable();
        consumable.setConsumableName("xxx");
        Meal meal = new Meal();
        meal.setValidate(Boolean.FALSE);
        consumable.setMeal(meal);

        Optional<Consumable> op = Optional.of(consumable);

        when(iConsumableRepository.findById(any())).thenReturn(op);

        instance.removeConsumable(consumableId);

        verify(iConsumableRepository).delete(any());

    }

    /**
     * Test of getConsumableById method, of class IConsumableService.
     */
    @Test
    public void testGetConsumableByIdWhenConsumableDoesNotExist() throws Exception {
        System.out.println("getConsumableById");

        Exception exception = assertThrows(EntityNotFoundException.class,
                () -> {

                    Long consumableId = 0L;

                    Optional<Consumable> opConsumable = Optional.empty();

                    when(iConsumableRepository.findById(any())).thenReturn(opConsumable);

                    instance.getConsumableById(consumableId);

                });

        String expectedMessage = "La consommation n'existe pas !";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    /**
     * Test of getConsumableById method, of class IConsumableService.
     */
    @Test
    public void testGetConsumableByIdWhenConsumableExist() throws Exception {
        System.out.println("getConsumableById");

        Consumable consumable = new Consumable();
        consumable.setConsumableName("xxx");

        Optional<Consumable> op = Optional.of(consumable);

        when(iConsumableRepository.findById(any())).thenReturn(op);

        Consumable result = instance.getConsumableById(any());

        assertEquals(consumable.getConsumableName(), result.getConsumableName());
        verify(iConsumableRepository).findById(any());
    }
}
