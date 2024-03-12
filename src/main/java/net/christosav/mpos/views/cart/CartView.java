package net.christosav.mpos.views.cart;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.H5;
import com.vaadin.flow.component.html.H6;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.orderedlayout.FlexComponent.JustifyContentMode;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.flow.spring.data.VaadinSpringDataHelpers;
import com.vaadin.flow.theme.lumo.LumoUtility.Gap;
import net.christosav.mpos.data.OrderableItem;
import net.christosav.mpos.data.SamplePerson;
import net.christosav.mpos.services.OrderableItemCrudService;
import net.christosav.mpos.services.SamplePersonService;
import net.christosav.mpos.views.MainLayout;
import org.springframework.data.domain.PageRequest;

import static net.christosav.mpos.views.components.AbstractGrid.createPermissionIcon;

@PageTitle("Cart")
@Route(value = "ordering/cart", layout = MainLayout.class)
@AnonymousAllowed
@Uses(Icon.class)
public class CartView extends Composite<VerticalLayout> {

    public CartView(OrderableItemCrudService orderableItemCrudService) {
        getContent().add(new H5("Cart"));
        getContent().add(new H6("This is a simple example of a cart view."));

        Grid<OrderableItem> grid = new Grid<>(OrderableItem.class);
        grid.addColumn(OrderableItem::getId).setHeader("Id");
        grid.addColumn(OrderableItem::getName).setHeader("Name");
        grid.addColumn(OrderableItem::getDescription).setHeader("Description");
        grid.addColumn(OrderableItem::getPrice).setHeader("Price");
        grid.addComponentColumn(orderableItem -> createPermissionIcon(orderableItem.isActive())).setHeader("Active");
        grid.setItems(orderableItemCrudService.list());
        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER, GridVariant.LUMO_NO_ROW_BORDERS, GridVariant.LUMO_ROW_STRIPES);
        getContent().add(grid);

        HorizontalLayout buttons = new HorizontalLayout();
        buttons.setJustifyContentMode(JustifyContentMode.BETWEEN);
        buttons.setAlignItems(Alignment.BASELINE);
        getContent().add(buttons);

        Button checkout = new Button("Checkout");
        checkout.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        buttons.add(checkout);

        Button cancel = new Button("Cancel");
        cancel.addThemeVariants(ButtonVariant.LUMO_ERROR);
        buttons.add(cancel);

        getContent().setAlignItems(Alignment.CENTER);
        getContent().setJustifyContentMode(JustifyContentMode.CENTER);

    }

}
