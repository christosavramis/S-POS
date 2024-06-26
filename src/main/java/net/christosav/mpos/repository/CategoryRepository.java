package net.christosav.mpos.repository;

import net.christosav.mpos.data.Category;
import net.christosav.mpos.data.ImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CategoryRepository extends
        JpaRepository<Category, Long>,
        JpaSpecificationExecutor<Category> {
}