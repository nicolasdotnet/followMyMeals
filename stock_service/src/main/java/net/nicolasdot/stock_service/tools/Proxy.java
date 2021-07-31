package net.nicolasdot.stock_service.tools;

import java.util.Optional;
import net.nicolasdot.stock_service.exceptions.EntityNotFoundException;
import pl.coderion.model.Product;
import pl.coderion.model.ProductResponse;
import pl.coderion.service.OpenFoodFactsWrapper;
import pl.coderion.service.impl.OpenFoodFactsWrapperImpl;

/**
 *
 * @author nicolasdotnet
 *
 * Proxy is the class for connect to OpenfoodFact Api and fetch product entity.
 */
public class Proxy {

    public static Optional<Product> getOpenfoodFact(String code) throws EntityNotFoundException {

        OpenFoodFactsWrapper wrapper = new OpenFoodFactsWrapperImpl();

        ProductResponse productResponse = wrapper.fetchProductByCode(code);

        if (!productResponse.isStatus()) {
            return Optional.empty();
        }

        return Optional.of(productResponse.getProduct());

    }

}
