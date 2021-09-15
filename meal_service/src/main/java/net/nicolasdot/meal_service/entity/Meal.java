package net.nicolasdot.meal_service.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Date;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author nicolasdotnet
 *
 * Meal is the entity of a Meal.
 */
@Entity
@NoArgsConstructor
@Data
public class Meal implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private String userId;

    @Column()
    private Date date;

    @Column()
    private Boolean validate;

    @Column()
    private LocalDate validateDate;

    @ManyToOne
    @JoinColumn(name = "meal_type_id", nullable = false)
    private MealType mealType;

    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "meal", fetch = FetchType.LAZY)
    @JsonIgnore
    private Collection<Consumable> consumables;

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    @JoinColumn(name = "composition_id", referencedColumnName = "id")
    private Composition composition;

    @Override
    public String toString() {
        return "Meal{"
                + "id=" + id
                + "userId=" + userId
                + '}';
    }

}
