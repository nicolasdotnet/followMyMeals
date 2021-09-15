package net.nicolasdot.meal_service.specifications;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author nicolasdotnet
 */
@NoArgsConstructor
@Data
public class ConsumableCriteria {

    private Long consumableId;

    private Long mealId;
    
    private String userId;
}
