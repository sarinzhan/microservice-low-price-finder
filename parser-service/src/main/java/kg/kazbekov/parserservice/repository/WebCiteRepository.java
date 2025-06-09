package kg.kazbekov.parserservice.repository;

import kg.kazbekov.parserservice.model.WebCite;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

public interface WebCiteRepository extends ReactiveMongoRepository<WebCite, String> {
    @Query("{name: ?0}")
    Flux<WebCite> getByWebCiteName(WebCite.WebCiteName name);
}
