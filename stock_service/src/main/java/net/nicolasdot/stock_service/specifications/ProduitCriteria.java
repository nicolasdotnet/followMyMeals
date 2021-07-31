package net.nicolasdot.stock_service.specifications;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author nicolasdotnet
 */
@NoArgsConstructor
@Data
public class ProduitCriteria {

    private String code;

    private String produitName;
}
