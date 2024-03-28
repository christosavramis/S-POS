package net.christosav.mpos.data;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum OrderStatus {
    UNDEFINED("when the order status is not yet defined"),
    PLACED("when the order is placed"),
    PAID("when the order is paid"), // paid orders can not be edited
    CLOSED("when the order is closed, which means it was paid and left the store"),
    VOIDED("when the order is voided by the cashier, and does not count as a sale anymore");

    private final String description;

    public static OrderStatus getDefault() {
        return UNDEFINED;
    }
}
