package net.christosav.mpos.views.util;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;
import lombok.Getter;

@Getter
public abstract class ComponentEventWithPayload <T extends Component, P> extends ComponentEvent<T> {
    private final transient P payload;

    public ComponentEventWithPayload(T source, P payload) {
        super(source, false);
        this.payload = payload;
    }
}