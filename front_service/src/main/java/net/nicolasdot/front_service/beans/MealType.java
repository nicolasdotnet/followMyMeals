package net.nicolasdot.front_service.beans;

import java.util.Collection;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author nicolasdotnet
 * MealType is the bean to which meal can belong.
 * 
 */
@NoArgsConstructor
@Data
public class MealType{

    private Long id;

    private String label;

    private Collection<Meal> meals;

    @Override
    public String toString() {
        return "MealType{" + "Id=" + id + ", label=" + label + '}';
    }

}
