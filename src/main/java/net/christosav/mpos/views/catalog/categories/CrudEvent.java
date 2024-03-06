package net.christosav.mpos.views.catalog.categories;

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

    public static class GridRowSelected extends CrudEvent<CategoryGrid> {
        GridRowSelected(CategoryGrid source, Object payload) {
            super(source, payload);
        }
    }

    public static class FormSave extends CrudEvent<CategoryForm> {
        FormSave(CategoryForm source, Object payload) {
            super(source, payload);
        }
    }

    public static class FormDelete extends CrudEvent<CategoryForm> {
        FormDelete(CategoryForm source, Object payload) {
            super(source, payload);
        }
    }

    public static class FormCancel extends CrudEvent<CategoryForm> {
        public FormCancel(CategoryForm source, Object payload) {
            super(source, payload);
        }
    }

}

