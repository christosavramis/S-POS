package net.christosav.mpos.views.catalog;

import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.RolesAllowed;
import net.christosav.mpos.views.MainLayout;

@PageTitle("Catalog")
@Route(value = "catalog/categories", layout = MainLayout.class)
@RolesAllowed("USER")
@Uses(Icon.class)
public class CatalogView extends Composite<VerticalLayout> {

    public CatalogView() {
        getContent().setHeightFull();
        getContent().setWidthFull();
    }
}
