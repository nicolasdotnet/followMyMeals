package net.nicolasdot.front_service.beans;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author nicolasdotnet
 *
 * Consumable is the entity of a Consumable.
 */
@NoArgsConstructor
@Data
public class Consumable {

    private Long id;

    private String userId;
    
    private String consumableName;

    private float quantity;

    private String quantityUnit;

    private Long produitId;

    private Boolean favory;

    private Meal meal;

    private ConsumableNutriment consumableNutriment;

    @Override
    public String toString() {
        return "Consumable{"
                + "id=" + id
                + "quantity=" + quantity
                + "favory=" + favory
                + '}';
    }

}
