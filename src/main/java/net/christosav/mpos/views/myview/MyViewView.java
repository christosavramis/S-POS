package net.christosav.mpos.views.myview;

import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.flow.theme.lumo.LumoUtility.Gap;
import java.util.ArrayList;
import java.util.List;
import net.christosav.mpos.views.components.pricefield.PriceField;
import net.christosav.mpos.views.MainLayout;

@PageTitle("My View")
@Route(value = "my-view", layout = MainLayout.class)
@AnonymousAllowed
@Uses(Icon.class)
public class MyViewView extends Composite<VerticalLayout> {

    public MyViewView() {
        HorizontalLayout layoutRow = new HorizontalLayout();
        VerticalLayout layoutColumn3 = new VerticalLayout();
        VerticalLayout layoutColumn2 = new VerticalLayout();
        H3 h3 = new H3();
        TextField textField = new TextField();
        ComboBox comboBox = new ComboBox();
        PriceField price = new PriceField();
        H3 h32 = new H3();
        Select select = new Select();
        getContent().setWidth("100%");
        getContent().getStyle().set("flex-grow", "1");
        layoutRow.setWidthFull();
        getContent().setFlexGrow(1.0, layoutRow);
        layoutRow.addClassName(Gap.MEDIUM);
        layoutRow.setWidth("100%");
        layoutRow.getStyle().set("flex-grow", "1");
        layoutColumn3.setHeightFull();
        layoutRow.setFlexGrow(1.0, layoutColumn3);
        layoutColumn3.getStyle().set("flex-grow", "1");
        layoutColumn3.getStyle().set("flex-grow", "1");
        layoutColumn2.setHeightFull();
        layoutRow.setFlexGrow(1.0, layoutColumn2);
        layoutColumn2.setWidth("min-content");
        layoutColumn2.getStyle().set("flex-grow", "1");
        h3.setText("Info");
        h3.setWidth("max-content");
        textField.setLabel("name");
        textField.setWidth("min-content");
        comboBox.setLabel("category");
        comboBox.setWidth("min-content");
        setComboBoxSampleData(comboBox);
        price.setLabel("Price");
        price.setWidth("min-content");
        h32.setText("Ingredients");
        h32.setWidth("max-content");
        select.setLabel("Select");
        select.setWidth("min-content");
        setSelectSampleData(select);
        getContent().add(layoutRow);
        layoutRow.add(layoutColumn3);
        layoutRow.add(layoutColumn2);
        layoutColumn2.add(h3);
        layoutColumn2.add(textField);
        layoutColumn2.add(comboBox);
        layoutColumn2.add(price);
        layoutColumn2.add(h32);
        layoutColumn2.add(select);
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

    private void setSelectSampleData(Select select) {
        List<SampleItem> sampleItems = new ArrayList<>();
        sampleItems.add(new SampleItem("first", "First", null));
        sampleItems.add(new SampleItem("second", "Second", null));
        sampleItems.add(new SampleItem("third", "Third", Boolean.TRUE));
        sampleItems.add(new SampleItem("fourth", "Fourth", null));
        select.setItems(sampleItems);
        select.setItemLabelGenerator(item -> ((SampleItem) item).label());
        select.setItemEnabledProvider(item -> !Boolean.TRUE.equals(((SampleItem) item).disabled()));
    }
}
