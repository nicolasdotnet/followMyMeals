package net.nicolasdot.front_service.beans;

import java.time.LocalDate;
import java.util.Date;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author nicolasdotnet
 *
 * Produit is the bean of a Produit.
 */
@NoArgsConstructor
@Data
public class Reporting {

    private Long id;

    private Date date;

    private String userId;

    private int numberMeals;

    private Date today;

    private Date seventhDay;

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
