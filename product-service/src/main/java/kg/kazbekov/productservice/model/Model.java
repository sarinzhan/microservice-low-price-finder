package kg.kazbekov.productservice.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "model")
@Data
public class Model {
    @Id
    private String id;

    private String name;

    private Integer ram;
    private Integer internalMemory;
}
