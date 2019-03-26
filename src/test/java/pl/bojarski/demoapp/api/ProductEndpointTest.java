package pl.bojarski.demoapp.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import pl.bojarski.demoapp.DemoappApplicationTests;
import pl.bojarski.demoapp.domain.ProductFacade;
import pl.bojarski.demoapp.domain.ProductRequestDto;
import pl.bojarski.demoapp.domain.ProductResponseDto;

import static org.assertj.core.api.Assertions.assertThat;

public class ProductEndpointTest extends DemoappApplicationTests {

    @Autowired
    ProductFacade productFacade;

    @Test
    public void shouldCreateProduct() {
        //given
        final String url = "http://localhost:" + port + "/products";
        final ProductRequestDto product = new ProductRequestDto("iphone");
        String productJson = mapToJson(product);

        //when
        ResponseEntity<ProductResponseDto> result = httpClient.postForEntity(url,
                getHttpRequest(productJson), ProductResponseDto.class);

        //then
        assertThat(result.getStatusCodeValue()).isEqualTo(200);
        assertThat(result.getBody().getName()).isEqualTo("iphone");
    }

    @Test
    public void shouldGetExistProduct() {
        //given
        ProductRequestDto requestDto = new ProductRequestDto("product");
        ProductResponseDto existingProduct = productFacade.create(requestDto);
        final String url = "http://localhost:" + port + "/products/" + existingProduct.getId();

        //when
        ResponseEntity<ProductResponseDto> result = httpClient.getForEntity(url,
                ProductResponseDto.class);

        //then
        assertThat(result.getStatusCodeValue()).isEqualTo(200);
        assertThat(result.getBody()).isEqualToComparingFieldByFieldRecursively(existingProduct);
    }

    @Test
    public void shouldGetNotExistProduct() {
        //given
        final String url = "http://localhost:" + port + "/products/" + -1;

        //when
        ResponseEntity<String> result = httpClient.getForEntity(url, String.class);

        //then
        assertThat(result.getStatusCodeValue()).isEqualTo(404);
    }

    @Test
    public void shouldUpdateProduct() {
        //given
        ProductRequestDto requestDto = new ProductRequestDto("iphone");
        ProductResponseDto existingProduct = productFacade.create(requestDto);

        final ProductRequestDto updatedRequestDto = new ProductRequestDto("samsung");
        String productJson = mapToJson(updatedRequestDto);

        final String url = "http://localhost:" + port + "/products/" + existingProduct.getId();

        //when
        ResponseEntity<ProductResponseDto> result = httpClient.postForEntity(url,
                getHttpRequest(productJson), ProductResponseDto.class);

        //then
        ProductResponseDto updatedProduct = productFacade.findById(existingProduct.getId());

        assertThat(result.getStatusCodeValue()).isEqualTo(200);
        assertThat(updatedProduct.getName()).isEqualTo(updatedRequestDto.getName());
    }

    @Test
    public void shouldDeleteProduct() {
        //given
        ProductRequestDto requestDto = new ProductRequestDto("iphone");
        ProductResponseDto existingProduct = productFacade.create(requestDto);

        final ProductRequestDto updatedProduct = new ProductRequestDto("samsung");
        String productJson = mapToJson(updatedProduct);

        final String url = "http://localhost:" + port + "/products/" + existingProduct.getId();

        //when
        ResponseEntity<ProductResponseDto> result = httpClient.postForEntity(url,
                getHttpRequest(productJson), ProductResponseDto.class);

        //then
        assertThat(result.getStatusCodeValue()).isEqualTo(200);
    }

    private String mapToJson(ProductRequestDto productRequestDto) {
        try {
            return objectMapper.writeValueAsString(productRequestDto);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private HttpEntity<String> getHttpRequest(String json) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("content-type", "application/json");
        return new HttpEntity<>(json, httpHeaders);
    }
}
