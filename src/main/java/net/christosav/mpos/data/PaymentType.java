package net.christosav.mpos.data;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum PaymentType {
    CASH("When paying with cash");

    private final String description;

    public static PaymentType getDefault() {
        return CASH;
    }
}
