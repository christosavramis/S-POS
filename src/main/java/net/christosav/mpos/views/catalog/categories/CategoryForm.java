package net.christosav.mpos.views.catalog.categories;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.customfield.CustomField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import net.christosav.mpos.data.Category;
import net.christosav.mpos.data.ImageEntity;
import net.christosav.mpos.views.components.AbstractForm;

class CategoryForm extends AbstractForm<Category> {

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
        binder.bind(name, Category::getName, Category::setName);

        Checkbox checkbox = new Checkbox("active");
        checkbox.setWidth("100%");
        binder.bind(checkbox, Category::isActive, Category::setActive);

        ImageField imageField = new ImageField();
        binder.bind(imageField, Category::getImage, Category::setImage);


        return new Component[]{name, checkbox, imageField};
    }

    class ImageField extends CustomField<ImageEntity> {
        Binder<ImageEntity> binder = new Binder<>(ImageEntity.class);

        public ImageField() {
            TextField name = new TextField("url");
            binder.bind(name, ImageEntity::getUrl, ImageEntity::setUrl);

            ComboBox<ImageEntity.Type> type = new ComboBox<>("type");
            type.setItems(ImageEntity.Type.values());
            binder.bind(type, ImageEntity::getType, ImageEntity::setType);
            add(name, type);
        }

        @Override
        protected ImageEntity generateModelValue() {
            return binder.getBean();
        }

        @Override
        protected void setPresentationValue(ImageEntity imageEntity) {
            binder.readBean(imageEntity);
        }
    }
}