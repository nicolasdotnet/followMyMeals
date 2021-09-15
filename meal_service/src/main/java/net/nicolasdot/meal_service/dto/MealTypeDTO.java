package net.nicolasdot.meal_service.dto;

import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author nicolasdotnet
 */
@NoArgsConstructor
@Data
public class MealTypeDTO {

    @NotEmpty(message = "le nom du type de repas n'est pas renseigné")
    @Size(min = 3, max = 10, message = "du type de repas est trop long ou trop court")
    @ApiModelProperty(notes = "label for a meal type")
    private String label;

    @Override
    public String toString() {
        return "MealTypeDTO{" + "label=" + label + '}';
    }

}
