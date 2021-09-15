package net.nicolasdot.stock_service.entity;

/**
 *
 * @author nicolasdotnet
 *
 * ReservationStatus is the enum class to which produit can belong.
 */
public enum StockStatus {

    TRUE("TRUE"), FALSE("FALSE");

    private String value;

    private StockStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}
