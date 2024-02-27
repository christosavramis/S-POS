package net.christosav.mpos.views.products;

import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.orderedlayout.FlexComponent.JustifyContentMode;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.TabSheet;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.data.VaadinSpringDataHelpers;
import com.vaadin.flow.theme.lumo.LumoUtility.Gap;
import com.vaadin.flow.theme.lumo.LumoUtility.Padding;
import jakarta.annotation.security.RolesAllowed;
import java.util.ArrayList;
import java.util.List;
import net.christosav.mpos.components.pricefield.PriceField;
import net.christosav.mpos.data.SamplePerson;
import net.christosav.mpos.services.SamplePersonService;
import net.christosav.mpos.views.MainLayout;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;

@PageTitle("Products")
@Route(value = "catalog/products", layout = MainLayout.class)
@RolesAllowed("USER")
@Uses(Icon.class)
public class ProductsView extends Composite<VerticalLayout> {

    public ProductsView() {
        HorizontalLayout layoutRow = new HorizontalLayout();
        VerticalLayout layoutColumn2 = new VerticalLayout();
        HorizontalLayout layoutRow2 = new HorizontalLayout();
        MenuBar menuBar = new MenuBar();
        Grid basicGrid = new Grid(SamplePerson.class);
        VerticalLayout layoutColumn3 = new VerticalLayout();
        TextField textField = new TextField();
        ComboBox comboBox = new ComboBox();
        TextField textField2 = new TextField();
        PriceField price = new PriceField();
        Checkbox checkbox = new Checkbox();
        TabSheet tabSheet = new TabSheet();
        HorizontalLayout layoutRow3 = new HorizontalLayout();
        Button buttonSecondary = new Button();
        Button buttonPrimary = new Button();
        getContent().setWidth("100%");
        getContent().getStyle().set("flex-grow", "1");
        layoutRow.addClassName(Gap.MEDIUM);
        layoutRow.setWidth("100%");
        layoutRow.getStyle().set("flex-grow", "1");
        layoutColumn2.setHeightFull();
        layoutRow.setFlexGrow(1.0, layoutColumn2);
        layoutColumn2.setWidth("100%");
        layoutColumn2.getStyle().set("flex-grow", "1");
        layoutRow2.setWidthFull();
        layoutColumn2.setFlexGrow(1.0, layoutRow2);
        layoutRow2.addClassName(Gap.MEDIUM);
        layoutRow2.setWidth("100%");
        layoutRow2.getStyle().set("flex-grow", "1");
        layoutRow2.setAlignSelf(FlexComponent.Alignment.CENTER, menuBar);
        menuBar.setWidth("min-content");
        setMenuBarSampleData(menuBar);
        basicGrid.setWidth("100%");
        basicGrid.setHeight("100%");
        setGridSampleData(basicGrid);
        layoutColumn3.getStyle().set("flex-grow", "1");
        textField.setLabel("name");
        textField.setWidth("100%");
        comboBox.setLabel("category");
        comboBox.setWidth("100%");
        setComboBoxSampleData(comboBox);
        textField2.setLabel("Text field");
        textField2.setWidth("100%");
        price.setLabel("Price");
        price.setWidth("100%");
        checkbox.setLabel("Checkbox");
        checkbox.setWidth("100%");
        tabSheet.setWidth("100%");
        tabSheet.getStyle().set("flex-grow", "1");
        setTabSheetSampleData(tabSheet);
        layoutRow3.setWidthFull();
        layoutColumn3.setFlexGrow(1.0, layoutRow3);
        layoutRow3.addClassName(Gap.LARGE);
        layoutRow3.addClassName(Padding.SMALL);
        layoutRow3.setWidth("100%");
        layoutRow3.setHeight("min-content");
        layoutRow3.setAlignItems(Alignment.CENTER);
        layoutRow3.setJustifyContentMode(JustifyContentMode.CENTER);
        buttonSecondary.setText("cancel");
        buttonSecondary.setWidth("min-content");
        buttonPrimary.setText("done");
        buttonPrimary.setWidth("min-content");
        buttonPrimary.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        getContent().add(layoutRow);
        layoutRow.add(layoutColumn2);
        layoutColumn2.add(layoutRow2);
        layoutRow2.add(menuBar);
        layoutColumn2.add(basicGrid);
        layoutRow.add(layoutColumn3);
        layoutColumn3.add(textField);
        layoutColumn3.add(comboBox);
        layoutColumn3.add(textField2);
        layoutColumn3.add(price);
        layoutColumn3.add(checkbox);
        layoutColumn3.add(tabSheet);
        layoutColumn3.add(layoutRow3);
        layoutRow3.add(buttonSecondary);
        layoutRow3.add(buttonPrimary);
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

    record SampleItem(String value, String label, Boolean disabled) {
    }

    private void setComboBoxSampleData(ComboBox comboBox) {
        List<SampleItem> sampleItems = new ArrayList<>();
        sampleItems.add(new SampleItem("first", "First", null));
        sampleItems.add(new SampleItem("second", "Second", null));
        sampleItems.add(new SampleItem("third", "Third", Boolean.TRUE));
        sampleItems.add(new SampleItem("fourth", "Fourth", null));
        comboBox.setItems(sampleItems);
        comboBox.setItemLabelGenerator(item -> ((SampleItem) item).label());
    }

    private void setTabSheetSampleData(TabSheet tabSheet) {
        tabSheet.add("Dashboard", new Div(new Text("This is the Dashboard tab content")));
        tabSheet.add("Payment", new Div(new Text("This is the Payment tab content")));
        tabSheet.add("Shipping", new Div(new Text("This is the Shipping tab content")));
    }
}
