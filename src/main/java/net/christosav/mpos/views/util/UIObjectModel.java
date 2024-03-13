package net.christosav.mpos.views.util;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

@Getter
@RequiredArgsConstructor
public class UIObjectModel <T> {
    private final T object;
    private final List<Modification> modifiedListeners = new ArrayList<>();

    public void addModifiedListener(Consumer<T> consumer, String property) {
        modifiedListeners.add(new Modification(consumer, property));
    }

    @Getter
    @RequiredArgsConstructor
    class Modification {
        private final Consumer<T> consumer;
        private final String property;
    }
}
