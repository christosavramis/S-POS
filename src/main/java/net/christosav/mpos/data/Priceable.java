package net.christosav.mpos.data;

import java.util.Collections;
import java.util.List;

public interface Priceable {
    default Priceable getParent() {
        return null;
    }

    default List<Priceable> getPriceableChildren() {
        return Collections.emptyList();
    }

    int getPrice();

    void setPrice(int price);

    boolean isPriceValid();

    void setPriceValid(boolean priceValid);

    default void modified() {
        setPriceValid(false);
        if (getParent() != null) {
            getParent().modified();
        }
    }

    default void calcPrice() {
        setPrice(calcPriceTarget());
        setPriceValid(true);
    }

    default int calcPriceTarget() {
        return calcPriceChildren();
    }

    default int calcPriceChildren() {
        return getPriceableChildren().stream()
                .mapToInt(Priceable::getPrice)
                .sum();
    }


}
