package net.nicolasdot.front_service.beans;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.time.LocalDate;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author nicolasdotnet
 *
 * ProduitDetails is the detail bean of a Produit.
 */
@NoArgsConstructor
@Data
public class ProduitDetails{

    private Long id;

    private LocalDate date;

    private int quantity;

    private float remainingQuantity;

    private float weight;

    private String weightUnit;

    private Boolean inStock;

    @JsonIgnore
    private Produit produit;

    @Override
    public String toString() {
        return "ProduitDetails{"
                + "id=" + id
                + ", date=" + date
                + ", quantity=" + quantity
                + ", inStock=" + inStock
                + ", product=" + produit + '}';
    }

}
