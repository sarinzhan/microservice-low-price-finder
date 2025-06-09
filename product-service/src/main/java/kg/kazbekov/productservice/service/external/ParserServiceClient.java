package kg.kazbekov.productservice.service.external;

import kg.kazbekov.productservice.dto.external.ParsedPhoneResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
public class ParserServiceClient {

    @Value("${service.external.parser.url}")
    private String url;

    public List<ParsedPhoneResponse> getPhones(){
        RestTemplate restTemplate = new RestTemplate();
        var response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<ParsedPhoneResponse>>() {
                }
        );
        return response.getBody();
    }
}
