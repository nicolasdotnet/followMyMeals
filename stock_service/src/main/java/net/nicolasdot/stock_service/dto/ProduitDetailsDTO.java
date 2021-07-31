package net.nicolasdot.stock_service.dto;

import javax.validation.constraints.NotNull;
import lombok.Data;

/**
 *
 * @author nicolasdotnet
 */
@Data
public class ProduitDetailsDTO {

    @NotNull(message = "la quantité de produit n'est pas renseigné")
    private int quantity;

    @NotNull(message = "id du produit n'est pas renseigné")
    private int id;

}
