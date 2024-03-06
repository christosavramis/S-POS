package net.christosav.mpos.views.catalog.categories;

import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.confirmdialog.ConfirmDialog;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.shared.Registration;
import com.vaadin.flow.theme.lumo.LumoUtility;
import net.christosav.mpos.data.Category;

public class CategoryForm extends VerticalLayout {
    private final Binder<Category> binder = new Binder<>(Category.class);
    private final TextField name = new TextField("name");
    {
        name.setWidth("100%");
        binder.bind(name, "name");
    }

    private final Checkbox checkbox = new Checkbox("active");
    {
        checkbox.setWidth("100%");
        binder.bind(checkbox, "active");
    }


    private final Button cancelButton = new Button("cancel", event -> {
        if (binder.hasChanges()) {
            createConfirmationDialog(cancelEvent -> {}, saveEvent -> clear());
        } else {
            clear();
        }
    });
    {
        cancelButton.setWidth("min-content");
    }

    private final Button saveButton = new Button("save");
    {
        saveButton.setWidth("min-content");
        saveButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        saveButton.addClickListener(event -> {
            fireEvent(new CrudEvent.FormSave(this, binder.getBean()));
            clear();
        });
    }

    private final Button deleteButton = new Button("delete");
    {
        deleteButton.setWidth("min-content");
        deleteButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        deleteButton.addClickListener(event -> {
            if (binder.hasChanges()) {
                createConfirmationDialog(cancelEvent -> {}, saveEvent -> {
                    fireEvent(new CrudEvent.FormDelete(this, binder.getBean()));
                    clear();
                });
            }
        });
    }
    public void clear() {
        this.setVisible(false);
        binder.setBean(new Category());
    }


    public CategoryForm() {
        setVisible(false);
        setWidth("inherit");
        add(name, checkbox);

        HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.setWidthFull();
        buttonLayout.addClassName(LumoUtility.Gap.LARGE);
        buttonLayout.addClassName(LumoUtility.Padding.SMALL);
        buttonLayout.setWidth("100%");
        buttonLayout.setHeight("min-content");
        buttonLayout.setAlignItems(Alignment.CENTER);
        buttonLayout.setJustifyContentMode(JustifyContentMode.CENTER);
        buttonLayout.getStyle().setMarginTop("auto");



        buttonLayout.add(cancelButton, saveButton, deleteButton);


        add(buttonLayout);


        getEventBus().addListener(CrudEvent.FormSave.class, event -> System.out.println("Save event fired"));
    }

    public void newObject() {
        setObject(new Category());
    }

    public void setObject(Category category) {
        this.setVisible(true);
        binder.setBean(category);
    }

    private void createConfirmationDialog(ComponentEventListener<ConfirmDialog.CancelEvent> cancelListener,
                                          ComponentEventListener<ConfirmDialog.ConfirmEvent> saveListener) {
        ConfirmDialog dialog = new ConfirmDialog();
        dialog.setHeader("Unsaved changes");
        dialog.setText("There are unsaved changes. Do you want to cancel or save them?");

        dialog.setCancelable(true);
        dialog.addCancelListener(cancelListener);

        dialog.setConfirmText("Save");
        dialog.addConfirmListener(saveListener);

        Button button = new Button("Open confirm dialog");
        button.addClickListener(event -> dialog.open());

        dialog.open();
    }


    @Override
    public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType, ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }

}
