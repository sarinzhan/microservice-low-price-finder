package kg.kazbekov.productservice.service.internal;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface BaseService<T,L> {
    Mono<T> findByid(L id);
    Mono<T> save(T entity);
    Flux<T> getAll();
//    T update(T entity);
}
