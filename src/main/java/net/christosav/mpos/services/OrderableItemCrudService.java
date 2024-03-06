package net.christosav.mpos.services;

import net.christosav.mpos.data.OrderableItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Service;
@Service
public class OrderableItemCrudService extends AbstractCrudService<OrderableItem, Long> {
    public OrderableItemCrudService(JpaRepository<OrderableItem, Long> repository, JpaSpecificationExecutor<OrderableItem> jpaSpecificationExecutor) {
        super(repository, jpaSpecificationExecutor);
    }
}
