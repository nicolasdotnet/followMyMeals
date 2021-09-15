package net.nicolasdot.meal_service.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author nicolasdotnet
 */
@NoArgsConstructor
@Data
public class MealDTO {
    
    @NotEmpty(message = "l'id du user n'est pas renseigné")
    private String userId;
    
    @NotNull(message = "id du type de repas n'est pas renseigné")
    private int typeId;
    
}
