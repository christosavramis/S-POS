package net.christosav.mpos.converters;

import java.text.NumberFormat;

public class PriceFormatter {
    private static final float priceDenominator = 1000.0f;
    private static final NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance();

    public static String format(int price) {
        return currencyFormatter.format(price / priceDenominator);
    }
}
