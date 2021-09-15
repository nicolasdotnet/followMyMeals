package net.nicolasdot.meal_service.bean;

import java.time.LocalDate;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

/**
 *
 * @author nicolasdotnet
 *
 * ProduitDetails is the bean entity of a Produit.
 */
@Component
@NoArgsConstructor
@Data
public class ProduitDetails {

    private Long id;

    private LocalDate date;

    private int quantity;

    private Boolean inStock;

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
