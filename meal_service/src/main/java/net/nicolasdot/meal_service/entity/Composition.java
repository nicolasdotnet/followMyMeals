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

/**
 *
 * @author nicolasdotnet
 *
 * Composition is the entity of a Composition.
 */
@Entity
@Data
public class Composition implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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

    @OneToOne(mappedBy = "composition")
    @JsonIgnore
    private Meal meal;

    public Composition(){
        
        this.energy = 0;
        this.fat = 0;
        this.saturatedFat = 0;
        this.carbohydrates = 0;
        this.sugars = 0;
        this.proteins = 0;
        this.salt = 0;  
        
    }
    
    

}
