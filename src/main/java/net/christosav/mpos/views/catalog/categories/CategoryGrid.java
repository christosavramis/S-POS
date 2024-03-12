package net.christosav.mpos.views.catalog.categories;

import com.vaadin.flow.component.grid.Grid;
import net.christosav.mpos.data.Category;
import net.christosav.mpos.views.components.AbstractGrid;

public class CategoryGrid extends AbstractGrid<Category> {
    public CategoryGrid() {
        super(Category.class);
    }

    @Override
    public void setupGrid(Grid<Category> grid) {
        grid.addColumn(Category::getId).setHeader("Id");
        grid.addColumn(Category::getName).setHeader("Name");
        grid.addComponentColumn(category -> createPermissionIcon(category.isActive())).setHeader("Active");
    }
}
