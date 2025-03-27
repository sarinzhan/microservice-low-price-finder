package kg.kazbekov.productservice.model;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "product")
@Data
public class Product {
    private Model model;
    private Long providerId;
    private Integer price;
    private LocalDateTime lastUpdateTime;
}
