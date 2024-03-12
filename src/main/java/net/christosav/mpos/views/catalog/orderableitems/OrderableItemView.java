package net.christosav.mpos.views.catalog.orderableitems;

import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.RolesAllowed;
import lombok.extern.slf4j.Slf4j;
import net.christosav.mpos.data.OrderableItem;
import net.christosav.mpos.services.OrderableItemCrudService;
import net.christosav.mpos.views.MainLayout;
import net.christosav.mpos.views.components.AbstractCrudEditor;

@PageTitle("Products")
@Route(value = "catalog/orderableitems", layout = MainLayout.class)
@RolesAllowed("USER")
@Uses(Icon.class)
@Slf4j
public class OrderableItemView extends AbstractCrudEditor<OrderableItem> {
    public OrderableItemView(OrderableItemCrudService entityService) {
        super(new OrderableItemGrid(), new OrderableItemForm(), entityService);
    }
}
