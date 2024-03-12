package net.christosav.mpos.views.catalog.orderableitems;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import net.christosav.mpos.data.OrderableItem;
import net.christosav.mpos.views.components.AbstractForm;

class OrderableItemForm extends AbstractForm<OrderableItem> {

        public OrderableItemForm() {
            super(OrderableItem.class);
        }

        @Override
        public OrderableItem construct() {
            return new OrderableItem();
        }

        @Override
        public Component[] getFields(Binder<OrderableItem> binder) {
            TextField name = new TextField("name");
            name.setWidth("100%");
            binder.bind(name, "name");

            Checkbox checkbox = new Checkbox("active");
            checkbox.setWidth("100%");
            binder.bind(checkbox, "active");

            return new Component[]{name, checkbox};
        }
    }