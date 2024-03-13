package net.christosav.mpos.views.pos;

import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.button.Button;

import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H5;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.TabSheet;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.dom.Style;
import com.vaadin.flow.shared.Registration;
import net.christosav.mpos.data.Order;
import net.christosav.mpos.data.OrderableItem;
import net.christosav.mpos.data.OrderedItem;
import net.christosav.mpos.views.util.DialogUtil;
import org.springframework.format.number.CurrencyStyleFormatter;

import java.text.NumberFormat;
import java.util.Locale;

public class OrderedItemPanelLayout extends VerticalLayout {
        private Order order;
        private final Grid<OrderedItem> grid;

        private NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance();

        private H5 totalLabel = new H5("Total: ");

        public OrderedItemPanelLayout() {

            MenuBar menuBar = new MenuBar();
            menuBar.addItem("Clear", event -> DialogUtil.createConfirmationDialog(
                    "Clear order",
                    "Are you sure you want to clear the order?",
                    "Clear",
                    cancelEvent -> {},
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
            grid.addColumn(orderedItem -> currencyFormatter.format(orderedItem.getTotal() / 1000f)).setHeader("Total");

            grid.getStyle().setMaxHeight("500px");
            add(grid);

            setOrder(new Order());

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
            tabSheet.setWidthFull();
            tabSheet.add("Details", new Div() {
                { //temporarily add some fields
                    add(new VerticalLayout() {
                        {
                            add(new TextField("Name"));
                            add(new TextField("Address"));
                            add(new TextField("Phone"));
                        }
                    });
                }
            });
            tabSheet.add("Payment", new Div("Payment section"));
            tabSheet.getStyle().set("flex-grow", "1");
            add(tabSheet);


            Button placeOrderButton = new Button("Place order", event -> {
                DialogUtil.createConfirmationDialog(
                        "Place order",
                        "Are you sure you want to place the order?",
                        "Place order",
                        cancelEvent -> {},
                        saveEvent -> {
                            System.out.println("Order placed");
                            //save the order and get its id
                            //then clear the page
                            //then proceed to the order details page with the id
                            setOrder(new Order());
                        }
                );
            });
            add(placeOrderButton);
        }

        @Override
        public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType, ComponentEventListener<T> listener) {
            return getEventBus().addListener(eventType, listener);
        }

        public void setOrder(Order order) {
            this.order = order;
            grid.setDataProvider(new ListDataProvider<>(order.getOrderedItems()));
            refreshTotal();
        }

        public void addOrderedItem(OrderableItem orderableItem) {
            OrderedItem orderedItem = new OrderedItem();
            orderedItem.setOrderableItem(orderableItem);
            orderedItem.setQuantity(1);

            order.getOrderedItems().add(orderedItem);
            refreshGrid();
            refreshTotal();
        }

        public void changeQuantity(OrderedItem orderedItem, int quantity) {
            if (quantity <= 0) {
                order.getOrderedItems().remove(orderedItem);
            } else {
                orderedItem.setQuantity(quantity);
            }
            refreshGrid();
            refreshTotal();
        }

        private void refreshGrid() {
            grid.getDataProvider().refreshAll();
        }

        private void refreshTotal() {
            totalLabel.setText("Total: " + currencyFormatter.format(order.getTotal() / 1000f));
        }
    }