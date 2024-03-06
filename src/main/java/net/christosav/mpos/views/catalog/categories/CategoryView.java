package net.christosav.mpos.views.catalog.categories;

import com.vaadin.flow.component.*;
import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.shared.Registration;
import com.vaadin.flow.theme.lumo.LumoUtility.Gap;
import jakarta.annotation.security.RolesAllowed;
import lombok.extern.slf4j.Slf4j;
import net.christosav.mpos.data.Category;
import net.christosav.mpos.services.CategoryCrudService;
import net.christosav.mpos.views.MainLayout;

@PageTitle("Products")
@Route(value = "catalog/categories", layout = MainLayout.class)
@RolesAllowed("USER")
@Uses(Icon.class)
@Slf4j
public class CategoryView extends Composite<VerticalLayout> {
    private final CategoryForm form = new CategoryForm();
    private final CategoryGrid categoryGrid = new CategoryGrid();

    public CategoryView(CategoryCrudService categoryService) {
        layout();

        form.setSaveListener(categoryService::save);
        form.setDeleteListener(entity -> {
            categoryService.delete(entity);
            categoryGrid.updateGrid(categoryService::list);
        });

        form.addListener(CrudEvent.FormSave.class, event -> {
            categoryService.save((Category) event.getPayload());
            categoryGrid.updateGrid(categoryService::list);
        });

        form.addListener(CrudEvent.FormDelete.class, event -> {
            categoryService.delete((Category) event.getPayload());
            categoryGrid.updateGrid(categoryService::list);
        });

        form.addListener(CrudEvent.FormCancel.class, event -> System.out.println("Cancel button clicked"));


        categoryGrid.addListener(CrudEvent.GridRowSelected.class, event -> {
            form.setObject((Category) event.getPayload());
            System.out.println("Grid row selected");
        });

        categoryGrid.updateGrid(categoryService::list);
    }
    private void layout() {
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
        gridLeftLayout.add(categoryGrid);
        categoryGrid.getStyle().setBackgroundColor("green !important");
        gridLeftLayout.getStyle().setBackgroundColor("red !important");
        mainLayout.add(form);
    }

    @Override
    public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType, ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }
}
