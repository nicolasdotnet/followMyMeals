package net.nicolasdot.meal_service.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author nicolasdotnet
 
 MealType is the class to which meal can belong.
 * 
 */
@Entity
@NoArgsConstructor
@Data
public class MealType implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100, nullable = false)
    private String label;

    @OneToMany(mappedBy = "mealType", fetch = FetchType.LAZY)
    @JsonIgnore
    private Collection<Meal> meals;

    @Override
    public String toString() {
        return "MealType{" + "Id=" + id + ", label=" + label + '}';
    }

}
