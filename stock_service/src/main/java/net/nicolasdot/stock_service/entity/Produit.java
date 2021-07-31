package net.nicolasdot.stock_service.entity;

import java.io.Serializable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author nicolasdotnet
 *
 * Produit is the entity of a Produit.
 */
@Entity
@NoArgsConstructor
@Data
public class Produit implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column()
    private String code;

    @Column(name = "last_modified_t")
    private Long lastModifiedT;

    @Column(name = "produit_name")
    private String produitName;

    @Column(name = "brand")
    private String brand;

    @Column(name = "image_front_url")
    private String imageFrontUrl;

    @Column(name = "ingredients_text", length = 9000)
    private String ingredientsText;

    @Column(name = "weight")
    private String weight;

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    @JoinColumn(name = "produit_details_id", referencedColumnName = "id")
    private ProduitDetails produitDetails;

    @Column(name = "nutrition_grades")
    private String nutritionGrades;

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    @JoinColumn(name = "nutriment_id", referencedColumnName = "id")
    private Nutriment nutriment;

    @Override
    public String toString() {
        return "Produit{"
                + "id=" + id
                + "code=" + code
                + ", produitName=" + produitName + '}';
    }

}
