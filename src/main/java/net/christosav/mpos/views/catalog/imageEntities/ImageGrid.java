package net.christosav.mpos.views.catalog.imageEntities;

import com.vaadin.flow.component.grid.Grid;
import net.christosav.mpos.data.Category;
import net.christosav.mpos.data.ImageEntity;
import net.christosav.mpos.views.components.AbstractGrid;

public class ImageGrid extends AbstractGrid<ImageEntity> {
    public ImageGrid() {
        super(ImageEntity.class);
    }

    @Override
    public void setupGrid(Grid<ImageEntity> grid) {
        grid.addColumn(ImageEntity::getName).setHeader("Name");
        grid.addColumn(ImageEntity::getUrl).setHeader("URL");
        grid.addColumn(ImageEntity::getType).setHeader("Type");
    }
}
