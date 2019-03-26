package pl.bojarski.demoapp.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.bojarski.demoapp.infrastucture.ProductRepository;

import java.time.LocalDateTime;
import java.util.UUID;

import static java.util.Objects.isNull;

@Component
public class ProductFacadeImpl implements ProductFacade {

    private final ProductRepository productRepository;

    @Autowired
    public ProductFacadeImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public ProductResponseDto create(ProductRequestDto productRequest) {

        if (!productRequest.isValid()) {
            throw new RuntimeException("Product name cannot be empty!");
        }

        String id = UUID.randomUUID().toString();
        LocalDateTime createdAt = LocalDateTime.now();
        Product product = new Product(id, productRequest.getName(), createdAt);

        productRepository.save(product);

        ProductResponseDto productResponse = new ProductResponseDto(
                product.getId(),
                product.getName()
        );

        return productResponse;
    }

    @Override
    public ProductResponseDto findById(String id) {
        Product product = findProductById(id);

        return new ProductResponseDto(product.getId(), product.getName());
    }

    @Override
    public ProductResponseDto update(String id, ProductRequestDto productRequest) {
        Product product = findProductById(id);
        Product updatedProduct = product.name(productRequest.getName());

        productRepository.save(updatedProduct );

        return new ProductResponseDto(updatedProduct.getId(), updatedProduct.getName());
    }

    @Override
    public ProductResponseDto delete(String id) {
        return null;
    }

    private Product findProductById(String id) {
        Product product = productRepository.findById(id);

        if (isNull(product)) {
            throw new NullPointerException("Product does not exist where id = " + id);
        }

        return product;
    }
}
