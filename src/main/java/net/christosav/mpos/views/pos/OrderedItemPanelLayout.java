package net.christosav.mpos.views.pos;

import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.button.Button;

import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.confirmdialog.ConfirmDialog;
import com.vaadin.flow.component.contextmenu.MenuItem;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.NativeLabel;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.tabs.TabSheet;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ReadOnlyHasValue;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.shared.Registration;
import net.christosav.mpos.converters.PriceFormatter;
import net.christosav.mpos.data.*;
import net.christosav.mpos.services.OrderService;
import net.christosav.mpos.views.util.DialogUtil;

import java.util.List;
import java.util.Optional;


public class OrderedItemPanelLayout extends VerticalLayout {
    private final OrderService orderService;
    private Order order;
    private final Binder<Order> orderBinder = new Binder<>(Order.class);

    private final MenuItem clearButton;

    public OrderedItemPanelLayout(OrderService orderService) {
        this.orderService = orderService;


        MenuBar menuBar = new MenuBar();
        clearButton = menuBar.addItem("Clear", event -> DialogUtil.createConfirmationDialog(
                "Clear order",
                "Are you sure you want to clear the order?",
                "Clear",
                cancelEvent -> {},
                saveEvent -> {
                    if (OrderStatus.UNDEFINED.equals(order.getStatus())) {
                        setOrder(new Order());
                    } else {
                        setOrder(orderService.getOrderById(order.getId()).orElse(new Order()));
                    }
                }
        ));
        add(menuBar);

        Grid<OrderedItem> grid = new Grid<>(OrderedItem.class, false);
        grid.addComponentColumn(orderedItem -> {
            Image image = new Image();
            image.setSrc(Optional.ofNullable(orderedItem.getOrderableItem().getImage()).map(ImageEntity::getUrl).orElse("images/icon/NoImage.png"));
            image.setWidth("50px");
            image.setHeight("50px");
            return image;
        }).setHeader("Image");
        grid.addColumn(orderedItem -> orderedItem.getOrderableItem().getName()).setHeader("Name");
        grid.addComponentColumn(orderedItem -> {
            HorizontalLayout div = new HorizontalLayout();
            div.getStyle().set("align-items", "center");

            Button decreaseButton = new Button("-", event -> changeQuantity(orderedItem, orderedItem.getQuantity() - 1));
            decreaseButton.getStyle().set("min-width", "unset");
            decreaseButton.getStyle().set("min-height", "unset");
            decreaseButton.setEnabled(!OrderStatus.PAID.equals(order.getStatus()));
            div.add(decreaseButton);

            div.add(new Div(String.valueOf(orderedItem.getQuantity())));

            Button increaseButton = new Button("+", event -> changeQuantity(orderedItem, orderedItem.getQuantity() + 1));
            increaseButton.getStyle().set("min-width", "unset");
            increaseButton.getStyle().set("min-height", "unset");
            increaseButton.setEnabled(!OrderStatus.PAID.equals(order.getStatus()));
            div.add(increaseButton);

            return div;
        }).setHeader("Quantity");
        grid.addColumn(orderedItem -> PriceFormatter.format(orderedItem.getTotalPrice())).setHeader("Total");

        grid.getStyle().setMaxHeight("500px");
        add(grid);

        orderBinder.forField(new ReadOnlyHasValue<List<OrderedItem>>(grid::setItems))
                   .bindReadOnly(Order::getOrderedItems);

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

        NativeLabel label = new NativeLabel();
        orderBinder.forField(new ReadOnlyHasValue<>(label::setText)).bindReadOnly(order2 -> PriceFormatter.format(order2.getTotalPrice()));

        totalLayout.add(new HorizontalLayout(new NativeLabel("Total: "), label));

        add(totalLayout);

        TabSheet tabSheet = new TabSheet();
        tabSheet.getStyle().set("height", "400px");
        tabSheet.getStyle().set("width", "100%");

        CustomerDetailsForm customerDetailsForm = new CustomerDetailsForm();
        orderBinder.forField(customerDetailsForm).bind(Order::getCustomer, Order::setCustomer);

        tabSheet.add("Details", customerDetailsForm);
        add(tabSheet);


        Select<PaymentType> paymentTypeSelect = new Select<>();
        paymentTypeSelect.setRenderer(new ComponentRenderer<>(paymentType -> {
            FlexLayout wrapper = new FlexLayout();
            wrapper.setAlignItems(Alignment.CENTER);

            Image image = new Image();
            image.setSrc("images/icon/PaymentType_" + paymentType.name() + ".png");
            image.setAlt("Payment type ");
            image.setWidth("var(--lumo-size-m)");
            image.getStyle().set("margin-right", "var(--lumo-space-s)");

            Div info = new Div();
            info.setText(paymentType.name());
            wrapper.add(image, info);
            return wrapper;
        }));
        paymentTypeSelect.setItems(PaymentType.values());
        orderBinder.forField(paymentTypeSelect).bind(order1 -> order1.getPaymentInfo().getPaymentType(), (order1, paymentType) -> order1.getPaymentInfo().setPaymentType(paymentType));

        Button placeOrderButton = new Button("Place order", event -> placeOrderStepConfirm());
        placeOrderButton.setHeightFull();

        HorizontalLayout orderFinalizationLayout = new HorizontalLayout(paymentTypeSelect, placeOrderButton);
        orderFinalizationLayout.setJustifyContentMode(JustifyContentMode.CENTER);
        orderFinalizationLayout.setAlignItems(Alignment.CENTER);

        add(orderFinalizationLayout);
    }

    private void afterOrderActionStep() {
        setOrder(new Order());
    }

    private void placeOrderStepFinal() {
        orderService.placeOrder(order);
        Notification.show("Order placed successfully").addThemeVariants(NotificationVariant.LUMO_SUCCESS);
        afterOrderActionStep();
    }

    private void placeOrderAndPayStepFinal() {
        orderService.placeAndPayOrder(order);
        Notification.show("Order placed successfully").addThemeVariants(NotificationVariant.LUMO_SUCCESS);
        afterOrderActionStep();
    }

    private void placeOrderStepConfirm() {
        ConfirmDialog dialog = new ConfirmDialog();
        dialog.setHeader("Placing order");
        dialog.setText("will this order be paid now" + order.getPaymentInfo().getPaymentType() + " ?");

        dialog.setCancelable(true);
        dialog.setCancelText("no");
        dialog.addCancelListener(cancelEvent -> this.placeOrderStepFinal());

        dialog.setConfirmText("yes");
        dialog.addConfirmListener(e -> this.placeOrderAndPayStepFinal());
        dialog.open();
    }


    public void setOrder(Order order) {
        this.order = order;
        orderBinder.setBean(order);

        if (OrderStatus.UNDEFINED.equals(order.getStatus())) {
            clearButton.setEnabled(false);
        }

    }

    public void orderItem(OrderableItem orderableItem) {
        order.orderItem(orderableItem);
        orderBinder.readBean(order);
    }

    public void changeQuantity(OrderedItem orderedItem, int quantity) {
        orderedItem.setQuantity(quantity);
        orderBinder.readBean(order);
    }

    @Override
    public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType, ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }


}