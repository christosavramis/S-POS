package net.christosav.mpos.services;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class AbstractCrudService<T, ID> {
    private final JpaRepository<T, ID> repository;
    private final JpaSpecificationExecutor<T> jpaSpecificationExecutor;
    public Page<T> pageList(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public List<T> list() {
        return repository.findAll();
    }

    public void delete(T entity) { repository.delete(entity); }

    public T save(T entity) {
        return repository.save(entity);
    }

    public Optional<T> findById(ID id) {
        return repository.findById(id);
    }

}
