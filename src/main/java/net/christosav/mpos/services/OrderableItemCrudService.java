package net.christosav.mpos.services;

import net.christosav.mpos.data.Category;
import net.christosav.mpos.data.OrderableItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderableItemCrudService extends AbstractCrudService<OrderableItem, Long> {
    private final JpaRepository<OrderableItem, Long> repository;

    public OrderableItemCrudService(JpaRepository<OrderableItem, Long> repository, JpaSpecificationExecutor<OrderableItem> jpaSpecificationExecutor) {
        super(repository, jpaSpecificationExecutor);
        this.repository = repository;
    }

    public List<OrderableItem> findByCategory(Category category) {
        return repository.findAll().stream().filter(orderableItem -> orderableItem.getCategory().equals(category)).toList();
    }
}
