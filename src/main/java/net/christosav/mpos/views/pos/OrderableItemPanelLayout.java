package net.christosav.mpos.views.pos;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.dom.Style;
import net.christosav.mpos.data.Category;
import net.christosav.mpos.data.OrderableItem;

import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;


import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.shared.Registration;
import net.christosav.mpos.views.util.ComponentEventWithPayload;

public class OrderableItemPanelLayout extends VerticalLayout {
        private final Div orderableItemWrapper = new Div();
        private transient final Function<Category, List<OrderableItem>> categoryOrderableItemsSupplier;

        public OrderableItemPanelLayout(Supplier<List<Category>> categoriesSupplier, Function<Category, List<OrderableItem>> categoryOrderableItemsSupplier) {
            this.categoryOrderableItemsSupplier = categoryOrderableItemsSupplier;
            List<Category> categories = categoriesSupplier.get();
            MenuBar menuBar = new MenuBar();
            categories.forEach(category -> menuBar.addItem(category.getName(), event -> fireEvent(new CategoryClicked(this, category))));
            add(menuBar);

            orderableItemWrapper.getStyle().setDisplay(Style.Display.FLEX);
            orderableItemWrapper.getStyle().set("flex-wrap", "wrap");
            orderableItemWrapper.getStyle().set("gap", "5px");
            orderableItemWrapper.setMaxHeight("100%");
            setItemPanels(categories.stream().findFirst().orElse(null));

            addListener(CategoryClicked.class, event -> setItemPanels(event.getPayload()));


            add(new Scroller(orderableItemWrapper));
        }

        private void setItemPanels(Category category) {
            if (category != null) {
                orderableItemWrapper.removeAll();
                orderableItemWrapper.add(categoryOrderableItemsSupplier.apply(category).stream().map(this::orderableItemToComponent).toList());
            }
        }

        private Component orderableItemToComponent(OrderableItem orderableItem) {
            Button card = new Button(orderableItem.getName(), event -> fireEvent(new OrderableItemClicked(this, orderableItem)));
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

        @Override
        public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType, ComponentEventListener<T> listener) {
            return getEventBus().addListener(eventType, listener);
        }

        private class CategoryClicked extends ComponentEventWithPayload<OrderableItemPanelLayout, Category> {
            private CategoryClicked(OrderableItemPanelLayout source, Category category) {
                super(source, category);
            }
        }

        public class OrderableItemClicked extends ComponentEventWithPayload<OrderableItemPanelLayout, OrderableItem> {
            public OrderableItemClicked(OrderableItemPanelLayout source, OrderableItem payload) {
                super(source, payload);
            }
        }

    }