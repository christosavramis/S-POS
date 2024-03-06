package net.christosav.mpos.repository;

import net.christosav.mpos.data.OrderableItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface OrderableItemRepository extends
        JpaRepository<OrderableItem, Long>,
        JpaSpecificationExecutor<OrderableItem> {
}