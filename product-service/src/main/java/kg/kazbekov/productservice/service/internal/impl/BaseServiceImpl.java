package kg.kazbekov.productservice.service.internal.impl;

import kg.kazbekov.productservice.service.internal.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public abstract class BaseServiceImpl<T, L> implements BaseService<T,L> {

    @Autowired
    private ReactiveMongoRepository<T, L> repository;

    @Override
    public Mono<T> findByid(L id) {
        return repository.findById(id);
    }

    @Override
    public Mono<T> save(T entity) {
        return repository.save(entity);
    }

    @Override
    public Flux<T> getAll() {
        return repository.findAll();
    }
}
