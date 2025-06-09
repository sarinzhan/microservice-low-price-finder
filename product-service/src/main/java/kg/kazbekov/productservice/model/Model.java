package kg.kazbekov.productservice.model;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "model")
@Data
public class Model {
    @Id
    private String id;

    private String name;

//    private ObjectId manufactureId;
    //      индексация быстрее,
    //		сравнения быстрее,
    //		хранение эффективнее.
    // 12 byte vs 24 byte
    // Некоторые Mongo-функции требуют именно ObjectId ( lookup, graphLookup, match)

    private String manufactureId;

    public Model(String name, String manufactureId) {
        this.name = name;
        this.manufactureId = manufactureId;
    }

    //    private Integer ram;
//    private Integer internalMemory;
}
