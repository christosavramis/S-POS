package net.christosav.mpos.views.pos;

import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;

import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.customfield.CustomField;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H5;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.TabSheet;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.shared.Registration;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.christosav.mpos.data.*;
import net.christosav.mpos.services.POSOrderingService;
import net.christosav.mpos.views.util.DialogUtil;

import java.text.NumberFormat;
import java.time.LocalDate;

public class OrderedItemPanelLayout extends VerticalLayout {
    private final POSOrderingService posOrderingService;
    private Order order;
    private final Grid<OrderedItem> grid;

    private NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance();

    private H5 totalLabel = new H5("Total: ");


    private final CustomerDetailsFormCF customerDetailsForm = new CustomerDetailsFormCF();

    public OrderedItemPanelLayout(POSOrderingService posOrderingService) {
        this.posOrderingService = posOrderingService;

        MenuBar menuBar = new MenuBar();
        menuBar.addItem("Clear", event -> DialogUtil.createConfirmationDialog(
                "Clear order",
                "Are you sure you want to clear the order?",
                "Clear",
                cancelEvent -> {
                },
                saveEvent -> setOrder(new Order())
        ));

        add(menuBar);

        grid = new Grid<>(OrderedItem.class, false);
        grid.addColumn(orderedItem -> orderedItem.getOrderableItem().getName()).setHeader("Name");
        grid.addComponentColumn(orderedItem -> {
            HorizontalLayout div = new HorizontalLayout();
            div.getStyle().set("align-items", "center");

            Button decreaseButton = new Button("-", event -> changeQuantity(orderedItem, orderedItem.getQuantity() - 1));
            decreaseButton.getStyle().set("min-width", "unset");
            decreaseButton.getStyle().set("min-height", "unset");
            div.add(decreaseButton);

            div.add(new Div(String.valueOf(orderedItem.getQuantity())));

            Button increaseButton = new Button("+", event -> changeQuantity(orderedItem, orderedItem.getQuantity() + 1));
            increaseButton.getStyle().set("min-width", "unset");
            increaseButton.getStyle().set("min-height", "unset");
            div.add(increaseButton);

            return div;
        }).setHeader("Quantity");
        grid.addColumn(orderedItem -> currencyFormatter.format(orderedItem.getPrice() / 1000f)).setHeader("Total");

        grid.getStyle().setMaxHeight("500px");
        add(grid);


        // add a payment section
        // just with cash for now

        //add an details section
        // there you could add address, phone number, etc
        // or table number, etc but not implemented yet just address and name
        // we will assume its delivery for now.

        //Implement the sections with a tabbed layout
        VerticalLayout totalLayout = new VerticalLayout();
        totalLayout.setWidthFull();
        totalLayout.getStyle().set("align-items", "flex-end");
        totalLayout.getStyle().set("justify-content", "flex-end");
        totalLayout.add(totalLabel);

        add(totalLayout);

        TabSheet tabSheet = new TabSheet();
        tabSheet.getStyle().set("height", "400px");
        tabSheet.getStyle().set("width", "100%");
        tabSheet.add("Details", new VerticalLayout(customerDetailsForm, new Button("test", event -> System.out.println(customerDetailsForm.getValue()))));
        tabSheet.add("Payment", new Div("Payment section"));
        add(tabSheet);


        Button placeOrderButton = new Button("Place order", event ->
                DialogUtil.createConfirmationDialog(
                "Place order",
                "Are you sure you want to place the order?",
                "Place order",
                cancelEvent -> {
                },
                saveEvent -> placeOrder()
        ));
        add(placeOrderButton);


        setOrder(new Order());
    }

    private void placeOrder() {
        System.out.println("Placing order...");
        order.setCustomer(customerDetailsForm.getValue());
        long orderId = posOrderingService.placeOrder(order).getId();
        System.out.println("Order placed with id: " + orderId + order);

        setOrder(new Order());
    }


    private void setOrder(Order order) {
        this.order = order;
        customerDetailsForm.setValue(order.getCustomer());
        grid.setDataProvider(new ListDataProvider<>(order.getOrderedItems()));
        refreshTotal();
    }

    public void orderItem(OrderableItem orderableItem) {
        order.orderItem(orderableItem);
        refreshGrid();
        refreshTotal();
    }

    public void changeQuantity(OrderedItem orderedItem, int quantity) {
        orderedItem.setQuantity(quantity);
        refreshGrid();
        refreshTotal();
    }

    private void refreshGrid() {
        grid.getDataProvider().refreshAll();
    }

    private void refreshTotal() {
        totalLabel.setText("Total: " + currencyFormatter.format(order.getPrice() / 1000f));
    }
    @Override
    public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType, ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }


    class CustomerDetailsFormCF extends CustomField<Customer> {
        private final Binder<Customer> binder = new Binder<>(Customer.class);
        public CustomerDetailsFormCF() {
            TextField number = new TextField("Number");
            number.setWidth("5em");

            TextField name = new TextField("Name");
            binder.bind(name, Customer::getName, Customer::setName);
            TextField phone = new TextField("Phone");
            binder.bind(phone, Customer::getPhone, Customer::setPhone);

            AddressDetailsField addressDetailsField = new AddressDetailsField();
            binder.bind(addressDetailsField, Customer::getAddressDetails, Customer::setAddressDetails);

            binder.setBean(new Customer());
            add(name, phone);
        }

        class AddressDetailsField extends CustomField<AddressDetails>  {
            private final Binder<AddressDetails> binder = new Binder<>(AddressDetails.class);

            public AddressDetailsField() {
                TextField road = new TextField("Road");
                binder.bind(road, AddressDetails::getRoad, AddressDetails::setRoad);
                TextField number = new TextField("Number");
                binder.bind(number, AddressDetails::getNumber, AddressDetails::setNumber);
                TextField city = new TextField("City");
                binder.bind(city, AddressDetails::getCity, AddressDetails::setCity);
                binder.setBean(new AddressDetails());
                add(road, number, city);
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

        @Override
        protected Customer generateModelValue() {
            return binder.getBean();
        }

        @Override
        protected void setPresentationValue(Customer customer) {
            binder.setBean(customer);
        }
    }

    public class DateRangePicker extends CustomField<DateRangePicker.LocalDateRange> {

        private DatePicker start;
        private DatePicker end;

        public DateRangePicker(String label) {
            this();
            setLabel(label);
        }

        public DateRangePicker() {
            start = new DatePicker();
            start.setPlaceholder("Start date");
            // Sets title for screen readers
            start.getElement().executeJs(
                    "this.focusElement.setAttribute('title', 'Start date')");

            end = new DatePicker();
            end.setPlaceholder("End date");
            end.getElement().executeJs(
                    "this.focusElement.setAttribute('title', 'End date')");

            add(start, new Text(" – "), end);
        }

        @Override
        protected LocalDateRange generateModelValue() {
            return new LocalDateRange(start.getValue(), end.getValue());
        }

        @Override
        protected void setPresentationValue(LocalDateRange dateRange) {
            start.setValue(dateRange.getStartDate());
            end.setValue(dateRange.getEndDate());
        }


        @Data @AllArgsConstructor @NoArgsConstructor
        public static class LocalDateRange {

            private LocalDate startDate;
            private LocalDate endDate;
        }
    }

}