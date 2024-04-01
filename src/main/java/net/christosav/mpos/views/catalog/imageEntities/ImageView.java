package net.christosav.mpos.views.catalog.imageEntities;

import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.RolesAllowed;
import lombok.extern.slf4j.Slf4j;
import net.christosav.mpos.data.ImageEntity;
import net.christosav.mpos.services.ImageCrudService;
import net.christosav.mpos.views.MainLayout;
import net.christosav.mpos.views.components.AbstractCrudEditor;

@PageTitle("Images")
@Route(value = "catalog/image_entities", layout = MainLayout.class)
@RolesAllowed("USER")
@Uses(Icon.class)
@Slf4j
public class ImageView extends AbstractCrudEditor<ImageEntity> {
    public ImageView(ImageCrudService imageCrudService) {
        super(new ImageGrid(), new ImageForm(), imageCrudService);
    }
}
