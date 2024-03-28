package net.christosav.mpos.views.pos;

import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.confirmdialog.ConfirmDialog;
import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H6;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteParameters;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import net.christosav.mpos.converters.DateFormatter;
import net.christosav.mpos.converters.PriceFormatter;
import net.christosav.mpos.data.Order;
import net.christosav.mpos.services.OrderCrudService;
import net.christosav.mpos.views.MainLayout;


@PageTitle("Order History")
@Route(value = "pos/order/history", layout = MainLayout.class)
@AnonymousAllowed
@Uses(Icon.class)
public class OrderHistoryView extends Composite<VerticalLayout> {
    private Order selectedOrder;
    public OrderHistoryView(OrderCrudService orderService) {
        Grid<Order> ordersGrid = new Grid<>(Order.class, false);
        ordersGrid.addColumn(Order::getId).setHeader("Id");
        ordersGrid.addColumn(order -> DateFormatter.format(order.getTimePlaced())).setHeader("Order Date");
        ordersGrid.addColumn(order -> PriceFormatter.format(order.getTotalPrice())).setHeader("Total");
        ordersGrid.setItems(orderService.list());

        ordersGrid.getSelectionModel().addSelectionListener(e -> selectedOrder = e.getFirstSelectedItem().orElse(null));


        MenuBar menuBar = new MenuBar();
        menuBar.addItem("New", e -> getUI().ifPresent(ui -> ui.navigate(OrderEditorView.class)));
        menuBar.addItem("Edit", e -> {
            if (selectedOrder == null) {
                Notification notification = new Notification();
                notification.addThemeVariants(NotificationVariant.LUMO_WARNING);
                notification.add(new Icon("lumo", "error"));
                notification.add(new H6("Please select an order to edit"));
                notification.setDuration(3000);
                notification.open();
                return;
            }

            ConfirmDialog dialog = new ConfirmDialog();
            dialog.setHeader("Edit order");
            dialog.setText("Are you sure you want to edit the order ?");

            dialog.setCancelable(true);
            dialog.addCancelListener(cancelEvent -> {});

            dialog.setConfirmText("Edit");
            dialog.addConfirmListener(e1 -> getUI().flatMap(ui -> ui.navigate(OrderEditorView.class, new RouteParameters("orderId", selectedOrder.getId().toString()))));
            dialog.open();

        });

        menuBar.addItem("Void", e -> {
            if (selectedOrder == null) {
                Notification notification = new Notification();
                notification.addThemeVariants(NotificationVariant.LUMO_WARNING);
                notification.add(new Icon("lumo", "error"));
                notification.add(new H6("Please select an order to void"));
                notification.setDuration(3000);
                notification.open();
                return;
            }

            //Open the order void dialog
        });

        getContent().add(menuBar);


        getContent().add(ordersGrid);
    }


}
