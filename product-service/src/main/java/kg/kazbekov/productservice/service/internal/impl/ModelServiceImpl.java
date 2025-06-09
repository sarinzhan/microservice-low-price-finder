package kg.kazbekov.productservice.service.internal.impl;

import kg.kazbekov.productservice.model.Model;
import kg.kazbekov.productservice.repository.ModelRepository;
import kg.kazbekov.productservice.service.internal.ManufactureService;
import kg.kazbekov.productservice.service.internal.ModelService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
@RequiredArgsConstructor
public class ModelServiceImpl extends BaseServiceImpl<Model, String> implements ModelService {

    private final ModelRepository modelRepository;
    private final ManufactureService manufactureService;

    @Override
    public Flux<Model> findByManufactureId(String manufactureId) {
        return modelRepository.findByManufactureId(manufactureId);
    }


}
