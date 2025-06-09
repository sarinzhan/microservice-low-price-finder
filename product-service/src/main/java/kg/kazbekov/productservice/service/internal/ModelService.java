package kg.kazbekov.productservice.service.internal;

import kg.kazbekov.productservice.model.Model;
import reactor.core.publisher.Flux;

public interface ModelService extends BaseService<Model,String>{
    Flux<Model> findByManufactureId(String manufactureId);
}
