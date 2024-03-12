package net.christosav.mpos.views.catalog.orderableitems;

import com.vaadin.flow.component.grid.Grid;
import net.christosav.mpos.data.OrderableItem;
import net.christosav.mpos.views.components.AbstractGrid;

public class OrderableItemGrid extends AbstractGrid<OrderableItem> {
    public OrderableItemGrid() {
        super(OrderableItem.class);
    }

    @Override
    public void setupGrid(Grid<OrderableItem> grid) {
        grid.addColumn(OrderableItem::getId).setHeader("Id");
        grid.addColumn(OrderableItem::getName).setHeader("Name");
        grid.addComponentColumn(orderableItem -> createPermissionIcon(orderableItem.isActive())).setHeader("Active");
    }
}
