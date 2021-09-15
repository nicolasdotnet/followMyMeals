package net.nicolasdot.meal_service.specifications;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author nicolasdotnet
 */
@NoArgsConstructor
@Data
public class MealCriteria {

    private Long mealId;

    private String Mealtype;
    
    private String userId;
}
