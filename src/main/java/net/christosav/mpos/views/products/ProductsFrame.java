package net.christosav.mpos.views.products;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.TabSheet;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.theme.lumo.LumoUtility;
import net.christosav.mpos.views.components.pricefield.PriceField;

import java.util.ArrayList;
import java.util.List;

public class ProductsFrame extends VerticalLayout {
    TextField name = new TextField();
    public ProductsFrame() {
        setWidth("inherit");

        name.setLabel("name");
        name.setWidth("100%");
        add(name);

        ComboBox comboBox = new ComboBox();
        comboBox.setLabel("category");
        comboBox.setWidth("100%");
        setComboBoxSampleData(comboBox);
        add(comboBox);

        TextField textField2 = new TextField();
        textField2.setLabel("Text field");
        textField2.setWidth("100%");
        add(textField2);

        PriceField price = new PriceField();
        price.setLabel("Price");
        price.setWidth("100%");
        add(price);

        Checkbox checkbox = new Checkbox();
        checkbox.setLabel("Checkbox");
        checkbox.setWidth("100%");
        add(checkbox);

        TabSheet tabSheet = new TabSheet();
        tabSheet.setWidth("100%");
        tabSheet.getStyle().set("flex-grow", "1");
        setTabSheetSampleData(tabSheet);
        add(tabSheet);

        HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.setWidthFull();
        buttonLayout.addClassName(LumoUtility.Gap.LARGE);
        buttonLayout.addClassName(LumoUtility.Padding.SMALL);
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

        add(buttonLayout);
    }

    private void setComboBoxSampleData(ComboBox comboBox) {
        List<ProductsView.SampleItem> sampleItems = new ArrayList<>();
        sampleItems.add(new ProductsView.SampleItem("first", "First", null));
        sampleItems.add(new ProductsView.SampleItem("second", "Second", null));
        sampleItems.add(new ProductsView.SampleItem("third", "Third", Boolean.TRUE));
        sampleItems.add(new ProductsView.SampleItem("fourth", "Fourth", null));
        comboBox.setItems(sampleItems);
        comboBox.setItemLabelGenerator(item -> ((ProductsView.SampleItem) item).label());
    }

    private void setTabSheetSampleData(TabSheet tabSheet) {
        tabSheet.add("Dashboard", new Div(new Text("This is the Dashboard tab content")));
        tabSheet.add("Payment", new Div(new Text("This is the Payment tab content")));
        tabSheet.add("Shipping", new Div(new Text("This is the Shipping tab content")));
    }
}
