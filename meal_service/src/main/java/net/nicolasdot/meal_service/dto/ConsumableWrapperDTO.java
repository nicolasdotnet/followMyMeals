package net.nicolasdot.meal_service.dto;

import java.util.List;
import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author nicolasdotnet
 */
@NoArgsConstructor
@Data
public class ConsumableWrapperDTO {
    
    @NotNull(message = "id du repas n'est pas renseigné")
    private int mealId;
    
    private List<ConsumableDTO> consumables;
    
}
