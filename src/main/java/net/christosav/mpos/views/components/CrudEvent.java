package net.christosav.mpos.views.components;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;
import lombok.Getter;

@Getter
public abstract class CrudEvent<T extends Component> extends ComponentEvent<T> {
    private final Object payload;
    private CrudEvent(T source, Object payload) {
        super(source, false);
        this.payload = payload;
    }

    public static class GridRowSelected extends CrudEvent<AbstractGrid> {
        GridRowSelected(AbstractGrid source, Object payload) {
            super(source, payload);
        }
    }

    public static class FormSave extends CrudEvent<AbstractForm> {
        FormSave(AbstractForm source, Object payload) {
            super(source, payload);
        }
    }

    public static class FormDelete extends CrudEvent<AbstractForm> {
        FormDelete(AbstractForm source, Object payload) {
            super(source, payload);
        }
    }

    public static class FormCancel extends CrudEvent<AbstractForm> {
        public FormCancel(AbstractForm source, Object payload) {
            super(source, payload);
        }
    }

}

