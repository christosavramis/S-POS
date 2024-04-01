package net.christosav.mpos.repository;

import net.christosav.mpos.data.ImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ImageRepository extends
        JpaRepository<ImageEntity, Long>,
        JpaSpecificationExecutor<ImageEntity> {
}