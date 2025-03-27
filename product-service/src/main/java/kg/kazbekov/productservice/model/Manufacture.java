package kg.kazbekov.productservice.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "manufacture")
@Data
public class Manufacture {
    @Id
    private String id;

    private String name;

    private List<Model> models;
}
