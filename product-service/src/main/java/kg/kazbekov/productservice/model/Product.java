package kg.kazbekov.productservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "product")
@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class Product {
    @Id
    private String id;
    private String modelId;
    private Long providerId;
    private String url;
    private Integer price;
    private Integer ram;
    private Integer storage;

    private LocalDateTime lastUpdateTime;
}
