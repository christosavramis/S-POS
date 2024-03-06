package net.christosav.mpos.views.products;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.HasComponents;
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
    private final SamplePersonService samplePersonService;
    public ProductsView(SamplePersonService samplePersonService) {
        this.samplePersonService = samplePersonService;
        HorizontalLayout mainLayout = new HorizontalLayout();


        MenuBar menuBar = new MenuBar();
        Grid basicGrid = new Grid(SamplePerson.class);

        getContent().getStyle().set("flex-grow", "1");
        getContent().setSizeFull();

        VerticalLayout gridLeftLayout = new VerticalLayout();
        gridLeftLayout.setSizeFull();
        gridLeftLayout.getStyle().set("flex-grow", "1");

        mainLayout.addClassName(Gap.MEDIUM);
        mainLayout.setWidth("100%");
        mainLayout.getStyle().set("flex-grow", "1");
        mainLayout.setFlexGrow(1.0, gridLeftLayout);

        HorizontalLayout gridActionBarLayout = new HorizontalLayout();
        gridActionBarLayout.setWidthFull();
        gridActionBarLayout.addClassName(Gap.MEDIUM);
        gridActionBarLayout.setAlignSelf(FlexComponent.Alignment.CENTER, menuBar);
        gridActionBarLayout.add(menuBar);

        menuBar.setWidth("min-content");
        setMenuBarSampleData(menuBar);
        setGridSampleData(basicGrid);


        getContent().add(mainLayout);
        mainLayout.add(gridLeftLayout);
        gridLeftLayout.add(gridActionBarLayout);

        gridLeftLayout.add(basicGrid);


        mainLayout.add(setupForm());
    }


    private VerticalLayout setupForm() {
        VerticalLayout form = new VerticalLayout();
        form.setWidth("inherit");

        TextField textField = new TextField();
        textField.setLabel("name");
        textField.setWidth("100%");
        form.add(textField);

        ComboBox comboBox = new ComboBox();
        comboBox.setLabel("category");
        comboBox.setWidth("100%");
        setComboBoxSampleData(comboBox);
        form.add(comboBox);

        TextField textField2 = new TextField();
        textField2.setLabel("Text field");
        textField2.setWidth("100%");
        form.add(textField2);

        PriceField price = new PriceField();
        price.setLabel("Price");
        price.setWidth("100%");
        form.add(price);

        Checkbox checkbox = new Checkbox();
        checkbox.setLabel("Checkbox");
        checkbox.setWidth("100%");
        form.add(checkbox);

        TabSheet tabSheet = new TabSheet();
        tabSheet.setWidth("100%");
        tabSheet.getStyle().set("flex-grow", "1");
        setTabSheetSampleData(tabSheet);
        form.add(tabSheet);

        HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.setWidthFull();
        buttonLayout.addClassName(Gap.LARGE);
        buttonLayout.addClassName(Padding.SMALL);
        buttonLayout.setWidth("100%");
        buttonLayout.setHeight("min-content");
        buttonLayout.setAlignItems(Alignment.CENTER);
        buttonLayout.setJustifyContentMode(JustifyContentMode.CENTER);

        Button cancelButton = new Button();
        cancelButton.setText("cancel");
        cancelButton.setWidth("min-content");
        buttonLayout.add(cancelButton);

        Button saveButton = new Button();
        saveButton.setText("done");
        saveButton.setWidth("min-content");
        saveButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        buttonLayout.add(saveButton);

        form.add(buttonLayout);

        return form;
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

        System.out.println(samplePersonService.list(PageRequest.of(0, 10)).getContent());
    }

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
