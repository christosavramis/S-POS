package net.christosav.mpos.views.catalog.categories;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import net.christosav.mpos.data.Category;
import net.christosav.mpos.views.components.AbstractForm;

public class CategoryForm extends AbstractForm<Category> {

    public CategoryForm() {
        super(Category.class);
    }

    @Override
    public Category construct() {
        return new Category();
    }

    @Override
    public Component[] getFields(Binder<Category> binder) {
        TextField name = new TextField("name");
        name.setWidth("100%");
        binder.bind(name, "name");

        Checkbox checkbox = new Checkbox("active");
        checkbox.setWidth("100%");
        binder.bind(checkbox, "active");

        return new Component[]{name, checkbox};
    }
}
