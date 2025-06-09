package kg.kazbekov.productservice.service.internal;

import kg.kazbekov.productservice.dto.external.ParsedPhoneResponse;
import kg.kazbekov.productservice.model.Product;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public interface ProductService extends BaseService<Product, String>{
    void addAll(List<ParsedPhoneResponse> parsedPhoneResponses);
    Flux<Product> getAllByModelId(String modelId);
}
