package net.nicolasdot.meal_service.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Data;

/**
 *
 * @author nicolasdotnet
 *
 * Reporting is the entity of a Reporting.
 */
@Entity
@Data
public class Reporting implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column()
    private Date date;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "number_meals")
    private int numberMeals;

    @Column(name = "today")
    private LocalDate today;

    @Column(name = "seventh_day")
    private LocalDate seventhDay;

    @Column(name = "energy")
    private int energy;

    @Column(name = "energy_kj_unit")
    private String energyKjUnit;

    @Column(name = "fat")
    private float fat;

    @Column(name = "fat_unit")
    private String fatUnit;

    @Column(name = "saturated_fat")
    private float saturatedFat;

    @Column(name = "saturated_fat_unit")
    private String saturatedFatUnit;

    @Column(name = "carbohydrates")
    private float carbohydrates;

    @Column(name = "carbohydrates_unit")
    private String carbohydratesUnit;

    @Column(name = "sugars")
    private float sugars;

    @Column(name = "sugars_unit")
    private String sugarsUnit;

    @Column(name = "proteins")
    private float proteins;

    @Column(name = "proteins_unit")
    private String proteinsUnit;

    @Column(name = "salt")
    private float salt;

    @Column(name = "salt_unit")
    private String saltUnit;

    public Reporting() {

        this.energy = 0;
        this.fat = 0;
        this.saturatedFat = 0;
        this.carbohydrates = 0;
        this.sugars = 0;
        this.proteins = 0;
        this.salt = 0;
    }

}
