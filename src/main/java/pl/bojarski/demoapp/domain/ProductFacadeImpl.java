package pl.bojarski.demoapp.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.bojarski.demoapp.exceptions.ProductNotFoundException;
import pl.bojarski.demoapp.exceptions.ProductRequestNotValidException;
import pl.bojarski.demoapp.infrastucture.ProductRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static java.util.Objects.isNull;

@Component
class ProductFacadeImpl implements ProductFacade {

    private final ProductRepository productRepository;

    @Autowired
    public ProductFacadeImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public ProductResponseDto create(ProductRequestDto productRequest) {

        if (!productRequest.isValid()) {
            throw new ProductRequestNotValidException(productRequest);
        }

        String id = UUID.randomUUID().toString();
        LocalDateTime createdAt = LocalDateTime.now();
        Product product = new Product.Builder(id,
                productRequest.getName(),
                productRequest.getPrice(),
                productRequest.getDescriptionDto(),
                createdAt)
                .withImage(productRequest.getImageDto())
                .withTags(productRequest.getTags())
                .build();

        productRepository.save(product);

        ProductResponseDto productResponse = new ProductResponseDto.Builder()
                .withId(product.getId())
                .withName(product.getName())
                .withPrice(product.getPrice())
                .withImage(product.getImage())
                .withDescription(product.getDescription())
                .withTags(product.getTags())
                .build();

        return productResponse;
    }

    @Override
    public ProductsResponseDto findAll() {
        List<Product> products = productRepository.findAll();
        List<ProductResponseDto> productsReponse = new ArrayList<>();

        for (Product product : products) {
            ProductResponseDto productResponseDto = new ProductResponseDto.Builder()
                    .withId(product.getId())
                    .withName(product.getName())
                    .withPrice(product.getPrice())
                    .withImage(product.getImage())
                    .withDescription(product.getDescription())
                    .withTags(product.getTags())
                    .build();

            productsReponse.add(productResponseDto);
        }

        return new ProductsResponseDto(productsReponse);
    }

    @Override
    public ProductResponseDto findById(String id) {
        Product product = findProductById(id);

        return new ProductResponseDto.Builder()
                .withId(product.getId())
                .withName(product.getName())
                .withPrice(product.getPrice())
                .withImage(product.getImage())
                .withDescription(product.getDescription())
                .withTags(product.getTags())
                .build();
    }

    @Override
    public ProductResponseDto update(ProductUpdateRequestDto productUpdateRequestDto) {

        if (!productUpdateRequestDto.isValid()) {
            throw new ProductRequestNotValidException(productUpdateRequestDto);
        }

        Product product = findProductById(productUpdateRequestDto.getId());
        Product updatedProduct = new Product.Builder(productUpdateRequestDto.getId(),
                productUpdateRequestDto.getName(),
                productUpdateRequestDto.getPrice(),
                productUpdateRequestDto.getDescriptionDto(),
                product.getCreatedAt())
                .withImage(productUpdateRequestDto.getImageDto())
                .withTags(productUpdateRequestDto.getTags())
                .build();

        productRepository.save(updatedProduct);

        return new ProductResponseDto.Builder()
                .withId(updatedProduct.getId())
                .withName(updatedProduct.getName())
                .withPrice(updatedProduct.getPrice())
                .withImage(updatedProduct.getImage())
                .withDescription(updatedProduct.getDescription())
                .withTags(updatedProduct.getTags())
                .build();
    }

    @Override
    public void delete(String id) {
        productRepository.remove(id);
    }

    private Product findProductById(String id) {
        Product product = productRepository.findById(id);

        if (isNull(product)) {
            throw new ProductNotFoundException(id);
        }

        return product;
    }
}
