package net.nicolasdot.stock_service.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author nicolasdotnet
 *
 * ProduitDetails is the detail entity of a Produit.
 */
@Entity
@NoArgsConstructor
@Data
public class ProduitDetails implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDate date;

    @Column()
    private int quantity;

    @Column(name = "remaining_quantity")
    private float remainingQuantity;

    @Column(name = "weight")
    private float weight;
        
    @Column(name = "weight_unit")
    private String weightUnit;

    @Column(name = "in_stock", nullable = false)
    private Boolean inStock;

    @OneToOne(mappedBy = "produitDetails")
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
