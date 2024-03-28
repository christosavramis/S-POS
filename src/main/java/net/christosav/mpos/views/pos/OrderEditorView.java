package net.christosav.mpos.views.pos;

import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.*;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import net.christosav.mpos.data.Order;
import net.christosav.mpos.services.OrderService;
import net.christosav.mpos.views.MainLayout;

@PageTitle("Ordering")
@RouteAlias(value = "pos/order/editor", layout = MainLayout.class)
@Route(value = "pos/order/editor/:orderId?", layout = MainLayout.class)
@AnonymousAllowed
@Uses(Icon.class)
public class OrderEditorView extends Composite<VerticalLayout> implements BeforeEnterObserver, AfterNavigationObserver {
    private final OrderService orderService;
    private final OrderedItemPanelLayout orderedItemPanelLayout;
    private final OrderableItemPanelLayout orderableItemPanelLayout;

    public OrderEditorView(OrderService orderService) {
        this.orderService = orderService;
        getContent().setSizeFull();

        HorizontalLayout mainLayout = new HorizontalLayout();
        mainLayout.setSizeFull();

        orderedItemPanelLayout = new OrderedItemPanelLayout(orderService);

        orderableItemPanelLayout = new OrderableItemPanelLayout(orderService, orderedItemPanelLayout::orderItem);

        mainLayout.add(orderableItemPanelLayout, orderedItemPanelLayout);
        getContent().add(mainLayout);
    }

    private void setOrderWithMode(Order order) {
        orderedItemPanelLayout.setOrder(order);
        orderableItemPanelLayout.setOrder(order);
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        setOrderWithMode(event.getRouteParameters().get("orderId")
                .flatMap(orderId -> orderService.getOrderById(Long.parseLong(orderId)))
                .orElse(new Order()));
    }

    @Override
    public void afterNavigation(AfterNavigationEvent event) {

    }
}
