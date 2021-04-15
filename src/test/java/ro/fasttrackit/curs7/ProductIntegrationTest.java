package ro.fasttrackit.curs7;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.diff.JsonDiff;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import ro.fasttrackit.curs7.domain.Product;

import java.util.List;

public class ProductIntegrationTest {
    public static final String BASE_URL = "http://localhost:8080/products";

    @Test
    void testGetAll() {
        restTemplate().exchange(
                BASE_URL,
                HttpMethod.GET,
                new HttpEntity<>(null),
                new ParameterizedTypeReference<List<Product>>() {
                }
        ).getBody()
                .forEach(System.out::println);

    }

    @Test
    void testPostNew() {
        for (int i = 0; i < 50; i++) {
            Product response = restTemplate().postForObject(
                    BASE_URL,
                    new Product("Monitor-" + i, "description " + i),
                    Product.class);
            System.out.println(response);
        }
    }

    @Test
    void testPut() {
        String url = UriComponentsBuilder.fromHttpUrl(BASE_URL)
                .path("/2")
                .toUriString();

        Product request = new Product("new-name4", "");
        request.setShopId("altceva");
        restTemplate().put(url, request);
    }

    @SneakyThrows
    @Test
    void testPatch() {
        String url = UriComponentsBuilder.fromHttpUrl(BASE_URL)
                .path("/2")
                .toUriString();
        ObjectMapper mapper = new ObjectMapper();
        JsonPatch patch = JsonPatch.fromJson(mapper.readTree(
                """
                            [
                                 { "op": "replace", "path": "/description", "value": "new-description" },
                                 { "op": "replace", "path": "/name", "value": "another"},
                                 { "op": "replace", "path": "/id", "value": 111}
                            ]   
                        """
        ));
        Product product = restTemplate().patchForObject(url,
                patch,
                Product.class);

        Product product2 = new Product("abv", product.getDescription());
        JsonNode jsonNode = JsonDiff.asJson(mapper.valueToTree(product), mapper.valueToTree(product2));
        System.out.println(jsonNode);
    }

    private RestTemplate restTemplate() {
        return new RestTemplate(new HttpComponentsClientHttpRequestFactory());
    }
}
