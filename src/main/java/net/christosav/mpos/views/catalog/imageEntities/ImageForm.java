package net.christosav.mpos.views.catalog.imageEntities;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.customfield.CustomField;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import net.christosav.mpos.data.Category;
import net.christosav.mpos.data.ImageEntity;
import net.christosav.mpos.views.components.AbstractForm;

class ImageForm extends AbstractForm<ImageEntity> {

    public ImageForm() {
        super(ImageEntity.class);
    }

    @Override
    public ImageEntity construct() {
        return new ImageEntity();
    }

    @Override
    public Component[] getFields(Binder<ImageEntity> binder) {
        TextField name = new TextField("name");
        name.setWidth("100%");
        binder.bind(name, ImageEntity::getName, ImageEntity::setName);

        ComboBox<ImageEntity.Type> type = new ComboBox<>("type");
        type.setItems(ImageEntity.Type.values());
        binder.bind(type, ImageEntity::getType, ImageEntity::setType);

        TextField url = new TextField("url");
        url.setWidth("100%");
        binder.bind(url, ImageEntity::getUrl, ImageEntity::setUrl);

        Image image = new Image(); //later change this to a custom field
        image.setMaxHeight("100px");
        image.setMaxWidth("100px");
        binder.addStatusChangeListener(e -> {
            if (e.getBinder().getBean() != null) {
                image.setSrc(((ImageEntity)e.getBinder().getBean()).getUrl());
            } else {
                image.setSrc("");
            }
        });

        return new Component[]{name, new HorizontalLayout(url, type), image};
    }

}