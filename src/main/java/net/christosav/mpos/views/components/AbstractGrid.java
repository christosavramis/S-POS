package net.christosav.mpos.views.components;

import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.shared.Registration;
import com.vaadin.flow.spring.data.VaadinSpringDataHelpers;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

@Slf4j
public abstract class AbstractGrid<T> extends Div {
    private final Grid<T> basicGrid;
    protected AbstractGrid(Class<T> zclass) {
        setSizeFull();
        basicGrid = new Grid<>(zclass, false);
        setupGrid(basicGrid);
        basicGrid.setSelectionMode(Grid.SelectionMode.SINGLE);
        basicGrid.addSelectionListener(selection -> {
            T t = selection.getFirstSelectedItem().orElse(null);
            log.info("Grid row selected " + t);
            fireEvent(new CrudEvent.GridRowSelected(this, t));
        });
        add(basicGrid);
    }

    public abstract void setupGrid(Grid<T> grid);

    public void updateGrid(Function<Pageable, Page<T>> pageProvider) {
        basicGrid.setItems(query -> pageProvider.apply(PageRequest.of(query.getPage(), query.getPageSize(), VaadinSpringDataHelpers.toSpringDataSort(query))).stream());
    }

    public void updateGrid(Supplier<List<T>> categoryList) {
        basicGrid.setItems(categoryList.get());
    }

    @Override
    public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType, ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }


    public static Icon createPermissionIcon(boolean hasPermission) {
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

}
