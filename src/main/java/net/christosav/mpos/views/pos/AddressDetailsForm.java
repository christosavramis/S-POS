package net.christosav.mpos.views.pos;

import com.vaadin.flow.component.customfield.CustomField;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import net.christosav.mpos.data.AddressDetails;

public class AddressDetailsForm extends CustomField<AddressDetails> {
    private final Binder<AddressDetails> binder = new Binder<>(AddressDetails.class);

    public AddressDetailsForm() {
        TextField road = new TextField("Road");
        binder.bind(road, AddressDetails::getRoad, AddressDetails::setRoad);
        TextField number = new TextField("Number");
        binder.bind(number, AddressDetails::getNumber, AddressDetails::setNumber);
        TextField city = new TextField("City");
        binder.bind(city, AddressDetails::getCity, AddressDetails::setCity);
        add(new HorizontalLayout(road, number, city));
    }

    @Override
    protected AddressDetails generateModelValue() {
        return binder.getBean();
    }

    @Override
    protected void setPresentationValue(AddressDetails addressDetails) {
        binder.setBean(addressDetails);
    }
}