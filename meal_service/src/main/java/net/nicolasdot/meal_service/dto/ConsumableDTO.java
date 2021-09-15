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
public class ConsumableDTO {

    @NotNull(message = "la quantité n'est pas renseigné")
    private float quantity;

    @NotNull(message = "id du produit n'est pas renseigné")
    private int produitId;

    @NotNull(message = "id du repas n'est pas renseigné")
    private int mealId;

    @NotEmpty(message = "l'id du user n'est pas renseigné")
    private String userId;

}
