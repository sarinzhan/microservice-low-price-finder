package kg.kazbekov.parserservice.parser;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ParsedCardDto {
    public String manufacture;
    public String model;
    private Integer ram;
    private Integer storage;
    private Integer price;
    private String url;
}
