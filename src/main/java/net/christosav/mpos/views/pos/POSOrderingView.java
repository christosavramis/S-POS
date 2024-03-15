package net.christosav.mpos.views.pos;

import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import net.christosav.mpos.services.POSOrderingService;
import net.christosav.mpos.views.MainLayout;

@PageTitle("Ordering")
@Route(value = "pos/ordering", layout = MainLayout.class)
@AnonymousAllowed
@Uses(Icon.class)
public class POSOrderingView extends Composite<VerticalLayout> {

    public POSOrderingView(POSOrderingService posOrderingService) {

        getContent().setSizeFull();

        HorizontalLayout mainLayout = new HorizontalLayout();
        mainLayout.setSizeFull();

        OrderableItemPanelLayout orderableItemPanelLayout = new OrderableItemPanelLayout(posOrderingService);
        OrderedItemPanelLayout orderedItemPanelLayout = new OrderedItemPanelLayout(posOrderingService);

        orderableItemPanelLayout.addListener(OrderableItemPanelLayout.OrderableItemClicked.class, event -> orderedItemPanelLayout.orderItem(event.getPayload()));

        mainLayout.add(orderableItemPanelLayout, orderedItemPanelLayout);
        getContent().add(mainLayout);
    }



}
