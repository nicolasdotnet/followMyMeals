package net.nicolasdot.meal_service.dto;

import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author nicolasdotnet
 */
@NoArgsConstructor
@Data
public class ConsumableWrapper {

    private Long mealId;
    private List<Long> consumables;

}
