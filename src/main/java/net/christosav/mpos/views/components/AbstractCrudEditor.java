package net.christosav.mpos.views.components;

import com.vaadin.flow.component.*;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.shared.Registration;
import com.vaadin.flow.theme.lumo.LumoUtility.Gap;
import net.christosav.mpos.services.AbstractCrudService;


public abstract class AbstractCrudEditor<T> extends Composite<VerticalLayout> {

    protected AbstractCrudEditor(AbstractGrid<T> grid, AbstractForm<T> form, AbstractCrudService<T, Long> crudService) {

        getContent().getStyle().set("flex-grow", "1");
        getContent().setSizeFull();

        HorizontalLayout mainLayout = new HorizontalLayout();
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


        MenuBar menuBar = new MenuBar();
        menuBar.setWidth("min-content");
        menuBar.addItem("new", event -> {
            form.newObject();
            System.out.println("New button clicked");
        });

        gridActionBarLayout.setAlignSelf(Alignment.CENTER, menuBar);
        gridActionBarLayout.add(menuBar);

        getContent().add(mainLayout);
        mainLayout.add(gridLeftLayout);
        gridLeftLayout.add(gridActionBarLayout);
        gridLeftLayout.add(grid);
        mainLayout.add(form);

        form.addListener(CrudEvent.FormSave.class, event -> {
            crudService.save((T) event.getPayload());
            grid.updateGrid(crudService::pageList);
        });

        form.addListener(CrudEvent.FormDelete.class, event -> {
            crudService.delete((T) event.getPayload());
            grid.updateGrid(crudService::pageList);
        });

        grid.addListener(CrudEvent.GridRowSelected.class, event -> form.setObject((T) event.getPayload()));

        grid.updateGrid(crudService::pageList);
    }

    @Override
    public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType, ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }
}
