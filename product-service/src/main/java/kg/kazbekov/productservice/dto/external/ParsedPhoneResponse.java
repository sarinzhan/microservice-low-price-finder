package kg.kazbekov.productservice.dto.external;

import lombok.Data;

@Data
public class ParsedPhoneResponse {
    public String manufacture;
    public String model;
    private Integer ram;
    private Integer storage;
    private Integer price;
    private String url;
}
