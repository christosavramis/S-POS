package net.christosav.mpos.views.catalog.orderableitems;

import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.data.VaadinSpringDataHelpers;
import com.vaadin.flow.theme.lumo.LumoUtility.Gap;
import jakarta.annotation.security.RolesAllowed;
import net.christosav.mpos.data.Category;
import net.christosav.mpos.services.CategoryCrudService;
import net.christosav.mpos.views.MainLayout;
import org.springframework.data.domain.PageRequest;

import java.util.Optional;

@PageTitle("Orderable Items")
@Route(value = "catalog/orderableitems", layout = MainLayout.class)
@RolesAllowed("USER")
@Uses(Icon.class)
public class OrderableItemView extends Composite<VerticalLayout> {
    private final CategoryCrudService categoryService;

    private final OrderableItemForm orderableItemForm = new OrderableItemForm();

    private final Grid<Category> basicGrid = new Grid<>(Category.class, false);

    {
        basicGrid.addColumn(Category::getId).setHeader("Id");
        basicGrid.addColumn(Category::getName).setHeader("Name");
        basicGrid.addComponentColumn(category -> createPermissionIcon(category.isActive())).setHeader("Active");
        setGridSampleData(basicGrid);
        basicGrid.setSelectionMode(Grid.SelectionMode.SINGLE);
        basicGrid.addSelectionListener(selection -> {
            Optional<Category> optionalPerson = selection.getFirstSelectedItem();
            optionalPerson.ifPresent(orderableItemForm::setObject);
        });
    }

    public OrderableItemView(CategoryCrudService categoryService) {
        this.categoryService = categoryService;
        this.orderableItemForm.setSaveListener(categoryService::save);
        this.orderableItemForm.setDeleteListener(categoryService::delete);

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
            orderableItemForm.newObject();
            System.out.println("New button clicked");
        });

        gridActionBarLayout.setAlignSelf(Alignment.CENTER, menuBar);
        gridActionBarLayout.add(menuBar);

        getContent().add(mainLayout);
        mainLayout.add(gridLeftLayout);
        gridLeftLayout.add(gridActionBarLayout);

        gridLeftLayout.add(basicGrid);

        mainLayout.add(orderableItemForm);
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

    private void setGridSampleData(Grid<Category> grid) {
        grid.setItems(query -> categoryService.pageList(PageRequest.of(query.getPage(), query.getPageSize(), VaadinSpringDataHelpers.toSpringDataSort(query))).stream());
    }

}
