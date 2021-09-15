package net.nicolasdot.front_service.beans;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author nicolasdotnet
 *
 * Produit is the bean of a Produit.
 */
@NoArgsConstructor
@Data
public class Produit {

    private Long id;

    private String code;

    private Long lastModifiedT;

    private String produitName;

    private String brand;

    private String ImageFrontUrl;

    private String ingredientsText;

    private String Weight;

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
