package net.nicolasdot.meal_service.entity;

import java.io.Serializable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author nicolasdotnet
 *
 * Consumable is the entity of a Consumable.
 */
@Entity
@NoArgsConstructor
@Data
public class Consumable implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "consumable_name")
    private String consumableName;

    @Column()
    private float quantity;

    @Column(name = "quantity_unit")
    private String quantityUnit;

    @Column(name = "produit_id")
    private Long produitId;

    @Column()
    private Boolean favory;

    @ManyToOne
    @JoinColumn(name = "meal_id", nullable = false)
    private Meal meal;

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    @JoinColumn(name = "consumable_nutriment_id", referencedColumnName = "id")
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
