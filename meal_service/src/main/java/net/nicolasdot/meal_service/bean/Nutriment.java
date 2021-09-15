package net.nicolasdot.meal_service.bean;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

/**
 *
 * @author nicolasdotnet
 *
 * Nutriment is the bean of a Nutriment.
 */
@Component
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

    private Produit produit;
}
