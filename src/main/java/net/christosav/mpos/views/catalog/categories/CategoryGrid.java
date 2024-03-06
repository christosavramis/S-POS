package net.christosav.mpos.views.catalog.categories;

import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.shared.Registration;
import net.christosav.mpos.data.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

public class CategoryGrid extends Div {
    public CategoryGrid() {
        setSizeFull();
        add(basicGrid);
    }
    private final Grid<Category> basicGrid = new Grid<>(Category.class, false);

    {
        basicGrid.addColumn(Category::getId).setHeader("Id");
        basicGrid.addColumn(Category::getName).setHeader("Name");
        basicGrid.addComponentColumn(category -> createPermissionIcon(category.isActive())).setHeader("Active");
        basicGrid.setSelectionMode(Grid.SelectionMode.SINGLE);
        basicGrid.addSelectionListener(selection -> fireEvent(new CrudEvent.GridRowSelected(this, selection.getFirstSelectedItem().orElse(null))));
    }
    private static Icon createPermissionIcon(boolean hasPermission) {
        Icon icon;
        if (hasPermission) {
            icon = createIcon(VaadinIcon.CHECK, "Yes");
            icon.getElement().getThemeList().add("badge success");
        } else {
            icon = createIcon(VaadinIcon.CLOSE_SMALL, "No");
            icon.getElement().getThemeList().add("badge error");
        }
        return icon;
    }

    private static Icon createIcon(VaadinIcon vaadinIcon, String label) {
        Icon icon = vaadinIcon.create();
        icon.getStyle().set("padding", "var(--lumo-space-xs");
        // Accessible label
        icon.getElement().setAttribute("aria-label", label);
        // Tooltip
        icon.getElement().setAttribute("title", label);
        return icon;
    }

    public void updateGrid(Function<Pageable, Page<Category>> pageProvider) {
//        basicGrid.setItems(query -> pageProvider.apply(PageRequest.of(query.getPage(), query.getPageSize(), VaadinSpringDataHelpers.toSpringDataSort(query))).stream());
        basicGrid.setItems(pageProvider.apply(PageRequest.of(0, 10)).stream().toList());
    }

    public void updateGrid(Supplier<List<Category>> categoryList) {
        basicGrid.setItems(categoryList.get());
    }

    @Override
    public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType, ComponentEventListener<T> listener) {
        System.out.println("Adding listener to " + this + " " + eventType.getSimpleName() + " " + listener.getClass().getSimpleName());
        return getEventBus().addListener(eventType, listener);
    }
}
