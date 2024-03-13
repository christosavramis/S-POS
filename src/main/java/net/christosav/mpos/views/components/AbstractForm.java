package net.christosav.mpos.views.components;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.shared.Registration;
import com.vaadin.flow.theme.lumo.LumoUtility;
import lombok.extern.slf4j.Slf4j;
import net.christosav.mpos.views.util.DialogUtil;

@Slf4j
public abstract class AbstractForm<T> extends VerticalLayout {
    private final Binder<T> binder;

    public abstract T construct();

    public void clear() {
        this.setVisible(false);
        binder.setBean(construct());
    }

    public abstract Component[] getFields(Binder<T> binder);

    public AbstractForm(Class<T> zclass) {
        setVisible(false);
        binder = new Binder<>(zclass);

        setWidth("inherit");
        add(getFields(binder));

        HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.setWidthFull();
        buttonLayout.addClassName(LumoUtility.Gap.LARGE);
        buttonLayout.addClassName(LumoUtility.Padding.SMALL);
        buttonLayout.setWidth("100%");
        buttonLayout.setHeight("min-content");
        buttonLayout.setAlignItems(Alignment.CENTER);
        buttonLayout.setJustifyContentMode(JustifyContentMode.CENTER);
        buttonLayout.getStyle().setMarginTop("auto");

        Button cancelButton = new Button("cancel", event -> {
            log.info("Cancel button clicked");
            if (binder.hasChanges()) {
                DialogUtil.createConfirmationDialog(cancelEvent -> {
                }, saveEvent -> clear());
            } else {
                clear();
            }
        });
        cancelButton.setWidth("min-content");

        Button saveButton = new Button("save", event -> {
            log.info("Save button clicked");
            fireEvent(new CrudEvent.FormSave(this, binder.getBean()));
            clear();
        });
        saveButton.setWidth("min-content");
        saveButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        Button deleteButton = new Button("delete", event -> {
            log.info("Delete button clicked");
            if (binder.hasChanges()) {
                DialogUtil.createConfirmationDialog(cancelEvent -> {
                }, saveEvent -> {
                    fireEvent(new CrudEvent.FormDelete(this, binder.getBean()));
                    clear();
                });
            }
        });
        deleteButton.setWidth("min-content");
        deleteButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        buttonLayout.add(cancelButton, saveButton, deleteButton);


        add(buttonLayout);


        getEventBus().addListener(CrudEvent.FormSave.class, event -> System.out.println("Save event fired"));
    }

    public void newObject() {
        setObject(construct());
    }

    public void setObject(T category) {
        this.setVisible(true);
        binder.setBean(category);
    }

    @Override
    public <L extends ComponentEvent<?>> Registration addListener(Class<L> eventType, ComponentEventListener<L> listener) {
        return getEventBus().addListener(eventType, listener);
    }

}
