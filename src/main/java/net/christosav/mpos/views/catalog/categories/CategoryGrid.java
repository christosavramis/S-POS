package net.christosav.mpos.views.catalog.categories;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import net.christosav.mpos.data.Category;
import net.christosav.mpos.views.components.AbstractGrid;

public class CategoryGrid extends AbstractGrid<Category> {
    public CategoryGrid() {
        super(Category.class);
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

    @Override
    public void setupGrid(Grid<Category> grid) {
        grid.addColumn(Category::getId).setHeader("Id");
        grid.addColumn(Category::getName).setHeader("Name");
        grid.addComponentColumn(category -> createPermissionIcon(category.isActive())).setHeader("Active");
    }
}
