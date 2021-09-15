package net.nicolasdot.front_service.beans;

import java.io.Serializable;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author nicolasdotnet
 *
 * Composition is the bean of a Composition.
 */
@NoArgsConstructor
@Data
public class Composition implements Serializable {

    private Long id;

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

    private Meal meal;

}
