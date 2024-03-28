package net.christosav.mpos.views.pos;

import com.vaadin.flow.component.customfield.CustomField;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import net.christosav.mpos.data.Customer;

public class CustomerDetailsForm extends CustomField<Customer> {
        private final Binder<Customer> binder = new Binder<>(Customer.class);
        public CustomerDetailsForm() {
            TextField number = new TextField("Number");
            number.setWidth("5em");

            TextField name = new TextField("Name");
            binder.bind(name, Customer::getName, Customer::setName);
            TextField phone = new TextField("Phone");
            binder.bind(phone, Customer::getPhone, Customer::setPhone);

            AddressDetailsForm addressDetailsForm = new AddressDetailsForm();
            binder.bind(addressDetailsForm, Customer::getAddressDetails, Customer::setAddressDetails);

            FormLayout formLayout = new FormLayout();
            formLayout.add(name, phone, addressDetailsForm);
            add(formLayout);
        }

        @Override
        protected Customer generateModelValue() {
            return binder.getBean();
        }

        @Override
        protected void setPresentationValue(Customer customer) {
            binder.setBean(customer);
        }
    }