package net.nicolasdot.meal_service.entity;

/**
 *
 * @author nicolasdotnet
 *
 * MealStatus is the enum class to which meal can belong.
 */
public enum MealStatus {

    TRUE("TRUE"), FALSE("FALSE");

    private String value;

    private MealStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}
