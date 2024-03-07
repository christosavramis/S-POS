package net.christosav.mpos.views.components;

import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
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
}
