package net.nicolasdot.meal_service.bean;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

/**
 *
 * @author nicolasdotnet
 *
 * Produit is the bean of a Produit.
 */
@Component
@NoArgsConstructor
@Data
public class Produit {

    private Long id;

    private String code;

    private Long lastModifiedT;

    private String produitName;

    private String ingredientsText;

    private ProduitDetails produitDetails;

    private String nutritionGrades;

    private Nutriment nutriment;

    @Override
    public String toString() {
        return "Produit{"
                + "id=" + id
                + "code=" + code
                + ", produitName=" + produitName + '}';
    }

}
