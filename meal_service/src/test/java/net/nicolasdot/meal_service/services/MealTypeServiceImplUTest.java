package net.nicolasdot.meal_service.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import net.nicolasdot.meal_service.dao.IMealTypeRepository;
import net.nicolasdot.meal_service.dto.MealTypeDTO;
import net.nicolasdot.meal_service.entity.MealType;
import net.nicolasdot.meal_service.exceptions.EntityAlreadyExistsException;
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
public class MealTypeServiceImplUTest {

    @InjectMocks
    private MealTypeServiceImpl instance;

    @Mock
    private IMealTypeRepository iMealTypeRepository;

    /**
     * Test of register method, of class MealTypeServiceImpl.
     */
    @Test
    public void testRegisterWhenMealAlreadyExists() throws Exception {
        System.out.println("register");

        Exception exception = assertThrows(EntityAlreadyExistsException.class,
                () -> {

                    MealTypeDTO mealTypeDTO = new MealTypeDTO();
                    mealTypeDTO.setLabel("xxx");

                    Optional<MealType> op = Optional.of(new MealType());

                    when(iMealTypeRepository.findByLabel(any())).thenReturn(op);

                    instance.register(mealTypeDTO);
                });

        String expectedMessage = "Le type de repas existe déjà !";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));

    }

    /**
     * Test of register method, of class MealTypeServiceImpl.
     */
    @Test
    public void testRegisterWhenMealTypeDTOisGood() throws Exception {
        System.out.println("register");

        MealTypeDTO mealTypeDTO = new MealTypeDTO();
        mealTypeDTO.setLabel("xxx");

        Optional<MealType> op = Optional.empty();

        when(iMealTypeRepository.findByLabel(any())).thenReturn(op);

        MealType expResult = new MealType();
        expResult.setLabel("xxx");

        when(iMealTypeRepository.save(any())).thenReturn(expResult);

        MealType result = instance.register(mealTypeDTO);
        assertEquals(expResult.getLabel(), result.getLabel());

        verify(iMealTypeRepository).findByLabel(any());

    }

    /**
     * Test of getMealType method, of class MealTypeServiceImpl.
     */
    @Test
    public void testGetMealTypeWhenMealIsGood() {
        System.out.println("getMealType");

        Long id = 001L;

        MealType mealType = new MealType();
        mealType.setLabel("xxx");
        Optional<MealType> expResult = Optional.of(mealType);

        when(iMealTypeRepository.findById(id)).thenReturn(expResult);

        Optional<MealType> result = instance.getMealType(id);
        assertEquals(expResult.get().getLabel(), result.get().getLabel());

        verify(iMealTypeRepository).findById(id);

    }

    /**
     * Test of getAllMealTypes method, of class MealTypeServiceImpl.
     */
    @Test
    public void testGetAllMealTypes() {
        System.out.println("getAllMealTypes");

        List<MealType> expResult = new ArrayList<>();
        expResult.add(new MealType());

        when(iMealTypeRepository.findAll()).thenReturn(expResult);

        List<MealType> result = instance.getAllMealTypes();
        assertEquals(expResult.size(), result.size());

        verify(iMealTypeRepository).findAll();
    }

}
