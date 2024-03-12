package net.christosav.mpos.services;

import lombok.RequiredArgsConstructor;
import net.christosav.mpos.data.Category;
import net.christosav.mpos.data.OrderableItem;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class POSOrderingService {
    private final OrderableItemCrudService orderableItemCrudService;
    private final CategoryCrudService categoryCrudService;


    public List<Category> getCategories() {
        return categoryCrudService.list();
    }

    public List<OrderableItem> getItemsByCategory(Category category) {
        return orderableItemCrudService.findByCategory(category);
    }


}
