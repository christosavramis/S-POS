package net.christosav.mpos.views.cart;

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
import net.christosav.mpos.data.SamplePerson;
import net.christosav.mpos.services.SamplePersonService;
import net.christosav.mpos.views.MainLayout;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;

@PageTitle("Cart")
@Route(value = "ordering/cart", layout = MainLayout.class)
@AnonymousAllowed
@Uses(Icon.class)
public class CartView extends Composite<VerticalLayout> {

    public CartView() {
        HorizontalLayout layoutRow = new HorizontalLayout();
        MenuBar menuBar = new MenuBar();
        HorizontalLayout layoutRow2 = new HorizontalLayout();
        VerticalLayout layoutColumn5 = new VerticalLayout();
        VerticalLayout layoutColumn2 = new VerticalLayout();
        H6 h6 = new H6();
        VerticalLayout layoutColumn3 = new VerticalLayout();
        Grid minimalistGrid = new Grid(SamplePerson.class);
        VerticalLayout layoutColumn4 = new VerticalLayout();
        HorizontalLayout layoutRow3 = new HorizontalLayout();
        H5 h5 = new H5();
        H5 h52 = new H5();
        HorizontalLayout layoutRow4 = new HorizontalLayout();
        H5 h53 = new H5();
        H5 h54 = new H5();
        HorizontalLayout layoutRow5 = new HorizontalLayout();
        Button buttonPrimary = new Button();
        getContent().setWidth("100%");
        getContent().getStyle().set("flex-grow", "1");
        layoutRow.addClassName(Gap.MEDIUM);
        layoutRow.setWidth("100%");
        layoutRow.setHeight("min-content");
        menuBar.setWidth("min-content");
        setMenuBarSampleData(menuBar);
        layoutRow2.addClassName(Gap.MEDIUM);
        layoutRow2.setWidth("100%");
        layoutRow2.getStyle().set("flex-grow", "1");
        layoutColumn5.getStyle().set("flex-grow", "1");
        layoutColumn5.setHeight("100%");
        layoutColumn2.setMinWidth("370px");
        layoutColumn2.getStyle().set("flex-grow", "1");
        h6.setText("cart");
        h6.setWidth("max-content");
        layoutColumn3.setWidthFull();
        layoutColumn2.setFlexGrow(1.0, layoutColumn3);
        layoutColumn3.setWidth("100%");
        layoutColumn3.getStyle().set("flex-grow", "1");
        minimalistGrid.addThemeVariants(GridVariant.LUMO_COMPACT, GridVariant.LUMO_NO_BORDER,
                GridVariant.LUMO_NO_ROW_BORDERS);
        minimalistGrid.setWidth("100%");
        minimalistGrid.setHeight("100%");
        setGridSampleData(minimalistGrid);
        layoutColumn4.setWidthFull();
        layoutColumn2.setFlexGrow(1.0, layoutColumn4);
        layoutColumn4.setWidth("100%");
        layoutColumn4.setHeight("min-content");
        layoutRow3.setWidthFull();
        layoutColumn4.setFlexGrow(1.0, layoutRow3);
        layoutRow3.addClassName(Gap.MEDIUM);
        layoutRow3.setWidth("100%");
        layoutRow3.setHeight("min-content");
        layoutRow3.setAlignItems(Alignment.CENTER);
        layoutRow3.setJustifyContentMode(JustifyContentMode.END);
        h5.setText("tax");
        h5.setWidth("max-content");
        h52.setText("Heading");
        h52.setWidth("max-content");
        layoutRow4.setWidthFull();
        layoutColumn4.setFlexGrow(1.0, layoutRow4);
        layoutRow4.addClassName(Gap.MEDIUM);
        layoutRow4.setWidth("100%");
        layoutRow4.setHeight("min-content");
        layoutRow4.setAlignItems(Alignment.CENTER);
        layoutRow4.setJustifyContentMode(JustifyContentMode.END);
        h53.setText("total");
        h53.setWidth("max-content");
        h54.setText("Heading");
        h54.setWidth("max-content");
        layoutRow5.setWidthFull();
        layoutColumn2.setFlexGrow(1.0, layoutRow5);
        layoutRow5.addClassName(Gap.MEDIUM);
        layoutRow5.setWidth("100%");
        layoutRow5.setHeight("min-content");
        layoutRow5.setAlignItems(Alignment.CENTER);
        layoutRow5.setJustifyContentMode(JustifyContentMode.CENTER);
        buttonPrimary.setText("checkout");
        layoutRow5.setAlignSelf(FlexComponent.Alignment.CENTER, buttonPrimary);
        buttonPrimary.setWidth("min-content");
        buttonPrimary.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        getContent().add(layoutRow);
        layoutRow.add(menuBar);
        getContent().add(layoutRow2);
        layoutRow2.add(layoutColumn5);
        layoutRow2.add(layoutColumn2);
        layoutColumn2.add(h6);
        layoutColumn2.add(layoutColumn3);
        layoutColumn3.add(minimalistGrid);
        layoutColumn2.add(layoutColumn4);
        layoutColumn4.add(layoutRow3);
        layoutRow3.add(h5);
        layoutRow3.add(h52);
        layoutColumn4.add(layoutRow4);
        layoutRow4.add(h53);
        layoutRow4.add(h54);
        layoutColumn2.add(layoutRow5);
        layoutRow5.add(buttonPrimary);
    }

    private void setMenuBarSampleData(MenuBar menuBar) {
        menuBar.addItem("View");
        menuBar.addItem("Edit");
        menuBar.addItem("Share");
        menuBar.addItem("Move");
    }

    private void setGridSampleData(Grid grid) {
        grid.setItems(query -> samplePersonService.list(
                PageRequest.of(query.getPage(), query.getPageSize(), VaadinSpringDataHelpers.toSpringDataSort(query)))
                .stream());
    }

    @Autowired()
    private SamplePersonService samplePersonService;
}
