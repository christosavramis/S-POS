package net.christosav.mpos.data;

public interface PricedEntity {

    /**
     * Retrieves the total price of the order,
     * if the price is not already calculated, it will calculate it.
     * do not use getPrice() to get the total price of the order.
     */
    default int getTotalPrice() {
        if (!isPriceValid()) {
            calcPrice();
        }
        return getPrice();
    }

    boolean isPriceValid();

    void calcPrice();

    int getPrice();
}
