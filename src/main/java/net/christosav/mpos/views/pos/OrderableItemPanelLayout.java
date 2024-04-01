package net.christosav.mpos.views.pos;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.dom.Style;
import net.christosav.mpos.data.*;

import java.util.List;
import java.util.Optional;
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
            card.getStyle().set("background-image", "url('" + Optional.ofNullable(orderableItem.getImage()).map(ImageEntity::getUrl).orElse("") + "')");
            card.getStyle().set("background-repeat", "no-repeat");
            card.getStyle().set("background-size", "contain");
            card.getStyle().set("background-position", "center");
            //box-shadow: 10px 5px 5px red;
            card.getStyle().set("box-shadow", "5px 5px 5px black");
            card.getStyle().set("border-radius", "10px");

            //align name center
//            card.getStyle().setDisplay(Style.Display.FLEX);
            //TODO: MOVE TO CSS FILE
//            card.getStyle().set("align-items", "center");
//            card.getStyle().set("justify-content", "center");
            card.getStyle().set("border", "1px solid black");

            return card;
        }

    public void setOrder(Order order) {
        setEnabled(!OrderStatus.PAID.equals(order.getStatus()));
    }
}