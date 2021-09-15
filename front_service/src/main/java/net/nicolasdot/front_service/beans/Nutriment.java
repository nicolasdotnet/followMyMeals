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
public class Nutriment {

    private Long id;

    private int energy100G;

    private String energyKjUnit;

    private float fat100G;

    private String fatUnit;

    private float saturatedFat100G;

    private String saturatedFatUnit;

    private float carbohydrates100G;

    private String carbohydratesUnit;

    private float sugars100G;

    private String sugarsUnit;

    private float fiber100G;

    private String fiberUnit;

    private float proteins100G;

    private String proteinsUnit;

    private float salt100G;

    private String saltUnit;

    private float calcium100G;

    private String calciumUnit;

    private float iron100G;

    private String ironUnit;

    private float vitaminA100G;

    private String vitaminAUnit;

    private float vitaminC100G;

    private String vitaminCUnit;

    private float vitaminD100G;

    private String vitaminDUnit;

}
