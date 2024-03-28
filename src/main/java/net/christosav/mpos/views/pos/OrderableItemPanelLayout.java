package net.christosav.mpos.views.pos;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.dom.Style;
import net.christosav.mpos.data.Category;
import net.christosav.mpos.data.Order;
import net.christosav.mpos.data.OrderStatus;
import net.christosav.mpos.data.OrderableItem;

import java.util.List;
import java.util.function.Consumer;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.menubar.MenuBar;
import net.christosav.mpos.services.OrderService;

public class OrderableItemPanelLayout extends VerticalLayout {
        private final Div orderableItemWrapper = new Div();
        private final OrderService orderService;
        private final Consumer<OrderableItem> orderableItemConsumer;

        public OrderableItemPanelLayout(OrderService orderService, Consumer<OrderableItem> orderableItemConsumer) {
            this.orderService = orderService;
            this.orderableItemConsumer = orderableItemConsumer;

            List<Category> categories = orderService.getCategories();
            MenuBar menuBar = new MenuBar();
            categories.forEach(category -> menuBar.addItem(category.getName(), event -> setItemPanels(category)));
            add(menuBar);

            orderableItemWrapper.getStyle().setDisplay(Style.Display.FLEX);
            orderableItemWrapper.getStyle().set("flex-wrap", "wrap");
            orderableItemWrapper.getStyle().set("gap", "5px");
            orderableItemWrapper.setMaxHeight("100%");
            setItemPanels(categories.stream().findFirst().orElse(null));

            add(new Scroller(orderableItemWrapper));
        }

        private void setItemPanels(Category category) {
            if (category != null) {
                orderableItemWrapper.removeAll();
                orderableItemWrapper.add(orderService.getItemsByCategory(category).stream().map(this::orderableItemToComponent).toList());
            }
        }

        private Component orderableItemToComponent(OrderableItem orderableItem) {
            Button card = new Button(orderableItem.getName(), event -> orderableItemConsumer.accept(orderableItem));
            card.setWidth("150px");
            card.setHeight("150px");
//            card.getStyle().setBackground("url('https://picsum.photos/150/150')");

            //align name center
            card.getStyle().setDisplay(Style.Display.FLEX);
            //TODO: MOVE TO CSS FILE
            card.getStyle().set("align-items", "center");
            card.getStyle().set("justify-content", "center");
            card.getStyle().set("border", "1px solid black");

            return card;
        }

    public void setOrder(Order order) {
        setEnabled(!OrderStatus.PAID.equals(order.getStatus()));
    }
}