package net.nicolasdot.meal_service.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author nicolasdotnet

 ConsumableNutriment is the entity of a ConsumableNutriment.
 */
@Entity
@NoArgsConstructor
@Data
public class ConsumableNutriment implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column()
    private String grade;

    @OneToOne(mappedBy = "consumableNutriment")
    @JsonIgnore
    private Consumable consumable;

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

    @Column(name = "fiber")
    private float fiber;

    @Column(name = "fiber_unit")
    private String fiberUnit;

    @Column(name = "proteins")
    private float proteins;

    @Column(name = "proteins_unit")
    private String proteinsUnit;

    @Column(name = "salt")
    private float salt;

    @Column(name = "salt_unit")
    private String saltUnit;

    @Column(name = "calcium")
    private float calcium;

    @Column(name = "calcium_unit")
    private String calciumUnit;

    @Column(name = "iron")
    private float iron;

    @Column(name = "iron_unit")
    private String ironUnit;

    @Column(name = "vitamin_a")
    private float vitaminA;

    @Column(name = "vitamin_a_unit")
    private String vitaminAUnit;

    @Column(name = "vitamin_c")
    private float vitaminC;

    @Column(name = "vitamin_c_unit")
    private String vitaminCUnit;

    @Column(name = "vitamin_d")
    private float vitaminD;

    @Column(name = "vitamin_d_unit")
    private String vitaminDUnit;
}
