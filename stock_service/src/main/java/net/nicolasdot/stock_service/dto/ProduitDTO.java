package net.nicolasdot.stock_service.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.Data;

/**
 *
 * @author nicolasdotnet
 */
@Data
public class ProduitDTO {

    @NotNull(message = "id du produit n'est pas renseigné")
    private int produitId;
    
    @NotEmpty(message = "l'id du user n'est pas renseigné")
    private String userId;

}
