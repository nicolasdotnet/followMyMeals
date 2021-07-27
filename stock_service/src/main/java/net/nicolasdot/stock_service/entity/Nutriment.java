package net.nicolasdot.stock_service.entity;

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
 *
 * Nutriment is the entity of a Nutriment.
 */
@Entity
@NoArgsConstructor
@Data
public class Nutriment implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "energy_100G")
    private int energy100G;

    @Column(name = "energy_kj_unit")
    private String energyKjUnit;

    @Column(name = "fat_100G")
    private float fat100G;

    @Column(name = "fat_unit")
    private String fatUnit;

    @Column(name = "saturated_fat_100G")
    private float saturatedFat100G;

    @Column(name = "saturated_fat_unit")
    private String saturatedFatUnit;

    @Column(name = "carbohydrates_100G")
    private float carbohydrates100G;

    @Column(name = "carbohydrates_unit")
    private String carbohydratesUnit;

    @Column(name = "sugars_100G")
    private float sugars100G;

    @Column(name = "sugars_unit")
    private String sugarsUnit;

    @Column(name = "fiber_100G")
    private float fiber100G;

    @Column(name = "fiber_unit")
    private String fiberUnit;

    @Column(name = "proteins_100G")
    private float proteins100G;

    @Column(name = "proteins_unit")
    private String proteinsUnit;

    @Column(name = "salt_100G")
    private float salt100G;

    @Column(name = "salt_unit")
    private String saltUnit;

    @Column(name = "calcium_100G")
    private float calcium100G;

    @Column(name = "calcium_unit")
    private String calciumUnit;

    @Column(name = "iron_100G")
    private float iron100G;

    @Column(name = "iron_unit")
    private String ironUnit;

    @Column(name = "vitamin_a_100G")
    private float vitaminA100G;

    @Column(name = "vitamin_a_unit")
    private String vitaminAUnit;

    @Column(name = "vitamin_c_100G")
    private float vitaminC100G;

    @Column(name = "vitamin_c_unit")
    private String vitaminCUnit;

    @Column(name = "vitamin_d_100G")
    private float vitaminD100G;

    @Column(name = "vitamin_d_unit")
    private String vitaminDUnit;

    @OneToOne(mappedBy = "nutriment")
    @JsonIgnore
    private Produit produit;
}
