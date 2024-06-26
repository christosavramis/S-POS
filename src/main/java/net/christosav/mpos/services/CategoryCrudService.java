package net.christosav.mpos.services;

import net.christosav.mpos.data.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Service;

@Service
public class CategoryCrudService extends AbstractCrudService<Category, Long> {
    public CategoryCrudService(JpaRepository<Category, Long> repository, JpaSpecificationExecutor<Category> jpaSpecificationExecutor) {
        super(repository, jpaSpecificationExecutor);
    }

}
