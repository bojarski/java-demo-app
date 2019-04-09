package pl.bojarski.demoapp.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import pl.bojarski.demoapp.DemoappApplicationTests;
import pl.bojarski.demoapp.domain.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpMethod.DELETE;
import static org.springframework.http.HttpMethod.PUT;

public class ProductEndpointTest extends DemoappApplicationTests {

    @Autowired
    ProductFacade productFacade;

    @Test
    public void shouldCreateProduct() {
        //given
        final String url = "http://localhost:" + port + "/api/v1/products";
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
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    public void shouldGetAllProducts() {
        //given
        ProductRequestDto requestDtoFirst = new ProductRequestDto("product1");
        ProductRequestDto requestDtoSecond = new ProductRequestDto("product2");

        ProductResponseDto existingProductFirst = productFacade.create(requestDtoFirst);
        ProductResponseDto existingProductSecond = productFacade.create(requestDtoSecond);

        final String url = "http://localhost:" + port + "/api/v1/products";

        //when
        ResponseEntity<ProductsResponseDto> result = httpClient.getForEntity(url,
                ProductsResponseDto.class);

        //then
        ProductsResponseDto productsResponseDto = result.getBody();
        assertThat(result.getStatusCodeValue()).isEqualTo(200);
        assertThat(productsResponseDto.getProducts().size()).isEqualTo(2);
        assertThat(productsResponseDto.getProducts().get(0)).isEqualToComparingFieldByFieldRecursively(existingProductFirst);
        assertThat(productsResponseDto.getProducts().get(1)).isEqualToComparingFieldByFieldRecursively(existingProductSecond);
    }


    @Test
    public void shouldGetExistProduct() {
        //given
        ProductRequestDto requestDto = new ProductRequestDto("product");
        ProductResponseDto existingProduct = productFacade.create(requestDto);
        final String url = "http://localhost:" + port + "/api/v1/products/" + existingProduct.getId();

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
        final String url = "http://localhost:" + port + "/api/v1/products/" + -1;

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

        final ProductUpdateRequestDto productUpdateRequestDto = new ProductUpdateRequestDto(existingProduct.getId(), "samsung");
        String productJson = mapToJson(productUpdateRequestDto);

        final String url = "http://localhost:" + port + "/api/v1/products";

        //when
        ResponseEntity<ProductResponseDto> response = httpClient.exchange(url, PUT,
                getHttpRequest(productJson), ProductResponseDto.class);

        //then
        ProductResponseDto updatedProduct = productFacade.findById(existingProduct.getId());

        assertThat(response.getStatusCodeValue()).isEqualTo(200);
        assertThat(updatedProduct.getName()).isEqualTo(productUpdateRequestDto.getName());
    }

    @Test
    public void shouldDeleteProduct() {
        //given
        ProductRequestDto requestDto = new ProductRequestDto("iphone");
        ProductResponseDto existingProduct = productFacade.create(requestDto);

        final String deleteUrl = "http://localhost:" + port + "/api/v1/products/" + existingProduct.getId();
        final String getUrl = "http://localhost:" + port + "/api/v1/products/" + existingProduct.getId();

        //when
        ResponseEntity<ProductResponseDto> responseDelete = httpClient.exchange(deleteUrl, DELETE,
                null, ProductResponseDto.class);
        ResponseEntity<String> getResponse = httpClient.getForEntity(getUrl, String.class);

        //then
        assertThat(responseDelete.getStatusCodeValue()).isEqualTo(200);
        assertThat(getResponse.getStatusCodeValue()).isEqualTo(404);

    }

    private String mapToJson(Object requestDto) {
        try {
            return objectMapper.writeValueAsString(requestDto);
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
