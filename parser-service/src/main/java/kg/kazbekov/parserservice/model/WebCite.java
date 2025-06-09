package kg.kazbekov.parserservice.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "webcite")
@Data
public class WebCite {
    @Id
    private String id;
    private WebCiteName name;
    private List<String> urls;

    public enum WebCiteName{
        MY_PHONE, O_STORE, SULPAK
    }
}
