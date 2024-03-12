package net.christosav.mpos.views.pos;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H5;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.dom.Style;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import net.christosav.mpos.data.Category;
import net.christosav.mpos.data.OrderableItem;
import net.christosav.mpos.services.POSOrderingService;
import net.christosav.mpos.views.MainLayout;

import java.util.*;
import java.util.function.Function;
import java.util.function.Supplier;


@PageTitle("Ordering")
@Route(value = "pos/ordering", layout = MainLayout.class)
@AnonymousAllowed
@Uses(Icon.class)
public class POSOrderingView extends Composite<VerticalLayout> {
    private final POSOrderingService posOrderingService;

    public POSOrderingView(POSOrderingService posOrderingService) {
        this.posOrderingService = posOrderingService;

        getContent().setSizeFull();

        HorizontalLayout mainLayout = new HorizontalLayout();
        mainLayout.setSizeFull();
        mainLayout.add(new OrderableItemPanelLayout(posOrderingService::getCategories, posOrderingService::getItemsByCategory), getOrderedTreeLayout());
        getContent().add(mainLayout);
    }


    Component getOrderedTreeLayout() {
        VerticalLayout layout = new VerticalLayout();
        H5 h5 = new H5("Ordering");
        layout.add(h5);


        return layout;
    }

    class OrderableItemPanelLayout extends VerticalLayout {
        private final Div orderableItemWrapper = new Div();
        private transient final Function<Category, List<OrderableItem>> categoryOrderableItemsSupplier;

        public OrderableItemPanelLayout(Supplier<List<Category>> categoriesSupplier, Function<Category, List<OrderableItem>> categoryOrderableItemsSupplier) {
            this.categoryOrderableItemsSupplier = categoryOrderableItemsSupplier;
            List<Category> categories = categoriesSupplier.get();
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
                orderableItemWrapper.add(categoryOrderableItemsSupplier.apply(category).stream().map(this::orderableItemToComponent).toList());
            }
        }

        private Component orderableItemToComponent(OrderableItem orderableItem) {
            Button card = new Button(orderableItem.getName());
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
    }


}
