package net.nicolasdot.front_service.beans;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author nicolasdotnet
 *
 * Nutriment is the bean of a Nutriment.
 */
@NoArgsConstructor
@Data
public class ConsumableNutriment {

    private Long id;
    
    private Consumable consumable;
    
    private String grade;

    private int energy;

    private String energyKjUnit;

    private float fat;

    private String fatUnit;

    private float saturatedFat;

    private String saturatedFatUnit;

    private float carbohydrates;

    private String carbohydratesUnit;

    private float sugars;

    private String sugarsUnit;

    private float proteins;

    private String proteinsUnit;

    private float salt;

    private String saltUnit;

}
