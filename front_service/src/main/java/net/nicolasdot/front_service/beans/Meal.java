package net.nicolasdot.front_service.beans;

import java.time.LocalDate;
import java.util.Date;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author nicolasdotnet
 * 
 * Meal is the bean of a Meal.
 */
@NoArgsConstructor
@Data
public class Meal {
    
    private Long id;

    private String userId;

    private Date date;

    private Boolean validate;
    
    private LocalDate validateDate;

    private MealType mealType;

    private Composition composition;

    @Override
    public String toString() {
        return "Meal{"
                + "id=" + id
                + "userId=" + userId
                + '}';
    }
    
}
