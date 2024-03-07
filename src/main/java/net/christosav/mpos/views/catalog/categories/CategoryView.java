package net.christosav.mpos.views.catalog.categories;

import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.RolesAllowed;
import lombok.extern.slf4j.Slf4j;
import net.christosav.mpos.data.Category;
import net.christosav.mpos.services.CategoryCrudService;
import net.christosav.mpos.views.MainLayout;
import net.christosav.mpos.views.components.AbstractCrudEditor;

@PageTitle("Products")
@Route(value = "catalog/categories", layout = MainLayout.class)
@RolesAllowed("USER")
@Uses(Icon.class)
@Slf4j
public class CategoryView extends AbstractCrudEditor<Category> {
    public CategoryView(CategoryCrudService categoryService) {
        super(new CategoryGrid(), new CategoryForm(), categoryService);
    }
}
