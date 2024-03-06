package net.christosav.mpos.services;

import net.christosav.mpos.data.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Service;

@Service
public class OrderCrudService extends AbstractCrudService<Order, Long> {
    public OrderCrudService(JpaRepository<Order, Long> repository, JpaSpecificationExecutor<Order> jpaSpecificationExecutor) {
        super(repository, jpaSpecificationExecutor);
    }

}
